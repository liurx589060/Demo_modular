package com.lrx.router.lib.core;


import com.lrx.router.lib.interfaces.NativeDexCallback;
import com.lrx.router.lib.interfaces.RegisterPluginCallback;
import com.lrx.router.lib.utils.LogUtil;

/**
 * Created by daven.liu on 2018/1/31 0031.
 */

public abstract class Router<T> {
    private boolean isCreatedSuccess;
    private boolean isLoadComplete;
    private T proxy;
    private T errorProxy;
    private boolean isAvailable = false; //当为false时则调用error或者直接返回null
    private boolean isConvertNull;
    private String pluginDexPath;
    private String impClassName;
    private LoadType loadType = LoadType.LOAD_LIB;
    private boolean isSync = true;// is load the dex synchronize

    protected abstract String getDefaultImpClassName();
    protected abstract T getErrorProxyClass();

    public LoadType getLoadType() {
        return loadType;
    }

    public boolean isSync() {
        return isSync;
    }

    public void setSync(boolean sync) {
        isSync = sync;
    }

    public String getImpClassName() {
        return impClassName==null?getDefaultImpClassName():impClassName;
    }

    public void setImpClassName(String impClassName) {
        this.impClassName = impClassName;
    }

    public enum LoadType {
        LOAD_LIB,LOAD_PLUGIN
    }

    /**
     * 创建proxy
     */
    private void createProxy() {
        try {
            String impName = impClassName==null?getImpClassName():impClassName;
            proxy = (T) ReflectCore.create(impName);
            if(proxy != null) {
                isCreatedSuccess = true;
                isLoadComplete = true;
                isAvailable = true;
            }
            LogUtil.i("createProxy proxy class--" + impName + "---" + isCreatedSuccess);
            loadType = LoadType.LOAD_LIB;
        }catch (Exception e) {
            String msg = "createProxy--" + e.toString();
            LogUtil.e(msg);
            ReflectCore.exceptionMes = msg;
            throw new RouterException(msg);
        }
    }

    private void createPluginDexProxy(final RegisterPluginCallback registerPluginCallback) {
        try {
            if(RouterManager.getInstance().getContext() == null) return;
            if(pluginDexPath == null) throw new RouterException("please set the pluginDexPath");
            ReflectCore.createNativeDex(RouterManager.getInstance().getContext(), this, new NativeDexCallback() {
                @Override
                public void onResult(Object clz, String dexPath, String className, int errorCode,String errorMsg) {
                    if(clz != null) {
                        proxy = (T) clz;
                        isCreatedSuccess = true;
                        isLoadComplete = true;
                        isAvailable = true;
                        LogUtil.i("createPluginDexProxy class--" + proxy.getClass().getName());
                    }else {
                        proxy = null;
                    }
                    LogUtil.i("createPluginDexProxy--errorImp=" + isCreatedSuccess());
                    if(registerPluginCallback != null) {
                        registerPluginCallback.onResult(dexPath,className,errorCode,errorMsg);
                    }
                }
            });
            loadType = LoadType.LOAD_PLUGIN;
        }catch (Exception e) {
            String msg = "createPluginDexProxy--" + e.toString();
            LogUtil.e(msg);
            ReflectCore.exceptionMes = msg;
            throw new RouterException(msg);
        }
    }

    public T getProxy() {
        if(!isConvertNull) {
            //return the true proxy,don't convert the proxy
            return proxy;
        }

        if(!isAvailable) {
            if(errorProxy == null) {
                errorProxy = getErrorProxyClass();
                isLoadComplete = true;
            }
            return errorProxy;
        }else {
            if(proxy == null) {
                proxy = getErrorProxyClass();
                isLoadComplete = true;
            }
            return proxy;
        }
    }

    public boolean isCreatedSuccess() {
        return isCreatedSuccess;
    }

    public boolean isLoadComplete() {
        return isLoadComplete;
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

    public String getPluginDexPath() {
        return pluginDexPath;
    }

    public void setPluginDexPath(String pluginDexPath) {
        this.pluginDexPath = pluginDexPath;
    }

    public void setErrorProxy(T errorProxy) {
        this.errorProxy = errorProxy;
    }
}
