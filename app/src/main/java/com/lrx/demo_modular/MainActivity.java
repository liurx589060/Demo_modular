package com.lrx.demo_modular;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.lrx.commonlib.RouterManager;
import com.lrx.commonlib.router.LoginRouter;

public class MainActivity extends AppCompatActivity {
    private LoginRouter loginRouter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Log.e("yy","实现层 loginByGuest--" + LoginApi.getInstance().loginByGuest("Song yue"));

        loginRouter = new LoginRouter();
        Log.e("yy","实现层 loginByGuest--" + loginRouter.getProxyImp().loginByGuest("Song yue"));
    }
}
