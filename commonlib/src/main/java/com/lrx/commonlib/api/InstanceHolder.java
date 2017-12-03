package com.lrx.commonlib.api;

import android.util.Log;

import com.lrx.commonlib.api.error.ErrorLoginApi;

/**
 * Created by Administrator on 2017/12/3.
 */

public class InstanceHolder {
    public static<T> T init(String className,Class<T> convertClass) {
        Log.e("yy","className=" + className);
        T result = null;
        try {
            Class cls = Class.forName(className);
            result = (T) cls.newInstance();
        }catch (Exception e) {
            e.printStackTrace();
            Log.e("yy","create Api class is exception--" + e.toString());
        }
        return result;
    }
}
