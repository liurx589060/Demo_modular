package com.lrx.commonlib.router;

import com.lrx.commonlib.Router;
import com.lrx.commonlib.api.LoginApi;

/**
 * Created by daven.liu on 2018/1/31 0031.
 */

public class LoginRouter extends Router implements LoginApi{
    private static final String className = "com.lrx.loginlib.LoginApiImp";
    private LoginApi proxyImp;

    public LoginRouter() {
        proxyImp = create(className,LoginApi.class);
        if(proxyImp != null) {
            isCreatedSuccess = true;
        }
    }

    public LoginApi getProxyImp() {
        if(proxyImp == null) {
            proxyImp = create(this.getClass().getName(),this.getClass());
        }
        return proxyImp;
    }

    @Override
    public String loginByGuest(String userName) {
        return "this is error LoginApi";
    }

    @Override
    public void loginOut() {

    }

    public boolean isCreatedSuccess() {
        return isCreatedSuccess;
    }
}
