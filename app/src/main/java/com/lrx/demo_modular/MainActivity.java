package com.lrx.demo_modular;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.lrx.commonlib.api.LoginApi;
import com.lrx.loginlib.LoginApiImp;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.e("yy","实现层 loginByGuest--" + LoginApi.getInstance().loginByGuest("宋月"));
    }
}
