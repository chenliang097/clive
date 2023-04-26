package com.rongtuoyouxuan.chatlive.stream.view.adapter

import android.widget.ImageView
import com.rongtuoyouxuan.chatlive.biz2.model.gif.GifListBean
import com.rongtuoyouxuan.chatlive.image.util.GlideUtils
import com.rongtuoyouxuan.chatlive.stream.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class GifListAdapter: BaseQuickAdapter<GifListBean.GifItemBean, BaseViewHolder>, LoadMoreModule {
    constructor(layoutId:Int):super(layoutId){}

    override fun convert(holder: BaseViewHolder, item: GifListBean.GifItemBean) {
        var img = holder.getView<ImageView>(R.id.gifItemImg)
        GlideUtils.loadImage(context, item.gif_url, img, R.drawable.icon_default_live_common)

    }
}