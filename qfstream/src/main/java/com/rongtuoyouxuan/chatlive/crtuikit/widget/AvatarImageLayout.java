package com.rongtuoyouxuan.chatlive.crtuikit.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.rongtuoyouxuan.chatlive.stream.R;


/**
 * @Description : IM中圆形头像  带底部文字标识或者底部icon
 * @Author : jianbo
 * @Date : 2022/8/31  16:46
 */
public class AvatarImageLayout extends FrameLayout {

    private static final int DEFAULT_IMG_BOTTOM_TEXT_SIZE = 9;
    private static final int DEFAULT_IMG_BOTTOM_TEXT_COLOR = Color.parseColor("#FF8021");
    private static final int DEFAULT_IMG_BOTTOM_TEXT_BACKGROUND = R.drawable.chat_bg_group_owner;

    //底部icon的Drawable
    private static final int DEFAULT_IMG_BOTTOM_IMG_DRAWABLE = R.mipmap.chat_ic_onlive;

    private CircleImageView circleImageView;
    private ImageView bottomImageView;
    private TextView textView;

//    //ImageView的src
//    private Drawable mDrawable;
//    //是否显示底部文字
//    private boolean isShowBottomText;
//    //底部文字内容
//    private String content;
//    //底部文字颜色
//    private int textColor;
//    //底部文字大小
//    private float textSize;
//    //底部文字背景
//    private Drawable textBackground;
//
//    //是否显示底部icon
//    private boolean isShowBottomImg;
//    //底部icon Drawable
//    private Drawable bottomDrawable;

    public AvatarImageLayout(@NonNull Context context) {
        super(context);
    }

    public AvatarImageLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AvatarImageLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.view_layout_avatar_image, this, true);
        circleImageView = view.findViewById(R.id.imageView);
        bottomImageView = view.findViewById(R.id.bottomImageView);
        textView = view.findViewById(R.id.textView);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AvatarImageLayout, defStyleAttr, 0);
//        mDrawable = typedArray.getDrawable(R.styleable.AvatarImageLayout_avatar_src);
//        content = typedArray.getString(R.styleable.AvatarImageLayout_avatar_bottom_text_content);
        //底部文字大小
        float textSize = typedArray.getDimension(R.styleable.AvatarImageLayout_avatar_bottom_text_size, DEFAULT_IMG_BOTTOM_TEXT_SIZE);
        //底部文字颜色
        int textColor = typedArray.getColor(R.styleable.AvatarImageLayout_avatar_bottom_text_color, DEFAULT_IMG_BOTTOM_TEXT_COLOR);
        //底部文字背景
        Drawable textBackground = typedArray.getDrawable(R.styleable.AvatarImageLayout_avatar_bottom_text_background);
//        bottomDrawable = typedArray.getDrawable(R.styleable.AvatarImageLayout_avatar_bottom_img_src);
//        isShowBottomText = typedArray.getBoolean(R.styleable.AvatarImageLayout_avatar_show_bottom_text, false);
//        isShowBottomImg = typedArray.getBoolean(R.styleable.AvatarImageLayout_avatar_show_bottom_img, false);
        typedArray.recycle();

        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, Math.round(textSize));
        textView.setTextColor(textColor);
        textView.setBackground(textBackground);

    }

    public void setDrawable(Drawable drawable) {
        circleImageView.setImageDrawable(drawable);
    }

    public void setBitmap(Bitmap bitmap) {
        circleImageView.setImageBitmap(bitmap);
    }

    public void setShowBottomText(boolean showBottomText, String content, int textColor, float textSize, Drawable textBackground) {
        if (showBottomText) {
            textView.setVisibility(VISIBLE);
            textView.setText(content);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, Math.round(textSize));
            textView.setTextColor(textColor);
            textView.setBackground(textBackground);
        } else {
            textView.setVisibility(GONE);
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void setShowBottomText(boolean showBottomText, String content) {
        setShowBottomText(showBottomText, content, DEFAULT_IMG_BOTTOM_TEXT_COLOR, DEFAULT_IMG_BOTTOM_TEXT_SIZE, getResources().getDrawable(DEFAULT_IMG_BOTTOM_TEXT_BACKGROUND));
    }

    public void setBottomDrawable(boolean showBottomImg, Drawable bottomDrawable) {
        if (showBottomImg) {
            bottomImageView.setVisibility(VISIBLE);
            bottomImageView.setImageDrawable(bottomDrawable);
        } else {
            bottomImageView.setVisibility(GONE);
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void setBottomDrawable(boolean showBottomImg) {
        setBottomDrawable(showBottomImg, getResources().getDrawable(DEFAULT_IMG_BOTTOM_IMG_DRAWABLE));
    }
}
