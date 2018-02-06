package com.lrx.router.lib.core;


import android.content.Context;

import com.lrx.router.lib.utils.LogUtil;

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

    public static Object createNativeDex(Context context,String dexPath,String impClassName) {
        //由于dex文件是包含在apk或者jar文件中的,所以在加载class之前就需要先将dex文件解压出来，dexOutputDir为解压路径
        String dexOutputDir = context.getApplicationInfo().dataDir;
        //目标类可能使用的c或者c++的库文件的存放路径
        String libPath = context.getApplicationInfo().nativeLibraryDir;
        DexClassLoader dcLoader = new DexClassLoader(dexPath,dexOutputDir,null,context.getClassLoader());
        LogUtil.i("dexPath:" + dexPath + "   " +
                "dexOutputDir:" + dexOutputDir + "   " +
                "libPath:" + libPath);

        try {
            Class clz = dcLoader.loadClass(impClassName);
            LogUtil.d("yy",impClassName + "--find this class and load the class");
            return clz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e(e.toString());
            throw new RouterException(e.toString() + "--" + "the dex path is not valid or the class is not correct");
        }

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
