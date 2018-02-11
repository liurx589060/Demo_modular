package com.lrx.extralib;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;

import com.lrx.extralib.login.LoginRouter;
import com.lrx.extralib.test.TestRouter;
import com.lrx.router.lib.activitys.ActivityStub;
import com.lrx.router.lib.core.PluginActivity;
import com.lrx.router.lib.core.PluginResourceLoader;
import com.lrx.router.lib.core.Router;
import com.lrx.router.lib.core.RouterManager;
import com.lrx.router.lib.interfaces.RegisterPluginCallback;

/**
 * Created by Administrator on 2018/2/4.
 */

public class RouterSDK {
    public static void init(Application application) {
        RouterManager.getInstance().init(application);
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
        loginRouter.setPluginDexPath("file://login/login.module-debug.apk");
//        loginRouter.setPluginDexPath(Environment.getExternalStorageDirectory().getPath() + "/login.module-release.apk");
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

    public static void startActivity(Activity activity, Class<? extends PluginActivity> clz, Bundle bundle,Class<? extends Router> routerClz) {
        RouterManager.getInstance().startActivity(activity,clz,bundle,routerClz);
    }

    public static void startActivity(Activity activity, Class<? extends ActivityStub> stubClz
            ,Class<? extends PluginActivity> clz, Bundle bundle, Class<? extends Router> routerClz) {
        RouterManager.getInstance().startActivity(activity, stubClz,clz, bundle, routerClz);
    }
}
