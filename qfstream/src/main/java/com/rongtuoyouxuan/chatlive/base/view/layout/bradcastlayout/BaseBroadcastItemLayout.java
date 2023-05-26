package com.rongtuoyouxuan.chatlive.base.view.layout.bradcastlayout;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.rongtuoyouxuan.chatlive.crtuikit.widget.ClickiTextSpan;

/**
 * 描述：
 *
 * @time 2019/9/26
 */
public class BaseBroadcastItemLayout extends RelativeLayout {
    public BaseBroadcastItemLayout(Context context) {
        this(context,null);
    }

    public BaseBroadcastItemLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BaseBroadcastItemLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    public static void addTextSpan(SpannableStringBuilder builder, CharSequence text, int text_color, ClickiTextSpan.SpanClick click) {
        SpannableString userName = new SpannableString(text);
        if (click != null) {
            userName.setSpan(new ClickiTextSpan(text_color, click),
                    0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {
            userName.setSpan(new ForegroundColorSpan(text_color),
                    0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        builder.append(userName);
    }

}
