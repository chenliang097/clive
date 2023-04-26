package com.rongtuoyouxuan.chatlive.util;

import android.os.Bundle;

public class The3rdEnv {
    private static The3rdEnv instance;
//    private String FACEBOOK_APP_ID;

    private The3rdEnv() {
    }

    static synchronized The3rdEnv getInstance() {
        if (instance == null) {
            return new The3rdEnv();
        }
        return instance;
    }

//    public String getFacebookAppId() {
//        return FACEBOOK_APP_ID;
//    }

    public void init(Bundle metaData) {
//        FACEBOOK_APP_ID = getValueFromBundle(metaData, "FACEBOOK_APP_ID");
    }

    private String getValueFromBundle(Bundle metaData, String key) {
        String value = metaData.getString(key);
        if (StringUtils.isEmpty(value)) {
            throw new RuntimeException("not found " + key + " config");
        }
        return value;
    }
}
