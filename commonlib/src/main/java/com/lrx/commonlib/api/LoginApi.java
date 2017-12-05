package com.lrx.commonlib.api;

import android.util.Log;

import com.lrx.commonlib.api.error.ErrorLoginApi;

/**
 * Created by Administrator on 2017/12/3.
 */

public abstract class LoginApi extends BaseApi{
    private static LoginApi instance = null;
    protected static String CLASS_NAME = "com.lrx.loginlib.LoginApiImp";

    public abstract String absLoginByGuest(String userName);
    public abstract void absLoginOut();

    private static class LoginApiHolder {
        private static LoginApi INSTANCE = init(CLASS_NAME);
        private static LoginApi init(String className) {
            LoginApi result = InstanceHolder.init(className,LoginApi.class);
            if(result == null) {
                result = InstanceHolder.init(ErrorLoginApi.class.getName(),LoginApi.class);
            }
            return result;
        }
    }

    public static LoginApi getInstance() {
        return LoginApiHolder.INSTANCE;
    }

    public String loginByGuest(String userName) {
        return absLoginByGuest(userName);
    }

    public void loginOut() {
        absLoginOut();
    }
}
