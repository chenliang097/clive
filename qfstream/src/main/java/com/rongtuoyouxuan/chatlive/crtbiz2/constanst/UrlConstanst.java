package com.rongtuoyouxuan.chatlive.crtbiz2.constanst;

public class UrlConstanst {

    /**
     * base url
     */
    public static String BASE_URL_LIVE_API_BOBOO_COM = "https://open.rongtuolive.com";
    public static String BASE_URL_GIFT_API_BOBOO_COM = "https://open.rongtuolive.com";

    public static String ZEGO_URL = "https://aieffects-api.zego.im?Action=DescribeEffectsLicense";

    public static String BASE_URL_FANS_API_BOBOO_COM = "https://open.rongtuolive.com/userProxy/v1/user/fansList";

    public static String BASE_URL_MUTE_LIST_API_BOBOO_COM = "https://open.rongtuolive.com/userProxy/v1/user/forbidSpeakList";

    //H5地址
    public static String BASE_URL_H5_BOBOO_COM = "https://open.rongtuolive.com";

    public static void devEnv() {
        BASE_URL_LIVE_API_BOBOO_COM = "https://open.rongtuolive.com";
        BASE_URL_GIFT_API_BOBOO_COM = "https://open.rongtuolive.com";
        //H5地址
        BASE_URL_H5_BOBOO_COM = "https://open.rongtuolive.com";
    }

    public static void testEnv() {
        BASE_URL_LIVE_API_BOBOO_COM = "https://open.rongtuolive.com";
        BASE_URL_GIFT_API_BOBOO_COM = "https://open.rongtuolive.com";
        //H5地址
        BASE_URL_H5_BOBOO_COM = "https://open.rongtuolive.com";
    }

    public static void productEnv() {
        BASE_URL_LIVE_API_BOBOO_COM = "https://open.rongtuolive.com";
        BASE_URL_GIFT_API_BOBOO_COM = "https://open.rongtuolive.com";
        //H5地址
        BASE_URL_H5_BOBOO_COM = "https://open.rongtuolive.com";
    }
}
