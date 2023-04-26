package com.rongtuoyouxuan.libgift.adapter

import android.widget.TextView
import com.rongtuoyouxuan.libgift.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * @Author:         wy
 * @Description:    类作用描述
 */
class GiftSelectNumAdapter :
    BaseQuickAdapter<Int, BaseViewHolder>(R.layout.gift_item_gift_number_select) {
    override fun convert(holder: BaseViewHolder, item: Int) {
        holder.getView<TextView>(R.id.tvContent).text = "$item"
    }
}