package com.rongtuoyouxuan.chatlive.base.view.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.makeramen.roundedimageview.RoundedImageView
import com.rongtuoyouxuan.chatlive.biz2.model.stream.FansListBean
import com.rongtuoyouxuan.chatlive.image.util.GlideUtils
import com.rongtuoyouxuan.chatlive.stream.R

class FansListAdapter: BaseQuickAdapter<FansListBean.ItemBean, BaseViewHolder>, LoadMoreModule {
    private var type:Int = 0
    private var onFollowStatus:OnFollowStatus? = null
    constructor(layoutId:Int, type:Int):super(layoutId){
        this.type =type
        this.onFollowStatus = onFollowStatus
    }

    override fun convert(holder: BaseViewHolder, item: FansListBean.ItemBean) {
        var name = holder.getView<TextView>(R.id.itemNickName)
        var avatar = holder.getView<RoundedImageView>(R.id.itemAvatar)
        var btn1 = holder.getView<TextView>(R.id.itemBtn)
        GlideUtils.loadImage(context, item.avatar, avatar, R.drawable.rt_default_avatar)
        name.text = item.nick_name
        if(item.status){
            btn1.setBackgroundResource(R.drawable.rt_common_gray_btn)
            btn1.text = context.resources.getString(R.string.stream_center_followed)
        }else{
            btn1.setBackgroundResource(R.drawable.rt_common_btn)
            btn1.text = context.resources.getString(R.string.stream_center_add_follow)
        }
//        btn1.setOnClickListener {
//            onFollowStatus?.onFollowStatus(item.status)
//        }
    }

    interface OnFollowStatus{
        fun onFollowStatus(status:Boolean)
    }
}