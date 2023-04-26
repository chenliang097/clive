package com.rongtuoyouxuan.libuikit.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.appcompat.widget.AppCompatImageView;

import com.rongtuoyouxuan.libuikit.R;

/**
 * @Description :圆形ImageView
 * @Author : jianbo
 * @Date : 2022/8/31  16:50
 */
public class CircleImageView extends AppCompatImageView {

    private static final ScaleType SCALE_TYPE = ScaleType.CENTER_CROP;

    private static final Bitmap.Config BITMAP_CONFIG = Bitmap.Config.ARGB_8888;
    private static final int COLORDRAWABLE_DIMENSION = 2;

    private static final int DEFAULT_BORDER_WIDTH = 0;
    private static final int DEFAULT_BORDER_COLOR = Color.BLACK;
    private static final int DEFAULT_FILL_COLOR = Color.TRANSPARENT;
    private static final boolean DEFAULT_BORDER_OVERLAY = false;

    //内部图片所占矩形区域
    private final RectF mDrawableRect = new RectF();
    //外部圆环所占矩形区域
    private final RectF mBorderRect = new RectF();

    private final Matrix mShaderMatrix = new Matrix();
    //画内部圆形图片用到的Paint
    private final Paint mBitmapPaint = new Paint();
    //画外部圆环用到的paint
    private final Paint mBorderPaint = new Paint();
    //画图片背景用到的paint
    private final Paint mFillPaint = new Paint();

    //外边框圆环颜色
    private int mBorderColor = DEFAULT_BORDER_COLOR;
    //外边框圆环宽度
    private int mBorderWidth = DEFAULT_BORDER_WIDTH;
    //图片背景颜色
    private int mFillColor = DEFAULT_FILL_COLOR;

    private Bitmap mBitmap;
    private BitmapShader mBitmapShader;
    private int mBitmapWidth;
    private int mBitmapHeight;

    //内部圆形图片半径
    private float mDrawableRadius;
    //外部圆环半径
    private float mBorderRadius;

    private ColorFilter mColorFilter;

    private boolean mReady;
    private boolean mSetupPending;
    //外边圆环是否压住圆形图片
    private boolean mBorderOverlay;
    private boolean mDisableCircularTransformation;

    public CircleImageView(Context context) {
        super(context);

        init();
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView, defStyle, 0);

        mBorderWidth = a.getDimensionPixelSize(R.styleable.CircleImageView_civ_border_width, DEFAULT_BORDER_WIDTH);
        mBorderColor = a.getColor(R.styleable.CircleImageView_civ_border_color, DEFAULT_BORDER_COLOR);
        mBorderOverlay = a.getBoolean(R.styleable.CircleImageView_civ_border_overlay, DEFAULT_BORDER_OVERLAY);
        mFillColor = a.getColor(R.styleable.CircleImageView_civ_fill_color, DEFAULT_FILL_COLOR);

