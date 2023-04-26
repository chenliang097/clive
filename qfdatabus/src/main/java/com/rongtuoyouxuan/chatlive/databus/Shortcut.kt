/**
 * 全局变量快捷方式
 */
@file:JvmName("C")

package com.rongtuoyouxuan.chatlive.databus

import com.rongtuoyouxuan.chatlive.biz2.model.config.ClientConfModel
import com.rongtuoyouxuan.chatlive.databus.config.ConfigManager

/**
 * DataBus 实例
 */
val dataBus: DataBus by lazy { DataBus.instance() }

/**
 * 全局配置
 */
val configManager: ConfigManager by lazy { dataBus.configMananger }

/**
 * /static/client_conf.json 接口配置
 */
val clientConfig: ClientConfModel by lazy { configManager.appConfig }