package com.rongtuoyouxuan.libuikit;

import android.content.Context;
import android.content.res.Resources;

import androidx.appcompat.app.AppCompatActivity;

import com.rongtuoyouxuan.chatlive.util.MyAdaptScreenUtils;

public class LanguageActivity extends AppCompatActivity {
    @Override
    protected void attachBaseContext(Context base) {
        if (UiKitConfig.INSTANCE.getGetLanguageContext() != null) {
            super.attachBaseContext(UiKitConfig.INSTANCE.getGetLanguageContext().invoke(base));
        } else {
            super.attachBaseContext(base);
        }
    }

    @Override
    public Resources getResources() {
        return MyAdaptScreenUtils.adaptVertical(super.getResources(),
                MyAdaptScreenUtils.defaultWidth);
    }
}