        a.recycle();
        init();
    }

    private void init() {
        super.setScaleType(SCALE_TYPE);
        mReady = true;
        if (mSetupPending) {
            setup();
            mSetupPending = false;
        }
    }

    /**
     * 外部类获取CircleImageView的ScaleType属性。
     *
     * @return
     */
    @Override
    public ScaleType getScaleType() {
        return SCALE_TYPE;
    }

    /**
     * 重写父类方法，给CircleImageView设置ScaleType属性，这里需要注意如果设置的scaleType不是ScaleType.CENTER_CROP，抛出异常，只支持ScaleType.CENTER_CROP；
     *
     * @param scaleType
     */
    @Override
    public void setScaleType(ScaleType scaleType) {
        if (scaleType != SCALE_TYPE) {
            throw new IllegalArgumentException(String.format("ScaleType %s not supported.", scaleType));
        }
    }

    /**
     * 重写父类方法，adjustViewBounds属性为是否保持宽高比。需要与maxWidth、MaxHeight一起使用，否则单独使用没有效果。当前控件不支持设置保持宽高比；起到禁止设置的作用。
     *
     * @param adjustViewBounds
     */
    @Override
    public void setAdjustViewBounds(boolean adjustViewBounds) {
        if (adjustViewBounds) {
            throw new IllegalArgumentException("adjustViewBounds not supported.");
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 是否允许转换成圆形设置
        if (mDisableCircularTransformation) {
            super.onDraw(canvas);
            return;
        }

        if (mBitmap == null) {
            return;
        }

        //如果设置了图片底色，绘制图片底色。
        if (mFillColor != Color.TRANSPARENT) {
            canvas.drawCircle(mDrawableRect.centerX(), mDrawableRect.centerY(), mDrawableRadius, mFillPaint);
        }
        //画内部图片区域（我们给mBitmapPaint设置了Shader，给Shader设置了LocalMatrix，通过ShaderMatrix设置了缩放比，及平移操作完成功能）；
        canvas.drawCircle(mDrawableRect.centerX(), mDrawableRect.centerY(), mDrawableRadius, mBitmapPaint);
        //如果设置了BorderWidth宽度，绘制；
        if (mBorderWidth > 0) {
            canvas.drawCircle(mBorderRect.centerX(), mBorderRect.centerY(), mBorderRadius, mBorderPaint);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        setup();
    }

    /**
     * 设置padding属性，该控件兼容设置padding属性。
     *
     * @param start
     * @param top
     * @param end
     * @param bottom
     */
    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        super.setPadding(left, top, right, bottom);
        setup();
    }

    /**
     * setPadding的话不管方向如何都按照左上右下的顺序来配置Padding，setPaddingRelative的话则会按照配置的LayoutDirection来进行设置，从左到右的话为左上右下，从右到左的话为右上左下的顺序（Android4.0以后添加）。
     *
     * @param start
     * @param top
     * @param end
     * @param bottom
     */
    @Override
    public void setPaddingRelative(int start, int top, int end, int bottom) {
        super.setPaddingRelative(start, top, end, bottom);
        setup();
    }

    /**
     * 获取外边框圆环颜色。
     *
     * @return
     */
    public int getBorderColor() {
        return mBorderColor;
    }

    /**
     * 设置外边框圆环颜色。
     *
     * @param borderColor
     */
    public void setBorderColor(@ColorInt int borderColor) {
        if (borderColor == mBorderColor) {
            return;
        }

        mBorderColor = borderColor;
        mBorderPaint.setColor(mBorderColor);
        invalidate();
    }

    /**
     * @deprecated Use {@link #setBorderColor(int)} instead
     */
    @Deprecated
    public void setBorderColorResource(@ColorRes int borderColorRes) {
        setBorderColor(getContext().getResources().getColor(borderColorRes));
    }

    /**
     * Return the color drawn behind the circle-shaped drawable.
     *
     * @return The color drawn behind the drawable
     * @deprecated Fill color support is going to be removed in the future
     */
    @Deprecated
    public int getFillColor() {
        return mFillColor;
    }

    /**
     * 设置图片背景颜色
     * Set a color to be drawn behind the circle-shaped drawable. Note that
     * this has no effect if the drawable is opaque or no drawable is set.
     *
     * @param fillColor The color to be drawn behind the drawable
     * @deprecated Fill color support is going to be removed in the future
     */
    @Deprecated
    public void setFillColor(@ColorInt int fillColor) {
        if (fillColor == mFillColor) {
            return;
        }

        mFillColor = fillColor;
        mFillPaint.setColor(fillColor);
        invalidate();
    }

    /**
     * Set a color to be drawn behind the circle-shaped drawable. Note that
     * this has no effect if the drawable is opaque or no drawable is set.
     *
     * @param fillColorRes The color resource to be resolved to a color and
     *                     drawn behind the drawable
     * @deprecated Fill color support is going to be removed in the future
     */
    @Deprecated
    public void setFillColorResource(@ColorRes int fillColorRes) {
        setFillColor(getContext().getResources().getColor(fillColorRes));
    }

    /**
     * 获取外边框宽度。
     *
     * @return
     */
    public int getBorderWidth() {
        return mBorderWidth;
    }

    /**
     * 设置外边框宽度。
     *
     * @param borderWidth
     */
    public void setBorderWidth(int borderWidth) {
        if (borderWidth == mBorderWidth) {
            return;
        }

        mBorderWidth = borderWidth;
        setup();
    }

    /**
     * 外边圆环是否压住圆形图片。
     *
     * @return
     */
    public boolean isBorderOverlay() {
        return mBorderOverlay;
    }

    /**
     * 设置外边圆环是否压住内部圆形图片。
     *
     * @param borderOverlay
     */
    public void setBorderOverlay(boolean borderOverlay) {
        if (borderOverlay == mBorderOverlay) {
            return;
        }

        mBorderOverlay = borderOverlay;
        setup();
    }

    /**
     * 是否禁用图片圆形属性。如果为true，则就是普通方形图片。
     *
     * @return
     */
    public boolean isDisableCircularTransformation() {
        return mDisableCircularTransformation;
    }

    /**
     * 设置是否禁用图片圆形属性。
     *
     * @param disableCircularTransformation
     */
    public void setDisableCircularTransformation(boolean disableCircularTransformation) {
        if (mDisableCircularTransformation == disableCircularTransformation) {
            return;
        }

        mDisableCircularTransformation = disableCircularTransformation;
        initializeBitmap();
    }

    /**
     * 1.重写父类设置图片方法。
     * 如果我们在XML中设置了android:src属性，会执行我们的第一个方法（setImageBitmap）该方法会先于构造函数调用之前调用。
     *
     * @param bm
     */
    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        initializeBitmap();
    }

    /**
     * 2.重写父类设置图片方法。
     *
     * @param drawable
     */
    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        initializeBitmap();
    }

    /**
     * 3.重写父类设置图片方法。
     *
     * @param resId
     */
    @Override
    public void setImageResource(@DrawableRes int resId) {
        super.setImageResource(resId);
        initializeBitmap();
    }

    /**
     * 4.重写父类设置图片方法。
     *
     * @param uri
     */
    @Override
    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        initializeBitmap();
    }

    /**
     * 设置ColorFilter，查看ColorFilter文档你会发现，
     * ColorFilter有三个子类：ColorMatrixColorFilter：颜色矩阵过滤器；
     * LightingColorFilter：“光照色彩过滤器”，模拟一个光照照过图像所产生的效果；
     * PorterDuffColorFilter：PorterDuff混合模式的色彩过滤器。
     * ————————————————
     * 版权声明：本文为CSDN博主「刘永雷」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
     * 原文链接：https://blog.csdn.net/liuyonglei1314/article/details/55102720
     *
     * @param cf
     */
    @Override
    public void setColorFilter(ColorFilter cf) {
        if (cf == mColorFilter) {
            return;
        }

        mColorFilter = cf;
        applyColorFilter();
        invalidate();
    }

    /**
     * 获取着色器。
     *
     * @return
     */
    @Override
    public ColorFilter getColorFilter() {
        return mColorFilter;
    }

    private void applyColorFilter() {
        if (mBitmapPaint != null) {
            mBitmapPaint.setColorFilter(mColorFilter);
        }
    }

    private Bitmap getBitmapFromDrawable(Drawable drawable) {
        if (drawable == null) {
            return null;
        }

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        try {
            Bitmap bitmap;

            if (drawable instanceof ColorDrawable) {
                bitmap = Bitmap.createBitmap(COLORDRAWABLE_DIMENSION, COLORDRAWABLE_DIMENSION, BITMAP_CONFIG);
            } else {
                bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), BITMAP_CONFIG);
            }

            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 判断是否禁止圆形属性，禁止mBitmap为null,不禁止获取到我们设置的Drawable并通过getBitmapFromDrawable()方法转换成mBitmap，然后调用setup()方法
     */
    private void initializeBitmap() {
        if (mDisableCircularTransformation) {
            mBitmap = null;
        } else {
            mBitmap = getBitmapFromDrawable(getDrawable());
        }
        setup();
    }

    private void setup() {
        if (!mReady) {
            mSetupPending = true;
            return;
        }

        if (getWidth() == 0 && getHeight() == 0) {
            return;
        }

        if (mBitmap == null) {
            invalidate();
            return;
        }

//        TileMode：（一共有三种）
//        CLAMP  ：如果渲染器超出原始边界范围，会复制范围内边缘染色。
//        REPEAT ：横向和纵向的重复渲染器图片，平铺。
//        MIRROR ：横向和纵向的重复渲染器图片，这个和REPEAT重复方式不一样，他是以镜像方式平铺。
        mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        //抗锯齿
        mBitmapPaint.setAntiAlias(true);
        mBitmapPaint.setShader(mBitmapShader);

        //Paint.Style.FILL：填充内部
        //Paint.Style.FILL_AND_STROKE  ：填充内部和描边
        //Paint.Style.STROKE  ：描边
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setColor(mBorderColor);
        mBorderPaint.setStrokeWidth(mBorderWidth);

        mFillPaint.setStyle(Paint.Style.FILL);
        mFillPaint.setAntiAlias(true);
        mFillPaint.setColor(mFillColor);

        //取的原图片的宽高
        mBitmapHeight = mBitmap.getHeight();
        mBitmapWidth = mBitmap.getWidth();

        mBorderRect.set(calculateBounds());
        //计算整个圆形带Border部分的最小半径，取mBorderRect的宽高减去一个边缘大小的一半的较小值
        mBorderRadius = Math.min((mBorderRect.height() - mBorderWidth) / 2.0f, (mBorderRect.width() - mBorderWidth) / 2.0f);

        //初始图片显示区域为mBorderRect（CircleImageView中图片区域的实际大小）
        mDrawableRect.set(mBorderRect);
        if (!mBorderOverlay && mBorderWidth > 0) {
            //到现在图片区域Rect（mDrawableRect）与整个View所用Rect（mBorderRadius）相同【mDrawableRect.set(mBorderRect)设置】，
            //如果在xml中设置app:civ_border_overlay="false"（边框不覆盖图片）并且外框宽度大于0，将图片显示区域Rect向内（缩小）mBorderWidth-1.0f。
            //inset（）方法参数为正数表示缩小，为复数表示扩大区域
            mDrawableRect.inset(mBorderWidth - 1.0f, mBorderWidth - 1.0f);
        }
        //计算内圆最小半径，即去除边框后的Rect（内部图片Rect->mDrawableRect）宽度的半径
        mDrawableRadius = Math.min(mDrawableRect.height() / 2.0f, mDrawableRect.width() / 2.0f);

        //设置颜色过滤器。
        applyColorFilter();
        //设置BitmapShader的Matrix,设置缩放比，平移。
        updateShaderMatrix();
        invalidate();
    }

    private RectF calculateBounds() {

        //获取当前CircleImageView视图除去PaddingLeft与PaddingRight后剩余的可用宽度
        // （如果你设置的PaddingLeft+PaddingRight>+当前控件的宽度，当前控件会显示不出来）；
        int availableWidth = getWidth() - getPaddingLeft() - getPaddingRight();
        //获取当前CircleImageView视图除去PaddingTop与PaddingBottom后剩余的可用高度；
        int availableHeight = getHeight() - getPaddingTop() - getPaddingBottom();

        //获取除去Padding后宽高剩余可用空间较小的一个值。
        int sideLength = Math.min(availableWidth, availableHeight);

        //如果最后得到的availableWidth与availableHeight不一样（我们在代码中设置的原因），大的要向小的靠齐，
        //最终得到的RectF为正方形。
        float left = getPaddingLeft() + (availableWidth - sideLength) / 2f;
        float top = getPaddingTop() + (availableHeight - sideLength) / 2f;

        return new RectF(left, top, left + sideLength, top + sideLength);
    }

    private void updateShaderMatrix() {
        float scale;
        float dx = 0;
        float dy = 0;

        mShaderMatrix.set(null);

        //比较图片和所绘区域宽缩放比、高缩放比，那个小。取小的，作为矩阵的缩放比。
        //代码不太好理解，等价于(mBitmapWidth / mDrawableRect.width()) > (mBitmapHeight / mDrawableRect.height())
        if (mBitmapWidth * mDrawableRect.height() > mDrawableRect.width() * mBitmapHeight) {
            scale = mDrawableRect.height() / (float) mBitmapHeight;
            dx = (mDrawableRect.width() - mBitmapWidth * scale) * 0.5f;
        } else {
            scale = mDrawableRect.width() / (float) mBitmapWidth;
            dy = (mDrawableRect.height() - mBitmapHeight * scale) * 0.5f;
        }

        //设置缩放比
        mShaderMatrix.setScale(scale, scale);
        //平移操作，（dx + 0.5f）的处理，是四舍五入
        mShaderMatrix.postTranslate((int) (dx + 0.5f) + mDrawableRect.left, (int) (dy + 0.5f) + mDrawableRect.top);

        mBitmapShader.setLocalMatrix(mShaderMatrix);
    }

}