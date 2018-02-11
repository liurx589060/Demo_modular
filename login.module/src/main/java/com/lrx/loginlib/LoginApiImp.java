package com.lrx.loginlib;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.lrx.extralib.ActivityStub2;
import com.lrx.extralib.RouterSDK;
import com.lrx.extralib.login.LoginApi;
import com.lrx.extralib.login.LoginRouter;
import com.lrx.router.lib.helper.ResourcesHelper;

/**
 * Created by Administrator on 2017/12/3.
 */

public class LoginApiImp implements LoginApi {
    private ResourcesHelper resourcesHelper;

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
        final AlertDialog alertDialog = new AlertDialog.Builder(activity).setTitle("Title")
                .setIcon(getResourcesHelper(activity).getResources().getDrawable(getResourcesHelper(activity).getDrawableId(activity,"a")))
                .setMessage(getResourcesHelper(activity).getResources().getString(getResourcesHelper(activity).getStringId(activity,"tips_help_string")))
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

        Log.e("zz","resId=" + getResourcesHelper(activity).getStringId(activity,"message"));

        Dialog dialog = new Dialog(activity);
        int resId =  getResourcesHelper(activity).getLayoutId(activity,"activity_test");
        Log.e("zz","resId=" + resId);
        View contentView = LayoutInflater.from(activity).inflate(getResourcesHelper(activity).getResources().getLayout(resId),null);
        Button button = (Button) contentView.findViewById(getResourcesHelper(activity).getId(activity,"login_btn"));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity,"click the login button",Toast.LENGTH_LONG).show();
            }
        });
        dialog.setContentView(contentView);
        dialog.show();

        Log.e("yy","FunUtil--" + FunUtil.printTime());

        Bundle bundle = new Bundle();
        bundle.putString("imageUrl","http://p2.so.qhimgs1.com/t01033a1cccdcb273ce.jpg");
        RouterSDK.startActivity(activity,PTestActivity.class,bundle,LoginRouter.class);
//        RouterSDK.startActivity(activity,ActivityStub2.class,PTestActivity.class,bundle,LoginRouter.class);
    }

    private ResourcesHelper getResourcesHelper(Activity activity) {
        if(resourcesHelper == null) {
            resourcesHelper = new ResourcesHelper(activity,LoginRouter.class);
        }
        return resourcesHelper;
    }
}
