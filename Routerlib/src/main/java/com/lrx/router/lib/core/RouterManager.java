package com.lrx.router.lib.core;

import android.content.Context;

import com.lrx.router.lib.interfaces.NativeDexCallback;
import com.lrx.router.lib.interfaces.RegisterPluginCallback;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/2/1.
 */

public class RouterManager {
    private Map<String,Router> routerMap;
    private boolean isConvertNull;
    private Context context;
    private boolean isInit;

    public void init(Context context) {
        this.context = context;
        this.isInit = true;
    }

    private void checkContext() {
        if(context == null) {
            throw new RouterException("please init first,invoke the RouterManager.getInstance().init(Context context)" +
                    ",use the applicationContext,suggest init at the application onCreate()");
        }
    }

    public RouterManager() {
        routerMap = new HashMap();
    }

    public boolean isConvertNull() {
        return isConvertNull;
    }

    public void setConvertNull(boolean convertNull) {
        isConvertNull = convertNull;
    }

    public Context getContext() {
        return context;
    }

    public boolean isInit() {
        return isInit;
    }

    public Map<String, Router> getRouterMap() {
        return routerMap;
    }

    private static class RouterManagerHolder {
        public static RouterManager instance = new RouterManager();
    }

    public static RouterManager getInstance() {
        return RouterManagerHolder.instance;
    }

    public <T extends Router> T getRouter(Class<T> clz) {
        checkContext();
        checkRegisterStatus();
        T resultClass = (T) routerMap.get(clz.getName());
        if(!isConvertNull) {
            //return the true proxy,don't convert the proxy
            return resultClass;
        }

        if(resultClass == null) {
            try {
                resultClass = clz.newInstance();
            } catch (Exception e) {
                throw new RouterException(e.toString() + "--getRouter exception,please check the params of Class<T>");
            }
        }
        return resultClass;
    }

    private void checkRegisterStatus() {
        if(routerMap == null) {
            throw new RouterException("please Initialize the RouterManager");
        }
    }

    public boolean registerRouter(Router router) {
        return registerRouter(router,true);
    }

    public boolean registerRouterByPlugin(Router router,RegisterPluginCallback registerPluginCallback) {
        return registerRouterByPlugin(router,true,registerPluginCallback);
    }

    /**
     * register the router
     * @param router
     * @param isConvertNull if is false,return the true proxy(maybe is null)
     *                     if is true,will return the notNull proxy(will convert the null proxy)
     * @return
     */
    public boolean registerRouter(Router router,boolean isConvertNull) {
        checkContext();
        return ReflectCore.register(router,"createProxy",isConvertNull,null);
    }

    /**
     * register the router by plugin apk,jar,dex
     * @param router
     * @param isConvertNull if is false,return the true proxy(maybe is null)
     *                     if is true,will return the notNull proxy(will convert the null proxy)
     * @return
     */
    public boolean registerRouterByPlugin(Router router, boolean isConvertNull, RegisterPluginCallback registerPluginCallback) {
        checkContext();
        return ReflectCore.register(router,"createPluginDexProxy",isConvertNull,registerPluginCallback);
    }

    /**
     * can use impClassName to create instance
     * @param impClassName
     * @return
     */
    public static Object create(String impClassName) {
        return ReflectCore.create(impClassName);
    }

    /**
     * can use impClassName to create instance by plugin apk,jar,dex
     * @param context
     * @param dexFilePath
     * @param impClassName
     * @param callback
     * @return
     */
    public static void createNativeDex(final Context context, final String dexFilePath, final String impClassName, final NativeDexCallback callback) {
       ReflectCore.createNativeDex(context,dexFilePath,impClassName,callback);
    }
}
