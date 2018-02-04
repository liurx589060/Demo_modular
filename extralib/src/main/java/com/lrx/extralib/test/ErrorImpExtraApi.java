package com.lrx.extralib.test;

import android.content.Context;
import android.util.Log;

/**
 * Created by Administrator on 2018/2/3.
 */

public class ErrorImpExtraApi implements ExtraApi{
    @Override
    public void testTip(Context context, String msg) {
        Log.e("yy",msg);
    }

    @Override
    public void testResult(ExtraCallback callback) {
    }
}
