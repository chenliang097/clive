package com.rongtuoyouxuan.chatlive.util;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;


public class SpannableBuilder {
    private Context context;
    private List<SpanWrapper> list;

    private SpannableBuilder(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
    }

    public static SpannableBuilder create(Context context) {
        return new SpannableBuilder(context);
    }

    public SpannableBuilder append(CharSequence text, int textSize, int textColor, boolean isBold) {
        list.add(new SpanWrapper(text, textSize, textColor, isBold));
        return this;
    }

    public SpannableBuilder append(CharSequence text, int textSize, int textColor, boolean isBold, SpanClick spanClick) {
        list.add(new SpanWrapper(text, textSize, textColor, isBold, spanClick));
        return this;
    }

    public Spannable build() {
        SpannableStringBuilder textSpan = new SpannableStringBuilder();

        int start = 0;
        int end = 0;

        for (int i = 0; i < list.size(); i++) {
            SpanWrapper wrapper = list.get(i);
            CharSequence text = wrapper.getText();
            start = end;
            end = end + text.length();
            textSpan.append(text);

            AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan(getContext().getResources().getDimensionPixelSize(wrapper.getTextSize()));
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(getContext().getResources().getColor(wrapper.getTextColor()));
            if (wrapper.getSpanClick() != null) {
                textSpan.setSpan(new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        wrapper.getSpanClick().onClick(this);
                    }

                    //去除连接下划线
                    @Override
                    public void updateDrawState(TextPaint ds) {
                        /**set textColor**/
                        ds.setColor(ds.linkColor);
                        /**Remove the underline**/
                        ds.setUnderlineText(false);
                        ds.setAntiAlias(true);
                    }
                }, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            StyleSpan styleSpan = new StyleSpan(wrapper.isBold() ? Typeface.BOLD : Typeface.NORMAL);
            textSpan.setSpan(styleSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            textSpan.setSpan(sizeSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            textSpan.setSpan(colorSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        }
        return textSpan;
    }

    public Context getContext() {
        return context;
    }

    public interface SpanClick {
        void onClick(ClickableSpan clickableSpan);
    }

    private class SpanWrapper {
        private CharSequence text;
        private int textSize;
        private int textColor;
        private boolean isBold;
        private SpanClick spanClick;

        public SpanWrapper(CharSequence text, int textSize, int textColor, boolean isBold) {
            this.text = text;
            this.textSize = textSize;
            this.textColor = textColor;
            this.isBold = isBold;
        }

        public SpanWrapper(CharSequence text, int textSize, int textColor, boolean isBold, SpanClick spanClick) {
            this.text = text;
            this.textSize = textSize;
            this.textColor = textColor;
            this.isBold = isBold;
            this.spanClick = spanClick;
        }

        public CharSequence getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public int getTextSize() {
            return textSize;
        }

        public void setTextSize(int textSize) {
            this.textSize = textSize;
        }

        public int getTextColor() {
            return textColor;
        }

        public void setTextColor(int textColor) {
            this.textColor = textColor;
        }

        public boolean isBold() {
            return isBold;
        }

        public void setBold(boolean bold) {
            isBold = bold;
        }

        public SpanClick getSpanClick() {
            return spanClick;
        }

        public void setSpanClick(SpanClick spanClick) {
            this.spanClick = spanClick;
        }
    }

}
