package com.lrx.router.lib.core;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/2/1.
 */

public class RouterManager {
    private Map<String,Router> mRouterMap;
    private boolean isConvertNull;

    public RouterManager() {
        mRouterMap = new HashMap();
    }

    public boolean isConvertNull() {
        return isConvertNull;
    }

    public void setConvertNull(boolean convertNull) {
        isConvertNull = convertNull;
    }

    private static class RouterManagerHolder {
        public static RouterManager instance = new RouterManager();
    }

    public static RouterManager getInstance() {
        return RouterManagerHolder.instance;
    }

    public <T extends Router> T getRouter(Class<T> clz) {
        checkRegisterStatus();
        T resultClass = (T) mRouterMap.get(clz.getName());
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
        if(mRouterMap == null) {
            throw new RouterException("please Initialize the RouterManager");
        }
    }

    public boolean registerRouter(Class<? extends Router> routerClass) {
        return registerRouter(routerClass,true);
    }

    /**
     * register the router
     * @param routerClass
     * @param isConvertNull if is false,return the true proxy(maybe is null)
     *                     if is true,will return the notNull proxy(will convert the null proxy)
     * @return
     */
    public boolean registerRouter(Class<? extends Router> routerClass,boolean isConvertNull) {
        this.isConvertNull = isConvertNull;
        Router router;
        if(routerClass != null) {
            try {
                router = routerClass.newInstance();
                // 访问私有方法
                if(router.getClass().getSuperclass() != null) {
                    Method method = router.getClass().getSuperclass().getDeclaredMethod("createProxy");
                    method.setAccessible(true);//设置不做安全检查，这样才能访问private属性
                    method.invoke(router);
                    mRouterMap.put(routerClass.getName(),router);
                }
            }catch (NoSuchMethodException e) {
                throw new RouterException("please use class extends Router");
            }catch (Exception e1) {
                throw new RouterException("registerRouter fail,please check register router class,must use class extends Router");
            }
        }else {
            throw new RouterException("registerRouter the routerKey is null,please enter valid key");
        }
        if(router != null) {
            router.setConvertNull(isConvertNull);
        }
        return router.isCreatedSuccess;
    }
}
