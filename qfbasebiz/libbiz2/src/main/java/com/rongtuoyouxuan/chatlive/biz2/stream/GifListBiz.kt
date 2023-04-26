package com.rongtuoyouxuan.chatlive.biz2.stream

import com.rongtuoyouxuan.chatlive.biz2.ReqId
import com.rongtuoyouxuan.chatlive.biz2.model.gif.GifListBean
import com.rongtuoyouxuan.chatlive.net2.RequestListener
import newNetworkForThings

object GifListBiz {

    fun getGifList(page:Int, size:Int,requestListener: RequestListener<GifListBean>){
        newNetworkForThings(null, requestListener, ReqId.GIF_LIST){
            it.create(GifListServer::class.java).getGifList(page, size)
        }
    }
}