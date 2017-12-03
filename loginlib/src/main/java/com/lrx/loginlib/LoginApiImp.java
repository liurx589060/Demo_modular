package com.lrx.loginlib;

import android.util.Log;

import com.lrx.commonlib.api.LoginApi;

/**
 * Created by Administrator on 2017/12/3.
 */

public class LoginApiImp extends LoginApi {

    @Override
    public String absLoginByGuest(String userName) {
        Log.e("yy","LoginApiImp--loginByGuest=" + userName);
        return userName;
    }

    @Override
    public void absLoginOut() {
        Log.e("yy","LoginApiImp--loginOut");
    }
}
