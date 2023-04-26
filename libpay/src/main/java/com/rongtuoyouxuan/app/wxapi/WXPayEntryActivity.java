package com.rongtuoyouxuan.app.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.rongtuoyouxuan.app.R;
import com.rongtuoyouxuan.chatlive.util.LaToastUtil;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * @author ASnow
 * @date 2019/1/12
 * 文件   ChatLive
 * 描述
 */


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    private static final String TAG = "WXPayEntryActivity";
    private IWXAPI api;
    public static final String APP_ID = "wx9ccc2d5883fae2a7";
    private static final String APP_S = "127ce31d7902e0292c50c1a4ed1edd25";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.pay_result);
        api = WXAPIFactory.createWXAPI(this, APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        Log.e(TAG, "onPayFinish, errCode = " + resp.errCode);
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (resp.errCode == 0) {
                LaToastUtil.showShort(getResources().getString(R.string.rt_pay_pay_suc));
            } else if (resp.errCode == -1) {
                LaToastUtil.showShort(getResources().getString(R.string.rt_pay_pay_fail));
            } else if (resp.errCode == -2) {
                LaToastUtil.showShort(getResources().getString(R.string.rt_pay_pay_fail));
            }
            finish();
        }
    }
}

