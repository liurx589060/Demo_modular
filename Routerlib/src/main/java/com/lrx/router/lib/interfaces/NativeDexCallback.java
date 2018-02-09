package com.lrx.router.lib.interfaces;

/**
 * Created by daven.liu on 2018/2/7 0007.
 */

public interface NativeDexCallback {
    /**
     * return the native dex create result information
     * @param clz
     * @param dexPath
     * @param className
     */
    void onResult(Object clz,String dexPath,String className,int errorCode,String errorMsg);
}
