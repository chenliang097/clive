package com.rongtuoyouxuan.chatlive.crtdatabus.language;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.text.TextUtils;

import com.rongtuoyouxuan.chatlive.crtdatabus.DataBus;
import com.rongtuoyouxuan.chatlive.crtdatabus.storage.Storage;
import com.rongtuoyouxuan.chatlive.crtutil.sp.SPConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.os.Build.VERSION_CODES.JELLY_BEAN_MR1;
import static android.os.Build.VERSION_CODES.N;

public class LocaleManager {

    public static final String LANGUAGE_ENGLISH = "en"; //英文
    public static final String LANGUAGE_SIMPLIFIED_CHINESE = "zh"; //简体中文
    public static final String LANGUAGE_ZH_TW = "zhtw"; //繁体
    public static final String LANGUAGE_TH = "th"; //泰语
    public static final String LANGUAGE_IN = "in"; //印尼
    public static final String LANGUAGE_MS = "ms"; //马来西亚
    public static final String LANGUAGE_VIETNAM = "vi"; //越南
    public static final String LANGUAGE_AR = "ar"; //阿拉伯语

    public static final String LANGUAGE_ENGLISH_NAME = "English"; //英文
    public static final String LANGUAGE_SIMPLIFIED_CHINESE_NAME = "简体中文"; //简体中文
    public static final String LANGUAGE_ZH_TW_NAME = "繁體中文"; //繁体
    public static final String LANGUAGE_TH_NAME = "ไทย"; //泰语
    public static final String LANGUAGE_IN_NAME = "Indonesia"; //印尼
    public static final String LANGUAGE_MS_NAME = "Malaysia"; //马来西亚
    public static final String LANGUAGE_VIETNAM_NAME = "Tiếng việt"; //越南
    public static final String LANGUAGE_AR_NAME = "الصينية المبسطة"; //阿拉伯语

    private List<LanguageBean> supportLanguages;
    private MutableLiveData<String> language = new MutableLiveData<>();

    public LocaleManager() {
        getSupportLanguages();
    }

    public static Locale getLocale(Resources res) {
        Configuration config = res.getConfiguration();
        return Utility.isAtLeastVersion(N) ? config.getLocales().get(0) : config.locale;
    }

    private boolean containLanguage(String lang) {
        for (LanguageBean languageBean : supportLanguages) {
            if (lang.equals(languageBean.language)) {
                return true;
            }
        }
        return false;
    }

    public void init() {
        String lang = Storage.Companion.getInstance().getString(SPConstants.StringConstants.APP_LANGUAGE);
        if (TextUtils.isEmpty(lang)) {
            //语言没有设置过，按系统走
            lang = getSysLanguage();
        }
        if (lang.equals("zh")) {
            String country = getSysCountry();
            if (!country.equalsIgnoreCase("CN")) {
                lang = LANGUAGE_ZH_TW;
            }
        }
        if (!containLanguage(lang)) {
            //支持语言列表里没有，默认英语
            lang = LANGUAGE_ENGLISH;
        }

        language.setValue(lang);
    }

    public Context setLocale(Context c) {
        return updateResources(c, getLanguage().getValue());
    }

    public void switchLanguage(String language) {
        if (TextUtils.isEmpty(language)) {
            return;
        }
        if (!containLanguage(language)) {
            return;
        }

        if (this.language.getValue().equals(language)) {
            return;
        }
        setLanguage(language);
    }

    public LiveData<String> getLanguage() {
        return language;
    }

    private void setLanguage(String language) {
        this.language.setValue(language);
        Storage.Companion.getInstance().set(SPConstants.StringConstants.APP_LANGUAGE, language);
    }

    private Locale getLocale(String language) {
        if (language.equals(LANGUAGE_ZH_TW)) {
            return Locale.TRADITIONAL_CHINESE;
        } else {
            return new Locale(language);
        }
    }

    private Context updateResources(Context context, String language) {
        Locale locale = getLocale(language);
        Locale.setDefault(locale);

        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());
        if (Utility.isAtLeastVersion(JELLY_BEAN_MR1)) {
            config.setLocale(locale);
            context = context.createConfigurationContext(config);
        } else {
            config.locale = locale;
            res.updateConfiguration(config, res.getDisplayMetrics());
        }
        return context;
    }

    /**
     * 支持的国家
     *
     * @return
     */
    public List<LanguageBean> getSupportLanguages() {
        if (supportLanguages == null) {
            List<LanguageBean> list = new ArrayList<>();
            list.add(new LanguageBean(LANGUAGE_ENGLISH, LANGUAGE_ENGLISH_NAME));
            list.add(new LanguageBean(LANGUAGE_TH, LANGUAGE_TH_NAME));
            list.add(new LanguageBean(LANGUAGE_SIMPLIFIED_CHINESE, LANGUAGE_SIMPLIFIED_CHINESE_NAME));
            list.add(new LanguageBean(LANGUAGE_ZH_TW, LANGUAGE_ZH_TW_NAME));
            list.add(new LanguageBean(LANGUAGE_VIETNAM, LANGUAGE_VIETNAM_NAME));
            list.add(new LanguageBean(LANGUAGE_MS, LANGUAGE_MS_NAME));
            list.add(new LanguageBean(LANGUAGE_IN, LANGUAGE_IN_NAME));
            list.add(new LanguageBean(LANGUAGE_AR, LANGUAGE_AR_NAME));
            supportLanguages = list;
        }
        return supportLanguages;
    }

    public String getSysLanguage() {
        return getLocale(DataBus.instance().getAppContext().getResources()).getLanguage();
    }

    public String getSysCountry() {
        return getLocale(DataBus.instance().getAppContext().getResources()).getCountry();
    }

    public String getDefaultTransLanguage() {
        String lang = getSysLanguage();
        if (lang.equalsIgnoreCase("zh")) {
            lang += "-" + getSysCountry();
        }
        return lang;
    }

    public static class LanguageBean {
        public String language;
        public String languageName;

        public LanguageBean(String language, String languageName) {
            this.language = language;
            this.languageName = languageName;
        }
    }
}