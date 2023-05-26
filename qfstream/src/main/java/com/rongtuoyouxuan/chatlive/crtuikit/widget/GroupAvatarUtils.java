package com.rongtuoyouxuan.chatlive.crtuikit.widget;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import java.util.List;

/**
 * @Description : 群聊合成前4个成员头像bitmap
 * @Author : jianbo
 * @Date : 2022/9/1  16:08
 */
public class GroupAvatarUtils {

    private static final String TAG = "GroupAvatarUtils";

    private static final int WHOLE = 0;
    private static final int LEFT = 1;
    private static final int RIGHT = 2;
    private static final int TOP = 3;
    private static final int BOTTOM = 4;
    private static final int LEFT_TOP = 5;
    private static final int LEFT_BOTTOM = 6;
    private static final int RIGHT_TOP = 7;
    private static final int RIGHT_BOTTOM = 8;

    private static final int marginWhiteWidth = 2; // 中间白色宽度

    private static int mWidth;
    private static int mHeight;

    public static Bitmap getAvatar(List<Bitmap> list, int width, int height) {
        mWidth = width;
        mHeight = height;
        final Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);

        Path mPath = new Path();
        mPath.addCircle(width / 2, height / 2, width / 2, Path.Direction.CCW);
        //canvas.clipPath(mPath); //切割画布

        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        canvas.drawPath(mPath, paint);

        Log.d(TAG, "mWidth---> " + mWidth + "  mHeight---> " + mHeight);
        Log.d(TAG, "canvas Width ---> " + canvas.getWidth() + "  Height---> " + canvas.getHeight());

        if (list == null) {
            return null;
        }
        final int listSize = list.size();
        switch (listSize) {
            case 1:
                drawBitmap(canvas, (Bitmap) list.get(0), WHOLE);
                break;
            case 2:
                drawBitmap(canvas, (Bitmap) list.get(0), LEFT);
                drawBitmap(canvas, (Bitmap) list.get(1), RIGHT);
                break;
            case 3:
                drawBitmap(canvas, (Bitmap) list.get(0), TOP);
                drawBitmap(canvas, (Bitmap) list.get(1), LEFT_BOTTOM);
                drawBitmap(canvas, (Bitmap) list.get(2), RIGHT_BOTTOM);
                break;
            case 4:
                drawBitmap(canvas, (Bitmap) list.get(0), LEFT_TOP);
                drawBitmap(canvas, (Bitmap) list.get(1), RIGHT_TOP);
                drawBitmap(canvas, (Bitmap) list.get(2), LEFT_BOTTOM);
                drawBitmap(canvas, (Bitmap) list.get(3), RIGHT_BOTTOM);
                break;
            default:
                break;
        }
        return result;
    }

    private static void drawBitmap(Canvas canvas, Bitmap bitmap, int mode) {
        int left = 0, top = 0;

        Log.d(TAG, "bitmap Width ---> " + bitmap.getWidth());
        Log.d(TAG, "bitmap Height ---> " + bitmap.getHeight());

        int mWidth = canvas.getWidth();
        int mHeight = canvas.getHeight();

        int dstWidth = mWidth / 2;
        int dstHeight = mHeight / 2;

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        if (mode == WHOLE) {
            left = 0;
            top = 0;
            // 比例缩放
            Bitmap bmp = Bitmap.createScaledBitmap(bitmap, mWidth, mHeight, false);
            canvas.drawBitmap(bmp, left, top, paint);
        } else if (mode == LEFT) {
            left = 0;
            top = mWidth / 4 + marginWhiteWidth / 2;

            // 比例缩放
            Bitmap bmp = Bitmap.createScaledBitmap(bitmap, dstWidth, dstHeight, false);
            // 绘图
            canvas.drawBitmap(toRoundBitmap(bmp), left, top, paint);
        } else if (mode == RIGHT) {
            left = mWidth / 2 + marginWhiteWidth / 2;
            top = mWidth / 4;

            // 比例缩放
            Bitmap bmp = Bitmap.createScaledBitmap(bitmap, dstWidth, dstHeight, false);
            canvas.drawARGB(0, 153, 50, 204);

            // 绘图
            canvas.drawBitmap(toRoundBitmap(bmp), left, top, paint);
        } else if (mode == TOP) {
            left = mWidth / 4;
            top = 0;
            // 比例缩放
            Bitmap bmp = Bitmap.createScaledBitmap(bitmap, dstWidth, dstHeight, false);
            // 绘图
            canvas.drawBitmap(toRoundBitmap(bmp), left, top, paint);
        } else if (mode == LEFT_TOP) {
            left = 0;
            top = 0;

            // 比例缩放
            Bitmap bmp = Bitmap.createScaledBitmap(bitmap, dstWidth, dstHeight, false);
            // 绘图
            canvas.drawBitmap(toRoundBitmap(bmp), left, top, paint);
        } else if (mode == RIGHT_TOP) {
            left = mWidth / 2 + marginWhiteWidth / 2;
            top = 0;

            // 比例缩放
            Bitmap bmp = Bitmap.createScaledBitmap(bitmap, dstWidth, dstHeight, false);
            // 绘图
            canvas.drawBitmap(toRoundBitmap(bmp), left, top, paint);
        } else if (mode == LEFT_BOTTOM) {
            left = 0;
            top = mHeight / 2 + marginWhiteWidth / 2;

            // 比例缩放
            Bitmap bmp = Bitmap.createScaledBitmap(bitmap, dstWidth, dstHeight, false);
            // 绘图
            canvas.drawBitmap(toRoundBitmap(bmp), left, top, paint);
        } else if (mode == RIGHT_BOTTOM) {
            left = mWidth / 2 + marginWhiteWidth / 2;
            top = mHeight / 2 + marginWhiteWidth / 2;
            // 比例缩放
            Bitmap bmp = Bitmap.createScaledBitmap(bitmap, dstWidth, dstHeight, false);
//            // 裁取中间部分(从x点裁取置顶距离)
//            Bitmap dstBmp = Bitmap.createBitmap(bmp, x, y, width, height);
            // 绘图
            canvas.drawBitmap(toRoundBitmap(bmp), left, top, paint);
        }
    }

    /**
     * 转换图片成圆形
     *
     * @param bitmap 传入Bitmap对象
     * @return
     */
    public static Bitmap toRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            top = 0;
            bottom = width;
            left = 0;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }

        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
//        final int color = Color.RED;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right,
                (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top,
                (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
//        canvas.drawARGB(0, 153, 50, 204);
//        canvas.drawColor(Color.RED);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        return output;
    }

    /**
     * to圆角Bitmap
     *
     * @param bitmap
     * @return
     */
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {
        try {
            Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                    bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);
            final int color = 0xff424242;// 颜色值（0xff---alpha）
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            final RectF rectF = new RectF(rect);// Rect是使用int类型作为数值，RectF是使用float类型作为数值
            // --------抗锯齿-------//
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            final Rect src = new Rect(0, 0, bitmap.getWidth(),
                    bitmap.getHeight());
            canvas.drawBitmap(bitmap, null, rect, paint);
            return output;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
