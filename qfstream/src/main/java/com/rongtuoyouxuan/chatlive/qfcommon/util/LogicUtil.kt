package com.rongtuoyouxuan.chatlive.qfcommon.util

import android.content.Intent
import android.net.Uri
import com.blankj.utilcode.util.*
import com.rongtuoyouxuan.chatlive.crtbiz2.constanst.SpConfig
import com.rongtuoyouxuan.chatlive.crtdatabus.DataBus
import java.util.*

/*
*Create by {Mr秦} on 2022/8/31
*/
object LogicUtil {
    //+86   转换 86
    fun regionCodeStr(regionCode: String?): String {
        try {
            if (regionCode?.contains("+", true) == true) {
                return regionCode.substring(1)
            }
        } catch (e: Exception) {
            return ""
        }
        return ""
    }

    /**
     * 清楚用户数据
     * */
    fun clearData() {
        DataBus.instance().cleanUserInfo()
        SPUtils.getInstance(SpConfig.SP_USER).clear()
//        ImHelper.getInstance().logout()
    }

    /**
     * 清楚用户数据并跳转登陆页面
     * */
    fun clearAndToLogin(toast: String?) {
        clearData()
    }



    /**
     * 打点  当天  和 第 2 5 7 天  打开app
     * */
    fun onEvent() {

        try {
            val spKey = "time_${DataBus.instance().userInfo.value?.user_info?.userId}"
            val value: Long = SPUtils.getInstance(SpConfig.SP_SAVE).getLong(spKey, -1L)

            if (value != -1L) {
                val timeStr = TimeUtils.getFitTimeSpan(Date(),
                    Date(value), 1)
                var eventKey: String? = when (timeStr) {
                    else -> null
                }
            } else {
                SPUtils.getInstance(SpConfig.SP_SAVE)
                    .put(spKey, Date().time)
            }

        } catch (e: Exception) {

        }
    }


    /**
     * 调转到Google Play
     */
    fun launchGooglePlay() {
        try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://play.google.com/store/apps/details?id=${AppUtils.getAppPackageName()}")
                setPackage("com.android.vending")
            }
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            ActivityUtils.getTopActivity().startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    //使用系统浏览器打开链接
    fun openURL(url:String?){
        if(StringUtils.isTrimEmpty(url))return
        val uri: Uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        ActivityUtils.getTopActivity().startActivity(intent)
    }

}