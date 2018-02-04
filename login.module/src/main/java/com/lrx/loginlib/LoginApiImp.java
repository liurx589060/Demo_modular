package com.lrx.loginlib;

import android.util.Log;

import com.lrx.extralib.login.LoginApi;

/**
 * Created by Administrator on 2017/12/3.
 */

public class LoginApiImp implements LoginApi {

    @Override
    public String loginByGuest(String userName) {
        Log.e("yy","LoginApiImp--loginByGuest=" + userName);
        return userName;
    }

    @Override
    public void loginOut() {
        Log.e("yy","LoginApiImp--loginOut");
    }
}
