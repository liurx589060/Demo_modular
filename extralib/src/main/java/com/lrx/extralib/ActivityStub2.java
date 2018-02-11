package com.lrx.extralib;

import com.lrx.router.lib.activitys.ActivityStub;

/**
 * Created by Administrator on 2018/2/11.
 */

public class ActivityStub2 extends ActivityStub {
    @Override
    protected void onResume() {
        super.onResume();
        if(pluginActivity != null) {
            pluginActivity.onPResume();
        }
    }
}
