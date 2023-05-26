package com.rongtuoyouxuan.chatlive.crtutil.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import androidx.annotation.ColorInt;
import androidx.core.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;

/**
 * malin on 18-1-26.
 */

public class SpannableStringBuilderUtil {

    private static final String ONE_BLANK_STRING = " ";

    /**
     * SpannableStringBuilder中增加图片
     *
     * @param context     Context
     * @param imageHeight imageHeight
     * @param builder     SpannableStringBuilder
     * @param bitmap      Bitmap
     */
    public static void addImageSpan(Context context, int imageHeight, SpannableStringBuilder builder, Bitmap bitmap) {
        try {
            if (context == null || builder == null || bitmap == null || bitmap.isRecycled()) return;
            SpannableString span = new SpannableString(ONE_BLANK_STRING);
            Drawable drawable = new BitmapDrawable(context.getResources(), bitmap);
            if (imageHeight == Integer.MAX_VALUE) {
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            } else {
                float ratio = (float) drawable.getIntrinsicWidth() / drawable.getIntrinsicHeight();
                drawable.setBounds(0, 0, (int) (imageHeight * ratio), imageHeight);
            }
            LineSpacingImageSpan lineSpacingImageSpan = new LineSpacingImageSpan(drawable);
            span.setSpan(lineSpacingImageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.append(span);
            builder.append(ONE_BLANK_STRING);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }


    /**
     * SpannableStringBuilder中增加图片
     *
     * @param context Context
     * @param builder SpannableStringBuilder
     * @param bitmap  Bitmap
     */
    public static void addImageSpan(Context context, SpannableStringBuilder builder, Bitmap bitmap) {
        addImageSpan(context, Integer.MAX_VALUE, builder, bitmap);
    }


    /**
     * SpannableStringBuilder中增加图片
     *
     * @param context Context
     * @param builder SpannableStringBuilder
     * @param resId   resId
     */
    public static void addDrawableImageSpan(Context context, int imageHeight, SpannableStringBuilder builder, int resId) {
        if (context == null || builder == null || resId <= 0) return;
        try {
            SpannableString span = new SpannableString(ONE_BLANK_STRING);
            Drawable drawable = ContextCompat.getDrawable(context, resId);
            if (drawable == null) return;
            if (imageHeight == Integer.MAX_VALUE) {
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            } else {
                float ratio = (float) drawable.getIntrinsicWidth() / drawable.getIntrinsicHeight();
                drawable.setBounds(0, 0, (int) (imageHeight * ratio), imageHeight);
            }
            LineSpacingImageSpan lineSpacingImageSpan = new LineSpacingImageSpan(drawable);
            span.setSpan(lineSpacingImageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.append(span);
            builder.append(ONE_BLANK_STRING);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    /**
     * SpannableStringBuilder中增加图片
     *
     * @param context Context
     * @param builder SpannableStringBuilder
     * @param resId   resId
     */
    public static void addDrawableImageSpan(Context context, SpannableStringBuilder builder, int resId) {
        addDrawableImageSpan(context, Integer.MAX_VALUE, builder, resId);
    }


    /**
     * SpannableStringBuilder 中增加文字
     *
     * @param builder    SpannableStringBuilder
     * @param text       text
     * @param text_color text_color
     */
    public static void addTextSpan(SpannableStringBuilder builder, CharSequence text, @ColorInt int text_color) {
        try {
            SpannableString spannableString = new SpannableString(text);
            spannableString.setSpan(new ForegroundColorSpan(text_color), 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.append(spannableString);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }


    /**
     * SpannableStringBuilder 中增加文字
     *
     * @param builder    SpannableStringBuilder
     * @param text       text
     * @param text_color text_color
     * @param text_size  text_size
     */
    public static void addTextSpan(SpannableStringBuilder builder, CharSequence text, @ColorInt int text_color, float text_size) {
        try {
            SpannableString spannableString = new SpannableString(text);
            spannableString.setSpan(new ForegroundColorSpan(text_color), 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new RelativeSizeSpan(text_size), 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.append(spannableString);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
