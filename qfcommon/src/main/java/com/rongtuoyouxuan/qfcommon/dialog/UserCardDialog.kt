package com.rongtuoyouxuan.qfcommon.dialog

import android.annotation.SuppressLint
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.rongtuoyouxuan.chatlive.biz2.model.login.response.BatchUserInfo
import com.rongtuoyouxuan.chatlive.biz2.model.stream.LiveUserData
import com.rongtuoyouxuan.chatlive.biz2.model.stream.LiveUserRes
import com.rongtuoyouxuan.chatlive.biz2.model.stream.ProfileUserData
import com.rongtuoyouxuan.chatlive.biz2.stream.StreamBiz
import com.rongtuoyouxuan.chatlive.biz2.stream.UserCardBiz
import com.rongtuoyouxuan.chatlive.databus.DataBus
import com.rongtuoyouxuan.chatlive.databus.liveeventbus.LiveDataBus
import com.rongtuoyouxuan.chatlive.databus.liveeventbus.constansts.LiveDataBusConstants
import com.rongtuoyouxuan.chatlive.net2.BaseModel
import com.rongtuoyouxuan.chatlive.net2.RequestListener
import com.rongtuoyouxuan.chatlive.router.Router
import com.rongtuoyouxuan.qfcommon.R
import com.rongtuoyouxuan.chatlive.util.LaToastUtil
import com.rongtuoyouxuan.qfcommon.util.UserCardHelper
import com.hbb20.Country
import com.lxj.xpopup.core.BottomPopupView
import kotlinx.android.synthetic.main.qf_dialog_user_plate.view.*

/**
 * 
 * date:2022/8/11-15:21
 * des: 资料卡
 */
