package com.rongtuoyouxuan.qfcommon.webview.cocos;

import android.content.Intent;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.blankj.utilcode.util.LogUtils;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.JsAccessEntrace;

import org.jetbrains.annotations.Nullable;

/*
 *Create by {Mr秦} on 2022/8/11
 */
public class CocosLifeCyclePlugin implements LifecycleObserver {
    protected JsAccessEntrace jsAccessEntrace;

    public CocosLifeCyclePlugin(AgentWeb mAgentWeb) {
        jsAccessEntrace = mAgentWeb.getJsAccessEntrace();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    protected void onCreate() {
        LogUtils.e("onCreate");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    protected void onDestroy() {
        LogUtils.e("onDestroy");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    protected void onStart() {
        LogUtils.e("onStart");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    protected void onStop() {
        LogUtils.e("onStop");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    protected void onResume() {
        LogUtils.e("onResume");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    protected void onPause() {
        LogUtils.e("onPause");
    }


    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

    }
}