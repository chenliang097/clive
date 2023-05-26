package com.rongtuoyouxuan.chatlive.crtutil.util;

import static android.graphics.drawable.GradientDrawable.LINEAR_GRADIENT;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.Dimension;
import androidx.annotation.DrawableRes;
import androidx.annotation.IntDef;
import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.ColorUtils;

/**
 * 使用参考网址   https://www.jianshu.com/p/7fb9567a5869
 */
public class ShapeFactory {

    public static ShapeSelector newShapeSelector() {
        return new ShapeSelector();
    }

    public static final class ShapeSelector {

        @IntDef({GradientDrawable.RECTANGLE, GradientDrawable.OVAL,
                GradientDrawable.LINE, GradientDrawable.RING})
        private @interface Shape {
        }

        private int mShape;               //the shape of background
        private int mDefaultBgColor;      //default background color
        private int mDisabledBgColor;     //state_enabled = false
        private int mPressedBgColor;      //state_pressed = true
        private int mSelectedBgColor;     //state_selected = true
        private int mFocusedBgColor;      //state_focused = true
        private int mStrokeWidth;         //stroke width in pixel
        private int mDefaultStrokeColor;  //default stroke color
        private int mDisabledStrokeColor; //state_enabled = false
        private int mPressedStrokeColor;  //state_pressed = true
        private int mSelectedStrokeColor; //state_selected = true
        private int mFocusedStrokeColor;  //state_focused = true
        private int mCornerRadius;        //corner radius

        private boolean hasSetDisabledBgColor = false;
        private boolean hasSetPressedBgColor = false;
        private boolean hasSetSelectedBgColor = false;
        private boolean hasSetFocusedBgColor = false;

        private boolean hasSetDisabledStrokeColor = false;
        private boolean hasSetPressedStrokeColor = false;
        private boolean hasSetSelectedStrokeColor = false;
        private boolean hasSetFocusedStrokeColor = false;

        private ShapeSelector() {
            //initialize default values
            mShape = GradientDrawable.RECTANGLE;
            mDefaultBgColor = Color.TRANSPARENT;
            mDisabledBgColor = Color.TRANSPARENT;
            mPressedBgColor = Color.TRANSPARENT;
            mSelectedBgColor = Color.TRANSPARENT;
            mFocusedBgColor = Color.TRANSPARENT;
            mStrokeWidth = 0;
            mDefaultStrokeColor = Color.TRANSPARENT;
            mDisabledStrokeColor = Color.TRANSPARENT;
            mPressedStrokeColor = Color.TRANSPARENT;
            mSelectedStrokeColor = Color.TRANSPARENT;
            mFocusedStrokeColor = Color.TRANSPARENT;
            mCornerRadius = 0;
        }

        public ShapeSelector setShape(@Shape int shape) {
            mShape = shape;
            return this;
        }

        public ShapeSelector setDefaultBgColor(@ColorInt int color) {
            mDefaultBgColor = color;
            if (!hasSetDisabledBgColor)
                mDisabledBgColor = color;
            if (!hasSetPressedBgColor)
                mPressedBgColor = color;
            if (!hasSetSelectedBgColor)
                mSelectedBgColor = color;
            if (!hasSetFocusedBgColor)
                mFocusedBgColor = color;
            return this;
        }

        public ShapeSelector setDisabledBgColor(@ColorInt int color) {
            mDisabledBgColor = color;
            hasSetDisabledBgColor = true;
            return this;
        }

        public ShapeSelector setPressedBgColor(@ColorInt int color) {
            mPressedBgColor = color;
            hasSetPressedBgColor = true;
            return this;
        }

        public ShapeSelector setSelectedBgColor(@ColorInt int color) {
            mSelectedBgColor = color;
            hasSetSelectedBgColor = true;
            return this;
        }

        public ShapeSelector setFocusedBgColor(@ColorInt int color) {
            mFocusedBgColor = color;
            hasSetPressedBgColor = true;
            return this;
        }

        public ShapeSelector setStrokeWidth(@Dimension int width) {
            mStrokeWidth = width;
            return this;
        }

