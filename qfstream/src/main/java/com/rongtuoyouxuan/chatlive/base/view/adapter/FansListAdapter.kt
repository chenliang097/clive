package com.rongtuoyouxuan.chatlive.base.view.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.makeramen.roundedimageview.RoundedImageView
import com.rongtuoyouxuan.chatlive.crtbiz2.model.stream.FansListBean
import com.rongtuoyouxuan.chatlive.crtdatabus.DataBus
import com.rongtuoyouxuan.chatlive.crtimage.util.GlideUtils
import com.rongtuoyouxuan.chatlive.stream.R

class FansListAdapter: BaseQuickAdapter<FansListBean.ItemBean, BaseViewHolder>, LoadMoreModule {
    private var type:Int = 0
    private var onFollowStatus:OnFollowStatus? = null
    private var userId:String? = ""
    constructor(layoutId:Int, type:Int, userId:String?):super(layoutId){
        this.type =type
        this.onFollowStatus = onFollowStatus
        this.userId = userId
    }

    override fun convert(holder: BaseViewHolder, item: FansListBean.ItemBean) {
        var name = holder.getView<TextView>(R.id.itemNickName)
        var avatar = holder.getView<RoundedImageView>(R.id.itemAvatar)
        var btn1 = holder.getView<TextView>(R.id.itemBtn)
        if(DataBus.instance().USER_ID == userId){
            if(DataBus.instance().USER_ID != item.id.toString()) {
                holder.getView<ImageView>(R.id.itemRemoveImg).visibility = View.VISIBLE
                btn1.visibility = View.VISIBLE
                if (item.status) {
                    btn1.setBackgroundResource(R.drawable.rt_common_gray_btn)
                    btn1.text = context.resources.getString(R.string.stream_center_followed)
                } else {
                    btn1.setBackgroundResource(R.drawable.rt_common_btn)
                    btn1.text = context.resources.getString(R.string.stream_center_add_follow)
                }
            }else{
                holder.getView<ImageView>(R.id.itemRemoveImg).visibility = View.GONE
                btn1.visibility = View.GONE
            }
        }else{
            if(DataBus.instance().USER_ID != item.id.toString()) {
                holder.getView<ImageView>(R.id.itemRemoveImg).visibility = View.GONE
                btn1.visibility = View.VISIBLE
                if (item.status) {
                    btn1.setBackgroundResource(R.drawable.rt_common_gray_btn)
                    btn1.text =
                        context.resources.getString(R.string.stream_user_card_delete_folowed)
                } else {
                    btn1.setBackgroundResource(R.drawable.rt_common_btn)
                    btn1.text = context.resources.getString(R.string.stream_follow)
                }
            }else{
                holder.getView<ImageView>(R.id.itemRemoveImg).visibility = View.GONE
                btn1.visibility = View.GONE
            }
        }
        GlideUtils.loadImage(context, item.avatar, avatar, R.drawable.rt_default_avatar)
        name.text = item.nick_name

//        btn1.setOnClickListener {
//            onFollowStatus?.onFollowStatus(item.status)
//        }
    }

    interface OnFollowStatus{
        fun onFollowStatus(status:Boolean)
    }
}