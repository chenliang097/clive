package com.rongtuoyouxuan.chatlive.crtbiz2.model.im.response.convert;

import androidx.room.TypeConverter;
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 *	@Description : 转换器
 *	@Author : jianbo
 *	@Date : 2022/9/2  14:40
 */
class AnyConverter {

    @TypeConverter
    fun stringToObject(value: String): List<Any> {
        val listType = object : TypeToken<List<Any>>() {

        }.type

        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun objectToString(list: List<Any>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}
