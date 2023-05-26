package com.rongtuoyouxuan.chatlive.crtbiz2.model.mine.response

import com.google.gson.annotations.SerializedName

/**
 *	@Description : 版本更新model
 *	@Author : jianbo
 *	@Date : 2022/9/7  18:15
 */
data class VersionInfo(
    @SerializedName("ios_version")
    val iosVersion: String,

    @SerializedName("android_version")
    val androidVersion: String,

    @SerializedName("updated_at")
    val updatedAtTime: String,

    @SerializedName("android_setting")
    val androidSetting: AndroidSetting,
)

data class AndroidSetting(
    @SerializedName("version")
    val version: String,

    @SerializedName("ignore_reminder_version")
    val ignoreReminderVersion: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("content")
    val content: String,

    @SerializedName("force_update")
    val forceUpdate: Int,

    @SerializedName("reminder")
    val reminder: Int,
)


data class VersionInfoModel(val data: VersionInfo) : com.rongtuoyouxuan.chatlive.crtnet.BaseModel()