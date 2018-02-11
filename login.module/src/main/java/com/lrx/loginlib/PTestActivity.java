package com.lrx.loginlib;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lrx.extralib.login.LoginRouter;
import com.lrx.router.lib.core.PluginActivity;
import com.lrx.router.lib.helper.ResourcesHelper;

/**
 * Created by Administrator on 2018/2/10.
 */

public class PTestActivity extends PluginActivity {
    private ResourcesHelper resourcesHelper;
    private ImageView mImageView;

    @Override
    public void onPluginCreate(@Nullable Bundle savedInstanceState) {
        Log.e("zz","onPluginCreate");
    }

    @Override
    public View getContentView() {
        int resId =  getResourcesHelper(getActivity()).getLayoutId(getActivity(),"activity_ptest");
        View view = LayoutInflater.from(getActivity()).inflate(resourcesHelper.getResources().getLayout(resId),null);
        mImageView = (ImageView) view.findViewById(getResourcesHelper(getActivity()).getId(getActivity(),"imageView"));
        int imageResId = getResourcesHelper(getActivity()).getDrawableId(getActivity(),"a");
        mImageView.setImageDrawable(getResourcesHelper(getActivity()).getResources().getDrawable(imageResId));

        Intent intent = getIntent();
        String imageUrl = "";
        if(intent.getExtras() != null) {
            imageUrl = intent.getExtras().getString("imageUrl","http://uploadfile.bizhizu.cn/2014/1129/20141129042605527.jpg");
        }
        Glide.with(getActivity())
                .load(imageUrl)
                .into(mImageView);
        return view;
    }

    @Override
    public ResourcesHelper getResourcesHelper(Activity activity) {
        if(resourcesHelper == null) {
            resourcesHelper = new ResourcesHelper(activity,LoginRouter.class);
        }
        return resourcesHelper;
    }

    @Override
    public void onPResume() {
        Log.e("zz","onPResume");
    }

    @Override
    public void onPStart() {
        Log.e("zz","onPStart");
    }

    @Override
    public void onPPause() {
        Log.e("zz","onPPause");
    }

    @Override
    public void onPStop() {
        Log.e("zz","onPStop");
    }

    @Override
    public void onPDestroy() {
        Log.e("zz","onPDestroy");
    }

    @Override
    public void onPBackPressed() {
        Log.e("zz","onPBackPressed");
    }

    @Override
    public void onPRestart() {

    }

    @Override
    public void onPRequestPermissionsResult(int i, String[] strings, int[] ints) {

    }

    @Override
    public void onPActivityResult(int i, int i1, Intent intent) {

    }

    @Override
    public boolean onPKeyDown(int keyCode, KeyEvent event) {
        Log.e("zz","onPKeyDown");
        return super.onPKeyDown(keyCode,event);
    }
}
