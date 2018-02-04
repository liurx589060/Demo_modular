package com.lrx.extralib;

import com.lrx.extralib.login.LoginRouter;
import com.lrx.router.lib.core.RouterManager;

/**
 * Created by Administrator on 2018/2/4.
 */

public class RouterSDK {
    public static void registerLoginRouter(boolean isConvertNull) {
        RouterManager.getInstance().registerRouter(LoginRouter.class,isConvertNull);
    }

    public static void registerLoginRouter() {
        registerLoginRouter(true);
    }

    public static LoginRouter getLoginRouter() {
        return RouterManager.getInstance().getRouter(LoginRouter.class);
    }
}
