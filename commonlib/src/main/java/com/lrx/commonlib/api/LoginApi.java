package com.lrx.commonlib.api;

import android.util.Log;

import com.lrx.commonlib.api.error.ErrorLoginApi;

/**
 * Created by Administrator on 2017/12/3.
 */

public interface LoginApi{

    String loginByGuest(String userName);

    void loginOut();
}
