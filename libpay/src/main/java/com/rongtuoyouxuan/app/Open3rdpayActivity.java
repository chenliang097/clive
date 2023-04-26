package com.rongtuoyouxuan.app;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.rongtuoyouxuan.app.alipay.AliPayUtil;
import com.rongtuoyouxuan.app.wechatpay.WeChatPayUtil;
import com.rongtuoyouxuan.app.wxapi.WXPayEntryActivity;
import com.rongtuoyouxuan.chatlive.biz2.model.user.PayInfoBean;
import com.rongtuoyouxuan.chatlive.biz2.user.UserBiz;
import com.rongtuoyouxuan.chatlive.net2.RequestListener;
import com.rongtuoyouxuan.chatlive.util.LaToastUtil;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


public class Open3rdpayActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open3rdpay);
        findViewById(R.id.weixinBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LaToastUtil.showShort("微信支付");
                getPayInfo();
            }
        });
        findViewById(R.id.alipayBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LaToastUtil.showShort("支付宝支付");
                AliPayUtil aliPayUtil = new AliPayUtil().getInstance();
                aliPayUtil.aliPayInit(Open3rdpayActivity.this);
                aliPayUtil.startAliPay("app_id=2019010962804728&biz_content=%7B%22subject%22%3A%22%E6%89%98%E5%B8%81%22%2C%22out_trade_no%22%3A%221649322561961463808%22%2C%22total_amount%22%3A%220.01%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22time_expire%22%3A%222023-04-21+16%3A11%3A56%22%7D&charset=utf-8&format=JSON&method=alipay.trade.app.pay&notify_url=https%3A%2F%2Fvictor.vipgz6.91tunnel.com%2Forder%2Fv1%2Fnotify%2Falipay&sign=Cqb3gJB8vjgw2%2B1PIOuDmBLzQQHreytLooT1WhBM%2Ba%2F8ML%2BUvBUINBrUqUCHkHplSkqRhma8YszkWwbA7SAxmk2wceICfKixSqUufglU1i1UEXGxDZTNACqVx7mbZAgSzKMoxnQQ7VL6JgUviLrpFege0s4ai4bGSFS%2B1C%2Fs8howiJrtn4RrEmUdK9jmwVBKLEN%2FfboeUlr7GHnUrrN7JNu2AywzHvGR7pyjo85jzX%2BaMEUhp5qe3mH%2F12vPBpBH%2Ft5pKucnsgMH2bKyLs61kB0zIjoqnGV095Ug0VPEalkNZYkAn29eHeSlYIhmYHVD9uW1rEE%2F6KSehK28KObxAg%3D%3D&sign_type=RSA2&timestamp=2023-04-21+16%3A01%3A56&version=1.0");
            }
        });

    }

    private void getPayInfo(){
        String partnerid = "1641964961";
        String prepay_id = "wx14155428955300d536e37b26705bf60000";
        String nonce = "gdj54CnlymiFY1ZXslLs5ArpmRQtnRSI";
        String timestamp = "1681458868";
        String sign = "gT0yOMSOGhtyOA/y7d5XsGu/vi/eJpx+9C7dBnI+XABVMMa9xPXwOPOCnqNGSdGvaiTxAK/Q+0kafuHiQqY27Ts5EgMsry902SZ/LqexF1o0Y+gQYecKCwOjuNKXZq3twk5BHg+NvvqCzZIvtjgeAsWtYP7kDi9PVaQyrjtaB/UbmqXTlx8V4p2ScCETyqBbrwo5sOjak+ZzHRXRNXKJOWrFou0uSNSy3QjWd6UXgcfjWP20CprENos7Ne0BcPQF3JT13+8h5vvukOTEq42iKqtYXqYQnb0/4SMR1AG8gVxprWwzGCKIwI0hbSOZEttcqco6XVhRveY4e+MOBoZh5w==";
        WeChatPayUtil.startWeChatPay(this, WXPayEntryActivity.APP_ID, partnerid,
                prepay_id, nonce, timestamp, "Sign=WXPay", sign);
//        UserBiz.INSTANCE.getPayInfo(null, 1, 1, 1, 2, new RequestListener<PayInfoBean>() {
//            @Override
//            public void onSuccess(String reqId, PayInfoBean result) {
//                WeChatPayUtil.startWeChatPay(Open3rdpayActivity.this, WXPayEntryActivity.APP_ID, String.valueOf(result.data.pay_info.machid),
//                        result.data.pay_info.prepay_id, result.data.pay_info.nonce, result.data.pay_info.timestamp, "Sign=WXPay", result.data.pay_info.sign);
//            }
//
//            @Override
//            public void onFailure(String reqId, String errCode, String msg) {
//                LaToastUtil.showShort(msg + "--" + errCode);
//            }
//        });
    }
}
