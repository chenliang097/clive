package com.rongtuoyouxuan.chatlive.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import java.math.BigDecimal;


/**
 * 说明：UI工具类
 */
public final class UIUtils {

    /**
     * 说明：禁止实例化
     */
    public UIUtils(){
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context mContext,float dpValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context mContext,int dpValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context mContext,float pxValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 sp
     */
    public static int px2sp(Context mContext,float pxValue) {
        float fontScale = mContext.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 sp 的单位 转成为 px
     */
    public static int sp2px(Context mContext,float spValue) {
        float fontScale = mContext.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 获取资源
     * @return
     */
    private static Resources getResource(Context mContext) {
        return mContext.getResources();
    }

    /**
     * 返回String数组
     * @param id  资源id
     * @return
     */
    public static String[] getStringArray(Context mContext,int id) {
        return getResource(mContext).getStringArray(id);
    }

    /**
     * 说明：获取字符串资源
     * @param id
     * @return
     */
    public static String getString(Context mContext,int id) {
        return getResource(mContext).getString(id);
    }
    /**
     * 说明：获取屏幕的宽度
     * @return
     */
    public static int screenWidth(Context mContext){
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 获取view的宽度
     * @param view
     * @return
     */
    public static int getMeasuredWidth(View view){
        int width = 0;
        if (view != null){
            int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
            int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
            view.measure(w, h);
            width = view.getMeasuredWidth();
        }
        return width;
    }

    /**
     * 获取view的高度
     * @param view
     * @return
     */
    public static int getMeasuredHeight(View view){
        int height = 0;
        if (view != null){
            int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
            int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
            view.measure(w, h);
            height = view.getMeasuredHeight();
        }
        return height;
    }

    /**
     * 说明：获取屏幕的高度
     * @return
     */
    public static int screenHeight(Context mContext){
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }
    /**
     * 说明：获取屏幕的密度
     * @return
     */
    public static float density(Context mContext){
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.density;
    }
//
//    /**
//     * 说明：获取状态栏的高度
//     * @return
//     */
//    public static int getStatusBarHeight(){
//        int height = 0;
//        try {
//            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
//            Object object = clazz.newInstance();
//            Field field = clazz.getField("status_bar_height");
//            int x = NumberUtils.toInt(field.get(object).toString());
//            height = getResource().getDimensionPixelSize(x);
//        }catch (Exception e){
//            LogUtils.e(e);
//        }
//        return height;
//    }

    /**
     * 说明：获取布局文件
     * @param id
     * @param group
     * @param flag
     * @return
     *
     */
    public static View inflate(Context mContext,int id,ViewGroup group,boolean flag){
        return LayoutInflater.from(mContext).inflate(id, group, flag);
    }
    /**
     * 说明：获取布局文件
     * @param id
     * @return
     */
    public static View inflate(Context mContext,int id){
        return LayoutInflater.from(mContext).inflate(id, null, false);
    }
    /**
     * 处理昵称，太长时显示...
     *
     * @param nickName
     * @return
     */
    public static String getNickName(String nickName) {
        return getNickName(nickName, 10);
    }

    /**
     * 处理昵称，太长时显示...
     *
     * @param nickName
     * @return
     */
    public static String getNickName(String nickName, int len) {
        String nick = nickName;
        if (StringUtils.isNotEmpty(nickName) && nickName.length() >= len) {
            nick = nickName.substring(0, len - 3) + "...";
        }
        return nick;
    }

    /**
     * 获取图片宽高
     * @param resourcesId  本地Resource ID
     * @return
     */
    public static int[] getImageWidthHeight(Context mContext ,int resourcesId){
        BitmapFactory.Options options = new BitmapFactory.Options();

        /**
         * 最关键在此，把options.inJustDecodeBounds = true;
         * 这里再decodeFile()，返回的bitmap为空，但此时调用options.outHeight时，已经包含了图片的高了
         */
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), resourcesId, options); // 此时返回的bitmap为null
        /**
         *options.outHeight为原始图片的高
         */
        return new int[]{options.outWidth,options.outHeight};
    }

    /**
     * 提供精确的除法运算方法div
     *
     * @param value1 被除数
     * @param value2 除数
     * @param scale  精确范围
     * @return 两个参数的商
     * @throws IllegalAccessException
     */
    public static double div(double value1, double value2, int scale) throws IllegalAccessException {
        //如果精确范围小于0，抛出异常信息
        if (scale < 0) {
            throw new IllegalAccessException("精确度不能小于0");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(value1));
        BigDecimal b2 = new BigDecimal(Double.toString(value2));
        //默认保留两位会有错误，这里设置保留小数点后4位
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static SpannableString addTextColor(String msg, int startLength, int endLength, int color) {
        SpannableString spannable = new SpannableString(msg);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(color);
        spannable.setSpan(colorSpan, startLength, endLength, 33);
        return spannable;
    }

    public static SpannableString addTextColorBottomLine(String msg, int startLength, int endLength, final int color) {
        SpannableString spannable = new SpannableString(msg);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(color);
        spannable.setSpan(colorSpan, startLength, endLength, 33);
        spannable.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) { }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(color);//设置颜色
            }
        }, startLength, endLength, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannable;
    }

    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {

            if (c[i] == 32) {
                c[i] = (char) 12288;
                continue;
            }
            if (c[i] < 127 && c[i]>32)
                c[i] = (char) (c[i] + 65248);

        }
        return new String(c);
    }



}


