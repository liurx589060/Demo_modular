package com.lrx.loginlib;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.lrx.extralib.ResourcesUtil;
import com.lrx.extralib.RouterSDK;
import com.lrx.extralib.login.LoginApi;

/**
 * Created by Administrator on 2017/12/3.
 */

public class LoginApiImp implements LoginApi {

    @Override
    public String loginByGuest(String userName) {
        Log.e("yy","LoginApiImp--loginByGuest=" + userName);
        return userName;
    }

    @Override
    public void loginOut() {
        Log.e("yy","LoginApiImp--loginOut");
    }

    @Override
    public void startActivity(final Activity activity) {
//        final AlertDialog alertDialog = new AlertDialog.Builder(activity).setTitle("Title")
//                .setIcon(RouterSDK.getPluginResources().getDrawable(R.drawable.a))
//                .setMessage(RouterSDK.getPluginResources().getString(R.string.message))
//                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                    }
//                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                    }
//                }).create();
//        alertDialog.show();
//
//        Log.e("zz","resId=" + RouterSDK.getPluginResources().getIdentifier("message","string","com.lrx.loginlib"));

        Dialog dialog = new Dialog(activity);
        int resId =  ResourcesUtil("activity_test","layout","com.lrx.loginlib");
        View contentView = LayoutInflater.from(activity).inflate(RouterSDK.getPluginResources().getLayout(R.layout.activity_test),null);
        Button button = (Button) contentView.findViewById(R.id.login_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity,"click the login button",Toast.LENGTH_LONG).show();
            }
        });
        Log.e("zz","resId=" + resId + "---view=" + contentView);
        dialog.setContentView(contentView);
        dialog.show();
    }
}
