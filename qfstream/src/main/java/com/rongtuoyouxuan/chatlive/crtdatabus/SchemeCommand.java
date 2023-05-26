package com.rongtuoyouxuan.chatlive.crtdatabus;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import com.rongtuoyouxuan.chatlive.crtdatabus.helper.ActionHelper;
import com.rongtuoyouxuan.chatlive.crtrouter.Router;
import com.rongtuoyouxuan.chatlive.crtutil.util.EnvUtils;
import com.rongtuoyouxuan.chatlive.crtutil.util.SysUtil;
import java.net.URLDecoder;

public class SchemeCommand {

    public final static String SCHEME_BOBOO = "boboo";
    public final static String SCHEME_PATH_OPEN_WEBBROWSER = "openwebbrowser";//打开外部浏览器
    public final static String SCHEME_PATH_OPEN_APPWEB = "openappweb";//打开APP内部浏览器
    public final static String SCHEME_PATH_OPEN_STREAM_ROOM = "openStreamRoom";//打开推流页面


    public static boolean execCommand(Context context, Uri uri) {
        if (uri == null || TextUtils.isEmpty(uri.getScheme()) || TextUtils.isEmpty(uri.getHost())) {
            return false;
        }
        if (uri.getScheme().equalsIgnoreCase(EnvUtils.getSchema()) || uri.getScheme().equalsIgnoreCase(SCHEME_BOBOO)) {
            return execBobooCommand(context, uri);
        }
        return false;
    }

    private static boolean execBobooCommand(Context context, Uri uri) {
        if (!DataBus.instance().isAppHasStarted()) {
            DataBus.instance().setLastSchemeUri("appurl", uri.toString());
            return true;
        }
        if (uri.getHost().equalsIgnoreCase(SCHEME_PATH_OPEN_WEBBROWSER)) {
            String url = uri.getQueryParameter("url");
            String addcommonparam = uri.getQueryParameter("addcommonparam");
            addcommonparam = TextUtils.isEmpty(addcommonparam) ? "0" : addcommonparam;
            if (!TextUtils.isEmpty(url)) {
                try {
                    url = URLDecoder.decode(url);
                    if (addcommonparam.equals("1")) {
                        url = ActionHelper.addCommonParams(url);
                    }
                    SysUtil.openSystemBrowser(context, url);
                } catch (Exception e) {
                }
            }
        } else if (uri.getHost().equalsIgnoreCase(SCHEME_PATH_OPEN_APPWEB)) {
            String url = uri.getQueryParameter("url");
            String addcommonparam = uri.getQueryParameter("addcommonparam");
            addcommonparam = TextUtils.isEmpty(addcommonparam) ? "0" : addcommonparam;
            if (!TextUtils.isEmpty(url)) {
                try {
                    url = URLDecoder.decode(url);
                    Router.toWebViewActivity(url, addcommonparam.equals("1"));
                } catch (Exception e) {
                }
            }
        }
        return false;
    }
}
