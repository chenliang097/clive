package com.rongtuoyouxuan.chatlive.qfcommon.widget;

import android.content.Context;
import android.graphics.Paint;
import android.text.SpannableStringBuilder;
import android.widget.TextView;

import com.rongtuoyouxuan.chatlive.crtbiz2.model.im.msg.textmsg.GiftMsg;

public class GiftMsgStyle {

    private Context context;
    private TextView textView;

    public GiftMsgStyle(Context context, TextView textView) {
        this.context = context;
        this.textView = textView;
    }

    /**
     * 添加礼物缩略图
     */
    public void showGiftMsg(GiftMsg giftMsg) {
        textView.setText(getSpanString(giftMsg));
    }

    /**
     * 添加礼物缩略图
     */
    public CharSequence getSpanString(GiftMsg giftMsg) {
        SpannableStringBuilder spanString = new SpannableStringBuilder();
//        SpannableString span = new SpannableString(ConversationSummary.getSummary("", giftMsg));
//        spanString.append(span);
//        spanString.append(" $");
//        UrlDrawableLoader urlDrawableLoader = new UrlDrawableLoader(context, textView, getFontHeight(textView), giftMsg.giftImgUrl);
//        CustomImageSpan imageSpan = new CustomImageSpan(urlDrawableLoader.getDrawable());
//        int length = spanString.length();
//        spanString.setSpan(imageSpan, length - 1, length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        spanString.append(" x").append(String.valueOf(giftMsg.num));
        return spanString;
    }


    /**
     * 获取当前TextView 文字绘制高度
     */
    private int getFontHeight(TextView tv) {
        Paint paint = new Paint();
        paint.setTextSize(tv.getTextSize());
        Paint.FontMetrics fm = paint.getFontMetrics();
        return (int) (fm.bottom - fm.top);
    }
}
