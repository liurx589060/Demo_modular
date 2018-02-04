package com.lrx.demo_modular;

import com.lrx.extralib.RouterSDK;
import com.lrx.lib.core.RouterManager;
import com.lrx.extralib.test.ExtraRouter;

/**
 * Created by Administrator on 2018/2/4.
 */

public class AppRouterUtil extends RouterSDK {
    public static void registerExtraRouter() {
        registerExtraRouter(true);
    }

    public static void registerExtraRouter(boolean isConvertNull) {
        RouterManager.getInstance().registerRouter(ExtraRouter.class,isConvertNull);
    }

    public static ExtraRouter getExtraRouter() {
        return RouterManager.getInstance().getRouter(ExtraRouter.class);
    }
}