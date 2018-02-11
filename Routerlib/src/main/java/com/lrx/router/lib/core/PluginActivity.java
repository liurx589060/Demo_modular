package com.lrx.router.lib.core;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;

import com.lrx.router.lib.activitys.ActivityStub;
import com.lrx.router.lib.helper.ResourcesHelper;

/**
 * Created by Administrator on 2018/2/10.
 */

public abstract class PluginActivity extends Activity {
    private Activity activity;

    public abstract void onPluginCreate(Bundle savedInstanceState);
    public abstract View getContentView();
    public abstract ResourcesHelper getResourcesHelper(Activity activity);
    public abstract void onPResume();
    public abstract void onPStart();
    public abstract void onPPause();
    public abstract void onPStop();
    public abstract void onPDestroy();
    public abstract void onPBackPressed();
    public abstract void onPRestart();
    public abstract void onPRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults);
    public abstract void onPActivityResult(int requestCode, int resultCode, Intent data);

    public void onPCreate(Activity activity,Bundle savedInstanceState) {
        this.activity = activity;
        this.onPluginCreate(savedInstanceState);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getContentView() != null) {
            setContentView(getContentView());
        }
        onPluginCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.onPStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        this.onPRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.onPResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.onPPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.onPStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.onPDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.onPBackPressed();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(activity == null) {
            return super.onKeyDown(keyCode,event);
        }
        return this.onPKeyDown(keyCode,event);
    }

    public boolean onPKeyDown(int keyCode, KeyEvent event) {
        if(activity != null && activity instanceof ActivityStub) {
            return ((ActivityStub) activity).keyDown(keyCode,event);
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.onPActivityResult(requestCode,resultCode,data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        this.onPRequestPermissionsResult(requestCode,permissions,grantResults);
    }

    public Activity getActivity() {
        if(activity == null) {
            return this;
        }
        return activity;
    }
}
