package com.rongtuoyouxuan.chatlive.rtstream.sdkmanager.net;

import android.content.Context;

import org.jetbrains.annotations.NotNull;

import com.rongtuoyouxuan.chatlive.rtstream.sdkmanager.ZegoLicense;
import com.rongtuoyouxuan.chatlive.rtstream.sdkmanager.SDKManager;

public class LicenseAPI {

    private static final String TAG = "LicenseAPI";

    public static void getLicense(Context context, @NotNull final IGetLicenseCallback callback) {
        getLicense(SDKManager.getAuthInfo(context),callback);
    }

    public static void getLicense( String authInfo, @NotNull final IGetLicenseCallback callback) {

        String url = ZegoLicense.getURL(authInfo);

        APIBase.asyncGet(url, License.class, new IAsyncGetCallback<License>() {
            @Override
            public void onResponse(int code, @NotNull String message, License responseJsonBean) {
                if (callback != null) {
                    callback.onGetLicense(code, message,responseJsonBean);
                }
            }
        });
    }

}
