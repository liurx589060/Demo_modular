package com.lrx.lib.core;


import com.lrx.lib.utils.LogUtil;

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