        public ShapeSelector setDefaultStrokeColor(@ColorInt int color) {
            mDefaultStrokeColor = color;
            if (!hasSetDisabledStrokeColor)
                mDisabledStrokeColor = color;
            if (!hasSetPressedStrokeColor)
                mPressedStrokeColor = color;
            if (!hasSetSelectedStrokeColor)
                mSelectedStrokeColor = color;
            if (!hasSetFocusedStrokeColor)
                mFocusedStrokeColor = color;
            return this;
        }

        public ShapeSelector setDisabledStrokeColor(@ColorInt int color) {
            mDisabledStrokeColor = color;
            hasSetDisabledStrokeColor = true;
            return this;
        }

        public ShapeSelector setPressedStrokeColor(@ColorInt int color) {
            mPressedStrokeColor = color;
            hasSetPressedStrokeColor = true;
            return this;
        }

        public ShapeSelector setSelectedStrokeColor(@ColorInt int color) {
            mSelectedStrokeColor = color;
            hasSetSelectedStrokeColor = true;
            return this;
        }

        public ShapeSelector setFocusedStrokeColor(@ColorInt int color) {
            mFocusedStrokeColor = color;
            hasSetFocusedStrokeColor = true;
            return this;
        }

        public ShapeSelector setCornerRadius(@Dimension int radius) {
            mCornerRadius = radius;
            return this;
        }

        public StateListDrawable create() {
            StateListDrawable selector = new StateListDrawable();

            //enabled = false
            if (hasSetDisabledBgColor || hasSetDisabledStrokeColor) {
                GradientDrawable disabledShape = getItemShape(mShape, mCornerRadius,
                        mDisabledBgColor, mStrokeWidth, mDisabledStrokeColor);
                selector.addState(new int[]{-android.R.attr.state_enabled}, disabledShape);
            }

            //pressed = true
            if (hasSetPressedBgColor || hasSetPressedStrokeColor) {
                GradientDrawable pressedShape = getItemShape(mShape, mCornerRadius,
                        mPressedBgColor, mStrokeWidth, mPressedStrokeColor);
                selector.addState(new int[]{android.R.attr.state_pressed}, pressedShape);
            }

            //selected = true
            if (hasSetSelectedBgColor || hasSetSelectedStrokeColor) {
                GradientDrawable selectedShape = getItemShape(mShape, mCornerRadius,
                        mSelectedBgColor, mStrokeWidth, mSelectedStrokeColor);
                selector.addState(new int[]{android.R.attr.state_selected}, selectedShape);
            }

            //focused = true
            if (hasSetFocusedBgColor || hasSetFocusedStrokeColor) {
                GradientDrawable focusedShape = getItemShape(mShape, mCornerRadius,
                        mFocusedBgColor, mStrokeWidth, mFocusedStrokeColor);
                selector.addState(new int[]{android.R.attr.state_focused}, focusedShape);
            }

            //default
            GradientDrawable defaultShape = getItemShape(mShape, mCornerRadius,
                    mDefaultBgColor, mStrokeWidth, mDefaultStrokeColor);
            selector.addState(new int[]{}, defaultShape);

            return selector;
        }

        private GradientDrawable getItemShape(int shape, int cornerRadius,
                                              int solidColor, int strokeWidth, int strokeColor) {
            GradientDrawable drawable = new GradientDrawable();
            drawable.setShape(shape);
            drawable.setStroke(strokeWidth, strokeColor);
            drawable.setCornerRadius(cornerRadius);
            drawable.setColor(solidColor);
            return drawable;
        }
    }

    public static ColorSelector newColorSelector() {
        return new ColorSelector();
    }

    public static final class ColorSelector {

        private int mDefaultColor;
        private int mDisabledColor;
        private int mPressedColor;
        private int mSelectedColor;
        private int mFocusedColor;

        private boolean hasSetDisabledColor = false;
        private boolean hasSetPressedColor = false;
        private boolean hasSetSelectedColor = false;
        private boolean hasSetFocusedColor = false;

        private ColorSelector() {
            mDefaultColor = Color.BLACK;
            mDisabledColor = Color.GRAY;
            mPressedColor = Color.BLACK;
            mSelectedColor = Color.BLACK;
            mFocusedColor = Color.BLACK;
        }

