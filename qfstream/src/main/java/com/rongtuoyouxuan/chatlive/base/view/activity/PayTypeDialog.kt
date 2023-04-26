package com.rongtuoyouxuan.chatlive.base.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ccom.rongtuoyouxuan.chatlive.base.view.adapter.GoldBuyGridAdapter
import ccom.rongtuoyouxuan.chatlive.base.view.adapter.GoldBuyGridAdapter.BuyGoldClickListener
import com.alibaba.android.arouter.facade.annotation.Route
import com.rongtuoyouxuan.chatlive.base.view.model.RechargeInfoBean
import com.rongtuoyouxuan.chatlive.base.view.viewmodel.BuyGoldViewModel
import com.rongtuoyouxuan.chatlive.router.constants.RouterConstant
import com.rongtuoyouxuan.chatlive.stream.R
import com.rongtuoyouxuan.chatlive.util.LaToastUtil
import com.rongtuoyouxuan.libuikit.LanguageActivity
import kotlinx.android.synthetic.main.rt_dialog_pay_type.*

@Route(path = RouterConstant.PATH_PAY_TYPE_DIALOG)
class PayTypeDialog : LanguageActivity(), View.OnClickListener {
    private var banlanceTxt: TextView? = null
    private var btn: Button? = null
    private var buyGoldViewModel: BuyGoldViewModel? = null
    private var rtCoin = 0
    private var rtRmb = 0
    private var payType = 1 //1：支付宝， 2：微信

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
        banlanceTxt = findViewById(R.id.buyGoldBanlanceTxt)
        btn = findViewById(R.id.buyGoldBtn)
    }

    private fun initListener() {
        btn?.setOnClickListener(this)
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
                payType = 1
                payTypeWeChatRadioBtn.setImageResource(R.drawable.rt_icon_pay_type_radio_normal)
                payTypeAlipayRadioBtn.setImageResource(R.drawable.rt_icon_pay_type_radio_pressed)
            }
            R.id.payTypeWeChatRadioBtn->{
                payType = 2
                payTypeWeChatRadioBtn.setImageResource(R.drawable.rt_icon_pay_type_radio_pressed)
                payTypeAlipayRadioBtn.setImageResource(R.drawable.rt_icon_pay_type_radio_normal)
            }
        }

    }

    private fun doBuy() {
        LaToastUtil.showShort("去充值")
//        if(payDatas == null)return;
//        if(payDatas.size() == 0)return;
//        RechargeInfoBean buyGoldBean = payDatas.get(curPosition);
//        //Google支付
//        buyGoldViewModel.goldToPay(buyGoldBean.getPayPurchaseId(), "weixin");
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