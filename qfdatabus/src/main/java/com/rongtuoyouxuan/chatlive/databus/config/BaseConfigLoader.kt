package com.rongtuoyouxuan.chatlive.databus.config

import com.blankj.utilcode.util.SPUtils
import com.rongtuoyouxuan.chatlive.gson.GsonSafetyUtils
import com.rongtuoyouxuan.chatlive.log.PLog
import com.rongtuoyouxuan.chatlive.net2.BaseModel
import com.rongtuoyouxuan.chatlive.net2.RequestListener

abstract class BaseConfigLoader {
    companion object {
        const val TAG = "ConfigLoader"
    }

    protected val items = mutableSetOf<Item<BaseModel>>()

    /**
     * 加载配置并缓存
     */
    fun loadConfig() {
        items.forEach {
            doLoadConfig(it.key, it)
        }
    }

    /**
     * 加载单一配置项
     */
    fun getItem(key: String) = doLoadConfig(key, items.first { it.key == key })


    private fun <T : BaseModel> doLoadConfig(key: String, item: Item<T>): T {
        PLog.d(TAG, "doLoadConfig key: $key")
        when (item.status) {
            ModelStatus.DEFAULT -> {
                tryLoadModelFromCache(key, item, ModelStatus.CACHE)
                item.loader()
            }
            ModelStatus.CACHE -> {
                item.loader()
            }
            ModelStatus.NET -> {
            }
        }
        return item.model
    }

    private fun <T : BaseModel> tryLoadModelFromCache(key: String, item: Item<T>, status: ModelStatus) {
        val str = SPUtils.getInstance().getString(key)
        if (!str.isNullOrEmpty()) {
            try {
                PLog.d(TAG, "tryLoadModelFromCache key: $key from: $status")
                val model = GsonSafetyUtils.getInstance().mGson.fromJson(str, item.model.javaClass)
                item.model = model
                item.status = status
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    protected fun <T : BaseModel> genListenerWrapper(key: String) =
        object : RequestListener<T> {
            override fun onSuccess(reqId: String, result: T) {
                PLog.d(TAG, "Success reqId: $reqId")
                SPUtils.getInstance().put(key, GsonSafetyUtils.getInstance().mGson.toJson(result))
                updateModelFromNet(key)
            }

            override fun onFailure(reqId: String, errCode: String?, msg: String?) {
                PLog.d(TAG, "Failure reqId: $reqId")
            }
        }

    private fun updateModelFromNet(key: String) {
        items.firstOrNull { it.key == key }?.let {
            tryLoadModelFromCache(key, it, ModelStatus.NET)
        }
    }

    data class Item<T : BaseModel>(
        val key: String,
        var model: T,
        var status: ModelStatus = ModelStatus.DEFAULT,
        val loader: (() -> Unit)
    )

    enum class ModelStatus {
        /** 默认的数据 */
        DEFAULT,

        /** 磁盘缓存的数据 */
        CACHE,

        /** 网络返回的数据 */
        NET
    }
}