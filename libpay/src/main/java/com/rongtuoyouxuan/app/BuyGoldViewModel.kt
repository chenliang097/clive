package com.rongtuoyouxuan.app

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.rongtuoyouxuan.chatlive.biz2.model.user.WalletBean
import com.rongtuoyouxuan.chatlive.biz2.user.PayBiz
import com.rongtuoyouxuan.chatlive.databus.DataBus
import com.rongtuoyouxuan.chatlive.net2.RequestListener

class BuyGoldViewModel(application: Application) : AndroidViewModel(application) {
    private var mContext: Context? = null
    private var inGetWeixinInfo = false
    private var inGetZhifubaoInfo = false
    val balanceLiveData: MutableLiveData<Int> = MutableLiveData()

    fun initContext(context: Context?) {
        mContext = context
    }

    /**
     * 根据Type判断是那种支付方式
     * @param purchaseId  商品ID
     * @param payType     支付方式类型
     */
    fun goldToPay(purchaseId: String?, payType: String?) {
        when (payType) {
            "wechat" -> startWeChatPay(purchaseId)
            "ali" -> startALiPay(purchaseId)
        }
    }

    /**
     * 微信支付
     */
    fun startWeChatPay(purchaseId: String?) {
        if (inGetWeixinInfo) {
            return
        }
        inGetWeixinInfo = true
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
    fun startALiPay(purchaseId: String?) {
        if (inGetZhifubaoInfo) {
            return
        }
        inGetZhifubaoInfo = true
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

    fun getBalance() {
        PayBiz.getBalance(DataBus.instance().USER_ID, object : RequestListener<WalletBean> {
            override fun onSuccess(reqId: String?, result: WalletBean?) {
                if (null != result?.data) {
                    balanceLiveData.value = result.data.balance
                } else {
                    balanceLiveData.value = 0
                }
            }

            override fun onFailure(reqId: String?, errCode: String?, msg: String?) {
                balanceLiveData.value = 0
            }
        })
    }

    companion object {
        var isGetPayAllProduct = MutableLiveData<Boolean>()
    }
}