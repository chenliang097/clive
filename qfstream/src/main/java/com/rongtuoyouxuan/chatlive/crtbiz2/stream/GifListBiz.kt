package com.rongtuoyouxuan.chatlive.crtbiz2.stream

import com.rongtuoyouxuan.chatlive.crtbiz2.ReqId
import com.rongtuoyouxuan.chatlive.crtbiz2.model.gif.GifListBean
import newNetworkForThings

object GifListBiz {

    fun getGifList(page:Int, size:Int,requestListener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<GifListBean>){
        newNetworkForThings(null, requestListener, ReqId.GIF_LIST){
            it.create(GifListServer::class.java).getGifList(page, size)
        }
    }
}