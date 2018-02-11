package com.lrx.router.lib.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;

import com.lrx.router.lib.core.PluginActivity;

/**
 * Created by Administrator on 2018/2/10.
 */

public class ActivityStub extends Activity {
    protected PluginActivity pluginActivity;
    private Bundle savedInstanceState;

    private void setPluginActivity(PluginActivity pluginActivity) {
        this.pluginActivity = pluginActivity;
        pluginActivity.setIntent(this.getIntent());
        pluginActivity.onPCreate(this,savedInstanceState);
        setContentView(pluginActivity.getContentView());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(pluginActivity != null)
        pluginActivity.onPStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(pluginActivity != null)
        pluginActivity.onPStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(pluginActivity != null)
        pluginActivity.onPResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(pluginActivity != null)
        pluginActivity.onPPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(pluginActivity != null)
        pluginActivity.onPStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(pluginActivity != null)
        pluginActivity.onPDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(pluginActivity != null){
           return pluginActivity.onPKeyDown(keyCode,event);
        }
        return super.onKeyDown(keyCode, event);
    }

    public boolean keyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode,event);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(pluginActivity != null) {
            pluginActivity.onPBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(pluginActivity != null) {
            pluginActivity.onPActivityResult(requestCode,resultCode,data);
        }
    }
}
