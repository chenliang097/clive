package com.rongtuoyouxuan.chatlive.biz2.config

import com.rongtuoyouxuan.chatlive.biz2.ReqId
import com.rongtuoyouxuan.chatlive.biz2.model.config.ClientConfModel
import com.rongtuoyouxuan.chatlive.net2.RequestListener
import newNetworks

object ClientConfigBiz {
    /**
     * 加入匹配池
     */
    fun getClientConfig(
        listener: RequestListener<ClientConfModel>
    ) = newNetworks(
        null,
        listener,
        ReqId.CLIENT_CONFIG,
    ) {
        it.create(ClientConfigServer::class.java).getClientConfig()
    }
}