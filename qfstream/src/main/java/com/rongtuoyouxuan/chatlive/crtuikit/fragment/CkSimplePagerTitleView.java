package com.rongtuoyouxuan.chatlive.crtuikit.fragment;


import android.content.Context;
import android.graphics.Typeface;

import com.rongtuoyouxuan.chatlive.crtmagicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import com.rongtuoyouxuan.chatlive.crtmagicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

public class CkSimplePagerTitleView extends ColorTransitionPagerTitleView implements IPagerTitleView {
    private float mMinScale = 0.8f;

    public CkSimplePagerTitleView(Context context) {
        super(context);
    }

    //设置选中后字体
    @Override
    public void onSelected(int index, int totalCount) {
        super.onSelected(index, totalCount);
        setTypeface(Typeface.DEFAULT_BOLD);
    }

    //设选未选中字体大小
    @Override
    public void onDeselected(int index, int totalCount) {
        super.onDeselected(index, totalCount);
        setTypeface(Typeface.DEFAULT_BOLD);
    }

    @Override
    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
        super.onEnter(index, totalCount, enterPercent, leftToRight);    // 实现颜色渐变
//        setScaleX(mMinScale + (1.0f - mMinScale) * enterPercent);
//        setScaleY(mMinScale + (1.0f - mMinScale) * enterPercent);
    }

    @Override
    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
        super.onLeave(index, totalCount, leavePercent, leftToRight);    // 实现颜色渐变
//        setScaleX(1.0f + (mMinScale - 1.0f) * leavePercent);
//        setScaleY(1.0f + (mMinScale - 1.0f) * leavePercent);
    }

    public float getMinScale() {
        return mMinScale;
    }

    public void setMinScale(float minScale) {
        mMinScale = minScale;
    }
}