package com.rongtuoyouxuan.qfcommon.util;

import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.KeyboardUtils;

public class EdittextFocusUtil {
    private static EdittextFocusUtil mEdittextFocusUtil;

    private EdittextFocusUtil() {

    }

    public static EdittextFocusUtil newInstance() {
        if (mEdittextFocusUtil == null) {
            synchronized (EdittextFocusUtil.class) {
                mEdittextFocusUtil = new EdittextFocusUtil();
            }
        }
        return mEdittextFocusUtil;
    }

    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }

    public void setOutSideHideInput(View v, MotionEvent ev) {
        if (isShouldHideInput(v, ev)) {
            KeyboardUtils.hideSoftInput(ActivityUtils.getTopActivity());
        }
    }
}
