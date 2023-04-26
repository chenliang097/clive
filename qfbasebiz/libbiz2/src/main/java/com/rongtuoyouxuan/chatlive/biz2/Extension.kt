import androidx.lifecycle.LifecycleOwner
import com.rongtuoyouxuan.chatlive.biz2.constanst.UrlConstanst
import com.rongtuoyouxuan.chatlive.net2.BaseModel
import com.rongtuoyouxuan.chatlive.net2.NetWorks
import com.rongtuoyouxuan.chatlive.net2.RequestListener
import retrofit2.Call
import retrofit2.Retrofit

/**
 * 生成网络请求
 */
internal fun <Model : BaseModel> newNetworks(
    lifecycleOwner: LifecycleOwner?,
    listener: RequestListener<Model>,
    reqId: String,
    baseUrl: String = UrlConstanst.BASE_URL_LIVE_API_BOBOO_COM,
    createCall: (retrofit: Retrofit) -> Call<Model>,
) {
    object : NetWorks<Model>(lifecycleOwner, listener) {
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

internal fun <Model : BaseModel> newNetworkForUser(
    lifecycleOwner: LifecycleOwner?,
    listener: RequestListener<Model>?,
    reqId: String,
    baseUrl: String = UrlConstanst.BASE_URL_LIVE_API_BOBOO_COM,
    createCall: (retrofit: Retrofit) -> Call<Model>,
) {
    object : NetWorks<Model>(lifecycleOwner, listener) {
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

internal fun <Model : BaseModel> newNetworkForAccount(
    lifecycleOwner: LifecycleOwner?,
    listener: RequestListener<Model>?,
    reqId: String,
    baseUrl: String = UrlConstanst.BASE_URL_LIVE_API_BOBOO_COM,
    createCall: (retrofit: Retrofit) -> Call<Model>,
) {
    object : NetWorks<Model>(lifecycleOwner, listener) {
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

fun <Model : BaseModel> newNetworkForChatIM(
    lifecycleOwner: LifecycleOwner?,
    listener: RequestListener<Model>?,
    reqId: String,
    baseUrl: String = UrlConstanst.BASE_URL_LIVE_API_BOBOO_COM,
    createCall: (retrofit: Retrofit) -> Call<Model>,
) {
    object : NetWorks<Model>(lifecycleOwner, listener) {
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

internal fun <Model : BaseModel> newNetworkForGift(
    lifecycleOwner: LifecycleOwner?,
    listener: RequestListener<Model>,
    reqId: String,
    baseUrl: String = UrlConstanst.BASE_URL_GIFT_API_BOBOO_COM,
    createCall: (retrofit: Retrofit) -> Call<Model>,
) {
    object : NetWorks<Model>(lifecycleOwner, listener) {
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

internal fun <Model : BaseModel> newNetworkForLive(
    lifecycleOwner: LifecycleOwner?,
    listener: RequestListener<Model>?,
    reqId: String,
    baseUrl: String = UrlConstanst.BASE_URL_LIVE_API_BOBOO_COM,
    createCall: (retrofit: Retrofit) -> Call<Model>,
) {
    object : NetWorks<Model>(lifecycleOwner, listener) {
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

internal fun <Model : BaseModel> newNetworkForThings(
    lifecycleOwner: LifecycleOwner?,
    listener: RequestListener<Model>,
    reqId: String,
    baseUrl: String = UrlConstanst.BASE_URL_LIVE_API_BOBOO_COM,
    createCall: (retrofit: Retrofit) -> Call<Model>,
) {
    object : NetWorks<Model>(lifecycleOwner, listener) {
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