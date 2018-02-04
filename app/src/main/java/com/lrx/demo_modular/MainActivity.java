package com.lrx.demo_modular;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.lrx.extralib.login.LoginRouter;
import com.lrx.extralib.test.ExtraCallback;
import com.lrx.extralib.test.ExtraRouter;

public class MainActivity extends AppCompatActivity {
    private LoginRouter loginRouter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppRouterUtil.registerLoginRouter();
        Log.e("yy","实现层 loginByGuest--" + AppRouterUtil.getLoginRouter().getProxy().loginByGuest("Song yue"));

        AppRouterUtil.registerExtraRouter();
        ExtraRouter extraRouter = AppRouterUtil.getExtraRouter();
        extraRouter.getProxy().testTip(this,"蜡笔小新");
        extraRouter.getProxy().testResult(new ExtraCallback() {
            @Override
            public void onResult(int code, String msg) {
                Log.e("yy","code=" + code + "--msg=" + msg);
                AppRouterUtil.getLoginRouter().getProxy().loginOut();
            }
        });
    }
}
