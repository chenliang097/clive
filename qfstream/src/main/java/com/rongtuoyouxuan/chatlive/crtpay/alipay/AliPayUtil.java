package com.rongtuoyouxuan.chatlive.crtpay.alipay;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.rongtuoyouxuan.chatlive.crtpay.Bean.PayResult;
import com.rongtuoyouxuan.chatlive.crtbiz2.model.user.event.PayEvent;
import com.rongtuoyouxuan.chatlive.crtdatabus.liveeventbus.LiveDataBus;
import com.rongtuoyouxuan.chatlive.crtdatabus.liveeventbus.constansts.LiveDataBusConstants;
import com.rongtuoyouxuan.chatlive.stream.R;

import java.util.Map;

public class AliPayUtil {
    private static final int SDK_PAY_FLAG = 1;
    private AliPayUtil instance;
    private Context mContext;
    /**
     * 单例模式初始化GooglePay
     *
     * @return
     */
    public AliPayUtil getInstance() {
        if (instance == null) {
            synchronized (AliPayUtil.class) {
                if (instance == null) {
                    instance = new AliPayUtil();
                }
            }
        }
        return instance;
    }
    public void aliPayInit(Context context){
        mContext = context;

    }
    public void startAliPay(final String resultData){
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask((Activity) mContext);
                Map<String, String> result = alipay.payV2(resultData, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                payHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    private Handler payHandler = new Handler() {
        public void handleMessage(Message msg) {
            @SuppressWarnings("unchecked")
            PayResult payResult = new PayResult((Map<String, String>) msg.obj);

            /**
             * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
             */
            String resultInfo = payResult.getResult();// 同步返回需要验证的信息
            String resultStatus = payResult.getResultStatus();
            // 判断resultStatus 为9000则代表支付成功
            if (TextUtils.equals(resultStatus, "9000")) {
                // 支付成功
                Toast.makeText(mContext, R.string.bill_finish, Toast.LENGTH_LONG).show();
                LiveDataBus.getInstance().with(LiveDataBusConstants.EVENT_KEY_TO_PAY_COMPLETE, PayEvent.class)
                        .setValue(new PayEvent("alipay", true));
            } else {
                // 支付失败
                Toast.makeText(mContext, R.string.rt_pay_pay_fail, Toast.LENGTH_LONG).show();
            }
        }

        ;
    };
}
