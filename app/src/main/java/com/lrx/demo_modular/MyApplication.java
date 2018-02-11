package com.lrx.demo_modular;

import android.app.Application;

import com.lrx.extralib.RouterSDK;

/**
 * Created by Administrator on 2018/2/10.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        RouterSDK.init(this);
    }
}
