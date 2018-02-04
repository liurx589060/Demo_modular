package com.lrx.extralib.test;

import android.content.Context;

/**
 * Created by Administrator on 2018/2/3.
 */

public interface ExtraApi {
    void testTip(Context context,String msg);
    void testResult(ExtraCallback callback);
}
