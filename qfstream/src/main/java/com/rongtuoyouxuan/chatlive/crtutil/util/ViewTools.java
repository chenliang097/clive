package com.rongtuoyouxuan.chatlive.crtutil.util;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import androidx.annotation.Size;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

/**
 * 说明：View的帮助类，赋值等功能
 * <p/>
 * 作者：zhang
 * <p/>
 * 时间：2018/10/7 11:43
 * <p/>
 * 版本：verson 1.0
 */
public final class ViewTools {

    /**
     * 说明：禁止实例化
     */
    private ViewTools(){}

    /**
     * 说明：View赋值
     *          ---TextView
     *          ---Button
     *          ---CheckBox
     *          ---EditText
     *          ---RadioButton
     * @param v
     * @param text
     */
    public static void setText(View v, CharSequence text){
        if (v == null){
            return;
        }
        if (TextUtils.isEmpty(text)){
            text = "";
        }
        if (v instanceof TextView){
            ((TextView)v).setText(text);
        }else if (v instanceof Button){
            ((Button)v).setText(text);
        }else if (v instanceof CheckBox){
            ((CheckBox)v).setText(text);
        }else if (v instanceof EditText){
            ((EditText)v).setText(text);
        }else if (v instanceof RadioButton){
            ((RadioButton)v).setText(text);
        }
    }

    /**
     * 说明：View赋值
     * @param v
     * @param resString
     */
    public static void setText(View v, int resString){
        setText(v, v.getContext().getString(resString));
    }

    /**
     * 说明：View赋值
     * @param v
     * @param format
     * @param obj
     */
    public static void setText(View v, String format, Object...obj){
        setText(v, String.format(format,obj));
    }

    /**
     * 说明：View赋值
     * @param v
     * @param resFormat
     * @param obj
     */
    public static void setText(View v, int resFormat, Object...obj){
        setText(v, String.format(v.getContext().getString(resFormat),obj));
    }

    /**
     * 说明：设置View为GONE
     * @param view
     */
    public static void GONE(View...view){
        if (view != null){
            for (View v:view){
                if (v != null){
                    if (v.getVisibility() != View.GONE){
                        v.setVisibility(View.GONE);
                    }
                }
            }
        }
    }

    /**
     * 说明：设置View为VISIBLE
     * @param view
     */
    public static void VISIBLE(@Size(min = 1) View...view){
        if (view != null){
            for (View v:view){
                if (v != null){
                    if (v.getVisibility() != View.VISIBLE){
                        v.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }

    /**
     * 说明：设置View为INVISIBLE
     * @param view
     */
    public static void INVISIBLE(View...view){
        if (view != null){
            for (View v:view){
                if (v != null){
                    if (v.getVisibility() != View.INVISIBLE){
                        v.setVisibility(View.INVISIBLE);
                    }
                }
            }
        }
    }

    /**
     * 说明：判断是否为隐藏
     * @param view
     * @return
     */
    public static boolean isHide(View view){
        return !isShow(view);
    }

    /**
     * 说明：判断是否显示
     * @param view
     * @return
     */
    public static boolean isShow(View view){
        boolean flag = false;
        if (view != null){
            flag = view.getVisibility() == View.VISIBLE;
        }
        return flag;
    }

    /**
     * View Enable
     * @param view
     * @param enable
     */
    public static void enable(View view, boolean enable){
        if (view != null){
            view.setEnabled(enable);
        }
    }

    /**
     * 关闭对话框
     * @param dialogs
     */
    public static void dismiss(Dialog...dialogs){
        if (dialogs != null && dialogs.length > 0){
            for (Dialog dialog:dialogs){
                if (dialog != null && dialog.isShowing()){
                    dialog.dismiss();
                }
            }
        }
    }

    /**
     * findViewById
     * @param activity
     * @param id
     * @return
     */
    public static <T extends View> T find(Activity activity, int id) {
        T t = (T) activity.findViewById(id);
        return t;
    }

    /**
     * findViewById
     * @param view
     * @param id
     * @return
     */
    public static <T extends View> T find(View view, int id) {
        return find(view,id,null);
    }

    /**
     * findViewById
     * @param view
     * @param id
     * @return
     */
    public static <T extends View> T find(View view, int id, View.OnClickListener listener) {
        T t = (T) view.findViewById(id);
        if (t != null && listener != null){
            t.setOnClickListener(listener);
        }
        return t;
    }
    /**
     * 说明：设置View底部弹出隐藏动画
     * @param view
     */
    public static void BOTTOMOUT(View view, float start, float end){
        if (view != null){
            ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", start, end);
            animator.setDuration(300);
            animator.start();
        }
    }
}
