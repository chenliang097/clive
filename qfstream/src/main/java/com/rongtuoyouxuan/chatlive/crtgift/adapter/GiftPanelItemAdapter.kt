package com.rongtuoyouxuan.chatlive.crtgift.adapter

import android.view.View
import android.widget.TextView
import com.rongtuoyouxuan.chatlive.crtbiz2.model.gift.GiftEntity
import com.rongtuoyouxuan.chatlive.crtimage.util.GlideUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.rongtuoyouxuan.chatlive.stream.R

/**
 * 
 * date:2022/8/1-11:43
 * des: 礼物面板item
 */
class GiftPanelItemAdapter :
    BaseQuickAdapter<GiftEntity, BaseViewHolder>(R.layout.item_gift_panel_page_item) {

    var oldPosition = -1

    override fun convert(holder: BaseViewHolder, item: GiftEntity) {
        holder.getView<View>(R.id.viewBg).isSelected = item.isSelected == true
        GlideUtils.loadImageNoAnimate(
            context,
            item.giftUrl1X,
            holder.getView(R.id.ivImage),
            R.drawable.icon_gift_default
        )
        holder.setVisible(R.id.ivPrice, true)
        holder.setVisible(R.id.tvPrice, true)
        holder.setVisible(R.id.tvFreeNum, false)
        holder.getView<TextView>(R.id.tvName).text = item.giftName
        val price = (item.value?: 0).toString().plus(context.resources.getString(R.string.gift_panel_gift_price_unit))
        holder.getView<TextView>(R.id.tvPrice).text = price
    }

    fun change(clickPosition: Int) {
        if (clickPosition == oldPosition) {
            return
        }
        if (oldPosition != -1) {
            data[oldPosition].isSelected = false
            notifyItemChanged(oldPosition)
        }
        if (clickPosition != -1) {
            data[clickPosition].isSelected = true
            notifyItemChanged(clickPosition)
        }

        oldPosition = clickPosition
    }
}