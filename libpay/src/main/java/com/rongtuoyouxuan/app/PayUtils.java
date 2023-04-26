package com.rongtuoyouxuan.app;

public class PayUtils {

    public static double intToDouble(int payCount){
        return (double)(payCount/100.0);//这样为保持2位
    }

    public interface PayType{
        String WECHAT = "wechat";
        String ALI = "ali";
    }
}
