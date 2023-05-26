package com.rongtuoyouxuan.chatlive.crtutil.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.rongtuoyouxuan.chatlive.crtlog.PLog;


public class SysUtil {
    public static boolean openSystemBrowser(Context context, String url) {
        boolean res = false;
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "http://" + url;
        }
        try {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            context.startActivity(browserIntent);
            res = true;
        } catch (Throwable e) {
            PLog.e("ActivityNotFoundException", e.getMessage());
        }
        return res;
    }

    /**
     * 验证设备是否支持
     */
    public static boolean verifySupportDevice(Context context) {
        String model = android.os.Build.MODEL;
        String brand = android.os.Build.BRAND;
        if (model.equals("X600")
                || (model.equals("2014112") && brand.equals("Xiaomi"))
                || (model.equals("2014501") && brand.equals("Xiaomi"))) {//乐视x600,hongmi 2
//            Toast.makeText(context, "您的手机暂不支持", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    /**
     *  os >= Android Q (29)
     * @return boolean
     */
    public static boolean osLargerQ(){
        return android.os.Build.VERSION.SDK_INT >= 29;
    }

}