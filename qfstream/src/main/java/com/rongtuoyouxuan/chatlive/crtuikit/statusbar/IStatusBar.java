package com.rongtuoyouxuan.chatlive.crtuikit.statusbar;

import android.view.Window;

/**
 * 状态栏接口
 */

interface IStatusBar {

    void setStatusBarColor(Window window, int color);

    void resetStatusBarColor(Window window, int color);
}
