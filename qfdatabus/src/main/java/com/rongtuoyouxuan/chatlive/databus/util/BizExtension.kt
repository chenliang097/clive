@file:JvmName("BizExtension")

package com.rongtuoyouxuan.chatlive.databus.util

import com.rongtuoyouxuan.chatlive.databus.DataBus
import com.rongtuoyouxuan.chatlive.databus.config.ConfigLoader.getLanguageCode
import com.rongtuoyouxuan.chatlive.databus.R
import com.hbb20.Country

/**
 * 性别小icon
 */
fun Int?.getGenderIconRes(): Int = if (this != null && this == 1) R.drawable.commen_default else R.drawable.commen_default

fun String?.getGenderIconRes(): Int = this?.toIntOrNull().getGenderIconRes()

/**
 * 性别头像
 */
fun Int?.getGenderAvatarRes(): Int = if (this != null && this == 1)
    R.drawable.commen_default else R.drawable.commen_default

fun String?.getGenderAvatarRes(): Int = this?.toIntOrNull().getGenderAvatarRes()

/**
 * 当前id是否是我
 */
fun Int?.isMe(): Boolean = this != null && this.toString() == DataBus.instance().uid

fun String?.isMe(): Boolean = this?.toIntOrNull().isMe()

/**
 * 根据country code 得到当前语言的name
 */
fun String?.getCountryName(): String = if (this.isNullOrEmpty()) "" else
    DataBus.instance().configMananger.getCountryName(this)

/**
 * 国旗
 */
fun String?.getFlagResID(): Int = Country.getFlagResID(this)

/**
 * 对应语言文案
 */
fun String?.getLanguageName(): String = if (this.isNullOrEmpty()) "" else
    getLanguageCode().data?.firstOrNull { it.code == this }?.name ?: ""
