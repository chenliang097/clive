package com.rongtuoyouxuan.chatlive.base.view.activity

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.view.View
import android.view.View.OnClickListener
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.BarUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.rongtuoyouxuan.chatlive.base.utils.RoomDegreeUtils
import com.rongtuoyouxuan.chatlive.base.viewmodel.PersonalCenterViewModel
import com.rongtuoyouxuan.chatlive.crtbiz2.model.user.PersonalCenterInfoBean
import com.rongtuoyouxuan.chatlive.crtdatabus.DataBus
import com.rongtuoyouxuan.chatlive.crtimage.util.GlideUtils
import com.rongtuoyouxuan.chatlive.crtlog.upload.ULog
import com.rongtuoyouxuan.chatlive.crtrouter.Router
import com.rongtuoyouxuan.chatlive.crtrouter.constants.RouterConstant
import com.rongtuoyouxuan.chatlive.stream.R
import com.rongtuoyouxuan.chatlive.crtutil.util.LaToastUtil
import com.rongtuoyouxuan.chatlive.crtuikit.SimpleActivity
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.qf_stream_activity_end.*
import kotlinx.android.synthetic.main.rt_activity_personal_center.*


@Route(path = RouterConstant.PATH_PERSONAL_CENTER)
class PersonalCenterActivity: SimpleActivity(),OnClickListener {

    private var personalCenterViewModel:PersonalCenterViewModel? = null
    private var userId:String? = ""

    override fun getLayoutResId(): Int {
        return R.layout.rt_activity_personal_center
    }

    override fun initData() {
//        mTitleBar.setLeftIco(R.drawable.icon_white_back)
        userId = intent.getStringExtra("userId")
        personalCenterViewModel = ViewModelProvider(this).get(PersonalCenterViewModel::class.java)
        personalCenterViewModel?.infoLiveData?.observe(this){
            updateInfo(it)
        }
        userId?.let { personalCenterViewModel?.getPersonalCenterInfo(it) }
        if(userId == DataBus.instance().USER_ID){
            centerEditTxt.visibility = View.VISIBLE
            centerTabWalletLayout.visibility = View.VISIBLE
        }else{
            centerEditTxt.visibility = View.GONE
            centerTabWalletLayout.visibility = View.INVISIBLE
        }
    }

    override fun initListener() {
        centerTabProductLayout.setOnClickListener(this)
        centerTabWalletLayout.setOnClickListener(this)
        centerFansLayout.setOnClickListener(this)
        centerFollowLayout.setOnClickListener(this)
        centerEditTxt.setOnClickListener(this)
    }

    fun updateInfo(bean: PersonalCenterInfoBean?){
        GlideUtils.loadBlurImage(this, bean?.data?.user_center?.avatar, bg, 25, R.drawable.rt_icon_default)
        GlideUtils.loadImage(this, bean?.data?.user_center?.avatar, centerAvatar, R.drawable.rt_default_avatar)
//        GlideUtils.loadImage(this, bean?.data?.user_center?.avatar, bg, R.drawable.rt_default_avatar)
        centerNickNameTxt.text = bean?.data?.user_center?.user_name
        centerIDTxt.text = "ID:$userId"
        if(!TextUtils.isEmpty(bean?.data?.user_center?.describe)) {
            centerInsTxt.text = bean?.data?.user_center?.describe
        }
        when(bean?.data?.user_center?.sex){
            1->{
                val drawable = resources.getDrawable(R.drawable.qf_stream_gender_male)
                drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
                centerGenderTxt.setCompoundDrawables(drawable, null, null, null)
                centerGenderTxt.text = getString(R.string.stream_user_card_male)
            }
            0->{
                val drawable = resources.getDrawable(R.drawable.qf_stream_gender_female)
                drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
                centerGenderTxt.setCompoundDrawables(drawable, null, null, null)
                centerGenderTxt.text = getString(R.string.stream_user_card_female)
            }
        }
        if(!TextUtils.isEmpty(bean?.data?.user_center?.location)){
            centerLocationTxt.text = bean?.data?.user_center?.location
        }else {
            centerLocationTxt.text = resources.getString(R.string.stream_unknown_location1)
        }

        centerFansNumTxt.text = bean?.data?.fans_count?.let { RoomDegreeUtils.getDegree(it) }
        centerFollowNumTxt.text = bean?.data?.follow_count?.let { RoomDegreeUtils.getDegree(it) }
        centerLikeNumTxt.text = bean?.data?.like_count?.let { RoomDegreeUtils.getDegree(it) }

    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.centerTabProductLayout->{
                LaToastUtil.showShort("商品")
            }
            R.id.centerTabWalletLayout->{
                Router.toWalletActivity("")
            }
            R.id.centerFansLayout->{
                Router.toFansAndFollowListActivity(userId, 1)
            }
            R.id.centerFollowLayout->{
                Router.toFansAndFollowListActivity(userId, 2)
            }
            R.id.centerEditTxt->{
                LaToastUtil.showShort("编辑资料")
            }
        }
    }

    override fun statusBarSetting() {
        //设置状态栏 默认为白色
        BarUtils.transparentStatusBar(this)
        BarUtils.setStatusBarLightMode(this, true)
    }

    private fun updateCoverLayout(url: String, coverLayout: ImageView?) {
        Glide.with(this).asBitmap().load(url).placeholder(R.drawable.rt_icon_default)
            .error(R.drawable.rt_icon_default).apply(
                RequestOptions().transform(
                    BlurTransformation(25, 1)
                )
            ).into(object :
                SimpleTarget<Bitmap>() {
                override fun onLoadStarted(placeholder: Drawable?) {
                    super.onLoadStarted(placeholder)
                    coverLayout!!.setImageResource(R.drawable.rt_icon_default)
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    super.onLoadFailed(errorDrawable)
                    coverLayout!!.setImageResource(R.drawable.rt_icon_default)
                }

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    coverLayout!!.setImageBitmap(resource)
                }

            })
    }

}