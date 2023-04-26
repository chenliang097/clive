package com.rongtuoyouxuan.qfcommon.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.rongtuoyouxuan.chatlive.image.util.GlideUtils
import com.rongtuoyouxuan.qfcommon.R
import kotlinx.android.synthetic.main.layout_group_avatar.view.*

class GroupAvatarView @JvmOverloads  constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : RelativeLayout(context, attrs, defStyleAttr){
    init {
        LayoutInflater.from(context).inflate(R.layout.layout_group_avatar, this)
    }

    fun setAvatarList(avatars: List<String>){
        if(avatars == null){
            groupAvatar1.setImageResource(R.drawable.icon_default_live_common)
            groupAvatar2.setImageResource(R.drawable.icon_default_live_common)
            groupAvatar3.setImageResource(R.drawable.icon_default_live_common)
            groupAvatar4.setImageResource(R.drawable.icon_default_live_common)
            return
        }

        when(avatars?.size){
            0-> {
                groupAvatar1.setImageResource(R.drawable.icon_default_live_common)
                groupAvatar2.setImageResource(R.drawable.icon_default_live_common)
                groupAvatar3.setImageResource(R.drawable.icon_default_live_common)
                groupAvatar4.setImageResource(R.drawable.icon_default_live_common)
            }
            1->{
                GlideUtils.loadImage(context, avatars[0], groupAvatar1, R.drawable.icon_default_live_common)
                groupAvatar2.setImageResource(R.drawable.icon_default_live_common)
                groupAvatar3.setImageResource(R.drawable.icon_default_live_common)
                groupAvatar4.setImageResource(R.drawable.icon_default_live_common)
                }
            2->{
                GlideUtils.loadImage(context, avatars[0], groupAvatar1, R.drawable.icon_default_live_common)
                GlideUtils.loadImage(context, avatars[1], groupAvatar2, R.drawable.icon_default_live_common)
                groupAvatar3.setImageResource(R.drawable.icon_default_live_common)
                groupAvatar4.setImageResource(R.drawable.icon_default_live_common)
            }
            3->{
                GlideUtils.loadImage(context, avatars[0], groupAvatar1, R.drawable.icon_default_live_common)
                GlideUtils.loadImage(context, avatars[1], groupAvatar2, R.drawable.icon_default_live_common)
                GlideUtils.loadImage(context, avatars[2], groupAvatar3, R.drawable.icon_default_live_common)
                groupAvatar4.setImageResource(R.drawable.icon_default_live_common)
            }
            else->{
                GlideUtils.loadImage(context, avatars[0], groupAvatar1, R.drawable.icon_default_live_common)
                GlideUtils.loadImage(context, avatars[1], groupAvatar2, R.drawable.icon_default_live_common)
                GlideUtils.loadImage(context, avatars[2], groupAvatar3, R.drawable.icon_default_live_common)
                GlideUtils.loadImage(context, avatars[3], groupAvatar4, R.drawable.icon_default_live_common)
            }
        }

    }
}