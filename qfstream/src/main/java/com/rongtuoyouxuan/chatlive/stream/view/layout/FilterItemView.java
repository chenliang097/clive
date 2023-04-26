package com.rongtuoyouxuan.chatlive.stream.view.layout;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rongtuoyouxuan.chatlive.stream.R;
import com.makeramen.roundedimageview.RoundedImageView;

/**
 * Created by lirui on 2017/1/20.
 */

public class FilterItemView extends LinearLayout {
    private RoundedImageView mItemIcon;
    private TextView mItemText;

    public FilterItemView(Context context) {
        this(context, null);
    }

    public FilterItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FilterItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setLayoutParams(params);
        View viewRoot = LayoutInflater.from(context).inflate(R.layout.qf_stream_layout_filter_item, this, true);
        mItemIcon = viewRoot.findViewById(R.id.item_icon);
        mItemText = (TextView) viewRoot.findViewById(R.id.item_text);
        mItemText.setVisibility(VISIBLE);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setUnselectedBackground() {
        mItemIcon.setBackgroundColor(Color.parseColor("#00000000"));
        mItemIcon.setBorderColor(Color.parseColor("#00000000"));
        mItemText.setTextColor(Color.parseColor("#becad5"));
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setSelectedBackground() {
        mItemIcon.setBorderColor(Color.parseColor("#f4e226"));
        mItemText.setTextColor(Color.parseColor("#f4e226"));
    }

    public void setItemIcon(int resourceId) {
        mItemIcon.setImageDrawable(getResources().getDrawable(resourceId));
    }

    public void setItemText(String text) {
        mItemText.setText(text);
    }

    public void setItemText(int textId) {
        mItemText.setText(textId);
    }
}