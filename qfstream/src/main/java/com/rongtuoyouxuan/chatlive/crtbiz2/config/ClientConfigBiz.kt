package com.rongtuoyouxuan.chatlive.crtbiz2.config

import com.rongtuoyouxuan.chatlive.crtbiz2.ReqId
import com.rongtuoyouxuan.chatlive.crtbiz2.model.config.ClientConfModel
import newNetworks

object ClientConfigBiz {
    /**
     * 加入匹配池
     */
    fun getClientConfig(
        listener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<ClientConfModel>
    ) = newNetworks(
        null,
        listener,
        ReqId.CLIENT_CONFIG,
    ) {
        it.create(ClientConfigServer::class.java).getClientConfig()
    }
}