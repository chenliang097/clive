package com.rongtuoyouxuan.chatlive.base.view.viewmodel;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class BuyGoldViewModel extends AndroidViewModel {

    private Context mContext;
    private boolean inGetPaypalInfo = false;
    private boolean inGetVtcInfo = false;
    private boolean inGetDokyInfo = false;
    private boolean inGetWeixinInfo = false;
    private boolean inGetZhifubaoInfo = false;
    public BuyGoldViewModel(@NonNull Application application) {
        super(application);
    }
    public static MutableLiveData<Boolean> isGetPayAllProduct = new MutableLiveData<>();
    public MutableLiveData<Boolean> isGetPayGoogleProduct = new MutableLiveData<>();

    public void initContext(Context context){
        mContext = context;
    }

    public void initGooglePay(List<String> mSkuList){
    }

    /**
     * 根据Type判断是那种支付方式
     * @param purchaseId  商品ID
     * @param payType     支付方式类型
     */
    public void goldToPay(String purchaseId, String payType) {
        switch (payType){
            case "wechat":
                startWeChatPay(purchaseId);
                break;
            case "ali":
                startALiPay(purchaseId);
                break;
        }
    }

    /**
     * 微信支付
     */
    public void startWeChatPay(String purchaseId){
        if (inGetWeixinInfo) {
            return;
        }
        inGetWeixinInfo = true;
//        PayBiz.getInstance().getWeChatPayMsg(null, purchaseId, "wx_app", new RequestListener<WeChatPayMsgModel>() {
//            @Override
//            public void onSuccess(String reqId, WeChatPayMsgModel result) {
//                inGetWeixinInfo = false;
//                if(result.errCode == 0){
//                    WeChatPayMsgModel.DataBean msgData = result.getData();
//                    WeChatPayUtil.startWeChatPay(mContext,msgData.getAppid(),msgData.getMch_id(),msgData.getPrepay_id(),msgData.getNonce_str(),msgData.getTimestamp()+"","Sign=WXPay",msgData.getSign());
//                }
//            }
//
//            @Override
//            public void onFailure(String reqId, String errCode, String msg) {
//                inGetWeixinInfo = false;
//            }
//        });

    }

    /**
     * 支付宝支付
     * @param purchaseId 订单id
     */
    public void startALiPay(final String purchaseId){
        if (inGetZhifubaoInfo) {
            return;
        }
        inGetZhifubaoInfo = true;
//        PayBiz.getInstance().getALiPayMsg(null, purchaseId, "alipay_app", new RequestListener<ALiPayMsgModel>() {
//            @Override
//            public void onSuccess(String reqId, ALiPayMsgModel result) {
//                inGetZhifubaoInfo = false;
//                AliPayUtil aliPayUtil = new AliPayUtil().getInstance();
//                aliPayUtil.aliPayInit(mContext);
//                aliPayUtil.startAliPay(result.getData().getSign());
//            }
//
//            @Override
//            public void onFailure(String reqId, String errCode, String msg) {
//                inGetZhifubaoInfo = false;
//            }
//        });
    }

}
