package com.lrx.router.lib.core;

import android.content.Context;
import android.os.Handler;

import com.lrx.router.lib.interfaces.NativeDexCallback;
import com.lrx.router.lib.interfaces.RegisterPluginCallback;
import com.lrx.router.lib.utils.ConstantUtil;
import com.lrx.router.lib.utils.LogUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

/**
 * Created by daven.liu on 2018/2/9 0009.
 */

public class ReflectCore {
    /**
     * check the class can find or not
     * @param name
     * @return
     */
    public static boolean isClassPresent(String name) {
        try {
            Thread.currentThread().getContextClassLoader().loadClass(name);
            return true;
        } catch (ClassNotFoundException e) {
            LogUtil.e(e.toString());
            return false;
        }
    }

    /**
     * maybe need to copy file ,so must use asynchronous thread for return the result by callback
     * @param context
     * @param dexFilePath
     * @param impClassName
     * @return
     */
    public static void createNativeDex(final Context context, final String dexFilePath, final String impClassName, final NativeDexCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final String dexPath = copyDexToFilefromAssert(context,dexFilePath);
                if(!new File(dexPath).exists()) {
                    if(callback != null) {
                        new Handler(context.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onResult(null,dexPath,impClassName, ConstantUtil.ERROR_PLUGIN_DEXPATH_INVALID,"con't find the dex---" + dexFilePath);
                            }
                        });
                    }
                    return;
                }
                //由于dex文件是包含在apk或者jar文件中的,所以在加载class之前就需要先将dex文件解压出来，dexOutputDir为解压路径
                String dexOutputDir = context.getDir("dex",Context.MODE_PRIVATE).getPath();
                //目标类可能使用的c或者c++的库文件的存放路径
                String libPath = context.getApplicationInfo().nativeLibraryDir;
                DexClassLoader dcLoader = new DexClassLoader(dexPath,dexOutputDir,libPath,context.getClassLoader());
                LogUtil.i("dexPath: " + dexPath + "\n" +
                        "dexOutputDir: " + dexOutputDir + "\n" +
                        "libPath: " + libPath + "\n" +
                        "impClass: " + impClassName);

                try {
                    Class clz = dcLoader.loadClass(impClassName);
                    LogUtil.d("yy",impClassName + "--find this class and load the class");
                    final Object result = clz.newInstance();
                    //create unInstall apk resource
                    if(dexPath.endsWith("apk")) {
                        PluginResourceLoader.getInstance().createAssetManager(context,dexPath,dexFilePath);
                    }
                    if(callback != null) {
                        new Handler(context.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onResult(result,dexPath,impClassName,ConstantUtil.SUCCESS,"success");
                            }
                        });
                    }
                }catch (final ClassNotFoundException e1) {
                    e1.printStackTrace();
                    LogUtil.e(e1.toString());
                    if(callback != null) {
                        new Handler(context.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onResult(null,dexPath,impClassName,ConstantUtil.ERROR_PLUGIN_CLASS_CANNOT_FIND,e1.toString());
                            }
                        });
                    }
                }catch (final Exception e) {
                    e.printStackTrace();
                    LogUtil.e(e.toString());
                    if(callback != null) {
                        new Handler(context.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onResult(null,dexPath,impClassName,ConstantUtil.ERROR_UNKOWN,e.toString());
                            }
                        });
                    }
                }
            }
        };
        new Thread(runnable).start();
    }

    private static String copyDexToFilefromAssert(Context context,String dexPath) {
        String preSuffix = "file://";
        if(dexPath.startsWith(preSuffix)) {
            try {
                String filename = dexPath.substring(preSuffix.length());
                File file = new File(context.getApplicationInfo().dataDir + File.separator + filename);
                LogUtil.d("yy",file.getPath());
                if (file.exists()) {
                    return file.getPath();
                }
                LogUtil.d("copyDexToFilefromAssert--" + filename);
                InputStream inputStream = context.getAssets().open(filename);
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = inputStream.read(buffer)) != -1) {
                    fileOutputStream.write(buffer,0,len);
                }
                fileOutputStream.flush();
                inputStream.close();
                fileOutputStream.close();
                return file.getPath();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                LogUtil.e(e.toString());
                return dexPath;
            }
        }
        //原样返回
        return dexPath;
    }

    public static Object create(final String impClassName) {
        if(impClassName == null) {
            throw new RouterException("Router to create impClass excepiton,impClass is null," +
                    "please return valid imp class name from method getImpClassName");
        }

        Object result = null;
        if(isClassPresent(impClassName)) {
            try{
                Class cls = Class.forName(impClassName);
                return cls.newInstance();
            }catch (Exception e) {
                LogUtil.e(e.toString());
                result = null;
            }
        }
        return result;
    }

    public static boolean register(Router router, String methodName, boolean isConvertNull, RegisterPluginCallback registerPluginCallback) {
        RouterManager.getInstance().setConvertNull(isConvertNull);
        Method method = findMethod(router.getClass(),methodName);
        if(method != null) {
            toInvoke(router,method,registerPluginCallback);
        }
        if(router != null) {
            router.setConvertNull(isConvertNull);
        }
        return router.isCreatedSuccess();
    }

    private static Method findMethod(Class clz,String methodName) {
        if(clz != null) {
            if(clz.getName().equals("java.lang.Object")) {
                throw new RouterException("registerRouter fail,please check register router class,must use class extends Router");
            }
            try {
                Method method = null;
                if(methodName.equals("createProxy")) {
                    method = clz.getDeclaredMethod(methodName);
                }else if (methodName.equals("createPluginDexProxy")){
                    method = clz.getDeclaredMethod(methodName,RegisterPluginCallback.class);
                }
                return method;
            }catch (NoSuchMethodException e) {
                return findMethod(clz.getSuperclass(),methodName);
            }catch (Exception e1) {
                throw new RouterException("registerRouter fail,please check register router class,must use class extends Router");
            }
        }else {
            throw new RouterException("registerRouter the routerKey is null,please enter valid key");
        }
    }

    private static void toInvoke(Router router,Method method,RegisterPluginCallback registerPluginCallback) {
        try {
            method.setAccessible(true);//设置不做安全检查，这样才能访问private属性
            if(method.getName().equals("createProxy")) {
                method.invoke(router);
            }else if (method.getName().equals("createPluginDexProxy")) {
                method.invoke(router,registerPluginCallback);
            }
            RouterManager.getInstance().getRouterMap().put(router.getClass().getName(),router);
        }catch (Exception e) {
            e.printStackTrace();
            throw new RouterException(e.toString() + "---please use class extends Router");
        }
    }
}
