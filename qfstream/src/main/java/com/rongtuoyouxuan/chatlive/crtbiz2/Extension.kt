import androidx.lifecycle.LifecycleOwner
import com.rongtuoyouxuan.chatlive.crtbiz2.constanst.UrlConstanst
import retrofit2.Call
import retrofit2.Retrofit

/**
 * 生成网络请求
 */
internal fun <Model : com.rongtuoyouxuan.chatlive.crtnet.BaseModel> newNetworks(
    lifecycleOwner: LifecycleOwner?,
    listener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<Model>,
    reqId: String,
    baseUrl: String = UrlConstanst.BASE_URL_LIVE_API_BOBOO_COM,
    createCall: (retrofit: Retrofit) -> Call<Model>,
) {
    object : com.rongtuoyouxuan.chatlive.crtnet.NetWorks<Model>(lifecycleOwner, listener) {
        override fun createCall(retrofit: Retrofit): Call<Model> {
            return createCall(retrofit)
        }

        override fun getReqId(): String {
            return reqId
        }

        override fun getBaseUrl(): String {
            return baseUrl
        }
    }.start()
}

internal fun <Model : com.rongtuoyouxuan.chatlive.crtnet.BaseModel> newNetworkForUser(
    lifecycleOwner: LifecycleOwner?,
    listener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<Model>?,
    reqId: String,
    baseUrl: String = UrlConstanst.BASE_URL_LIVE_API_BOBOO_COM,
    createCall: (retrofit: Retrofit) -> Call<Model>,
) {
    object : com.rongtuoyouxuan.chatlive.crtnet.NetWorks<Model>(lifecycleOwner, listener) {
        override fun createCall(retrofit: Retrofit): Call<Model> {
            return createCall(retrofit)
        }

        override fun getReqId(): String {
            return reqId
        }

        override fun getBaseUrl(): String {
            return baseUrl
        }
    }.start()
}

internal fun <Model : com.rongtuoyouxuan.chatlive.crtnet.BaseModel> newNetworkForAccount(
    lifecycleOwner: LifecycleOwner?,
    listener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<Model>?,
    reqId: String,
    baseUrl: String = UrlConstanst.BASE_URL_LIVE_API_BOBOO_COM,
    createCall: (retrofit: Retrofit) -> Call<Model>,
) {
    object : com.rongtuoyouxuan.chatlive.crtnet.NetWorks<Model>(lifecycleOwner, listener) {
        override fun createCall(retrofit: Retrofit): Call<Model> {
            return createCall(retrofit)
        }

        override fun getReqId(): String {
            return reqId
        }

        override fun getBaseUrl(): String {
            return baseUrl
        }
    }.start()
}

fun <Model : com.rongtuoyouxuan.chatlive.crtnet.BaseModel> newNetworkForChatIM(
    lifecycleOwner: LifecycleOwner?,
    listener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<Model>?,
    reqId: String,
    baseUrl: String = UrlConstanst.BASE_URL_LIVE_API_BOBOO_COM,
    createCall: (retrofit: Retrofit) -> Call<Model>,
) {
    object : com.rongtuoyouxuan.chatlive.crtnet.NetWorks<Model>(lifecycleOwner, listener) {
        override fun createCall(retrofit: Retrofit): Call<Model> {
            return createCall(retrofit)
        }

        override fun getReqId(): String {
            return reqId
        }

        override fun getBaseUrl(): String {
            return baseUrl
        }
    }.start()
}

internal fun <Model : com.rongtuoyouxuan.chatlive.crtnet.BaseModel> newNetworkForGift(
    lifecycleOwner: LifecycleOwner?,
    listener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<Model>,
    reqId: String,
    baseUrl: String = UrlConstanst.BASE_URL_GIFT_API_BOBOO_COM,
    createCall: (retrofit: Retrofit) -> Call<Model>,
) {
    object : com.rongtuoyouxuan.chatlive.crtnet.NetWorks<Model>(lifecycleOwner, listener) {
        override fun createCall(retrofit: Retrofit): Call<Model> {
            return createCall(retrofit)
        }

        override fun getReqId(): String {
            return reqId
        }

        override fun getBaseUrl(): String {
            return baseUrl
        }
    }.start()
}

internal fun <Model : com.rongtuoyouxuan.chatlive.crtnet.BaseModel> newNetworkForLive(
    lifecycleOwner: LifecycleOwner?,
    listener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<Model>?,
    reqId: String,
    baseUrl: String = UrlConstanst.BASE_URL_LIVE_API_BOBOO_COM,
    createCall: (retrofit: Retrofit) -> Call<Model>,
) {
    object : com.rongtuoyouxuan.chatlive.crtnet.NetWorks<Model>(lifecycleOwner, listener) {
        override fun createCall(retrofit: Retrofit): Call<Model> {
            return createCall(retrofit)
        }

        override fun getReqId(): String {
            return reqId
        }

        override fun getBaseUrl(): String {
            return baseUrl
        }
    }.start()
}

internal fun <Model : com.rongtuoyouxuan.chatlive.crtnet.BaseModel> newNetworkForThings(
    lifecycleOwner: LifecycleOwner?,
    listener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<Model>,
    reqId: String,
    baseUrl: String = UrlConstanst.BASE_URL_LIVE_API_BOBOO_COM,
    createCall: (retrofit: Retrofit) -> Call<Model>,
) {
    object : com.rongtuoyouxuan.chatlive.crtnet.NetWorks<Model>(lifecycleOwner, listener) {
        override fun createCall(retrofit: Retrofit): Call<Model> {
            return createCall(retrofit)
        }

        override fun getReqId(): String {
            return reqId
        }

        override fun getBaseUrl(): String {
            return baseUrl
        }
    }.start()
}