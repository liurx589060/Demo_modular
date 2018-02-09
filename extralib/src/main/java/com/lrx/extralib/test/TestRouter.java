package com.lrx.extralib.test;

import com.lrx.router.lib.core.Router;

/**
 * Created by Administrator on 2018/2/3.
 */

public class TestRouter extends Router<TestApi> {
    @Override
    public String getImpClassName() {
        return "com.lrx.extratestlib.ExtraImpApi";
    }

    @Override
    public TestApi getErrorProxyClass() {
        return new ErrorImpTestApi();
    }
}
