package com.lrx.router.lib.core;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;

import com.lrx.router.lib.utils.LogUtil;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/2/7.
 */

public class PluginResourceLoader {
    private Map<String,Resources> resourcesMap;
    private Map<String,String> packageNameMap;

    public Resources getResources(String packageName) {
        if(resourcesMap == null || resourcesMap.size() == 0) {
            throw new RouterException("please register the plugin apk first");
        }
        return resourcesMap.get(packageName);
    }

    public String getPluginApkPackageName(String apkPath) {
        if(packageNameMap == null || packageNameMap.size() == 0) {
            throw new RouterException("please register the plugin apk first");
        }
        return packageNameMap.get(apkPath);
    }

    private static class ResourceLoaderHolder {
        private static PluginResourceLoader instance = new PluginResourceLoader();
    }

    public static PluginResourceLoader getInstance() {
        return ResourceLoaderHolder.instance;
    }

    public PluginResourceLoader() {
        resourcesMap = new HashMap<String, Resources>();
        packageNameMap = new HashMap<String, String>();
    }

    /**
     * get the uninstall apk resource
     * @param context
     * @param apkPath
     * @return
     */
    public void createAssetManager(Context context,String apkPath,String originDexPath) {
        try {
            String pluginApkPackageName = "";
            PackageManager pm = context.getPackageManager();
            PackageInfo info = pm.getPackageArchiveInfo(apkPath,PackageManager.GET_ACTIVITIES);
            if (info != null) {
                ApplicationInfo applicationInfo = info.applicationInfo;
                pluginApkPackageName = applicationInfo.packageName;
                packageNameMap.put(originDexPath,pluginApkPackageName);
            }

            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager,apkPath);
            Resources superRes = context.getResources();
            Resources resources = new Resources(assetManager, superRes.getDisplayMetrics(),
                    superRes.getConfiguration());
            resourcesMap.put(pluginApkPackageName,resources);
            LogUtil.d("yy","load the plugin apk resource--packageName=" + pluginApkPackageName);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e(e.toString());
        }
    }
}
