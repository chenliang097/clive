package com.rongtuoyouxuan.chatlive.crtutil.util;


import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Shader;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.style.ForegroundColorSpan;

public class BitmapGradientUtil {
    public static void addTextBitmapGradientSpan(SpannableStringBuilder builder, CharSequence text, Resources resoures, int res_img_id) {
        Bitmap bm = BitmapFactory.decodeResource(resoures, res_img_id);
        Bitmap newBitmap = getNewBitmap(bm);
        BitmapGradientSpan bitmapGradientSpan = new BitmapGradientSpan(bm);

        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(bitmapGradientSpan, 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.append(spannableString);
    }

    private static Bitmap getNewBitmap2(Bitmap bitmap) {
        // 获得图片的宽高
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        float newHeight = 61;


        float newWidth = width * newHeight * 1.0f / height * 1.0f;


        // 计算缩放比例
        float scaleWidth = newWidth / width;
        float scaleHeight = newHeight / height;


        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        // 得到新的图片
        Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        return newBitmap;
    }

    private static Bitmap getNewBitmap(Bitmap bitmap) {
        // 获得图片的宽高
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();


        // 设置想要的大小
        float newHeight = 61;


        float newWidth = width * newHeight * 1.0f / height * 1.0f;


        // 计算缩放比例
        float scaleWidth = newWidth / width;
        float scaleHeight = newHeight / height;


        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        // 得到新的图片
        Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        return newBitmap;
    }

    @SuppressLint("ParcelCreator")
    static public class BitmapGradientSpan extends ForegroundColorSpan {
        private Bitmap bm;

        public BitmapGradientSpan(Bitmap bm) {
            super(Color.parseColor("#FFFFFF"));
            this.bm = bm;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            BitmapShader bitmapShader = new BitmapShader(bm, Shader.TileMode.CLAMP, Shader.TileMode.REPEAT);
            ds.setShader(bitmapShader);
            ds.setUnderlineText(false);
        }
    }

}
