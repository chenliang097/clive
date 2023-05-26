package com.rongtuoyouxuan.chatlive.qfcommon.widget.adapter

import android.widget.ImageView
import com.rongtuoyouxuan.chatlive.crtbiz2.model.gif.GifListBean
import com.rongtuoyouxuan.chatlive.crtimage.util.GlideUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.rongtuoyouxuan.chatlive.stream.R

class GifPannelAdapter: BaseQuickAdapter<GifListBean.GifItemBean, BaseViewHolder>, LoadMoreModule {
    constructor(layoutId:Int):super(layoutId){}

    override fun convert(holder: BaseViewHolder, item: GifListBean.GifItemBean) {
        var img = holder.getView<ImageView>(R.id.gifItemImg)
        GlideUtils.loadImage(context, item.gif_url, img, R.drawable.icon_default_live_common)

    }
}