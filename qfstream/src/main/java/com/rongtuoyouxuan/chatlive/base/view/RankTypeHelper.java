package com.rongtuoyouxuan.chatlive.base.view;


public class RankTypeHelper {
    public static final String gift_date = "gift_date";
    public static final String gift_month = "gift_month";
    public static final String gift_total = "gift_total";
    public static final String share_date = "share_date";
    public static final String share_month = "share_month";
    public static final String share_total = "share_total";
    public static final String[] gift = {gift_date,gift_month,gift_total};
    public static final String[] share = {share_date,share_month,share_total};

    public static boolean isUser(String type){
        return gift_date.equals(type) || gift_month.equals(type) || gift_total.equals(type);
    }

    public static boolean isShare(String type){
        return share_date.equals(type) || share_month.equals(type) || share_total.equals(type);
    }
}
