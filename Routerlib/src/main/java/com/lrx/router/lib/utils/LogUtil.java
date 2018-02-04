package com.lrx.router.lib.utils;

import android.util.Log;

/**
 * Created by daven.liu on 2018/1/31 0031.
 */

public class LogUtil {
    public final static String TAG = "commonlib";
    public static boolean isDebug = true;

    public static void e(String msg) {
        e(TAG,msg);
    }

    public static void e(String tag,String msg) {
        Log.e(tag,msg);
    }

    public static void d(String msg) {
        d(TAG,msg);
    }

    public static void d(String tag,String msg) {
        if(isDebug) {
            Log.d(tag,msg);
        }
    }

    public static void i(String msg) {
        i(TAG,msg);
    }

    public static void i(String tag,String msg) {
        if(isDebug) {
            Log.i(tag,msg);
        }
    }
}
