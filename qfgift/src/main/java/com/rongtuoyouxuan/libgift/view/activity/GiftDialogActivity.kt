package com.rongtuoyouxuan.libgift.view.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.DebouncingUtils
import com.blankj.utilcode.util.SPUtils
import com.rongtuoyouxuan.chatlive.biz2.gift.GiftNewBiz
import com.rongtuoyouxuan.chatlive.biz2.model.gift.GiftSendResData
import com.rongtuoyouxuan.chatlive.databus.DataBus
import com.rongtuoyouxuan.chatlive.databus.liveeventbus.LiveDataBus
import com.rongtuoyouxuan.chatlive.databus.liveeventbus.constansts.LiveDataBusConstants
import com.rongtuoyouxuan.chatlive.log.upload.ULog
import com.rongtuoyouxuan.chatlive.net2.RequestListener
import com.rongtuoyouxuan.chatlive.router.Router
import com.rongtuoyouxuan.chatlive.router.constants.RouterConstant
import com.rongtuoyouxuan.libgift.R
import com.rongtuoyouxuan.libgift.view.layout.GiftNumberSelectPop
import com.rongtuoyouxuan.libgift.viewmodel.GiftHelper
import com.rongtuoyouxuan.libgift.viewmodel.GiftVM
import com.rongtuoyouxuan.libuikit.SimpleActivity
import com.rongtuoyouxuan.libuikit.dp
import com.rongtuoyouxuan.chatlive.util.LaToastUtil
import com.rongtuoyouxuan.libuikit.SimpleFragment
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_gift_dialog.*
import kotlinx.android.synthetic.main.activity_gift_dialog.viewBg
import kotlinx.android.synthetic.main.item_gift_panel_page_item.*
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Route(path = RouterConstant.PATH_ACTIVITY_GIFT_PANEL)
class GiftDialogActivity : SimpleActivity() {

    @Autowired
    @JvmField
    var roomId: String = ""

    @Autowired
    @JvmField
    var sceneId: String = ""

    @Autowired
    @JvmField
    var userId: String = ""

    @Autowired
    @JvmField
    var anchorId: String = ""

    @Autowired
    @JvmField
    var userName: String = ""

    @Autowired
    @JvmField
    var avatar: String = ""

    private var sendGiftTimes = 1

    private var giftVm: GiftVM? = null
    private var mGiftDialogItemFragment:GiftDialogItemFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        isHasStatusBar = false
        super.onCreate(savedInstanceState)

        overridePendingTransition(R.anim.common_bottom_dialog_in, R.anim.common_bottom_dialog_out)

        window?.decorView?.setPadding(0, 0, 0, 0)

