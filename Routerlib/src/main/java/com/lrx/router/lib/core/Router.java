package com.lrx.router.lib.core;


import android.content.Context;
import android.os.Handler;

import com.lrx.router.lib.interfaces.NativeDexCallback;
import com.lrx.router.lib.utils.LogUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import dalvik.system.DexClassLoader;

/**
 * Created by daven.liu on 2018/1/31 0031.
 */

public abstract class Router<T> {
    protected boolean isCreatedSuccess;
    private T proxy;
    private T errorProxy;
    private boolean isAvailable = true;
    private boolean isConvertNull;

    public abstract String getImpClassName();
    protected abstract T getErrorProxyClass();

    /**
     * 创建proxy
     */
    private void createProxy() {
        proxy = create(getImpClassName());
        if(proxy != null) {
            isCreatedSuccess = true;
            LogUtil.i("proxy class--" + proxy.getClass().getName());
        }
        LogUtil.i("createProxy--errorImp=" + isCreatedSuccess());
    }

    public T getProxy() {
        if(!isConvertNull) {
            //return the true proxy,don't convert the proxy
            return proxy;
        }

        if(!isAvailable) {
            if(errorProxy == null) {
                errorProxy = getErrorProxyClass();
            }
            return errorProxy;
        }else {
            if(proxy == null) {
                proxy = getErrorProxyClass();
            }
            return proxy;
        }
    }

    public boolean isCreatedSuccess() {
        return isCreatedSuccess;
    }

    public  static <T> T create(final String impClassName) {
        if(impClassName == null) {
            throw new RouterException("Router to create impClass excepiton,impClass is null," +
                    "please return valid imp class name from method getImpClassName");
        }

        T result = null;
        if(isClassPresent(impClassName)) {
            try{
                Class cls = Class.forName(impClassName);
                return (T) cls.newInstance();
            }catch (Exception e) {
                LogUtil.e(e.toString());
                result = null;
            }
        }
        return result;
    }

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
                                callback.onResult(null,dexPath,impClassName,"con't find the dex---" + dexFilePath);
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
                LogUtil.i("dexPath:" + dexPath + "   " +
                        "dexOutputDir:" + dexOutputDir + "   " +
                        "libPath:" + libPath);

                try {
                    Class clz = dcLoader.loadClass(impClassName);
                    LogUtil.d("yy",impClassName + "--find this class and load the class");
                    final Object result = clz.newInstance();
                    //create unInstall apk resource
                    if(dexPath.endsWith("apk")) {
                        PluginResourceLoader.getInstance().createAssetManager(context,dexPath);
                    }
                    if(callback != null) {
                        new Handler(context.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onResult(result,dexPath,impClassName,"success");
                            }
                        });
                    }
                } catch (final Exception e) {
                    e.printStackTrace();
                    LogUtil.e(e.toString());
                    if(callback != null) {
                        new Handler(context.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onResult(null,dexPath,impClassName,e.toString());
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

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public boolean isConvertNull() {
        return isConvertNull;
    }

    public void setConvertNull(boolean convertNull) {
        isConvertNull = convertNull;
    }
}
