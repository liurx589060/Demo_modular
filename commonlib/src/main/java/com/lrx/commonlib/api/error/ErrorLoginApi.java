package com.lrx.commonlib.api.error;

import com.lrx.commonlib.api.LoginApi;

/**
 * Created by Administrator on 2017/12/3.
 */

public class ErrorLoginApi extends LoginApi {
    @Override
    public String absLoginByGuest(String userName) {
        return null;
    }

    @Override
    public void absLoginOut() {

    }
}
