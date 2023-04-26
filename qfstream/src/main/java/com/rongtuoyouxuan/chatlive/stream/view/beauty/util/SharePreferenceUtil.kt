package com.rongtuoyouxuan.chatlive.stream.view.beauty.util

import android.content.Context
import android.content.SharedPreferences

/**
 * author: qingyingliu
 * date: 3/20/21
 */
class SharedPreferenceUtil {
    lateinit var context: Context
    lateinit var name: String

    // 默认模式
    var mode: Int = Context.MODE_PRIVATE


    /**
     * 设置sharePreference的存储文件名
     */
    fun withName(sharePreferenceName: String): SharedPreferenceUtil {
        this.name = sharePreferenceName
        return this
    }

    /**
     * 设置创建sharePreference的上下文
     */
    fun withContext(context: Context): SharedPreferenceUtil {
        this.context = context
        return this
    }

    /**
     * 设置SharedPreference的操作模式
     * 默认MODE_PRIVATE
     */
    fun withMode(mode: Int) {
        this.mode = mode
    }

    /**
     * 创建SharedPreference对象
     */
    fun build(): SharedPreferences? {
        return context.getSharedPreferences(name, mode)
    }

    companion object {
        fun getInstance(): SharedPreferenceUtil {
            return SharedPreferenceUtil()
        }
    }
}