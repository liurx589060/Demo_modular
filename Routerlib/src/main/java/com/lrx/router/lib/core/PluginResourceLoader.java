package com.lrx.router.lib.core;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;

import com.lrx.router.lib.utils.LogUtil;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2018/2/7.
 */

public class PluginResourceLoader {
    private Resources resources;

    public Resources getResources() {
        return resources;
    }

    private static class ResourceLoaderHolder {
        private static PluginResourceLoader instance = new PluginResourceLoader();
    }

    public static PluginResourceLoader getInstance() {
        return ResourceLoaderHolder.instance;
    }

    /**
     * get the uninstall apk resource
     * @param context
     * @param apkPath
     * @return
     */
    public void createAssetManager(Context context,String apkPath) {
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager,apkPath);
            Resources superRes = context.getResources();
            resources = new Resources(assetManager, superRes.getDisplayMetrics(),
                    superRes.getConfiguration());
            LogUtil.d("yy","load the plugin apk resource");
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e(e.toString());
        }
    }
}
