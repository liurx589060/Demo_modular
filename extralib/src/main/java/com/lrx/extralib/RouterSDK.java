package com.lrx.extralib;

import android.content.Context;
import android.content.res.Resources;

import com.lrx.extralib.login.LoginRouter;
import com.lrx.extralib.test.TestRouter;
import com.lrx.router.lib.core.PluginResourceLoader;
import com.lrx.router.lib.core.Router;
import com.lrx.router.lib.core.RouterManager;
import com.lrx.router.lib.interfaces.RegisterPluginCallback;

/**
 * Created by Administrator on 2018/2/4.
 */

public class RouterSDK {
    public static void init(Context context) {
        RouterManager.getInstance().init(context);
    }


    public static void registerLoginRouter(boolean isConvertNull) {
        RouterManager.getInstance().registerRouter(new LoginRouter(),isConvertNull);
    }

    public static void registerLoginRouter() {
        registerLoginRouter(true);
    }

    public static LoginRouter getLoginRouter() {
        return RouterManager.getInstance().getRouter(LoginRouter.class);
    }

    public static void registerLoginByPlugin(RegisterPluginCallback registerPluginCallback) {
        registerLoginByPlugin(true,registerPluginCallback);
    }

    public static void registerLoginByPlugin(boolean isConvertNull,RegisterPluginCallback registerPluginCallback) {
        LoginRouter loginRouter = new LoginRouter();
        loginRouter.setPluginDexPath("file://login.module-release.apk");
        RouterManager.getInstance().registerRouterByPlugin(loginRouter,isConvertNull,registerPluginCallback);
    }

    public static TestRouter getTestRouter() {
        return RouterManager.getInstance().getRouter(TestRouter.class);
    }

    public static void registerTestRouter() {
        registerTestRouter(true);
    }

    public static void registerTestRouter(boolean isConvertNull) {
        RouterManager.getInstance().registerRouter(new TestRouter(),isConvertNull);
    }

    public static Resources getPluginResources(Class<Router> clz) {
        return PluginResourceLoader.getInstance().getResources(PluginResourceLoader.getInstance()
                .getPluginApkPackageName(RouterManager.getInstance().getRouter(clz).getPluginDexPath()));
    }
}
