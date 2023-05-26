package com.rongtuoyouxuan.chatlive.live.view.dialog

import android.annotation.SuppressLint
import android.text.TextUtils
import android.view.View
import androidx.fragment.app.FragmentActivity
import com.rongtuoyouxuan.chatlive.base.utils.LiveRoomHelper
import com.rongtuoyouxuan.chatlive.crtbiz2.model.stream.LiveJoinGroupResData
import com.rongtuoyouxuan.chatlive.crtbiz2.stream.StreamBiz
import com.rongtuoyouxuan.chatlive.crtimage.util.GlideUtils
import com.rongtuoyouxuan.chatlive.crtnet.RequestListener
import com.rongtuoyouxuan.chatlive.stream.R
import com.rongtuoyouxuan.chatlive.crtuikit.TransferLoadingUtil
import com.rongtuoyouxuan.chatlive.crtutil.util.LaToastUtil
import com.lxj.xpopup.core.BottomPopupView
import kotlinx.android.synthetic.main.dialog_light_board.view.*

/**
 *
 * date:2022/8/12-11:19
 * des: 粉丝灯牌
 */
@SuppressLint("ViewConstructor")
class LiveLightBoardDialog(
    private val activity: FragmentActivity,
    private val liveId: Long,
    private val hostId: Long,
    private val hostHeader: String,
    private val hostName: String,
    private val groupId: Long = 0,
    private val isGameModel: Boolean = false,
    val result: (groupName: String, groupId: Long) -> Unit
) :
    BottomPopupView(activity) {

    override fun getImplLayoutId(): Int = R.layout.dialog_light_board

    override fun onCreate() {
        super.onCreate()
        if (!TextUtils.isEmpty(hostHeader)) {
            GlideUtils.loadCircleImage(
                activity,
                hostHeader,
                ivHeader,
                R.drawable.icon_default_avatar
            )
        }
        tvTitle?.text =
            activity.getString(R.string.live_dialog_usercard_ligth_board_title, hostName)

        if (groupId == 0L) {
            tvLightPay?.visibility = View.VISIBLE
            tvOpenGroup?.visibility = View.INVISIBLE
            tvGoon?.visibility = View.INVISIBLE

            tvLightPay?.setOnClickListener {
                TransferLoadingUtil.showDialogLoading(activity)
                StreamBiz.liveJoinGroup(
                    hostId,
                    liveId,
                    null,
                    object : RequestListener<LiveJoinGroupResData> {
                        override fun onSuccess(reqId: String?, result: LiveJoinGroupResData?) {
                            TransferLoadingUtil.dismissDialogLoading(activity)
                            result?.data?.let {
                                tvLightPay?.visibility = View.INVISIBLE
                                tvOpenGroup?.visibility = View.VISIBLE
                                tvGoon?.visibility = View.VISIBLE
                                if (it.groupName?.isNotEmpty() == true) {
                                    result(it.groupName!!, it.groupId ?: 0L)
                                }
                                it.groupId?.let {
                                    click(it)
                                }
                            }
                        }

                        override fun onFailure(reqId: String?, errCode: String?, msg: String?) {
                            TransferLoadingUtil.dismissDialogLoading(activity)
                            if (errCode == "13331000") {
                                dismiss()
                                LaToastUtil.showShort(R.string.gift_panel_check_gift_amount_empty)
                            } else if (msg?.isNotEmpty() == true) {
                                LaToastUtil.showShort(msg)
                            }
                        }
                    })
            }
        } else {
            tvLightPay?.visibility = View.INVISIBLE
            tvOpenGroup?.visibility = View.VISIBLE
            tvGoon?.visibility = View.VISIBLE
            click(groupId)
        }
    }

    private fun click(groupId: Long) {
        tvOpenGroup?.setOnClickListener {
            //最小化，没有权限，先申请权限
            LiveRoomHelper.liveFloatingWindow.post(groupId)
            if (isGameModel) {
                dismiss()
            }
        }

        tvGoon?.setOnClickListener {
            dismiss()
        }
    }
}