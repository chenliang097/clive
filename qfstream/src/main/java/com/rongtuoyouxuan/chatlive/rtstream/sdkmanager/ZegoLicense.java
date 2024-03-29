package com.rongtuoyouxuan.chatlive.rtstream.sdkmanager;

import android.net.Uri;
//
//  ZegoLicense.java
//  ZegoEffectsExample
//  im.zego.zegoeffectsexample.sdkmanager
//
//  Created by Patrick Fu on 2021/04/03.
//  Copyright © 2021 Zego. All rights reserved.
//

/// Apply license from Zego

/**
 * 当您从ZEGO申请到 APP_ID 和 APP_SIGN 之后，我们强烈建议您将其通过服务器下发到APP，而不是保存在代码当中
 * 这里将其保存在代码当中，只是为了执行demo
 *
 * APP_ID，APP_SIGN： 从技术支持获取
 * BACKEND_API_URL: 在线鉴权地址，从官网或者技术支持获取
 */
public class ZegoLicense {

    public static String effectsLicense = "";

    public final static String BACKEND_API_URL = "https://aieffects-api.zego.im?Action=DescribeEffectsLicense";

    public final static long APP_ID = 1582659105;
    public final static String APP_SIGN = "94a77dfc0ae6613451673d8a7cef05faa3f8a16288707773f54aff8df369efdc";

    public static String getURL(String authInfo){
        return getURL(APP_ID,authInfo);
    }

    public static String getURL(long appID,String authInfo){
        Uri.Builder builder = Uri.parse(ZegoLicense.BACKEND_API_URL).buildUpon();
        builder.appendQueryParameter("AppId", String.valueOf(appID));
        builder.appendQueryParameter("AuthInfo", authInfo);

        return builder.build().toString();
    }
}
