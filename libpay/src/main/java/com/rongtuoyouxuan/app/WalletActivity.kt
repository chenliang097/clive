package com.rongtuoyouxuan.app

import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.BarUtils
import com.rongtuoyouxuan.chatlive.biz2.model.user.WalletBean
import com.rongtuoyouxuan.chatlive.biz2.user.PayBiz
import com.rongtuoyouxuan.chatlive.databus.DataBus
import com.rongtuoyouxuan.chatlive.net2.RequestListener
import com.rongtuoyouxuan.chatlive.router.Router
import com.rongtuoyouxuan.chatlive.router.constants.RouterConstant
import com.rongtuoyouxuan.chatlive.util.LaToastUtil
import com.rongtuoyouxuan.libuikit.SimpleActivity
import kotlinx.android.synthetic.main.rt_stream_activity_wallet.*

@Route(path = RouterConstant.PATH_MY_WALLET)
class WalletActivity : SimpleActivity(), View.OnClickListener {
    private var goldBuyGridAdapter: GoldBuyGridAdapter? = null
    private var buyGoldViewModel: BuyGoldViewModel? = null
    private var curPosition = 0
    private var fromSource: String? = null
    private val rechargeCoins = intArrayOf(10, 60, 300, 980, 2980, 5180)
    private val rechargeRmb = intArrayOf(1, 6, 30, 98, 298, 518)
    private var payDatas: MutableList<RechargeInfoBean> = ArrayList()


    override fun getLayoutResId(): Int {
        return R.layout.rt_stream_activity_wallet
    }

    override fun initListener() {
        buyGoldBtn!!.setOnClickListener(this)
        initObserver()
    }

    private fun initObserver() {
        buyGoldViewModel = obtainStreamViewModel()
        buyGoldViewModel!!.initContext(this)
    }

    override fun initData() {
        mTitleBar.setTitleText(getString(R.string.stream_wallet_title))
        mTitleBar.setTitleTextColor(resources.getColor(R.color.white))
        mTitleBar.setLeftIco(R.drawable.icon_white_back)
        mTitleBar.setTitleRight(getString(R.string.stream_wallet_title_right))
        mTitleBar.setTitleRightTextColor(resources.getColor(R.color.c_40_white))
        fromSource = intent.getStringExtra("fromSource")

        getBalance()
        //初始化数据
        payDatas = ArrayList()
        for (i in rechargeCoins.indices) {
            val payBean = RechargeInfoBean()
            payBean.coin = rechargeCoins[i]
            payBean.rmp = rechargeRmb[i]
            payDatas.add(payBean)
        }
        val gridLayoutManager = GridLayoutManager(this, 3)
        buyGoldRecyclerView!!.layoutManager = gridLayoutManager
        goldBuyGridAdapter = GoldBuyGridAdapter(this, payDatas)
        buyGoldRecyclerView!!.adapter = goldBuyGridAdapter
        goldBuyGridAdapter!!.updateView(curPosition)
        goldBuyGridAdapter!!.setBuyGoldClickListener(object : GoldBuyGridAdapter.BuyGoldClickListener {
            override fun onBuyGoldClickListener(position: Int) {
                curPosition = position
                goldBuyGridAdapter!!.updateView(position)
            }
        })
    }

    override fun onClick(view: View) {
        if (view.id == R.id.buyGoldBtn) { //购买
            var buyGoldBean = payDatas[curPosition];
            Router.toPayTypeDialogg(buyGoldBean.coin, buyGoldBean.rmp)
        }
    }

    private fun getBalance(){
        PayBiz.getMyWallet(DataBus.instance().USER_ID, object : RequestListener<WalletBean> {
            override fun onSuccess(reqId: String, result: WalletBean) {
                walletBalanceTxt?.text = "" + result.data.balance
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

    override fun statusBarSetting() {
        //设置状态栏 默认为白色
        BarUtils.transparentStatusBar(this)
        BarUtils.setStatusBarLightMode(this, true)
    }
}