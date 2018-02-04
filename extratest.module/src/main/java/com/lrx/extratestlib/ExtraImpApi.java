package com.lrx.extratestlib;

import android.content.Context;
import android.widget.Toast;

import com.lrx.extralib.test.ExtraApi;
import com.lrx.extralib.test.ExtraCallback;

/**
 * Created by Administrator on 2018/2/3.
 */

public class ExtraImpApi implements ExtraApi {
    @Override
    public void testTip(Context context, String msg) {
        Toast.makeText(context.getApplicationContext(),msg,Toast.LENGTH_LONG).show();
    }

    @Override
    public void testResult(ExtraCallback callback) {
        if(callback != null) {
            callback.onResult(11000,"I love you");
        }
    }
}
