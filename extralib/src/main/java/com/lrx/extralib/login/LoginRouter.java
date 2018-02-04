package com.lrx.extralib.login;

import com.lrx.lib.core.Router;

/**
 * Created by daven.liu on 2018/1/31 0031.
 */

public class LoginRouter extends Router<LoginApi>{
    private final String className = "com.lrx.loginlib.LoginApiImp";

    @Override
    public String getImpClassName() {
        return className;
    }

    @Override
    protected LoginApi getErrorProxyClass() {
        return new ErrorImpLoginApi();
    }
}
