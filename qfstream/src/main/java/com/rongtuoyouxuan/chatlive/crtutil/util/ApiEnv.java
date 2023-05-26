package com.rongtuoyouxuan.chatlive.crtutil.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;

import com.blankj.utilcode.util.SPUtils;
import com.rongtuoyouxuan.chatlive.crtutil.sp.SPConstants;

public class ApiEnv {
    private static ApiEnv instance;
    private String BASE_URL_API_BOBOO;
    private String H5_APP_SERVICE;
    private String DOMAIN;
    private String H5_APP_PRIVACY;
    private String BASE_URL_RTMP_STREAM_BOBOO;

    private ApiEnv() {
    }

    public static synchronized ApiEnv getInstance() {
        if (instance == null) {
            return new ApiEnv();
        }
        return instance;
    }

    public String getBaseUrlApiBobooCom() {
        return BASE_URL_API_BOBOO;
    }

    public String getH5AppService() {
        return H5_APP_SERVICE;
    }

    public String getH5AppPrivacy() {
        return H5_APP_PRIVACY;
    }

    public String getDomain() {
        return DOMAIN;
    }

    public String getBaseRTMPStreamUrl(){
        return BASE_URL_RTMP_STREAM_BOBOO;
    }

    public boolean isUseDebugMode(Context context) {
        boolean isDebug = context.getApplicationInfo() != null &&
                (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        if (isDebug || EnvUtils.getAppChannel().endsWith("qa")) {
            return SPUtils.getInstance().getBoolean(SPConstants.BooleanConstants.IS_DEBUG, true);
        }
        return false;
    }

    public void init(Context context, Bundle metaData) {
        String prefix = isUseDebugMode(context) ? getValueFromBundle(metaData, "BASE_URL_PREFIX_DEBUG")
                : getValueFromBundle(metaData, "BASE_URL_PREFIX");
        BASE_URL_API_BOBOO= prefix+getValueFromBundle(metaData, "BASE_URL_API_BOBOO");
        H5_APP_SERVICE = getValueFromBundle(metaData, "H5_APP_SERVICE");
        H5_APP_PRIVACY = getValueFromBundle(metaData, "H5_APP_PRIVACY");
        DOMAIN = getValueFromBundle(metaData, "DOMAIN");
        BASE_URL_RTMP_STREAM_BOBOO = getValueFromBundle(metaData, "BASE_URL_RTMP_STREAM_BOBOO");
    }

    private String getValueFromBundle(Bundle metaData, String key) {
        String value = metaData.getString(key);
        if (StringUtils.isEmpty(value)) {
            throw new RuntimeException("not found " + key + " config");
        }
        return value;
    }
}
