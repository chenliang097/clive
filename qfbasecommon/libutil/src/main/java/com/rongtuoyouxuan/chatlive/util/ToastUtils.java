package com.rongtuoyouxuan.chatlive.util;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.LayoutRes;


/**
 * @Description :  废弃  请使用blackUtils中的ToastUtils
 * @Author : jianbo
 * @Date : 2022/7/29  20:14
 */
@Deprecated
public class ToastUtils {
    public static boolean isDebug = true;
    private static Toast sToast;

    /**
     * 完全自定义布局Toast
     */
    public ToastUtils() {
    }

    public static void show(Context context, String msg) {
        if (context == null) return;
        if (sToast != null) {
            sToast.cancel();
        }
        sToast = Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_SHORT);
        sToast.show();
    }

    public static void show(Context context, String msg, int xOffset, int yOffset) {
        if (context == null) return;
        if (sToast != null) {
            sToast.cancel();
        }
        sToast = Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_SHORT);
        sToast.setGravity(sToast.getGravity(),
                sToast.getXOffset() + xOffset,
                sToast.getYOffset() + yOffset);
        sToast.show();
    }

    public static void show(Context context, int resID) {
        if (context == null) return;
        if (sToast != null) {
            sToast.cancel();
        }
        sToast = Toast.makeText(context.getApplicationContext(), context.getString(resID), Toast.LENGTH_SHORT);
        sToast.show();
    }

    public static void showUseResId(Context context, int resID) {
        if (context == null) return;
        if (sToast != null) {
            sToast.cancel();
        }
        sToast = Toast.makeText(context.getApplicationContext(), resID, Toast.LENGTH_SHORT);
        sToast.show();
    }

    public static void show(Context context, String msg, int duration) {
        if (context == null) return;
        if (sToast != null) {
            sToast.cancel();
        }
        sToast = Toast.makeText(context.getApplicationContext(), msg, duration);
        sToast.show();
    }

    public static void showCenter(Context context, String msg, int duration) {
        if (context == null) return;
        if (sToast != null) {
            sToast.cancel();
        }
        sToast = Toast.makeText(context.getApplicationContext(), msg, duration);
        sToast.setGravity(Gravity.CENTER, 0, 0);
        sToast.show();
    }


    public static void showCenterWithResID(Context context, @LayoutRes int layoutResID, String message) {
        if (context == null) return;
        if (sToast != null) {
            sToast.cancel();
        }
        sToast = Toast.makeText(context.getApplicationContext(), "", Toast.LENGTH_SHORT);
        sToast.setGravity(Gravity.CENTER, 0, 0);
        View view = LayoutInflater.from(context.getApplicationContext()).inflate(layoutResID, null);
        TextView txt = view.findViewById(R.id.toastTxt);
        txt.setText(message);
        sToast.setView(view);
        sToast.show();
    }

}
