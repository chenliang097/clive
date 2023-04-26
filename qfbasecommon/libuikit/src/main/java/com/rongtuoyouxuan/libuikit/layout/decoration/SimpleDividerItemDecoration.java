package com.rongtuoyouxuan.libuikit.layout.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import androidx.annotation.DrawableRes;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.rongtuoyouxuan.libuikit.R;


/**
 * Created by duanjunjie on 2018/3/21.
 * RecyclerView的第一个条目和最后一个条目不绘制分割线
 */

public class SimpleDividerItemDecoration extends RecyclerView.ItemDecoration {
    private Drawable mDivider;
    private int mPaddingLeft = 0;
    private int mPaddingRight = 0;
    private boolean isFirstVisible;
    private boolean isFirstTopVisbile;
    private boolean isLastVisible;
    private boolean hasfooter;

    public SimpleDividerItemDecoration(Context context) {
        mDivider = context.getResources().getDrawable(R.drawable.line_divider);
    }

    public SimpleDividerItemDecoration(Context context, int paddingLeft, int paddingRight, boolean hasfooter,boolean isFirstVisible, boolean isLastVisible) {
        mDivider = context.getResources().getDrawable(R.drawable.line_divider);
        mPaddingLeft = paddingLeft;
        mPaddingRight = paddingRight;
        this.isFirstVisible = isFirstVisible;
        this.isLastVisible = isLastVisible;
        this.hasfooter = hasfooter;
    }

    public SimpleDividerItemDecoration(Context context, @DrawableRes int resID, int paddingLeft, int paddingRight, boolean isFirstVisible, boolean isLastVisible) {
        mDivider = context.getResources().getDrawable(resID);
        mPaddingLeft = paddingLeft;
        mPaddingRight = paddingRight;
        this.isFirstVisible = isFirstVisible;
        this.isLastVisible = isLastVisible;
    }

    /**
     * @param context
     * @param resID
     * @param paddingLeft
     * @param paddingRight
     * @param isFirstTopVisbile   第一个条目Top 是不是需要分割线
     * @param isFirstVisible      第一个条目bottom 是不是需要分割线
     * @param isLastVisible        最后一个条目bottom是不是需要分割线
     */
    public SimpleDividerItemDecoration(Context context, @DrawableRes int resID, int paddingLeft, int paddingRight, boolean isFirstTopVisbile, boolean isFirstVisible, boolean isLastVisible) {
        mDivider = context.getResources().getDrawable(resID);
        mPaddingLeft = paddingLeft;
        mPaddingRight = paddingRight;
        this.isFirstVisible = isFirstVisible;
        this.isLastVisible = isLastVisible;
        this.isFirstTopVisbile = isFirstTopVisbile;
    }

    public SimpleDividerItemDecoration(Context context, int resID) {
        mDivider = context.getResources().getDrawable(resID);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft() + mPaddingLeft;
        int right = parent.getWidth() - parent.getPaddingRight() - mPaddingRight;

        int childCount = parent.getChildCount();
        int start = 0;
        int end = childCount;
        if (!isFirstVisible) {
            start = 1;
        }
        if (!isLastVisible) {
            if(hasfooter){
                end = childCount - 2;
            }else{
                end = childCount - 1;
            }
        }
        for (int i = start; i < end; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            if (i == 0 && isFirstTopVisbile) {
                int top = child.getTop();
                int bottom = child.getTop() + mDivider.getIntrinsicHeight();
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + mDivider.getIntrinsicHeight();

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }
}
