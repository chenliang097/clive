package com.rongtuoyouxuan.chatlive.biz2.model.im.response.convert

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 *	@Description : list转换器
 *	@Author : jianbo
 *	@Date : 2022/9/2  14:40
 */
open class ListConvert<T> {

    @TypeConverter
    fun stringToObject(value: String): List<T> {
        val listType = object : TypeToken<List<T>>() {

        }.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun objectToString(list: List<T>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}

/**
 * String型List转换器
 */
class ListStringConverter : ListConvert<String>()