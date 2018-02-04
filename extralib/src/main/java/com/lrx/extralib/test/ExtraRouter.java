package com.lrx.extralib.test;

import com.lrx.lib.core.Router;

/**
 * Created by Administrator on 2018/2/3.
 */

public class ExtraRouter extends Router<ExtraApi> {
    @Override
    public String getImpClassName() {
        return "com.lrx.extratestlib.ExtraImpApi";
    }

    @Override
    protected ExtraApi getErrorProxyClass() {
        return new ErrorImpExtraApi();
    }
}
