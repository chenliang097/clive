package com.rongtuoyouxuan.chatlive.crtgift.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.widget.TextView;

/**
 * Describe:combo礼物数量
 *
 * @author Ning
 * @date 2019/7/24
 */
public class GiftColorTextView extends TextView {
    private LinearGradient mLinearGradient;
    private Paint mPaint;
    private int mViewWidth = 0;
    private Rect mTextBound = new Rect();


    public GiftColorTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mViewWidth = getMeasuredWidth();
        mPaint = getPaint();
        String mTipText = getText().toString();
        mPaint.getTextBounds(mTipText, 0, mTipText.length(), mTextBound);
        mLinearGradient = new LinearGradient(0, 0, mViewWidth, 0,
                new int[]{0xFFFFFFFF, 0xFFFFFFFF},
                null, Shader.TileMode.REPEAT);
        mPaint.setShader(mLinearGradient);
        canvas.drawText(mTipText, getMeasuredWidth() / 2 - mTextBound.width() / 2, getMeasuredHeight() / 2 + mTextBound.height() / 2, mPaint);
    }

    public void startAnimation(int giftNum, Animation animation) {
        setText("x"+giftNum);
        startAnimation(animation);
    }
}