        public ColorSelector setDefaultColor(@ColorInt int color) {
            mDefaultColor = color;
            if (!hasSetDisabledColor)
                mDisabledColor = color;
            if (!hasSetPressedColor)
                mPressedColor = color;
            if (!hasSetSelectedColor)
                mSelectedColor = color;
            if (!hasSetFocusedColor)
                mFocusedColor = color;
            return this;
        }

        public ColorSelector setDisabledColor(@ColorInt int color) {
            mDisabledColor = color;
            hasSetDisabledColor = true;
            return this;
        }

        public ColorSelector setPressedColor(@ColorInt int color) {
            mPressedColor = color;
            hasSetPressedColor = true;
            return this;
        }

        public ColorSelector setSelectedColor(@ColorInt int color) {
            mSelectedColor = color;
            hasSetSelectedColor = true;
            return this;
        }

        public ColorSelector setFocusedColor(@ColorInt int color) {
            mFocusedColor = color;
            hasSetFocusedColor = true;
            return this;
        }

        public ColorStateList create() {
            int[] colors;
            if (hasSetPressedColor) {
                colors = new int[5];
                colors[0] = hasSetDisabledColor ? mDisabledColor : mDefaultColor;
                colors[1] = hasSetPressedColor ? mPressedColor : mDefaultColor;
                colors[2] = hasSetSelectedColor ? mSelectedColor : mDefaultColor;
                colors[3] = hasSetFocusedColor ? mFocusedColor : mDefaultColor;
                colors[4] = mDefaultColor;
            } else {
                colors = new int[4];
                colors[0] = hasSetDisabledColor ? mDisabledColor : mDefaultColor;
                colors[1] = hasSetSelectedColor ? mSelectedColor : mDefaultColor;
                colors[2] = hasSetFocusedColor ? mFocusedColor : mDefaultColor;
                colors[3] = mDefaultColor;
            }

            int[][] states;
            if (hasSetPressedColor) {
                states = new int[5][];
                states[0] = new int[]{-android.R.attr.state_enabled};
                states[1] = new int[]{android.R.attr.state_pressed};
                states[2] = new int[]{android.R.attr.state_selected};
                states[3] = new int[]{android.R.attr.state_focused};
                states[4] = new int[]{};
            } else {
                states = new int[4][];
                states[0] = new int[]{-android.R.attr.state_enabled};
                states[1] = new int[]{android.R.attr.state_selected};
                states[2] = new int[]{android.R.attr.state_focused};
                states[3] = new int[]{};
            }

            return new ColorStateList(states, colors);
        }
    }

    public static GeneralSelector newGeneralSelector() {
        return new GeneralSelector();
    }

    public static final class GeneralSelector {

        private Drawable mDefaultDrawable;
        private Drawable mDisabledDrawable;
        private Drawable mPressedDrawable;
        private Drawable mSelectedDrawable;
        private Drawable mFocusedDrawable;

        private boolean hasSetDisabledDrawable = false;
        private boolean hasSetPressedDrawable = false;
        private boolean hasSetSelectedDrawable = false;
        private boolean hasSetFocusedDrawable = false;

        private GeneralSelector() {
            mDefaultDrawable = new ColorDrawable(Color.TRANSPARENT);
        }

        public GeneralSelector setDefaultDrawable(Drawable drawable) {
            mDefaultDrawable = drawable;
            if (!hasSetDisabledDrawable)
                mDisabledDrawable = drawable;
            if (!hasSetPressedDrawable)
                mPressedDrawable = drawable;
            if (!hasSetSelectedDrawable)
                mSelectedDrawable = drawable;
            if (!hasSetFocusedDrawable)
                mFocusedDrawable = drawable;
            return this;
        }

        public GeneralSelector setDisabledDrawable(Drawable drawable) {
            mDisabledDrawable = drawable;
            hasSetDisabledDrawable = true;
            return this;
        }

        public GeneralSelector setPressedDrawable(Drawable drawable) {
            mPressedDrawable = drawable;
            hasSetPressedDrawable = true;
            return this;
        }

        public GeneralSelector setSelectedDrawable(Drawable drawable) {
            mSelectedDrawable = drawable;
            hasSetSelectedDrawable = true;
            return this;
        }

