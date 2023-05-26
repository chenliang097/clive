package com.rongtuoyouxuan.chatlive.crtdatabus.helper;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.rongtuoyouxuan.chatlive.crtdatabus.DataBus;
import com.rongtuoyouxuan.chatlive.crtdatabus.SchemeCommand;
import com.rongtuoyouxuan.chatlive.crtrouter.Router;
import com.rongtuoyouxuan.chatlive.crtnet.BaseNetImpl;

import java.net.URL;
import java.util.Map;

public class ActionHelper {

    public static void exection(Context context, String act, String act_val) {
        if (TextUtils.isEmpty(act) || TextUtils.isEmpty(act_val)) {
            return;
        }
        if ("appurl".equals(act)) {
            SchemeCommand.execCommand(context, Uri.parse(act_val));
        } else if ("apph5".equals(act)) {
            act_val = addCommonParams(act_val);
            Router.toWebViewActivity(act_val, false);
        } else if ("browserh5".equals(act)) {
            act_val = addCommonParams(act_val);
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.setData(Uri.parse(act_val));
            context.startActivity(intent);
        }
    }

    public static void exection(String act, String act_val,String hostId) {
        if (TextUtils.isEmpty(act) || TextUtils.isEmpty(act_val)) {
            return;
        }
        act_val = addCommonParams(act_val);
        Router.toHalfScreenWebViewActivity(act_val, hostId,false);
    }

    public static String addCommonParams(String url) {
        boolean hasQuery = false;
        try {
            URL urlWrap = new URL(url);
            hasQuery = !TextUtils.isEmpty(urlWrap.getQuery());
        } catch (Exception e) {
            return url;
        }
        String div = hasQuery ? "&" : "?";

        Map<String, String> params = BaseNetImpl.getInstance().getUniversalParams();
        StringBuilder urlParam = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            urlParam.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        urlParam.append("uid").append("=").append(DataBus.instance().getUid());
        url += div + urlParam.toString();
        return url;
    }
}
