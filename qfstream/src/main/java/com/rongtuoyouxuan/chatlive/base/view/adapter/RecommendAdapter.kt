package com.rongtuoyouxuan.chatlive.base.view.adapter

import com.blankj.utilcode.util.AdaptScreenUtils
import com.rongtuoyouxuan.chatlive.biz2.model.main.LiveMainEntity
import com.rongtuoyouxuan.chatlive.image.util.GlideUtils
import com.rongtuoyouxuan.chatlive.stream.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/*
*Create by {Mr秦} on 2023/1/29
*/
class RecommendAdapter :
    BaseQuickAdapter<LiveMainEntity, BaseViewHolder>(R.layout.item_layout_recommend) {

    override fun convert(holder: BaseViewHolder, item: LiveMainEntity) {
        val liveItemEntity = item.live
        GlideUtils.loadRoundCircleImage(context,
            liveItemEntity?.pic,
            holder.getView(R.id.bg),
            AdaptScreenUtils.pt2Px(12f),
            R.drawable.icon_default_live_common)

        holder.setText(R.id.tvTitle, liveItemEntity?.title)
    }
}