        public GeneralSelector setFocusedDrawable(Drawable drawable) {
            mFocusedDrawable = drawable;
            hasSetFocusedDrawable = true;
            return this;
        }

        public StateListDrawable create() {
            StateListDrawable selector = new StateListDrawable();
            if (hasSetDisabledDrawable)
                selector.addState(new int[]{-android.R.attr.state_enabled}, mDisabledDrawable);
            if (hasSetPressedDrawable)
                selector.addState(new int[]{android.R.attr.state_pressed}, mPressedDrawable);
            if (hasSetSelectedDrawable)
                selector.addState(new int[]{android.R.attr.state_selected}, mSelectedDrawable);
            if (hasSetFocusedDrawable)
                selector.addState(new int[]{android.R.attr.state_focused}, mFocusedDrawable);
            selector.addState(new int[]{}, mDefaultDrawable);
            return selector;
        }

        //overload
        public GeneralSelector setDefaultDrawable(Context context, @DrawableRes int drawableRes) {
            return setDefaultDrawable(ContextCompat.getDrawable(context, drawableRes));
        }

        //overload
        public GeneralSelector setDisabledDrawable(Context context, @DrawableRes int drawableRes) {
            return setDisabledDrawable(ContextCompat.getDrawable(context, drawableRes));
        }

        //overload
        public GeneralSelector setPressedDrawable(Context context, @DrawableRes int drawableRes) {
            return setPressedDrawable(ContextCompat.getDrawable(context, drawableRes));
        }

        //overload
        public GeneralSelector setSelectedDrawable(Context context, @DrawableRes int drawableRes) {
            return setSelectedDrawable(ContextCompat.getDrawable(context, drawableRes));
        }

        //overload
        public GeneralSelector setFocusedDrawable(Context context, @DrawableRes int drawableRes) {
            return setFocusedDrawable(ContextCompat.getDrawable(context, drawableRes));
        }
    }

    public static GradientSelector newGradientSelector() {
        return new GradientSelector();
    }

    public static final class GradientSelector {
        private int defaultBgColor;
        private int strokeColor;
        private int strokeWidth;
        private float[] radius;
        private int[] gradientColors;
        private GradientDrawable.Orientation orientation;

        public GradientSelector() {
            defaultBgColor = ColorUtils.string2Int("#00000000");
            strokeColor = ColorUtils.string2Int("#00000000");
            strokeWidth = 0;
            radius = null;
            gradientColors = null;
            orientation = GradientDrawable.Orientation.TOP_BOTTOM;
        }

        /**
         * 背景颜色
         */
        public GradientSelector setDefaultBgColor(@ColorInt int color) {
            defaultBgColor = color;
            return this;
        }

        /**
         * @param color 边框颜色
         * @return
         */
        public GradientSelector setStrokeColor(@ColorInt int color) {
            strokeColor = color;
            return this;
        }

        /**
         * @param strokeWidth 边框宽度
         * @return
         */
        public GradientSelector setStrokeWidth(int strokeWidth) {
            this.strokeWidth = strokeWidth;
            return this;
        }

        /**
         * @param radius 圆角 左上,右上,右下,左下
         * @return
         */
        public GradientSelector setRadius(float[] radius) {
            this.radius = radius;
            return this;
        }

        /**
         * @param gradientColors 渐变颜色
         * @return
         */
        public GradientSelector setGradientColors(@ColorRes int[] gradientColors) {
            this.gradientColors = gradientColors;
            return this;
        }

        /**
         * @param orientation 方向
         * @return
         */
        public GradientSelector setOrientation(GradientDrawable.Orientation orientation) {
            this.orientation = orientation;
            return this;
        }


