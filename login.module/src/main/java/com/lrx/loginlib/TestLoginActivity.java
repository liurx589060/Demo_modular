package com.lrx.loginlib;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

/**
 * Created by daven.liu on 2018/2/6 0006.
 */

public class TestLoginActivity extends Activity {
    private Button mLoginBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        init();
    }

    private void init() {
        mLoginBtn = (Button) findViewById(R.id.login_btn);
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginApiImp loginApiImp = new LoginApiImp();
                loginApiImp.loginByGuest("RTDTER");
                loginApiImp.startActivity(TestLoginActivity.this);
            }
        });
    }
}
