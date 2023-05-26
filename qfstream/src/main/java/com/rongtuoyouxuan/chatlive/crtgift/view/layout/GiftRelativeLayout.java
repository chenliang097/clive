package com.rongtuoyouxuan.chatlive.crtgift.view.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Describe:
 *
 * @author Ning
 * @date 2019/6/3
 */
public class GiftRelativeLayout extends RelativeLayout {

    private volatile boolean isBounce = false;//数字跳动
    private volatile boolean isShow = false;//是否显示

    public GiftRelativeLayout(Context context) {
        super(context);
    }

    public GiftRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GiftRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setBounce(boolean bounce){
        this.isBounce = bounce;
    }

    public boolean isBounce(){
        return isBounce;
    }

    public void setShowState(boolean isShow){
        this.isShow = isShow;
    }

    public boolean isShow(){
        return isShow;
    }
}
