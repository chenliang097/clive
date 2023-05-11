package com.rongtuoyouxuan.chatlive.base.view.adapter

import android.text.TextUtils
import com.blankj.utilcode.util.AdaptScreenUtils
import com.rongtuoyouxuan.chatlive.biz2.model.main.LiveMainEntity
import com.rongtuoyouxuan.chatlive.image.util.GlideUtils
import com.rongtuoyouxuan.chatlive.stream.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.rongtuoyouxuan.chatlive.biz2.model.main.LiveItemEntity

/*
*Create by {Mr秦} on 2023/1/29
*/
class RecommendAdapter :
    BaseQuickAdapter<LiveItemEntity, BaseViewHolder>(R.layout.item_layout_recommend) {

    override fun convert(holder: BaseViewHolder, item: LiveItemEntity) {
        var cover = item.medium_cover_image
        if(TextUtils.isEmpty(cover)){
            cover = item.avatar
        }
        GlideUtils.loadRoundCircleImage(context,
            cover,
            holder.getView(R.id.bg),
            AdaptScreenUtils.pt2Px(12f),
            R.drawable.icon_default_live_common)
        GlideUtils.loadImage(context, item.avatar, holder.getView(R.id.recommondAvatar))
        holder.setText(R.id.recommondHotTxt, "" + item?.roomUserTotal)
        holder.setText(R.id.recommondNameTxt, item?.anchorName)
    }
}