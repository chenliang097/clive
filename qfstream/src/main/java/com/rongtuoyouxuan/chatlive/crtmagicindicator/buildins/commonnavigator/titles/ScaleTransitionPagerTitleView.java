package com.rongtuoyouxuan.chatlive.crtmagicindicator.buildins.commonnavigator.titles;

import android.content.Context;
import android.graphics.Typeface;

import com.rongtuoyouxuan.chatlive.crtmagicindicator.buildins.commonnavigator.abs.IPagerTitleView;

/**
 * Created by dell on 2018/8/20.
 */
public class ScaleTransitionPagerTitleView extends ColorTransitionPagerTitleView implements IPagerTitleView {
    private float mMinScale = 0.9f;
    public ScaleTransitionPagerTitleView(Context context) {
        super(context);
    }

    //设置选中后字体
    @Override
    public void onSelected(int index, int totalCount) {
        super.onSelected(index, totalCount);
        setTextSize(20);
        setTypeface(Typeface.DEFAULT_BOLD);
        setPadding(15,0,15,0);
    }

    //设选未选中字体大小
    @Override
    public void onDeselected(int index, int totalCount) {
        super.onDeselected(index, totalCount);
        setTextSize(18);
        setPadding(15,0,15,0);
        setTypeface(Typeface.DEFAULT);
    }

    @Override
    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
        super.onEnter(index, totalCount, enterPercent, leftToRight);    // 实现颜色渐变
        setScaleX(mMinScale + (1.0f - mMinScale) * enterPercent);
        setScaleY(mMinScale + (1.0f - mMinScale) * enterPercent);
    }

    @Override
    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
        super.onLeave(index, totalCount, leavePercent, leftToRight);    // 实现颜色渐变
        setScaleX(1.0f + (mMinScale - 1.0f) * leavePercent);
        setScaleY(1.0f + (mMinScale - 1.0f) * leavePercent);
    }

    public float getMinScale() {
        return mMinScale;
    }

    public void setMinScale(float minScale) {
        mMinScale = minScale;
    }
}
