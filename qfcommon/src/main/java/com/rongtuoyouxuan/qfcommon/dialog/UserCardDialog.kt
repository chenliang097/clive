package com.rongtuoyouxuan.qfcommon.dialog

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.os.Message
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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

        handler1.sendEmptyMessageDelayed(0, 200)
    }

    var handler1 = object :Handler(Looper.getMainLooper()){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            initObserver()
        }
    }

    fun initObserver(){
        mCommonViewModel = ViewModelProvider(context as ViewModelStoreOwner).get(CommonViewModel::class.java)
        var request = UserCardInfoRequest(tUserId, roomId, sceneId, DataBus.instance().USER_ID)
        mCommonViewModel?.getUserInfo(request)
        mCommonViewModel?.userInfoLiveData?.observe(context as LifecycleOwner){
            completeData(it.data)
        }
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
                userCardFollowAddBtn.text = context.getString(R.string.stream_follow_cancel)
                userCardFollowAddBtn.setTextColor(context.resources.getColor(R.color.c_333333))
                userCardFollowIcon.visibility = View.GONE
            }
            false->{//取消关注
                isFollow = false
                userCardFollowBtn.setBackgroundResource(R.drawable.bg_btn_follow)
                userCardFollowAddBtn.text = context.getString(R.string.stream_follow)
                userCardFollowAddBtn.setTextColor(context.resources.getColor(R.color.white))
                userCardFollowIcon.visibility = View.VISIBLE
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
        if (result.location.isNotEmpty() == true) {
            userCardLocationTxt?.text = result.location
        } else {
            userCardLocationTxt?.text = context.resources.getString(R.string.stream_unknown_location1)
        }
        userCardFansTxt?.text = context.resources.getString(R.string.stream_user_card_fans,result.fans_count)
        userCardFollowTxt?.text = context.resources.getString(R.string.stream_user_card_follow,result.follow_count)

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

        if(result.is_super_admin){
            userCardRightTxt.visibility = View.GONE
        }else if(result.is_anchor){
            userCardRightTxt.visibility = View.GONE
        }else{
            if(DataBus.instance().USER_ID == anchorId) {
                userCardRightTxt.visibility = View.VISIBLE
                userCardRightTxt.text = context.getString(R.string.stream_user_card_manager)
            }
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
        if(tUserId == DataBus.instance().USER_ID){
            userCardLayout3.visibility = View.GONE
            viewBottom1.visibility = View.GONE
        }else{
            userCardLayout3.visibility = View.VISIBLE
            viewBottom1.visibility = View.VISIBLE
        }

    }

}