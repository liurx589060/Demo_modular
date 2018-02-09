package com.lrx.router.lib.core;

import android.content.Context;
import android.content.res.Resources;

/**
 * Created by daven.liu on 2018/2/9 0009.
 */

public class ResourcesCore {

    public static String getPackageName(Context activity,Class<? extends Router> clz) {
        if(RouterManager.getInstance().getRouter(clz).getLoadType() == Router.LoadType.LOAD_PLUGIN) {
           return PluginResourceLoader.getInstance()
                    .getPluginApkPackageName(RouterManager.getInstance().getRouter(clz).getPluginDexPath());
        }else {
            return activity.getPackageName();
        }
    }

    public static Resources getResources(Context activity, Class<? extends Router> clz) {
        if(RouterManager.getInstance().getRouter(clz).getLoadType() == Router.LoadType.LOAD_PLUGIN) {
            return PluginResourceLoader.getInstance().getResources(PluginResourceLoader.getInstance()
                    .getPluginApkPackageName(RouterManager.getInstance().getRouter(clz).getPluginDexPath()));
        }else {
            return activity.getResources();
        }
    }

    public static int getLayoutId(Context activity, Class<? extends Router> clz,String paramString) {
        return getResources(activity,clz).getIdentifier(paramString, "layout", getPackageName(activity,clz));
    }

    public static int getStringId(Context activity, Class<? extends Router> clz,String paramString) {
        int stringId = getResources(activity,clz).getIdentifier(paramString, "string", getPackageName(activity,clz));
        if(stringId == 0){
            stringId = getResources(activity,clz).getIdentifier("net_error_0", "string", getPackageName(activity,clz));
        }
        return stringId;
    }

    public static String getString(Context activity,Class<? extends Router> clz,String paramString){
        int stringId = getResources(activity,clz).getIdentifier(paramString, "string", getPackageName(activity,clz));
        if(stringId == 0){
            return "";
        }
        return getResources(activity,clz).getString(stringId);
    }

    public static int getDrawableId(Context activity, Class<? extends Router> clz,String paramString) {
        return getResources(activity,clz).getIdentifier(paramString, "drawable", getPackageName(activity,clz));
    }

    public static int getStyleId(Context activity, Class<? extends Router> clz,String paramString) {
        return getResources(activity,clz).getIdentifier(paramString, "style", getPackageName(activity,clz));
    }

    public static int getId(Context activity,Class<? extends Router> clz, String paramString) {
        return getResources(activity,clz).getIdentifier(paramString, "id", getPackageName(activity,clz));
    }

    public static int getColorId(Context activity, Class<? extends Router> clz,String paramString) {
        return getResources(activity,clz).getIdentifier(paramString, "color", getPackageName(activity,clz));
    }

    public static int getDimenId(Context activity, Class<? extends Router> clz,String paramString) {
        return getResources(activity,clz).getIdentifier(paramString, "dimen", getPackageName(activity,clz));
    }

    public static int getAnimId(Context activity, Class<? extends Router> clz,String paramString) {
        return getResources(activity,clz).getIdentifier(paramString, "anim", getPackageName(activity,clz));
    }

    public static int getArrayId(Context activity, Class<? extends Router> clz,String paramString) {
        return getResources(activity,clz).getIdentifier(paramString, "array", getPackageName(activity,clz));
    }

    public static int getstyleableId(Context activity,Class<? extends Router> clz, String paramString) {
        return getResources(activity,clz).getIdentifier(paramString, "styleable", getPackageName(activity,clz));
    }

    public static int [] getstyleableArray(Context activity, Class<? extends Router> clz,String paramString) {
        int i = getResources(activity,clz).getIdentifier(paramString, "styleable", getPackageName(activity,clz));
        return getResources(activity,clz).getIntArray(i);
    }
}
