package com.rongtuoyouxuan.qfcommon.util

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.blankj.utilcode.util.SPUtils
import com.rongtuoyouxuan.chatlive.biz2.constanst.UrlConstanst
import com.rongtuoyouxuan.qfcommon.BuildConfig
import com.rongtuoyouxuan.qfcommon.player.GiftResourceManager
import kotlin.system.exitProcess

/**
 * 
 * date:2022/8/22-10:20
 * des:游戏-游戏-Hot
 */
object APIEnvironment {
    var ENV_TYPE = "product"

    fun initEnvironment() {
        ENV_TYPE = getEnvironment()
        when (ENV_TYPE) {
            EnvType.DEV.type -> {
                UrlConstanst.devEnv()
            }
            EnvType.TEST.type -> {
                UrlConstanst.testEnv()
            }
            else -> {
                UrlConstanst.productEnv()
            }
        }
    }

    fun changeEnvironment(context: Context) {
//        if (isProductEnvironment()) {
//            return
//        }
        var def = -1
        when (ENV_TYPE) {
            EnvType.PRODUCT.type -> def = 0
            EnvType.TEST.type -> def = 1
            EnvType.DEV.type -> def = 2
        }
        AlertDialog.Builder(context).setTitle("switch environment")
            .setSingleChoiceItems(
                arrayOf(
                    EnvType.PRODUCT.type,
                    EnvType.TEST.type,
                    EnvType.DEV.type
                ), def
            ) { dialog, which ->
                try {
                    when (which) {
                        0 -> {
                            ENV_TYPE = EnvType.PRODUCT.type
                            setEnvironment(EnvType.PRODUCT.type)
                        }
                        1 -> {
                            ENV_TYPE = EnvType.TEST.type
                            setEnvironment(EnvType.TEST.type)
                        }
                        2 -> {
                            ENV_TYPE = EnvType.DEV.type
                            setEnvironment(EnvType.DEV.type)
                        }
                    }
                    context.showAlertDialog(
                        message = "Click the switch to exit the application, reopen the application",
                        posListener = {
                            GiftResourceManager.clearTime()
                            LogicUtil.clearData()
                            exitProcess(0)
                        },
                        nagText = "",
                        outside = false
                    )
                    dialog.dismiss()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }.show()
    }

    fun isProductEnvironment(): Boolean {
        return getEnvironment() == "product"
    }

    fun isDevEnvironment(): Boolean {
        return getEnvironment() == "dev"
    }

    fun isTestEnvironment(): Boolean {
        return getEnvironment() == "test"
    }

    private fun setEnvironment(env: String) {
        getSharedPreferences().put("env", env)
    }

    private fun getEnvironment(): String {
        if (BuildConfig.DEBUG) {
            return getSharedPreferences().getString("env", "dev") ?: "dev"
        }
        return getSharedPreferences().getString("env", "product") ?: "product"
    }

    private fun getSharedPreferences(): SPUtils {
        return SPUtils.getInstance()
    }

    private fun Context.showAlertDialog(
        title: String? = null, message: String,
        posText: String = "confirm", posListener: () -> Unit,
        nagText: String = "cancel", nagListener: () -> Unit = {},
        outside: Boolean = true
    ) {
        val normalDialog = AlertDialog.Builder(this)
        if (!title.isNullOrEmpty()) {
            normalDialog.setTitle(title)
        }
        normalDialog.setMessage(message)
        normalDialog.setPositiveButton(
            posText
        ) { _, _ ->
            posListener()
        }
        normalDialog.setNegativeButton(
            nagText
        ) { dialog, _ ->
            nagListener()
            dialog.dismiss()
        }
        // 显示
        normalDialog.show().setCanceledOnTouchOutside(outside)
    }
}

enum class EnvType(val type: String) {
    DEV("dev"), TEST("test"), PRODUCT("product")
}