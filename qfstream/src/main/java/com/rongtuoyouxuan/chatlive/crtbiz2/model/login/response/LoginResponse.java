package com.rongtuoyouxuan.chatlive.crtbiz2.model.login.response;

/*
 *Create by {Mr秦} on 2022/7/23
 */

import androidx.annotation.Keep;

import com.rongtuoyouxuan.chatlive.crtnet.BaseModel;

@Keep
public class LoginResponse extends BaseModel {
    private UserInfo data;

    public UserInfo getData() {
        return data;
    }
}