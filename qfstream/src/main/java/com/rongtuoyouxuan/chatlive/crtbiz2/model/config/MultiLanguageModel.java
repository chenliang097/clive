package com.rongtuoyouxuan.chatlive.crtbiz2.model.config;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MultiLanguageModel implements Serializable {
    @SerializedName("zh")
    public String zh = "";
    @SerializedName("en")
    public String en = "";
    @SerializedName("ja")
    public String ja = "";
    @SerializedName("ko")
    public String ko = "";
    @SerializedName("ru")
    public String ru = "";
    @SerializedName("th")
    public String th = "";
    @SerializedName("vi")
    public String vi = "";
    @SerializedName("ar")
    public String ar = "";
    @SerializedName("zhtw")
    public String zhtw = "";
    @SerializedName("de")
    public String de = "";
    @SerializedName("fr")
    public String fr = "";
    @SerializedName("pt")
    public String pt = "";
    @SerializedName("es")
    public String es = "";
    @SerializedName("ms")
    public String ms = "";
    @SerializedName("in")
    public String in = "";

    public String getText(String languageCode) {
        String text = "";
        switch (languageCode) {
            case "zh":
                text = zh;
                break;
            case "en":
                text = en;
                break;
            case "ja":
                text = ja;
                break;
            case "ko":
                text = ko;
                break;
            case "ru":
                text = ru;
                break;
            case "th":
                text = th;
                break;
            case "vi":
                text = vi;
                break;
            case "ar":
                text = ar;
                break;
            case "zhtw":
                text = zhtw;
                break;
            case "de":
                text = de;
                break;
            case "fr":
                text = fr;
                break;
            case "pt":
                text = pt;
                break;
            case "es":
                text = es;
                break;
            case "ms":
                text = ms;
                break;
            case "in":
                text = in;
                break;
            default:
                text = en;
                break;
        }
        return TextUtils.isEmpty(text) ? en : text;
    }
}
