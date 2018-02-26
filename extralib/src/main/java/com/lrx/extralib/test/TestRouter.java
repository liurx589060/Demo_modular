package com.lrx.extralib.test;

import com.lrx.router.lib.core.Router;

/**
 * Created by Administrator on 2018/2/3.
 */

public class TestRouter extends Router<TestApi> {
    @Override
    protected String getDefaultImpClassName() {
        return "com.lrx.extratestlib.ExtraImpApi";
    }

    @Override
    protected TestApi getErrorProxyClass() {
        return new ErrorImpTestApi();
    }
}
