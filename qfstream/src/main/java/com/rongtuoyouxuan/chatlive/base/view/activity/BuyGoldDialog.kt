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
import com.rongtuoyouxuan.chatlive.router.Router
import com.rongtuoyouxuan.chatlive.router.constants.RouterConstant
import com.rongtuoyouxuan.chatlive.stream.R
import com.rongtuoyouxuan.libuikit.LanguageActivity

@Route(path = RouterConstant.PATH_BUY_GOLD_DIALOG)
class BuyGoldDialog : LanguageActivity(), View.OnClickListener {
    private var banlanceTxt: TextView? = null
    private var btn: Button? = null
    private var recyclerView: RecyclerView? = null
    private var goldBuyGridAdapter: GoldBuyGridAdapter? = null
    private var buyGoldViewModel: BuyGoldViewModel? = null
    private var curPosition = 0
    private var fromSource: String? = null
    private val rechargeCoins = intArrayOf(10, 60, 300, 980, 2980, 5180)
    private val rechargeRmb = intArrayOf(1, 6, 30, 98, 298, 518)
    private var payDatas: MutableList<RechargeInfoBean> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.commenDialogStyle)
        setContentView(R.layout.rt_dialog_buy_gold)
        fromSource = intent.getStringExtra("fromSource")
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
        recyclerView = findViewById(R.id.buyGoldRecyclerView)
        banlanceTxt = findViewById(R.id.buyGoldBanlanceTxt)
        btn = findViewById(R.id.buyGoldBtn)
    }

    private fun initListener() {
        btn!!.setOnClickListener(this)
    }

    private fun initObserver() {
        buyGoldViewModel = obtainStreamViewModel()
        buyGoldViewModel!!.initContext(this)
    }

    private fun initData() {
        //初始化数据
        payDatas = ArrayList()
        for (i in rechargeCoins.indices) {
            val payBean = RechargeInfoBean()
            payBean.coin = rechargeCoins[i]
            payBean.rmp = rechargeRmb[i]
            payDatas.add(payBean)
        }
        val gridLayoutManager = GridLayoutManager(this, 3)
        recyclerView!!.layoutManager = gridLayoutManager
        goldBuyGridAdapter = GoldBuyGridAdapter(this, payDatas)
        recyclerView!!.adapter = goldBuyGridAdapter
        goldBuyGridAdapter!!.updateView(curPosition)
        goldBuyGridAdapter!!.setBuyGoldClickListener(object : BuyGoldClickListener {
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