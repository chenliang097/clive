package com.rongtuoyouxuan.chatlive.base.view.model;

import android.os.Bundle;
import androidx.annotation.DrawableRes;

/**
 * 说明：ViewPageInfo
 * <p>
 * 作者：zhang
 * <p>
 * 类型：Class
 * <p>
 * 时间：2018/9/21 10:00
 * <p>
 * 版本：verson 1.0
 */
public class ViewPageInfo {
    public final String title;
    public final Bundle params;
    public final Class<?> clazz;
    public int resId = -1;


    public ViewPageInfo(String title, Class<?> clazz, Bundle params){
        this.title = title;
        this.clazz = clazz;
        this.params = params;
    }

    public ViewPageInfo(String title, @DrawableRes int resId, Class<?> clazz, Bundle params){
        this.title = title;
        this.resId = resId;
        this.clazz = clazz;
        this.params = params;
    }
}
