package com.lrx.extralib.login;

/**
 * Created by Administrator on 2018/2/3.
 */

public class ErrorImpLoginApi implements LoginApi {
    @Override
    public String loginByGuest(String userName) {
        return "thie is error loginApi";
    }

    @Override
    public void loginOut() {

    }
}
