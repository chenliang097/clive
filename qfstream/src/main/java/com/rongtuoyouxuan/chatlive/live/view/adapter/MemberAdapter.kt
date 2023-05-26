package com.rongtuoyouxuan.chatlive.live.view.adapter

import android.net.Uri
import android.widget.TextView
import com.blankj.utilcode.util.StringUtils
import com.rongtuoyouxuan.chatlive.crtbiz2.model.stream.LiveChatRoomUserEntity
import com.rongtuoyouxuan.chatlive.crtimage.util.GlideUtils
import com.rongtuoyouxuan.chatlive.stream.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 *
 * date:2022/8/11-10:19
 * des: 成员列表
 */
class MemberAdapter :
    BaseQuickAdapter<LiveChatRoomUserEntity, BaseViewHolder>(R.layout.item_dialog_member) {

    init {
        addChildClickViewIds(R.id.spaceClick, R.id.ivFollow)
    }

    var loginUID = 0L

    override fun convert(holder: BaseViewHolder, item: LiveChatRoomUserEntity) {
        if (item.avatar?.isNotEmpty() == true) {
            GlideUtils.loadCircleImage(
                context,
                item.avatar,
                holder.getView(R.id.ivHeader),
                R.drawable.icon_default_avatar
            )
            val uri = Uri.parse(item.avatar)
            val resultValue = uri.getQueryParameter("frame")
            if (!StringUtils.isTrimEmpty(resultValue)
                && resultValue?.endsWith("webp", true) == true
            ) {
                GlideUtils.loadImageWebP(context, resultValue, holder.getView(R.id.ivHeaderFrame))
            }
        }
        holder.getView<TextView>(R.id.tvName).text = item.nickname
        if (item.userId == loginUID) {
            holder.setVisible(R.id.ivFollow, false)
        } else if (item.isFollow == 1) {
            holder.setVisible(R.id.ivFollow, false)
        } else {
            holder.setVisible(R.id.ivFollow, true)
        }
    }
}