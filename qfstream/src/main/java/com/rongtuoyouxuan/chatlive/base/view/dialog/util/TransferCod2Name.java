package com.rongtuoyouxuan.chatlive.base.view.dialog.util;

import android.content.Context;

import com.rongtuoyouxuan.chatlive.crtbiz2.config.EditCountryModel;
import com.rongtuoyouxuan.chatlive.crtutil.util.GsonUtils;
import com.rongtuoyouxuan.chatlive.crtutil.util.ReadJsonUtil;


public class TransferCod2Name {

    /**
     * 对应的语言
     * @param context
     * @param code
     * @return
     */
    public static String searchLanguage(Context context, String code) {
        String language = ReadJsonUtil.getJson(context, "language.json");
        EditCountryModel editCountryModel = GsonUtils.fromJson(language, EditCountryModel.class);
        for (EditCountryModel.DataBean dataBean : editCountryModel.getData()) {
            if (dataBean.getCode().equals(code)) {
                return dataBean.getName();
            }
        }
        return code;

    }

    /**
     * 对应的国家
     * @param context
     * @param code
     * @return
     */
    public static String searchCountry(Context context, String code) {
        String language = ReadJsonUtil.getJson(context, "country.json");
        EditCountryModel editCountryModel = GsonUtils.fromJson(language, EditCountryModel.class);
        for (EditCountryModel.DataBean dataBean : editCountryModel.getData()) {
            if (dataBean.getCode().equals(code)) {
                return dataBean.getName();
            }
        }
        return code;
    }

}
