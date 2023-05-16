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
import com.rongtuoyouxuan.chatlive.biz2.model.stream.FansListBean
import com.rongtuoyouxuan.chatlive.biz2.model.stream.FollowStatusBean
import com.rongtuoyouxuan.chatlive.biz2.model.user.UserCardInfoBean
import com.rongtuoyouxuan.chatlive.biz2.model.user.UserCardInfoRequest
import com.rongtuoyouxuan.chatlive.biz2.user.UserRelationBiz
import com.rongtuoyouxuan.chatlive.databus.liveeventbus.LiveDataBus
import com.rongtuoyouxuan.chatlive.databus.liveeventbus.constansts.LiveDataBusConstants
import com.rongtuoyouxuan.chatlive.image.util.GlideUtils
import com.rongtuoyouxuan.libuikit.dialog.BottomDialog
import com.rongtuoyouxuan.qfcommon.viewmodel.CommonViewModel
import com.zhihu.matisse.MatisseUtil
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
    private val anchorId: String,
    private val isSuperManager: Boolean,
    private val isRoomManger: Boolean,
) :
    BottomPopupView(activity) {

    private var isFollow = false

    override fun getImplLayoutId(): Int = R.layout.qf_dialog_user_plate

    override fun onCreate() {
        super.onCreate()
        initObserver()
//        handler1.sendEmptyMessageDelayed(0, 500)
    }

    var handler1 = object :Handler(Looper.getMainLooper()){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            completeData(msg.obj as UserCardInfoBean.DataBean)
        }
    }

    fun initObserver(){
        var request = UserCardInfoRequest(tUserId, roomId, sceneId, DataBus.instance().USER_ID)
        UserCardBiz.getLiveUserCardInfo(
            request,
            null,
            object : RequestListener<UserCardInfoBean?> {
                override fun onSuccess(reqId: String, result: UserCardInfoBean?) {
                    result?.data?.let {
                        var msg = Message()
                        msg.obj = result.data
                        handler1.sendMessageDelayed(msg, 200)
                    }
                }

                override fun onFailure(reqId: String, errCode: String, msg: String) {}
            })

    }

    fun updateFollowUI(followStatus:Boolean){
        when(followStatus){
            true->{//已关注
                isFollow = true
                userCardFollowBtn.setBackgroundResource(R.drawable.bg_btn_followed)
                userCardFollowAddBtn.text = context.getString(R.string.stream_follow_cancel)
                userCardFollowAddBtn.setTextColor(context.resources.getColor(R.color.c_333333))
                userCardFollowIcon.visibility = View.GONE
            }
            false->{//未关注
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
            "0" -> {
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

        updateFollowUI(result.is_follow)
        userCardFollowBtn?.setOnClickListener {
            //关注
            if(isFollow){
                cancelFollowDialog()
            }else{
                addFollow(DataBus.instance().USER_ID, tUserId)
            }

        }

        if(isSuperManager && DataBus.instance().USER_ID != result.follow_id){
            userCardRightTxt.visibility = View.VISIBLE
            userCardRightTxt.text = context.getString(R.string.stream_user_card_manager)
        }else if(DataBus.instance().USER_ID == anchorId && DataBus.instance().USER_ID != result.follow_id){
            userCardRightTxt.visibility = View.VISIBLE
            userCardRightTxt.text = context.getString(R.string.stream_user_card_manager)
        }else if(isRoomManger && DataBus.instance().USER_ID != result.follow_id){
            userCardRightTxt.visibility = View.VISIBLE
            userCardRightTxt.text = context.getString(R.string.stream_user_card_manager)
        }else{
            userCardRightTxt.visibility = View.GONE
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

    fun addFollow(fUserId:String,tUserId:String){
        UserRelationBiz.instance?.addFollow(null, fUserId, tUserId, object : RequestListener<FollowStatusBean?> {
            override fun onSuccess(reqId: String, result: FollowStatusBean?) {
                if(result?.errCode == 0) {
                    updateFollowUI(true)
                    if(tUserId == anchorId){
                        UserCardHelper.followAnchorVM.post(true)
                    }
                }
            }

            override fun onFailure(reqId: String, errCode: String, msg: String) {
                try {
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        })
    }

    fun cancelFollow(fUserId:String,tUserId:String){
        UserRelationBiz.instance?.cancelFollow(null, fUserId, tUserId, object : RequestListener<FollowStatusBean?> {
            override fun onSuccess(reqId: String, result: FollowStatusBean?) {
                if(result?.errCode == 0) {
                    updateFollowUI(false)
                    if(tUserId == anchorId){
                        UserCardHelper.followAnchorVM.post(false)
                    }
                }
            }

            override fun onFailure(reqId: String, errCode: String, msg: String) {

            }
        })
    }

    private fun cancelFollowDialog(){
        val builder = BottomDialog.Builder(context)
        builder.setTitle(context.getString(R.string.stream_follow_cancel_tip))
        builder.setPositiveButton(context.getString(R.string.confirm),
            { dialog, p1 ->
                cancelFollow(DataBus.instance().USER_ID, tUserId)
                dialog?.dismiss()
            }, context.resources.getColor(R.color.rt_c_3478F6), R.drawable.bg_page_more_bottom)

        builder.setNegativeButton(R.string.cancel) { dialog, which -> dialog.dismiss() }
        builder.create().show()
    }


    override fun onDestroy() {
        super.onDestroy()
    }

}