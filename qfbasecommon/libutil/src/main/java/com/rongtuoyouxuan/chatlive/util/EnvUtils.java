package com.rongtuoyouxuan.chatlive.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Base64;

import java.nio.charset.StandardCharsets;

import androidx.annotation.Nullable;

public class EnvUtils {

    public final static String CHANNEL_GUANWANG = "guanwang";
    public final static String CHANNEL_GOOGLEPLAY = "googleplay";
    public final static The3rdEnv THE3RD = The3rdEnv.getInstance();
    public final static ApiEnv API = ApiEnv.getInstance();
    private static final String APP_PLAT = "android";
    private static final String EMPTY = "";
    // 获取渠道号
    private static final String UMENG_CHANNEL = "UMENG_CHANNEL";
    private static final String PRODUCT_NAME = "PRODUCT_NAME";
    private static final String PRODUCT_SHORT_NAME = "PRODUCT_SHORT_NAME";
    private static final String ZEGO_APP_ID = "ZEGO_APP_ID";
    private static final String ADJUST_ID = "ADJUST_ID";
    private static String sChannel = "";
    private static String apiChannel = "";
    private static String sVersion = "";
    private static String buylyAppID = "";
    private static String productName = "";
    private static String schema = "";
    private static String zegoAppId = "";
    private static String adjustId = "";
    private static String BASE_64_ENCODED_PUBLIC_KEY = "";//Google支付Base64编码 RSA 公共密钥

    public static void initEnv(Context context) {
        _getAppChannel(context);
        _getAppVersion(context);
        initLibEnv(context);
    }

    private static void initLibEnv(Context context) {
        if (context == null) {
            return;
        }
        ApplicationInfo appInfo = null;
        try {
            appInfo = context.getApplicationContext().getPackageManager()
                    .getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            productName = appInfo.metaData.getString(PRODUCT_NAME);
            zegoAppId = appInfo.metaData.getString(ZEGO_APP_ID);
            adjustId = appInfo.metaData.getString(ADJUST_ID);
            schema = appInfo.metaData.getString("SCHEMA");
            BASE_64_ENCODED_PUBLIC_KEY = appInfo.metaData.getString("BASE_64_ENCODED_PUBLIC_KEY");
            if (productName != null) {
                productName = productName.toLowerCase();
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        if (appInfo != null && appInfo.metaData != null) {
            THE3RD.init(appInfo.metaData);
            API.init(context, appInfo.metaData);
        }
    }
    public static String getAppChannel() {
        return sChannel;
    }

    public static String getApiChannel() {
        return apiChannel;
    }

    public static String getZegoAppId() {
        return zegoAppId;
    }

    public static String getAdjustId() {
        return adjustId;
    }

    public static String getBuylyAppID(){
        return buylyAppID;
    }

    public static String getProductName() {
        return productName;
    }

    public static String getSchema() {
        return schema;
    }

    public static boolean isGooglePlayChannel() {
        return sChannel.endsWith(CHANNEL_GOOGLEPLAY);
    }

    public static boolean isGuanwangChannel() {
        return sChannel != null && sChannel.startsWith(CHANNEL_GUANWANG);
    }

    private static String _getAppChannel(Context context) {
        if (context == null) {
            return "";
        }
        try {
            ApplicationInfo appInfo = context.getApplicationContext().getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            String channelTemp = appInfo.metaData.getString(UMENG_CHANNEL);
            sChannel = channelTemp != null ? channelTemp.toLowerCase() : "";
            apiChannel = appInfo.metaData.getString(PRODUCT_NAME).toLowerCase();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        if (TextUtils.isEmpty(sChannel)) {
            sChannel = "";
        }
        return sChannel;
    }

    public static String getAppVersion(Context ctx) {
        return sVersion;
    }

    private static String _getAppVersion(Context ctx) {
        if (ctx == null) {
            return "";
        }

        PackageInfo packageInfo;
        try {
            packageInfo = ctx.getApplicationContext().getPackageManager().getPackageInfo(ctx.getPackageName(), 0);
            sVersion = packageInfo.versionName;
        } catch (Throwable e) {
            e.printStackTrace();
        }
        if (TextUtils.isEmpty(sVersion)) {
            sVersion = "";
        }
        return sVersion;
    }

    public static String getAppPlat() {
        return APP_PLAT;
    }

    @Nullable
    public static void getDeviceIDFromDisk(Context context, Handler.Callback callback) {
        DeviceIdUtils.getOrGenDeviceId(context, callback);
    }

    public static String getDeviceId(Context context) {
        return DeviceIdUtils.getDeviceIDFromDisk(context);
    }

    public static String getDeviceName(Context context) {
        String deveiceName = Settings.Secure.getString(context.getContentResolver(), "bluetooth_name");
        String base64Name = "";
        if(!TextUtils.isEmpty(deveiceName)) {
            base64Name = Base64.encodeToString(deveiceName.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT);
        }
        return base64Name;
    }


    public static String getGooglePayBase64Key(){
        return BASE_64_ENCODED_PUBLIC_KEY;
    }
}
