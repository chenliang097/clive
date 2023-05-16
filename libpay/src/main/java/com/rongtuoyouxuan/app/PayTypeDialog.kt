package com.rongtuoyouxuan.app

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import com.alibaba.android.arouter.facade.annotation.Route
import com.rongtuoyouxuan.app.alipay.AliPayUtil
import com.rongtuoyouxuan.app.wechatpay.WeChatPayUtil
import com.rongtuoyouxuan.app.wxapi.WXPayEntryActivity
import com.rongtuoyouxuan.chatlive.biz2.model.user.PayInfoBean
import com.rongtuoyouxuan.chatlive.biz2.model.user.PayInfoRequest
import com.rongtuoyouxuan.chatlive.biz2.user.PayBiz.getPayOrderInfo
import com.rongtuoyouxuan.chatlive.databus.DataBus
import com.rongtuoyouxuan.chatlive.net2.RequestListener
import com.rongtuoyouxuan.chatlive.router.constants.RouterConstant
import com.rongtuoyouxuan.chatlive.util.LaToastUtil
import com.rongtuoyouxuan.libuikit.LanguageActivity
import kotlinx.android.synthetic.main.rt_dialog_pay_type.*

@Route(path = RouterConstant.PATH_PAY_TYPE_DIALOG)
class PayTypeDialog : LanguageActivity(), View.OnClickListener {
    private var buyGoldViewModel: BuyGoldViewModel? = null
    private var rtCoin = 0
    private var rtRmb = 0
    private var payType = 2 //2：支付宝， 1：微信

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.commenDialogStyle)
        setContentView(R.layout.rt_dialog_pay_type)
        setWindowLocation()
        initView()
        initListener()
        initData()
        initObserver()
    }

    private fun setWindowLocation() {
        try {
            val win = this.window
            win.decorView.setPadding(0, 0, 0, 0)
            val lp = win.attributes
            lp.width = WindowManager.LayoutParams.MATCH_PARENT
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT
            lp.gravity = Gravity.BOTTOM //设置对话框底部显示
            win.attributes = lp
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun initView() {
    }

    private fun initListener() {
        payTypeBtn?.setOnClickListener(this)
        payTypeWeChatRadioBtn?.setOnClickListener(this)
        payTypeAlipayRadioBtn?.setOnClickListener(this)
    }

    private fun initObserver() {
        buyGoldViewModel = obtainStreamViewModel()
        buyGoldViewModel!!.initContext(this)
    }

    private fun initData() {
        rtCoin = intent.getIntExtra("rt_coin", 0)
        rtRmb = intent.getIntExtra("rt_rmb", 0)
        payTypeRmbTxt.text = "" + rtRmb
        payTypeCoinTxt.text = resources.getString(R.string.rt_pay_type_coin, rtCoin)
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.payTypeBtn->{
                doBuy()
            }
            R.id.payTypeAlipayRadioBtn->{
                payType = 2
                payTypeWeChatRadioBtn.setImageResource(R.drawable.rt_icon_pay_type_radio_normal)
                payTypeAlipayRadioBtn.setImageResource(R.drawable.rt_icon_pay_type_radio_pressed)
            }
            R.id.payTypeWeChatRadioBtn->{
                payType = 1
                payTypeWeChatRadioBtn.setImageResource(R.drawable.rt_icon_pay_type_radio_pressed)
                payTypeAlipayRadioBtn.setImageResource(R.drawable.rt_icon_pay_type_radio_normal)
            }
        }

    }

    private fun doBuy() {
        val payInfo = PayInfoRequest(payType, 1, DataBus.instance().USER_ID, 2)
        getPayOrderInfo(payInfo, object : RequestListener<PayInfoBean> {
            override fun onSuccess(reqId: String, result: PayInfoBean) {
                if (payType == 2) {
                    val aliPayUtil: AliPayUtil = AliPayUtil().getInstance()
                    aliPayUtil.aliPayInit(this@PayTypeDialog)
                    aliPayUtil.startAliPay(result.data.pay_info.order_str)
                } else if (payType == 1) {
                    WeChatPayUtil.startWeChatPay(
                        this@PayTypeDialog,
                        WXPayEntryActivity.APP_ID,
                        result.data.pay_info.machid.toString(),
                        result.data.pay_info.prepay_id,
                        result.data.pay_info.nonce,
                        result.data.pay_info.timestamp,
                        "Sign=WXPay",
                        result.data.pay_info.sign
                    )
                }
            }

            override fun onFailure(reqId: String, errCode: String, msg: String) {
                LaToastUtil.showShort("$msg--$errCode")
            }
        })
    }

    private fun obtainStreamViewModel(): BuyGoldViewModel {
        return ViewModelProviders.of(this).get(BuyGoldViewModel::class.java)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1003) {
            //充值金币不做刷新
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(0, 0)
    }
}