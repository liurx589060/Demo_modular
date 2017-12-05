package com.lrx.commonlib.api.error;

import android.util.Log;

import com.lrx.commonlib.api.LoginApi;

/**
 * Created by Administrator on 2017/12/3.
 */

public class ErrorLoginApi extends LoginApi {
    private final String MESSAGE = "LoginApi create exception";

    @Override
    public String absLoginByGuest(String userName) {
        errorLog("yy");
        return MESSAGE;
    }

    @Override
    public void absLoginOut() {
        errorLog("yy");
    }

    private void errorLog(String tag) {
        Log.e(tag,MESSAGE);
    }
}
