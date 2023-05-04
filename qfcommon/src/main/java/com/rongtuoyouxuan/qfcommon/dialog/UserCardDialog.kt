package com.rongtuoyouxuan.qfcommon.dialog

import android.annotation.SuppressLint
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.rongtuoyouxuan.chatlive.biz2.stream.UserCardBiz
import com.rongtuoyouxuan.chatlive.databus.DataBus
import com.rongtuoyouxuan.chatlive.net2.RequestListener
import com.rongtuoyouxuan.chatlive.router.Router
import com.rongtuoyouxuan.qfcommon.R
import com.rongtuoyouxuan.chatlive.util.LaToastUtil
import com.rongtuoyouxuan.qfcommon.util.UserCardHelper
import com.hbb20.Country
import com.lxj.xpopup.core.BottomPopupView
import com.rongtuoyouxuan.chatlive.biz2.model.user.UserCardInfoBean
import com.rongtuoyouxuan.chatlive.biz2.model.user.UserCardInfoRequest
import com.rongtuoyouxuan.chatlive.image.util.GlideUtils
import com.rongtuoyouxuan.qfcommon.viewmodel.CommonViewModel
import kotlinx.android.synthetic.main.qf_dialog_user_plate.view.*

@SuppressLint("ViewConstructor")
class UserCardDialog(
    private val activity: FragmentActivity,
    //操作者
    private val fUserId: String,
    private val tUserId: String,
    private val roomId: String,
    private val sceneId: String,
    private val anchorId: String
) :
    BottomPopupView(activity) {

    private var isFollow = false
    private var mCommonViewModel:CommonViewModel? = null

    override fun getImplLayoutId(): Int = R.layout.qf_dialog_user_plate

    override fun onCreate() {
        super.onCreate()
        var request = UserCardInfoRequest(tUserId, roomId, sceneId, DataBus.instance().USER_ID)
        UserCardBiz.getLiveUserCardInfo(request,
            listener = object : RequestListener<UserCardInfoBean> {
                override fun onSuccess(reqId: String?, result: UserCardInfoBean?) {
                    if (null != result?.data) {
                        completeData(result.data)
                    }
                }

                override fun onFailure(reqId: String?, errCode: String?, msg: String?) {
                }
            })
    }

    fun initObserver(){
        mCommonViewModel = ViewModelProvider(context as ViewModelStoreOwner).get(CommonViewModel::class.java)
        mCommonViewModel?.addFollowLiveData?.observe(context as LifecycleOwner){
            if(it.errCode == 0){
                updateFollowUI(true)
            }
            LaToastUtil.showShort(it?.errMsg)

        }
        mCommonViewModel?.cancelFollowLiveData?.observe(context as LifecycleOwner){
            if(it.errCode == 0){
                updateFollowUI(false)
            }
            LaToastUtil.showShort(it?.errMsg)
        }
    }

    fun updateFollowUI(followStatus:Boolean){
        when(followStatus){
            true->{//添加关注
                isFollow = true
                userCardFollowBtn.setBackgroundResource(R.drawable.bg_btn_followed)
            }
            false->{//取消关注
                isFollow = false
                userCardFollowBtn.setBackgroundResource(R.drawable.bg_btn_follow)
            }
        }
    }

    private fun completeData(result: UserCardInfoBean.DataBean) {
        GlideUtils.loadImage(context, result.avatar, userCardAvatar, R.drawable.icon_default_avatar)
        userCardNameTxt?.text = result.nick_name
        when (result.sex) {
            "1" -> {
                userCardGenderImg?.setImageResource(R.drawable.qf_stream_gender_male)
                userCardGenderTxt?.text = context.getString(R.string.stream_user_card_male)
            }
            "2" -> {
                userCardGenderImg?.setImageResource(R.drawable.qf_stream_gender_female)
                userCardGenderTxt?.text = context.getString(R.string.stream_user_card_female)
            }
            else -> {
                userCardGenderImg?.setImageResource(R.drawable.qf_stream_gender_male)
                userCardGenderTxt?.text = context.getString(R.string.stream_user_card_male)
            }
        }
        if (result.location?.isNotEmpty() == true) {
            userCardLocationTxt?.text = result.location
        } else {
            userCardLocationTxt?.text = context.resources.getString(R.string.stream_unknown_location1)
        }
        userCardFansTxt?.text = context.resources.getString(R.string.stream_user_card_fans,result.fans_count)
        userCardFollowTxt?.text = context.resources.getString(R.string.stream_user_card_follow,result.follow_count)

        if(fUserId == DataBus.instance().USER_ID){
            userCardLayout3.visibility = View.GONE
        }else{
            userCardLayout3.visibility = View.VISIBLE
        }
        isFollow = result.is_follow
        updateFollowUI(isFollow)
        userCardFollowBtn?.setOnClickListener {
            //关注
            if(isFollow){
                mCommonViewModel?.cancelFollow(tUserId, roomId, sceneId)
            }else{
                mCommonViewModel?.addFollow(tUserId, roomId, sceneId)
            }

        }

        if(anchorId == DataBus.instance().USER_ID){
            userCardRightTxt.text = context.getString(R.string.stream_user_card_manager)
        }else{
            userCardRightTxt.text = context.getString(R.string.stream_user_card_report)
        }

        userCardWindowBtn.setOnClickListener {
            LaToastUtil.showShort("橱窗")
        }


        userCardAvatar?.setOnClickListener {
            Router.toPersonalCenterActivity(result.follow_id)
            this.dismiss()
        }

        userCardRightTxt?.setOnClickListener {
            if(DataBus.instance().USER_ID == anchorId){
                UserCardHelper.managerVM.post(UserCardInfoBean.ProfileUserData(result.follow_id, result.nick_name,
                    result.is_super_admin, result.is_room_admin, result.is_forbid_speak))
            }else if(result.is_super_admin){//超管
                UserCardHelper.managerVM.post(UserCardInfoBean.ProfileUserData(result.follow_id, result.nick_name,
                    result.is_super_admin, result.is_room_admin, result.is_forbid_speak))
            }else if(result.is_room_admin){//房管
                UserCardHelper.managerVM.post(UserCardInfoBean.ProfileUserData(result.follow_id, result.nick_name,
                    result.is_super_admin, result.is_room_admin, result.is_forbid_speak))
            }else{
                LaToastUtil.showShort("举报")
            }
            dismiss()
        }
    }

}