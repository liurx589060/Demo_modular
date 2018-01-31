package com.lrx.commonlib;

import android.content.Context;
import com.lrx.commonlib.LogUtil;

/**
 * Created by daven.liu on 2018/1/31 0031.
 */

public class Router {
    public static <T> T create(final String impClassName,Class<T> impClass) {
        T result = null;
        if(isClassPresent(impClassName)) {
            try{
                Class cls = Class.forName(impClassName);
                return (T) cls.newInstance();
            }catch (Exception e) {
                LogUtil.e(e.toString());
                result = null;
            }
        }
        return result;
    }

    public static boolean isClassPresent(String name) {
        try {
            Thread.currentThread().getContextClassLoader().loadClass(name);
            return true;
        } catch (ClassNotFoundException e) {
            LogUtil.e(e.toString());
            return false;
        }
    }
}
