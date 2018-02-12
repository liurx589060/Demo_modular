package com.lrx.router.lib.core;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.lrx.router.lib.activitys.ActivityStub;
import com.lrx.router.lib.interfaces.NativeDexCallback;
import com.lrx.router.lib.interfaces.RegisterPluginCallback;
import com.lrx.router.lib.utils.ConstantUtil;
import com.lrx.router.lib.utils.LogUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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
     * @param router
     * @return
     */
    public static void createNativeDex(final Context context, final Router router, final NativeDexCallback callback) {
        if(router.isSync()) {
            loadNativeDex(context,router,callback);
        }else {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    loadNativeDex(context,router,callback);
                }
            };
            new Thread(runnable).start();
        }
    }

    private static void loadNativeDex(Context context, final Router router, final NativeDexCallback callback) {
        final String dexPath = copyDexToFilefromDexPath(context,router.getPluginDexPath());
        if(dexPath == null || !new File(dexPath).exists()) {
            LogUtil.i("con't find the dex form the dexFilePath--" + router.getPluginDexPath());
            if(callback != null) {
                new Handler(context.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onResult(null,dexPath,router.getImpClassName()
                                , ConstantUtil.ERROR_PLUGIN_DEXPATH_INVALID,"con't find the dex---" + router.getPluginDexPath());
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
                "impClass: " + router.getImpClassName());
        RouterManager.getInstance().getClassLoaderMap().put(router.getClass(),dcLoader);

        try {
            Class clz = dcLoader.loadClass(router.getImpClassName());
            LogUtil.i(router.getImpClassName() + "--find this class and load the class");
            final Object result = clz.newInstance();
            //create unInstall apk resource
            if(dexPath.endsWith("apk")) {
                PluginResourceLoader.getInstance().createAssetManager(context,dexPath,router.getPluginDexPath());
            }
            if(callback != null) {
                new Handler(context.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onResult(result,dexPath,router.getImpClassName(),ConstantUtil.SUCCESS,"success");
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
                        callback.onResult(null,dexPath,router.getImpClassName(),ConstantUtil.ERROR_PLUGIN_CLASS_CANNOT_FIND,e1.toString());
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
                        callback.onResult(null,dexPath,router.getImpClassName(),ConstantUtil.ERROR_UNKOWN,e.toString());
                    }
                });
            }
        }
    }

    private static String toCopyFile(InputStream inputStream,File file) throws IOException {
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
    }

    private static String copyDexToFilefromDexPath(Context context,String dexPath) {
        String preSuffix = "file://";
        try{
            if(dexPath.startsWith(preSuffix)) {
                String filename = fileExistInAssets(context,dexPath,preSuffix);
                if(filename == null) {
                    return null;
                }
                File file = new File(context.getApplicationInfo().dataDir + File.separator + filename);
                LogUtil.d("yy",file.getPath());
                if (file.exists()) {
                    return file.getPath();
                }
                if(!file.getParentFile().exists()) {
                    //create parent dir
                    file.getParentFile().mkdirs();
                }
                LogUtil.d("copyDexToFilefromAssert--" + filename);
                return toCopyFile(context.getAssets().open(filename),file);
            }else {
                File originFile = new File(dexPath);
                File desFile = new File(context.getApplicationInfo().dataDir + File.separator + "sdcard" + File.separator + originFile.getName());
                LogUtil.d("yy",desFile.getPath());
                if (desFile.exists()) {
                    return desFile.getPath();
                }
                if(!originFile.exists()) {
                    return null;
                }
                if(!desFile.getParentFile().exists()) {
                    //create parent dir
                    desFile.getParentFile().mkdirs();
                }
                LogUtil.d("copyDexToFilefromSDcard--" + desFile.getPath());
                InputStream inputStream = new FileInputStream(originFile);
                return toCopyFile(inputStream,desFile);
            }
        }catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            LogUtil.e(e.toString());
            return null;
        }
    }

    /**
     * jugde the file exist in assets
     * @param context
     * @param fileFullPath
     * @param preSuffix
     * @return
     */
    private static String fileExistInAssets(Context context,String fileFullPath,String preSuffix) {
        try {
            String fileName = fileFullPath.substring(preSuffix.length());
            String realFileName = fileName;
            int lastIndex = fileName.lastIndexOf("/");
            String filePath = "";
            if(lastIndex != -1) {
               filePath = fileName.substring(0,lastIndex);
               realFileName = fileName.substring(lastIndex + 1,fileName.length());
            }
            String[] names = context.getAssets().list(filePath);
            boolean isExist = false;
            for (String name:names) {
                if(name.equals(realFileName)) {
                    isExist = true;
                    break;
                }
            }
            if(isExist) {
                return fileName;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e(e.toString());
            return null;
        }
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
            RouterManager.getInstance().getRouterMap().put(router.getClass(),router);
        }catch (Exception e) {
            e.printStackTrace();
            throw new RouterException(e.toString() + "---please use class extends Router");
        }
    }

    /**
     * start activity
     * @param activity
     * @param stubClz
     * @param bundle
     */
    public static void startActivity(Activity activity, Class<? extends ActivityStub> stubClz, Bundle bundle) {
        Intent intent = new Intent(activity,stubClz);
        if(bundle != null) {
            intent.putExtras(bundle);
        }
        activity.startActivity(intent);
    }

    /**
     * invoke the ActivityStub private metho -- setPluginActivity
     * @param activityStub
     * @param clz
     */
    public static void invokeSetPluginActivity(ActivityStub activityStub,Class<? extends Activity> clz,Class<? extends Router> routerClz) {
        try {
            Class pClz = null;
            if(isClassPresent(clz.getName())) {
                pClz = Class.forName(clz.getName());
            }else {
                pClz = RouterManager.getInstance().getClassLoaderMap().get(routerClz).loadClass(clz.getName());
            }
            PluginActivity pluginActivity = (PluginActivity) pClz.newInstance();
            Method method = findSetPluginActivityMethod(activityStub.getClass());
            method.setAccessible(true);//设置不做安全检查，这样才能访问private属性
            method.invoke(activityStub,pluginActivity);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e(e.toString());
            throw new RouterException("can't find method setPluginActivity,please check params");
        }
    }

    private static Method findSetPluginActivityMethod(Class clz) {
        if(clz.getName().equals("java.lang.Object")) {
            throw new RouterException("can't find method setPluginActivity,please check params");
        }
        try {
            Method method = clz.getDeclaredMethod("setPluginActivity",PluginActivity.class);
            return method;
        }catch (NoSuchMethodException e) {
            LogUtil.e(e.toString());
            return findSetPluginActivityMethod(clz.getSuperclass());
        }
    }
}
