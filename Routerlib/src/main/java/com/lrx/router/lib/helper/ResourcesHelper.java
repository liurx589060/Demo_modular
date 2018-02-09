package com.lrx.router.lib.helper;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;

import com.lrx.router.lib.core.ResourcesCore;
import com.lrx.router.lib.core.Router;

/**
 * Created by daven.liu on 2018/2/9 0009.
 */

public class ResourcesHelper {
    private Class<? extends Router> clz;
    private Context context;

    public ResourcesHelper(Context context, Class clz) {
        this.context = context;
        this.clz = clz;
    }

    public Resources getResources(Activity activity, Class clz) {
        return ResourcesCore.getResources(activity,clz);
    }

    public int getLayoutId(Context paramContext, String paramString) {
        return ResourcesCore.getLayoutId(paramContext, clz,paramString);
    }

    public int getStringId(Context paramContext, String paramString) {
        return ResourcesCore.getStringId(paramContext, clz,paramString);
    }

    public String getString(Context paramContext, String paramString) {
        return ResourcesCore.getString(paramContext, clz,paramString);
    }

    public int getDrawableId(Context paramContext, String paramString) {
        return ResourcesCore.getDrawableId(paramContext, clz,paramString);
    }

    public int getStyleId(Context paramContext, String paramString) {
        return ResourcesCore.getStyleId(paramContext,clz, paramString);
    }

    public int getId(Context paramContext, String paramString) {
        return ResourcesCore.getId(paramContext,clz,paramString);
    }

    public int getColorId(Context paramContext, String paramString) {
        return ResourcesCore.getColorId(paramContext,clz,paramString);
    }

    public int getDimenId(Context paramContext, String paramString) {
        return ResourcesCore.getDimenId(paramContext,clz,paramString);
    }

    public int getAnimId(Context paramContext, String paramString) {
        return ResourcesCore.getAnimId(paramContext, clz,paramString);
    }

    public int getArrayId(Context paramContext, String paramString) {
        return ResourcesCore.getArrayId(paramContext, clz,paramString);
    }

    public int getstyleableId(Context paramContext, String paramString) {
        return ResourcesCore.getstyleableId(paramContext, clz,paramString);
    }

    public int[] getstyleableArray(Context paramContext, String paramString) {
        return ResourcesCore.getstyleableArray(paramContext,clz,paramString);
    }
}
