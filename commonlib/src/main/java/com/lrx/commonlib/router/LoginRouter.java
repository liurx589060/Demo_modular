package com.lrx.commonlib.router;

import com.lrx.commonlib.Router;
import com.lrx.commonlib.api.LoginApi;

/**
 * Created by daven.liu on 2018/1/31 0031.
 */

public class LoginRouter extends Router {
    private static final String className = "com.lrx.loginlib.LoginApiImp";
    private LoginApi proxyImp;


    public LoginApi getProxyImp() {
        if(proxyImp == null) {
            proxyImp = create(className,LoginApi.class);
        }
        return proxyImp;
    }
}
