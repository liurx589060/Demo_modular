package com.lrx.loginlib;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;

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
    public void startActivity(Activity activity) {
        final AlertDialog alertDialog = new AlertDialog.Builder(activity).setTitle("Title")
                .setMessage("login.module")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create();
        alertDialog.show();
    }
}