        val params = window?.attributes
        params?.gravity = Gravity.BOTTOM
        params?.width = ViewGroup.LayoutParams.MATCH_PARENT
        params?.height = ViewGroup.LayoutParams.WRAP_CONTENT
        window?.attributes = params
    }

    override fun getLayoutResId() = R.layout.activity_gift_dialog

    override fun initData() {
        GiftHelper.giftNum.value = 1
        GiftHelper.giftSelected.value = null
        sendGiftTimes = SPUtils.getInstance().getInt("key_event_send_gift_times", 0)

        giftVm = getViewModel(GiftVM::class.java)
        giftVm?.giftSucVM?.observe(this) {
            mGiftDialogItemFragment =GiftDialogItemFragment.newInstance()
            showFragment()
        }
        giftVm?.balanceLiveData?.observe(this){
            tvCoin.text = "" + it
        }
        giftVm?.getPanel()
        giftVm?.getBalance()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun initListener() {
        ivBox?.setOnClickListener {
            GiftHelper.openUserCardVM.post(DataBus.instance().USER_ID)
            finish()
        }

        tvCoin?.setOnClickListener {
            GiftHelper.isRechargeOpen = true
            GiftHelper.clickGiftRecharge.post(1)
            finish()
        }

        ivRechargeArrow?.setOnClickListener {
            GiftHelper.isRechargeOpen = true
            GiftHelper.clickGiftRecharge.post(1)
            finish()
        }

        tvRecharge?.setOnClickListener {
            GiftHelper.isRechargeOpen = true
            GiftHelper.clickGiftRecharge.post(1)
            finish()
        }

        val pop = GiftNumberSelectPop(this, GiftHelper.giftNumList) {
            GiftHelper.giftNum.value = it
        }
        viewNumBg?.setOnClickListener {
            if (pop.isShowing) {
                pop.dismiss()
            } else {
                pop.showAsDropDown(viewNumBg, -13.5f.dp.toInt(), 0, Gravity.TOP)
                GiftHelper.isShowDialog.postValue(true)
            }
        }

        GiftHelper.giftBalance.observe(this) {
            tvCoin?.text = "$it"
        }

        GiftHelper.giftNum.observe(this) {
            tvSelectNum?.text = "$it"
        }

        GiftHelper.isShowDialog.observe(this) {
            if (it == true) {
                ivNumIcon?.rotation = 180f
            } else {
                ivNumIcon?.rotation = 0f
            }
        }

        tvSendGift?.setOnClickListener {
            val isVisitor = DataBus.instance().isVisitor
            if (isVisitor) {
                LiveDataBus.getInstance()
                    .with(LiveDataBusConstants.EVENT_KEY_TO_SHOW_LOGIN_DIALOG).value = true
                finish()
                return@setOnClickListener
            }
            if (!DebouncingUtils.isValid(it)) return@setOnClickListener

            sendGift()
        }

        LiveDataBus.getInstance().with(LiveDataBusConstants.EVENT_KEY_TO_FINISH_ROOM_ACTIVITY)
            .observe(this) {
                if (this != null && !isFinishing && !isDestroyed) {
                    finish()
                }
            }

        viewBg2?.setOnClickListener {
            if (gpLucky?.visibility == View.INVISIBLE) {
                finish()
            }
        }

        GiftHelper.giftSelected.observe(this) {
            if (it?.mark?.isNotEmpty() == true && it.mark!![0] == 1) {
                gpLucky?.visibility = View.VISIBLE

                viewBg?.setBackgroundResource(R.drawable.icon_gift_lucky_bg)
                tvLuckyTitle?.text = GiftHelper.title
                tvLuckyContent?.text = GiftHelper.content
                ivLuckyDesc?.visibility = View.VISIBLE
            } else if (it?.mark?.isNotEmpty() == true && it.mark!![0] == 2) {
                gpLucky?.visibility = View.VISIBLE

                viewBg?.setBackgroundResource(R.drawable.icon_gift_world_bg)
                tvLuckyTitle?.text = getString(R.string.gift_world_info_title)
                tvLuckyContent?.text = getString(R.string.gift_world_info_content)
                ivLuckyDesc?.visibility = View.INVISIBLE
            } else {
                gpLucky?.visibility = View.INVISIBLE
            }
        }

        ivLuckyDesc?.setOnClickListener {
            if (GiftHelper.helpTitle.isEmpty()) {
                return@setOnClickListener
            }
            Router.toGiftDescActivity(this, GiftHelper.helpTitle, GiftHelper.helpContent)
            finish()
        }
    }

    private fun showFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        mGiftDialogItemFragment?.let {
            transaction.add(R.id.flContainer, it)
                .show(mGiftDialogItemFragment!!)
                .commitAllowingStateLoss()
        }
    }

    private fun sendGift() {
        val giftEntity = GiftHelper.giftSelected.value

        if (giftEntity?.giftId == null) {
            //先选择礼物
            LaToastUtil.showShort(R.string.gift_panel_check_gift_empty)
            return
        }

        val count = GiftHelper.giftNum.value ?: 0

        GiftNewBiz.sendGift(giftEntity.giftId ?: 0,
            roomId, sceneId, userId,
            anchorId, count, userName,
            object : RequestListener<GiftSendResData> {
                override fun onSuccess(reqId: String?, result: GiftSendResData?) {
                    if (null != result?.data) {
//                        trackEvent(giftEntity.type ?: 1)
//                        giftEntity.giftNum = count
//                        giftEntity.userName = receiverName
//                        if (scene == "im") {
//                            GiftHelper.isChatPlayGift = true
//                            GiftHelper.sendGiftVM.post(giftEntity)
//                        }
                        tvCoin.text = "" + result.data.balance
                    }
                }

                override fun onFailure(reqId: String?, errCode: String?, msg: String?) {
                    when (errCode) {
                        "40001" -> {
                            LaToastUtil.showShort(R.string.gift_panel_check_gift_amount_empty)
                            GiftHelper.isRechargeOpen = true
                            GiftHelper.clickGiftRecharge.post(2)
                            finish()
                        }
                        else -> {
                            LaToastUtil.showShort(R.string.gift_side_send_error_toast)
                        }
                    }
                }
            }
        )
    }

//    private fun trackEvent(type: Int = 0) {
//        if (sendGiftTimes > 5) {
//            return
//        }
//        if (type == 1) {
//            sendGiftTimes += 1
//            SPUtils.getInstance().put("key_event_send_gift_times", sendGiftTimes)
//        }
//    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.common_bottom_dialog_in, R.anim.common_bottom_dialog_out)
    }
}