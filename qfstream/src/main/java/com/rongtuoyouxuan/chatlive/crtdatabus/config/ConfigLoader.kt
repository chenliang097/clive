package com.rongtuoyouxuan.chatlive.crtdatabus.config

import com.rongtuoyouxuan.chatlive.crtbiz2.model.config.ClientConfModel
import com.rongtuoyouxuan.chatlive.crtbiz2.model.language.LanguageCodeModel
import com.rongtuoyouxuan.chatlive.crtbiz2.config.ClientConfigBiz
import com.rongtuoyouxuan.chatlive.crtutil.sp.SPConstants


/**
 * app 配置加载器, 自动缓存配置项到磁盘。
 *
 * 使用方法步骤：
 * 2、ClientConfigBiz 中创建加载相关配置的方法
 * 3、在 init 方法中把biz配置到items中。
 * eg.  Item(
 *             Key.LANGUAGE_CODE, // key
 *             LanguageCodeModel() // 默认值
 *          ) { ClientConfigBiz.getLanguageCode(genListenerWrapper(Key.LANGUAGE_CODE)) } // biz
 * 4、定义加载方法
 * eg.  fun getClientModel(): ClientConfigModel {
 *           return getItem(Key.CLIENT_CONFIG) as ClientConfigModel
 *       }
 *
 */
object ConfigLoader : BaseConfigLoader() {

    init {
        //客户端配置
        items.add(
            Item(
                SPConstants.StringConstants.APP_CONFIG,
                ClientConfModel()
            ) { ClientConfigBiz.getClientConfig(genListenerWrapper(SPConstants.StringConstants.APP_CONFIG)) })

        //语言配置
//        items.add(
//            Item(
//                Key.LANGUAGE_CODE,
//                LanguageCodeModel()
//            ) { ClientConfigBiz.getLanguageCgetLanguageCodeode(genListenerWrapper(Key.LANGUAGE_CODE)) })
    }

    /**
     * client config
     */
    fun getClientModel(): ClientConfModel {
        return getItem(SPConstants.StringConstants.APP_CONFIG) as ClientConfModel
    }

    /**
     * client config
     */
    fun getLanguageCode(): LanguageCodeModel {
        return getItem(SPConstants.StringConstants.APP_CONFIG) as LanguageCodeModel
    }
}