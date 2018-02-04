package com.lrx.extralib.login;

/**
 * Created by Administrator on 2017/12/3.
 */

public interface LoginApi{

    String loginByGuest(String userName);

    void loginOut();
}
