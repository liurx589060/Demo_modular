package com.lrx.demo_modular;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lrx.extralib.RouterSDK;
import com.lrx.extralib.login.LoginRouter;
import com.lrx.extralib.test.TestCallback;
import com.lrx.extralib.test.TestRouter;
import com.lrx.router.lib.interfaces.RegisterPluginCallback;

public class MainActivity extends AppCompatActivity {
    private LoginRouter loginRouter;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Handler handler = new Handler();
        RouterSDK.registerLoginByPlugin(new RegisterPluginCallback() {
            @Override
            public void onResult(String dexPath, String className, int code, String errorMsg) {
                Log.e("yy","实现层 loginByGuest--" + RouterSDK.getLoginRouter().getProxy().loginByGuest("Song yue"));
                RouterSDK.getLoginRouter().getProxy().startActivity(MainActivity.this);
            }
        });

        mImageView = (ImageView) findViewById(R.id.imageView);
        Glide.with(this)
                .load("http://uploadfile.bizhizu.cn/2014/1129/20141129042605527.jpg")
                .into(mImageView);

//        RouterSDK.registerLoginRouter();
//        Log.e("yy","实现层 loginByGuest--" + RouterSDK.getLoginRouter().getProxy().loginByGuest("Song yue"));
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                RouterSDK.getLoginRouter().getProxy().startActivity(MainActivity.this);
//            }
//        },2000);

        RouterSDK.registerTestRouter();
        TestRouter extraRouter = RouterSDK.getTestRouter();
        extraRouter.getProxy().testTip(this,"蜡笔小新");
        extraRouter.getProxy().testResult(new TestCallback() {
            @Override
            public void onResult(int code, String msg) {
                Log.e("yy","code=" + code + "--msg=" + msg);
                RouterSDK.getLoginRouter().getProxy().loginOut();
            }
        });

//        String dexFilePath = Environment.getExternalStorageDirectory().getPath() + "/" + "login.module-release.apk";
//        String dexFilePath = "file://login.module-release.apk";
//        RouterManager.createNativeDex(this, dexFilePath, "com.lrx.loginlib.LoginApiImp", new NativeDexCallback() {
//            @Override
//            public void onResult(Object clz, String dexPath, String className, String errorMsg) {
//                if (clz instanceof LoginApi) {
//                    LoginApi loginApi = (LoginApi) clz;
//                    loginApi.startActivity(MainActivity.this);
//                }
//            }
//        });
    }
}
