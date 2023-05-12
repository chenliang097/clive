package com.rongtuoyouxuan.chatlive.stream.view.adapter

import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.makeramen.roundedimageview.RoundedImageView
import com.rongtuoyouxuan.chatlive.biz2.model.stream.RoomManagerBlackListBean
import com.rongtuoyouxuan.chatlive.biz2.model.stream.RoomManagerListBean
import com.rongtuoyouxuan.chatlive.image.util.GlideUtils
import com.rongtuoyouxuan.chatlive.stream.R

class BlackListAdapter: BaseQuickAdapter<RoomManagerBlackListBean.ItemBean, BaseViewHolder>, LoadMoreModule {
    private var type:Int = 0
    constructor(layoutId:Int, type:Int):super(layoutId){
        this.type =type
    }

    override fun convert(holder: BaseViewHolder, item: RoomManagerBlackListBean.ItemBean) {
        var name = holder.getView<TextView>(R.id.itemNickName)
        var avatar = holder.getView<RoundedImageView>(R.id.itemAvatar)
        var btn1 = holder.getView<TextView>(R.id.itemBtn)
        GlideUtils.loadImage(context, item.avatar, avatar, R.drawable.rt_default_avatar)
        name.text = item.nick_name

    }
}