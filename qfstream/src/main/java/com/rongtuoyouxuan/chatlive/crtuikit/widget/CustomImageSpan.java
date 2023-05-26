package com.rongtuoyouxuan.chatlive.crtuikit.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.style.ImageSpan;

/**
 * Created by Dell on 2018/8/16.
 *
 * 参考资料：
 * https://www.cnblogs.com/withwind318/p/5541267.html
 */

public class CustomImageSpan extends ImageSpan {

    //自定义对齐方式--与文字中间线对齐
    private static final int ALIGN_FONTCENTER = 2;

    public CustomImageSpan(Drawable drawable) {
        super(drawable);
    }

    public CustomImageSpan(Drawable drawable, int verticalAlignment) {
        super(drawable, verticalAlignment);
    }

    public CustomImageSpan(Context context, int resourceId) {
        super(context, resourceId);
    }

    public CustomImageSpan(Context context, int resourceId, int verticalAlignment) {
        super(context, resourceId, verticalAlignment);
    }
    public CustomImageSpan(Context context, Bitmap b) {
        super(context,b);
    }
    //        public CustomImageSpan(Context context, int resourceId,int width,int height) {
//            super(context, ThumbnailUtils.extractThumbnail(BitmapFactory.decodeResource(context.getResources(),resourceId),
//                    AutoUtils.getPercentWidthSize(width), AutoUtils.getPercentWidthSize(height)), 2);
//
//        }

    /**
     * Draws the span into the canvas.
     *
     * @param canvas Canvas into which the span should be rendered.
     * @param text Current text.
     * @param start Start character index for span.
     * @param end End character index for span.
     * @param x Edge of the replacement closest to the leading margin.
     * @param top Top of the line.
     * @param y Baseline.
     * @param bottom Bottom of the line.
     * @param paint Paint instance.
     */
    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom,
                     Paint paint) {

        //draw 方法是重写的ImageSpan父类 DynamicDrawableSpan中的方法，在DynamicDrawableSpan类中，虽有getCachedDrawable()，
        // 但是私有的，不能被调用，所以调用ImageSpan中的getrawable()方法，该方法中 会根据传入的drawable ID ，获取该id对应的
        // drawable的流对象，并最终获取drawable对象
//        Drawable drawable = getDrawable(); //调用imageSpan中的方法获取drawable对象
//        canvas.save();
//
//        //获取画笔的文字绘制时的具体测量数据
//        Paint.FontMetricsInt fm = paint.getFontMetricsInt();
//
//        //系统原有方法，默认是Bottom模式)
//        int transY = bottom - drawable.getBounds().bottom;
//        if (mVerticalAlignment == ALIGN_BASELINE) {
//            transY -= fm.descent;
//        } else if (mVerticalAlignment == ALIGN_FONTCENTER) {    //此处加入判断， 如果是自定义的居中对齐
//            //与文字的中间线对齐（这种方式不论是否设置行间距都能保障文字的中间线和图片的中间线是对齐的）
//            // y+ascent得到文字内容的顶部坐标，y+descent得到文字的底部坐标，（顶部坐标+底部坐标）/2=文字内容中间线坐标
//            transY = ((y + fm.descent) + (y + fm.ascent)) / 2 - drawable.getBounds().bottom / 2;
//        }
//
//        canvas.translate(x, transY);
//        drawable.draw(canvas);
//        canvas.restore();
        Drawable b = getDrawable();
        Paint.FontMetricsInt fm = paint.getFontMetricsInt();
        int transY = (y + fm.descent + y + fm.ascent) / 2 - b.getBounds().bottom / 2;//计算y方向的位移
        canvas.save();
        canvas.translate(x, transY);//绘制图片位移一段距离
        b.draw(canvas);
        canvas.restore();
    }

    /**
     * 重写getSize方法，只有重写该方法后，才能保证不论是图片大于文字还是文字大于图片，都能实现中间对齐
     */
    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        Drawable d = getDrawable();
        Rect rect = d.getBounds();
        if (fm != null) {
            Paint.FontMetricsInt fmPaint = paint.getFontMetricsInt();
            int fontHeight = fmPaint.bottom - fmPaint.top;
            int drHeight = rect.bottom - rect.top;

            int top = drHeight / 2 - fontHeight / 4;
            int bottom = drHeight / 2 + fontHeight / 4;
            fm.ascent = -bottom;
            fm.top = -bottom;
            fm.bottom = top;
            fm.descent = top;
        }
        return rect.right;
    }
}

