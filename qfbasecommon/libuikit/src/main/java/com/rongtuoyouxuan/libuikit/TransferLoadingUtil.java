package com.rongtuoyouxuan.libuikit;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;


public class TransferLoadingUtil {

    private static Dialog mLoadingDialog;

    public static void showDialogLoading(Context context) {
        showDialogLoading(context, "");
    }

    @Nullable
    public static Dialog showDialogLoading(Context context, String msg) {
        return showDialogLoading(context, msg, true);
    }

    @Nullable
    public static Dialog showDialogLoading(Context context, String msg, boolean isSingleton) {
        if (context == null) {
            return null;
        }
        if (!(context instanceof FragmentActivity)) {
            return null;
        }
        try {
            if (!((FragmentActivity) context).isFinishing() && !((FragmentActivity) context).isDestroyed() && mLoadingDialog == null) {
                dismissDialogLoading(context);
                LayoutInflater inflater = LayoutInflater.from(context);
                View v = inflater.inflate(R.layout.pl_libutil_dialog_loading, null);// 得到加载view
                LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
                TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
                tipTextView.setText(TextUtils.isEmpty(msg) ? context.getString(R.string.loading) : msg);// 设置加载信息

                // 创建自定义样式dialog
                Dialog mLoadingDialog = new Dialog(context, R.style.pl_libutil_loading_dialog);
                mLoadingDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//            mLoadingDialog.setCancelable(true);// 不可以用“返回键”取消
                mLoadingDialog.setCanceledOnTouchOutside(false);
                mLoadingDialog.setContentView(layout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
                mLoadingDialog.show();
                if (isSingleton) {
                    TransferLoadingUtil.mLoadingDialog = mLoadingDialog;
                }
                return mLoadingDialog;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static void dismissDialogLoading(Context context) {
        if (context == null) {
            return;
        }
        if (!(context instanceof FragmentActivity)) {
            return;
        }
        try {
            if (!((FragmentActivity) context).isFinishing() && !((FragmentActivity) context).isDestroyed() && mLoadingDialog != null && mLoadingDialog.isShowing()) {
                mLoadingDialog.dismiss();
                mLoadingDialog = null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (mLoadingDialog != null) {
                mLoadingDialog = null;
            }
        }

    }
}
