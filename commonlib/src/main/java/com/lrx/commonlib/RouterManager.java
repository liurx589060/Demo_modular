package com.lrx.commonlib;

import com.lrx.commonlib.router.LoginRouter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/2/1.
 */

public class RouterManager {
    private Map<String,Router> mRouterMap;

    public RouterManager() {
        mRouterMap = new HashMap();
    }
    private static class RouterManagerHolder {
        public static RouterManager instance = new RouterManager();
    }

    public static RouterManager getInstance() {
        return RouterManagerHolder.instance;
    }

    public boolean resigterLoginRounter() {
        LoginRouter loginRouter = new LoginRouter();
        return registerRouter("LOGINROUTER",loginRouter);
    }

    public boolean registerRouter(String routerKey,Router router) {
        if(routerKey != null) {
            mRouterMap.put(routerKey,router);
        }else {
            throw new RouterException("registerRouter the routerKey is null,please enter valid key");
        }
        return router==null?false:router.isCreatedSuccess;
    }
}