@SuppressLint("ViewConstructor")
class UserCardDialog(
    private val activity: FragmentActivity,
    //房主UID
    private val hostId: Long,
    private val uid: Long,
    //是否需要灯牌
    private val isNeedLightBoard: Boolean = true,
    private val groupId: String? = "0",
    private val liveId: String? = ""
) :
    BottomPopupView(activity) {

    private var loginUID = 0L

    override fun getImplLayoutId(): Int = R.layout.qf_dialog_user_plate

    override fun onCreate() {
        super.onCreate()
        loginUID = DataBus.instance().userInfo.value?.user_info?.userId ?: 0L
        UserCardBiz.getLiveUserCardInfo(
            uid = uid,
            listener = object : RequestListener<LiveUserData> {
                override fun onSuccess(reqId: String?, result: LiveUserData?) {
                    if (null != result?.data) {
                        completeData(result.data)
                        val liveUserRes = result.data
                        val batchUserInfo = BatchUserInfo()
                        batchUserInfo.uid = liveUserRes.userId!!;
                        batchUserInfo.nickname = liveUserRes.nickname;
                        batchUserInfo.gender = liveUserRes.gender!!;
                        batchUserInfo.avatar = liveUserRes.avatar;
                        batchUserInfo.signature = liveUserRes.signature;
                        batchUserInfo.userLevel = liveUserRes.userLevel!!;
                        batchUserInfo.anchorLevel = liveUserRes.anchorLevel!!;
                        batchUserInfo.isOnLine = liveUserRes.isOnLine == true;
                        batchUserInfo.isOnLive = liveUserRes.isOnLive == true;
                        batchUserInfo.liveId = liveUserRes.liveId!!;
                        batchUserInfo.realcertStatus = liveUserRes.realcertStatus!!;
                    }
                }

                override fun onFailure(reqId: String?, errCode: String?, msg: String?) {
                }
            })
    }

    private fun completeData(entity: LiveUserRes) {
        userCardAvatar?.bindData(entity.avatar)
        userCardNameTxt?.text = entity.nickname
        when (entity.gender) {
            1 -> {
                userCardGenderImg?.setImageResource(R.drawable.qf_stream_gender_male)
            }
            2 -> {
                userCardGenderImg?.setImageResource(R.drawable.qf_stream_gender_female)
            }
            else -> {
                userCardGenderImg?.setImageResource(R.drawable.icon_sex_other)
            }
        }
        userCardIdTxt?.text = activity.getString(R.string.live_dialog_usercard_id, entity.showId)
        if (entity.countryCode?.isNotEmpty() == true) {
            ivUserCardLocationTxt?.setImageResource(Country.getFlagResID(entity.countryCode!!))
        } else {
            ivUserCardLocationTxt?.visibility = View.INVISIBLE
        }
        if (entity.signature?.isNotEmpty() == true) {
            userCardSignTxt?.text = entity.signature
        } else {
            userCardSignTxt?.visibility = View.GONE
        }
        userCardFansTxt?.text = "${entity.fansTotal}"
        userCardFollowTxt?.text = "${entity.followTotal}"
        userCardSendTxt?.text = "${entity.sendCoin}"

        userCardHostLevelImg?.visibility = if (entity.realcertStatus == 1 || (entity.userLevel
                ?: 0) > 30
        ) VISIBLE else GONE

        val isHost = loginUID == hostId
        if (isHost) {
            userCardAvatar?.isEnabled = false
            //主播点击
            if (hostId == uid) {
                //主播信息
                userCardRightTxt?.visibility = View.INVISIBLE
                rlLayout?.visibility = View.GONE
                viewLayout?.visibility = View.GONE
            } else {
                //观众信息
                userCardRightTxt?.visibility = View.VISIBLE
                userCardRightTxt?.setImageResource(R.drawable.icon_dialog_user_card_manager)
                userCardRightTxt?.setOnClickListener {
                    //管理
                    UserCardHelper.managerVM.post(
                        ProfileUserData(
                            entity.userId ?: 0L,
                            entity.nickname ?: "",
                            entity.avatar ?: ""
                        )
                    )
                    this.dismiss()
                }
                rlLayout?.visibility = View.VISIBLE
                viewLayout?.visibility = View.VISIBLE
                if (!isNeedLightBoard) {
                    userCardATBtn?.visibility = View.VISIBLE
                    userCardATView?.visibility = View.VISIBLE
                    userCardPersonBtn?.visibility = View.VISIBLE
                    userCardPersonView?.visibility = View.VISIBLE
                    userCardAddFollowBtn?.visibility = View.VISIBLE
                } else {
                    userCardATBtn?.visibility = View.INVISIBLE
                    userCardATView?.visibility = View.INVISIBLE
                    userCardPersonBtn?.visibility = View.INVISIBLE
                    userCardPersonView?.visibility = View.INVISIBLE
                    userCardAddFollowBtn?.visibility = View.VISIBLE
                }
                followView(entity.isFollows)
            }
            llLightBoard?.visibility = View.GONE
            viewBoard?.visibility = View.GONE
        } else {
            //观众点击
            if (hostId == uid) {
                //主播信息
                userCardAvatar?.isEnabled = true
                userCardRightTxt?.visibility = View.VISIBLE
                followView(entity.isFollows)
                rlLayout?.visibility = View.VISIBLE
                viewLayout?.visibility = View.VISIBLE
                userCardRightTxt?.visibility = View.VISIBLE
                userCardRightTxt?.setImageResource(R.drawable.icon_dialog_user_card_report)
                userCardRightTxt?.setOnClickListener {
                    val isVisitor = DataBus.instance().userInfo.value?.user_info?.isVisitor
                    if (isVisitor == true) {
                        toLogin()
                        return@setOnClickListener
                    }
                    //举报
                    UserCardHelper.reportVM.post(
                        ProfileUserData(
                            entity.userId ?: 0L,
                            entity.nickname ?: "",
                            entity.avatar ?: ""
                        )
                    )
                    this.dismiss()
                }
                if (isNeedLightBoard) {
                    llLightBoard?.visibility =
                        if (entity.isFansGroup == false) View.VISIBLE else View.GONE
                    viewBoard?.visibility =
                        if (entity.isFansGroup == false) View.VISIBLE else View.GONE
                } else {
                    llLightBoard?.visibility = View.GONE
                    viewBoard?.visibility = View.GONE
                }
            } else if (loginUID == uid) {
                //观众自己
                userCardAvatar?.isEnabled = true
                userCardRightTxt?.visibility = View.INVISIBLE
                rlLayout?.visibility = View.GONE
                viewLayout?.visibility = View.GONE
                llLightBoard?.visibility = View.GONE
                viewBoard?.visibility = View.GONE
            } else {
                //观众别人
                userCardAvatar?.isEnabled = true
                userCardAddFollowBtn?.visibility = View.VISIBLE
                followView(entity.isFollows)
                userCardRightTxt?.visibility = View.VISIBLE
                userCardRightTxt?.setImageResource(R.drawable.icon_dialog_user_card_report)
                rlLayout?.visibility = View.VISIBLE
                viewLayout?.visibility = View.VISIBLE
                userCardRightTxt?.setOnClickListener {
                    val isVisitor = DataBus.instance().userInfo.value?.user_info?.isVisitor
                    if (isVisitor == true) {
                        toLogin()
                        return@setOnClickListener
                    }
                    //举报
                    UserCardHelper.reportVM.post(
                        ProfileUserData(
                            entity.userId ?: 0L,
                            entity.nickname ?: "",
                            entity.avatar ?: ""
                        )
                    )
                    this.dismiss()
                }
                llLightBoard?.visibility = View.GONE
                viewBoard?.visibility = View.GONE
            }
        }

        userCardATBtn?.setOnClickListener {
            val isVisitor = DataBus.instance().userInfo.value?.user_info?.isVisitor
            if (isVisitor == true) {
                toLogin()
                return@setOnClickListener
            }
            //@
            UserCardHelper.atUserVM.post(
                ProfileUserData(
                    entity.userId ?: 0L,
                    entity.nickname ?: "",
                    entity.avatar ?: ""
                )
            )
            this.dismiss()
        }

        userCardAddFollowBtn?.setOnClickListener {
            val isVisitor = DataBus.instance().userInfo.value?.user_info?.isVisitor
            if (isVisitor == true) {
                toLogin()
                return@setOnClickListener
            }
            //关注
            follow(entity)
        }

        if (!isNeedLightBoard) {
            userCardPersonBtn?.text = activity.getString(R.string.user_card_dialog_send_gift_tip)
        }

        userCardPersonBtn?.setOnClickListener {
            val isVisitor = DataBus.instance().userInfo.value?.user_info?.isVisitor
            if (isVisitor == true) {
                toLogin()
                return@setOnClickListener
            }
            //个人主页
            if (!isNeedLightBoard) {
                entity.userId?.let {
//                    Router.toGiftPanelActivity(
//                        activity, groupId?.toLongOrNull() ?: 0, 0, it, "im",
//                        entity.nickname ?: ""
//                    )
                }
            } else {
                entity.userId?.let {
                    Router.toUserInfoActivity(activity, it)
                }
            }
            this.dismiss()
        }

        if (isNeedLightBoard) {
            llLightBoard?.setOnClickListener {
                val isVisitor = DataBus.instance().userInfo.value?.user_info?.isVisitor
                if (isVisitor == true) {
                    toLogin()
                    return@setOnClickListener
                }
                //打开灯牌
                LaToastUtil.showShort(activity.getString(R.string.live_dialog_usercard_ligth_board_toast))
                UserCardHelper.lightBoardVM.post(
                    ProfileUserData(
                        entity.userId ?: 0L,
                        entity.nickname ?: "",
                        entity.avatar ?: ""
                    )
                )
                this.dismiss()
            }
        }

        userCardAvatar?.setOnClickListener {
            val isVisitor = DataBus.instance().userInfo.value?.user_info?.isVisitor
            if (isVisitor == true) {
                toLogin()
                return@setOnClickListener
            }
            entity.userId?.let {
                Router.toUserInfoActivity(activity, it)
            }
            this.dismiss()
        }
    }

    private fun followView(isFollows: Boolean?) {
        userCardAddFollowBtn?.text =
            if (isFollows == true) activity.getString(R.string.stream_user_card_delete_folowed) else
                activity.getString(R.string.stream_user_card_add_folow)

        userCardAddFollowBtn?.setTextColor(
            if (isFollows == true) ContextCompat.getColor(
                activity,
                R.color.c_666666
            ) else ContextCompat.getColor(activity, R.color.c_chat_8000ff)
        )
    }

    private fun follow(entity: LiveUserRes) {
    }

    //游客跳转到登录
    private fun toLogin() {
        LiveDataBus.getInstance().with(LiveDataBusConstants.EVENT_KEY_TO_SHOW_LOGIN_DIALOG).value =
            true
        dismiss()
    }
}