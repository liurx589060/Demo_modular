package com.lrx.router.lib.core;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.lrx.router.lib.activitys.ActivityStub;
import com.lrx.router.lib.interfaces.NativeDexCallback;
import com.lrx.router.lib.interfaces.RegisterPluginCallback;
import com.lrx.router.lib.utils.LogUtil;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import dalvik.system.DexClassLoader;

/**
 * Created by Administrator on 2018/2/1.
 */

public class RouterManager {
    private Map<Class,Router> routerMap;
    private Map<Class,DexClassLoader> classLoaderMap;
    private boolean isConvertNull;
    private Context context;
    private boolean isInit;
    private Class<? extends Activity> pluginClz;
    private Class<? extends Router> routerClz;

    public void init(Application application) {
        this.context = application.getApplicationContext();
        this.isInit = true;
        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                if(activity instanceof ActivityStub) {
                    ReflectCore.invokeSetPluginActivity((ActivityStub) activity,pluginClz,routerClz);
                }
                LogUtil.e("yy","onActivityCreated");
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    private void checkContext() {
        if(context == null) {
            throw new RouterException("please init first,invoke the RouterManager.getInstance().init(Context context)" +
                    ",use the applicationContext,suggest init at the application onCreate()");
        }
    }

    public RouterManager() {
        routerMap = new HashMap<Class, Router>();
        classLoaderMap = new HashMap<Class, DexClassLoader>();
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

    public Map<Class, Router> getRouterMap() {
        return routerMap;
    }

    public Map<Class, DexClassLoader> getClassLoaderMap() {
        return classLoaderMap;
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
        T resultClass = (T) routerMap.get(clz);
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
     * @param router
     * @param callback
     * @return
     */
    public static void createNativeDex(final Context context, Router router, final NativeDexCallback callback) {
       ReflectCore.createNativeDex(context,router,callback);
    }

    /**
     * start activity
     * @param activity
     * @param activityStub
     * @param clz
     * @param bundle
     * @param routerClz
     */
    public void startActivity(Activity activity,Class<? extends ActivityStub> activityStub,Class<? extends PluginActivity> clz
            ,Bundle bundle,Class<? extends Router> routerClz) {
        if(ReflectCore.isClassPresent(clz.getName())) {
            Intent intent = new Intent(activity,clz);
            if(bundle != null) {
                intent.putExtras(bundle);
            }
            activity.startActivity(intent);
            return;
        }
        ReflectCore.startActivity(activity,activityStub,bundle);
        this.pluginClz = clz;
        this.routerClz = routerClz;
    }

    public void startActivity(Activity activity,Class<? extends PluginActivity> clz
            ,Bundle bundle,Class<? extends Router> routerClz) {
        startActivity(activity,ActivityStub.class,clz,bundle,routerClz);
    }
}
