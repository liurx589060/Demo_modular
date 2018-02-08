package com.lrx.router.lib.utils;

import android.content.res.Resources;

import com.lrx.router.lib.core.PluginResourceLoader;

public class PluginResourceUtil {
	public static Resources getResources() {
		return PluginResourceLoader.getInstance().getResources();
	}

	 public static int getLayoutId(String paramString) {
	    return getResources().getIdentifier(paramString, "layout", PluginResourceLoader.getInstance().getPluginApkPackageName());
	 }

	 public static int getStringId(String paramString) {
	 	int stringId = getResources().getIdentifier(paramString, "string", PluginResourceLoader.getInstance().getPluginApkPackageName());
	    return stringId;
	 }

	 public static int getDrawableId(String paramString) {
	    return getResources().getIdentifier(paramString, "drawable", PluginResourceLoader.getInstance().getPluginApkPackageName());
	 }

	 public static int getStyleId(String paramString) {
	    return getResources().getIdentifier(paramString, "style", PluginResourceLoader.getInstance().getPluginApkPackageName());
	 }

	 public static int getId(String paramString) {
	    return getResources().getIdentifier(paramString, "id", PluginResourceLoader.getInstance().getPluginApkPackageName());
	 }

	 public static int getColorId(String paramString) {
	    return getResources().getIdentifier(paramString, "color", PluginResourceLoader.getInstance().getPluginApkPackageName());
	 }

	 public static int getDimenId(String paramString) {
	    return getResources().getIdentifier(paramString, "dimen", PluginResourceLoader.getInstance().getPluginApkPackageName());
	 }
	 
	 public static int getAnimId(String paramString) {
		    return getResources().getIdentifier(paramString, "anim", PluginResourceLoader.getInstance().getPluginApkPackageName());
		 }
	 
	 public static int getArrayId(String paramString) {
		return getResources().getIdentifier(paramString, "array", PluginResourceLoader.getInstance().getPluginApkPackageName());
	}	
	 
	public static int getstyleableId(String paramString) {
		return getResources().getIdentifier(paramString, "styleable", PluginResourceLoader.getInstance().getPluginApkPackageName());
	}
	 
	public static int [] getstyleableArray(String paramString) {
		int i = getResources().getIdentifier(paramString, "styleable", PluginResourceLoader.getInstance().getPluginApkPackageName());
		return getResources().getIntArray(i);
    }
}
