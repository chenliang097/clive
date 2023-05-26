package com.rongtuoyouxuan.chatlive.crtpay;

import android.content.Context;

import com.rongtuoyouxuan.chatlive.crtlog.upload.ULog;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WeChatPayUtil {

    public static void startWeChatPay(Context context, String appid, String partnerid, String prepayid, String noncestr, String timestamp, String packageValue, String sign){
        IWXAPI api = WXAPIFactory.createWXAPI(context, null);
        if(api.isWXAppInstalled()){
            ULog.d("clll", "startWeChatPay----" + appid);
            api.registerApp(appid);
        }
        PayReq req = new PayReq();
        req.appId = appid;//你的微信appid
        req.partnerId = partnerid;//商户号
        req.prepayId = prepayid;//预支付交易会话ID
        req.nonceStr = noncestr;//随机字符串
        req.timeStamp = timestamp;//时间戳
        req.packageValue = packageValue;//扩展字段,这里固定填写Sign=WXPay
        req.sign = sign;//签名
        ULog.d("clll", "startWeChatPay----");
//                            req.extData = "app data"; // 可选参数
        // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
        api.sendReq(req);
    }
}