        public GradientDrawable create() {
            try {
                GradientDrawable drawable = new GradientDrawable();

                //设置Shape类型
                drawable.setShape(GradientDrawable.RECTANGLE);

                //设置填充颜色
                drawable.setColor(defaultBgColor);

                // 边框相关
                //设置线条粗心和颜色,px
                if (strokeColor != 0 && strokeWidth > 0) {
                    drawable.setStroke(strokeWidth, strokeColor);
                }

                //圆角相关
                //每连续的两个数值表示是一个角度,四组:左上,右上,右下,左下
                if (radius != null && radius.length == 4) {
                    drawable.setCornerRadii(new float[]{radius[0], radius[0], radius[1], radius[1], radius[2], radius[2], radius[3], radius[3]});
                }

                // 渐变相关
                if (gradientColors != null && gradientColors.length > 0) {
                    drawable.setGradientType(LINEAR_GRADIENT);
                    drawable.setColors(gradientColors);
                    if (orientation != null) {
                        drawable.setOrientation(orientation);
                    } else {
                        drawable.setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);
                    }
                }

                return drawable;

            } catch (Exception e) {
                return new GradientDrawable();
            }
        }

    }

    public static OverlaySelector newOverlaySelector() {
        return new OverlaySelector();
    }

    public static final class OverlaySelector {
        private int frontColor;
        private int backColor;
        private float radiusPx;
        private int layerInsetBottom;
        private int layerInsetTop;
        private int layerInsetLeft;
        private int layerInsetRight;

        public OverlaySelector() {
            frontColor = ColorUtils.string2Int("#00000000");
            backColor = ColorUtils.string2Int("#00000000");
            radiusPx = 0;
            layerInsetBottom = 0;
            layerInsetTop = 0;
            layerInsetLeft = 0;
            layerInsetRight = 0;
        }

        /**
         * @param frontColor 前背景
         * @return
         */
        public OverlaySelector setFrontColor(@ColorRes int frontColor) {
            this.frontColor = frontColor;
            return this;
        }

        /**
         * @param backColor 后背景
         * @return
         */
        public OverlaySelector setBackColor(@ColorRes int backColor) {
            this.backColor = backColor;
            return this;
        }

        /**
         * 设置圆角
         */
        public OverlaySelector setRadius(int radiusPx) {
            this.radiusPx = radiusPx;
            return this;
        }

        /**
         * 设置下间距
         */
        public OverlaySelector setLayerInsetBottom(int layerInsetBottom) {
            this.layerInsetBottom = layerInsetBottom;
            return this;
        }

        /**
         * 设置上间距
         */
        public OverlaySelector setLayerInsetTop(int layerInsetTop) {
            this.layerInsetTop = layerInsetTop;
            return this;
        }

        /**
         * 设置左间距
         */
        public OverlaySelector setLayerInsetLeft(int layerInsetLeft) {
            this.layerInsetLeft = layerInsetLeft;
            return this;
        }

        /**
         * 设置右间距
         */
        public OverlaySelector setLayerInsetRight(int layerInsetRight) {
            this.layerInsetRight = layerInsetRight;
            return this;
        }


        @SuppressLint("NewApi")
        public Drawable create() {
            try {
                Drawable[] layers = new Drawable[2];

                GradientDrawable frontDrawable = new GradientDrawable();
                frontDrawable.setShape(GradientDrawable.RECTANGLE);
                frontDrawable.setColor(frontColor);
                //每连续的两个数值表示是一个角度,四组:左上,右上,右下,左下
                float radius = radiusPx;
                frontDrawable.setCornerRadii(new float[]{radius, radius, radius, radius, radius, radius, radius, radius});
                layers[1] = frontDrawable;

                GradientDrawable backDrawable = new GradientDrawable();
                backDrawable.setShape(GradientDrawable.RECTANGLE);
                backDrawable.setColor(backColor);
                radius = radiusPx;
                backDrawable.setCornerRadii(new float[]{radius, radius, radius, radius, radius, radius, radius, radius});
                layers[0] = backDrawable;

                LayerDrawable drawable = new LayerDrawable(layers);

                if (layerInsetBottom != 0) {
                    drawable.setLayerInsetBottom(1, layerInsetBottom);
                }
                if (layerInsetTop != 0) {
                    drawable.setLayerInsetTop(1, layerInsetTop);
                }
                if (layerInsetLeft != 0) {
                    drawable.setLayerInsetLeft(1, layerInsetLeft);
                }
                if (layerInsetRight != 0) {
                    drawable.setLayerInsetRight(1, layerInsetRight);
                }

                return drawable;

            } catch (Exception e) {
                return new GradientDrawable();
            }
        }


    }


}

