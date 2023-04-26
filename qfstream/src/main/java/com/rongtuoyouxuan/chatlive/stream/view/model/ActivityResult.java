package com.rongtuoyouxuan.chatlive.stream.view.model;

import android.content.Intent;

public class ActivityResult {

    public int requestCode;
    public int resultCode;
    public Intent data;

    public ActivityResult(int requestCode, int resultCode, Intent data) {
        this.requestCode = requestCode;
        this.resultCode = resultCode;
        this.data = data;
    }
}
