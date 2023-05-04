package com.rongtuoyouxuan.chatlive.base.view.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.makeramen.roundedimageview.RoundedImageView
import com.rongtuoyouxuan.chatlive.biz2.model.stream.PopolarityRankBean
import com.rongtuoyouxuan.chatlive.biz2.model.stream.RoomManagerListBean
import com.rongtuoyouxuan.chatlive.image.util.GlideUtils
import com.rongtuoyouxuan.chatlive.stream.R

class PopularityRankAdapter: BaseQuickAdapter<PopolarityRankBean.ItemBean, BaseViewHolder>, LoadMoreModule {
    private var type:Int = 0
    constructor(layoutId:Int, type:Int):super(layoutId){
        this.type =type
    }

    override fun convert(holder: BaseViewHolder, item: PopolarityRankBean.ItemBean) {
        var name = holder.getView<TextView>(R.id.itemNickName)
        var avatar = holder.getView<RoundedImageView>(R.id.itemAvatar)
        var txtNum = holder.getView<TextView>(R.id.itemNumTxt)
        var itemRankImg = holder.getView<ImageView>(R.id.itemRankImg)
        var itemRankTxt = holder.getView<TextView>(R.id.itemRankTxt)
        GlideUtils.loadImage(context, item.avatar, avatar, R.drawable.rt_default_avatar)
        name.text = item.nick_name
        txtNum.text = "" + item.degree
        when(item.rank){
            1->{
                itemRankImg.visibility = View.VISIBLE
                itemRankTxt.visibility = View.GONE
                itemRankImg.setImageResource(R.drawable.rt_icon_rank_one)
            }
            2->{
                itemRankImg.visibility = View.VISIBLE
                itemRankTxt.visibility = View.GONE
                itemRankImg.setImageResource(R.drawable.rt_icon_rank_two)
            }
            3->{
                itemRankImg.visibility = View.VISIBLE
                itemRankTxt.visibility = View.GONE
                itemRankImg.setImageResource(R.drawable.rt_icon_rank_three)
            }else->{
            itemRankImg.visibility = View.GONE
            itemRankTxt.visibility = View.VISIBLE
            itemRankTxt.text = "" + item.rank
            }
        }
    }
}