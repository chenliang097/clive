package com.rongtuoyouxuan.chatlive.base.view.activity

import android.view.View
import android.view.View.OnClickListener
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.BarUtils
import com.rongtuoyouxuan.chatlive.base.viewmodel.PersonalCenterViewModel
import com.rongtuoyouxuan.chatlive.biz2.model.user.PersonalCenterInfoBean
import com.rongtuoyouxuan.chatlive.image.util.GlideUtils
import com.rongtuoyouxuan.chatlive.router.Router
import com.rongtuoyouxuan.chatlive.router.constants.RouterConstant
import com.rongtuoyouxuan.chatlive.stream.R
import com.rongtuoyouxuan.chatlive.util.LaToastUtil
import com.rongtuoyouxuan.libuikit.SimpleActivity
import kotlinx.android.synthetic.main.rt_activity_personal_center.*


@Route(path = RouterConstant.PATH_PERSONAL_CENTER)
class PersonalCenterActivity: SimpleActivity(),OnClickListener {

    private var personalCenterViewModel:PersonalCenterViewModel? = null
    private var userId:String? = ""

    override fun getLayoutResId(): Int {
        return R.layout.rt_activity_personal_center
    }

    override fun initData() {
        userId = intent.getStringExtra("userId")
        personalCenterViewModel = ViewModelProvider(this).get(PersonalCenterViewModel::class.java)
        personalCenterViewModel?.infoLiveData?.observe(this){
            updateInfo(it)
        }
    }

    override fun initListener() {
    }

    fun updateInfo(bean: PersonalCenterInfoBean?){
        GlideUtils.loadImage(this, bean?.data?.user_center?.avatar, centerAvatar, R.drawable.rt_default_avatar)
        centerNickNameTxt.text = bean?.data?.user_center?.user_name
        centerIDTxt.text = "ID:$userId"
        centerInsTxt.text = bean?.data?.user_center?.describe
        when(bean?.data?.user_center?.sex){
            0->{
                val drawable = resources.getDrawable(R.drawable.qf_stream_gender_male)
                drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
                centerGenderTxt.setCompoundDrawables(null, null, drawable, null)
                centerGenderTxt.text = getString(R.string.stream_user_card_male)
            }
            1->{
                val drawable = resources.getDrawable(R.drawable.qf_stream_gender_female)
                drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
                centerGenderTxt.setCompoundDrawables(null, null, drawable, null)
                centerGenderTxt.text = getString(R.string.stream_user_card_female)
            }
        }
        centerLocationTxt.text = ""
        centerFansNumTxt.text = "" + bean?.data?.fans_count
        centerFollowNumTxt.text = "" + bean?.data?.follow_count
        centerLikeNumTxt.text = "" + bean?.data?.like_count

    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.centerTabProductLayout->{
                LaToastUtil.showShort("商品")
            }
            R.id.centerTabWalletLayout->{
                LaToastUtil.showShort("钱包")
            }
            R.id.centerFansLayout->{
                Router.toFansAndFollowListActivity(userId)
            }
            R.id.centerFollowNumTxt->{
                Router.toFansAndFollowListActivity(userId)
            }
        }
    }

    override fun statusBarSetting() {
        //设置状态栏 默认为白色
        BarUtils.transparentStatusBar(this)
        BarUtils.setStatusBarLightMode(this, true)
    }
}