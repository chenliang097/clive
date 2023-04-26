package com.rongtuoyouxuan.libuikit.layout;

import android.view.View;

/**
 * Created by Wells on 2018/3/16.
 */

public interface IStatusView {

    void showLoading();

    void showError();

    void showError(String msg);

    void showEmpty();

    void showEmpty(String msg);

    void setOnErrorClickListener(View.OnClickListener listener);

    void showContent();

    View getEmptyView();

}
