package com.rongtuoyouxuan.chatlive.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import java.lang.reflect.Method;
import java.math.BigDecimal;

public class DisplayUtil {

    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     *
     * @param pxValue
     * @param scale   （DisplayMetrics类中属性density）
     * @return
     */
    public static int px2dip(float pxValue, float scale) {
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue
     * @param scale    （DisplayMetrics类中属性density）
     * @return
     */
    public static int dip2px(float dipValue, float scale) {
        return (int) (dipValue * scale + 0.5f);
    }

    public static int dipToPixels(Context context, float dip) {
        if (context == null) {
            return 0;
        }
        Resources r = context.getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip,
                r.getDisplayMetrics());
        return (int) px;
    }

    public static int spToPixels(Context context, int sp) {
        if (context == null) {
            return 0;
        }
        Resources r = context.getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                r.getDisplayMetrics());
        return (int) px;
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @param fontScale （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(float pxValue, float fontScale) {
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @param fontScale （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(float spValue, float fontScale) {
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 根据图片宽高获取等比例的宽度
     *
     * @param nowWidth
     * @param bmpWidth
     * @param bmpHeight
     */
    public static int getReSizeHeight(int nowWidth, int bmpWidth, int bmpHeight) {
        return (int) (nowWidth * (bmpHeight * 1.0 / bmpWidth));
    }

    public static int getReSizeWidth(int nowHeight, int bmpWidth, int bmpHeight) {
        return (int) (nowHeight * (bmpWidth * 1.0 / bmpHeight));
    }

    /*
     * 隐藏键盘
     */
    public static void hideSolftInput(Context context, View v) {
        if (v == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    // 打开虚拟键盘
    public static void openSoftInput(Context context, View v) {
        if (v == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
        imm.showSoftInput(v, 0);
        // imm.showSoftInputFromInputMethod(v.getWindowToken(), 0);
    }

    /**
     * 解决重新设置背景导致padding失效问题
     *
     * @param view  需要重新设置背景的view
     * @param resid 背景资源id
     */
    public static void setBackgroundKeepPadding(View view, int resid) {
        int bottom = view.getPaddingBottom();
        int top = view.getPaddingTop();
        int right = view.getPaddingRight();
        int left = view.getPaddingLeft();
        view.setBackgroundResource(resid);
        view.setPadding(left, top, right, bottom);
    }

    public static void setBackgroundColorKeepPadding(View view, int color) {
        int bottom = view.getPaddingBottom();
        int top = view.getPaddingTop();
        int right = view.getPaddingRight();
        int left = view.getPaddingLeft();
        view.setBackgroundColor(color);
        view.setPadding(left, top, right, bottom);
    }

    /**
     * 解决重新设置背景导致padding失效问题
     *
     * @param view     需要重新设置背景的view
     * @param drawable 背景图片
     */
    @SuppressLint("NewApi")
    public static void setBackgroundKeepPadding(View view, Drawable drawable) {
        int bottom = view.getPaddingBottom();
        int top = view.getPaddingTop();
        int right = view.getPaddingRight();
        int left = view.getPaddingLeft();
        if (Build.VERSION.SDK_INT >= 16) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
        view.setPadding(left, top, right, bottom);
    }

    /**
     * 获取.9图的padding值
     *
     * @param context
     * @param drawalbeId
     * @return
     */
    public static Rect getNinePatchPading(Context context, int drawalbeId) {
        Rect rect = new Rect();
        Drawable drawable = context.getResources().getDrawable(drawalbeId);
        if (drawable instanceof NinePatchDrawable) {
            NinePatchDrawable ninePatchDrawable = (NinePatchDrawable) drawable;
            ninePatchDrawable.getPadding(rect);
        }
        return rect;

    }


    /**
     * 文字的高，不包括上下留白
     *
     * @param fontSize
     * @return
     */
    public static float getFontHeightOnlyText(float fontSize) {
        Paint paint = new Paint();
        paint.setTextSize(fontSize);
        FontMetrics fm = paint.getFontMetrics();
        return fm.descent - fm.ascent;
    }

    /**
     * 对喜欢 回复 删除 分享按钮进行调整
     *
     * @param size
     * @param top
     * @param textView
     */
    public static void adjustLeftDrawable(int size, int top, TextView textView) {
        Drawable drawable = textView.getCompoundDrawables()[0];
        drawable.setBounds(0, top, size, top + size);
        textView.setCompoundDrawables(drawable, null, null, null);
    }


    public static int getStatusBarHeight(Context context) {
        int result = 0;
        try {
            int resourceId = Resources.getSystem().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                int sizeOne = context.getResources().getDimensionPixelSize(resourceId);
                int sizeTwo = Resources.getSystem().getDimensionPixelSize(resourceId);

                if (sizeTwo >= sizeOne) {
                    return sizeTwo;
                } else {
                    float densityOne = context.getResources().getDisplayMetrics().density;
                    float densityTwo = Resources.getSystem().getDisplayMetrics().density;
                    float f = sizeOne * densityTwo / densityOne;
                    return (int) ((f >= 0) ? (f + 0.5f) : (f - 0.5f));
                }
            }
        } catch (Resources.NotFoundException ignored) {
            return 0;
        }
        return result;
    }

    @SuppressLint("WrongConstant")
    public static GradientDrawable createDrawable(Context context,
                                                  int contentColor, int strokeColor, int radius) {
        GradientDrawable drawable = new GradientDrawable(); // 生成Shape
        drawable.setGradientType(GradientDrawable.RECTANGLE); // 设置矩形
        if (contentColor != -1) {
            drawable.setColor(contentColor);// 内容区域的颜色
        }
        if (strokeColor != -1) {
            drawable.setStroke(DisplayUtil.dipToPixels(context, 1), strokeColor); // 四周描边,描边后四角真正为圆角，不会出现黑色阴影。如果父窗体是可以滑动的，需要把父View设置setScrollCache(false)
        }

        drawable.setCornerRadius(radius); // 设置四角都为圆角
        return drawable;
    }

    /**
     * 说明：获取屏幕的宽度
     *
     * @return
     */
    public static int screenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 获取屏幕高度 包含底部虚拟按键高度
     */
    public static int getRealScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getRealMetrics(dm);
        return dm.heightPixels;
    }

    /**
     * 获取屏幕高度 不包含底部虚拟按键高度
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    /**
     * 获取屏幕尺寸
     * @param context
     * @return
     */
    public static double getScreenInch(Context context) {
        double mInch = 0;
        if (mInch != 0.0d) {
            return mInch;
        }

        try {
            int realWidth = 0, realHeight = 0;
            Display display = ((Activity)context).getWindowManager().getDefaultDisplay();
            DisplayMetrics metrics = new DisplayMetrics();
            display.getMetrics(metrics);
            if (android.os.Build.VERSION.SDK_INT >= 17) {
                Point size = new Point();
                display.getRealSize(size);
                realWidth = size.x;
                realHeight = size.y;
            } else if (android.os.Build.VERSION.SDK_INT < 17
                    && android.os.Build.VERSION.SDK_INT >= 14) {
                Method mGetRawH = Display.class.getMethod("getRawHeight");
                Method mGetRawW = Display.class.getMethod("getRawWidth");
                realWidth = (Integer) mGetRawW.invoke(display);
                realHeight = (Integer) mGetRawH.invoke(display);
            } else {
                realWidth = metrics.widthPixels;
                realHeight = metrics.heightPixels;
            }

            mInch =formatDouble(Math.sqrt((realWidth/metrics.xdpi) * (realWidth /metrics.xdpi) + (realHeight/metrics.ydpi) * (realHeight / metrics.ydpi)),1);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return mInch;
    }

    /**
     * Double类型保留指定位数的小数，返回double类型（四舍五入）
     * newScale 为指定的位数
     */
    private static double formatDouble(double d,int newScale) {
        BigDecimal bd = new BigDecimal(d);
        return bd.setScale(newScale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }


    public static void showView(View... view) {
        if (view != null) {
            for (View value : view) {
                if (value != null) value.setVisibility(View.VISIBLE);
            }
        }
    }

    public static void hideView(View... view) {
        if (view != null) {
            for (View value : view) {
                if (value != null) value.setVisibility(View.GONE);
            }
        }
    }
}