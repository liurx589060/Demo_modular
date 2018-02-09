package com.lrx.router.lib.interfaces;

/**
 * Created by daven.liu on 2018/2/9 0009.
 */

public interface RegisterPluginCallback {
    /**
     * return the native dex create result information
     * @param dexPath
     * @param className
     * @param code
     * @param errorMsg
     */
    void onResult(String dexPath,String className,int code,String errorMsg);
}
