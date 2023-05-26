package com.rongtuoyouxuan.chatlive.qfcommon.webview.androidinterface;

import android.content.Context;
import android.content.Intent;

import com.just.agentweb.AgentWeb;

import org.jetbrains.annotations.Nullable;

/*
 *Create by {Mr秦} on 2022/8/24
 */
public class CocosInterfacePlugin extends AndroidInterfacePlugin {
    public CocosInterfacePlugin(AgentWeb mAgentWeb, Context context) {
        super(mAgentWeb, context);
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

    }
}