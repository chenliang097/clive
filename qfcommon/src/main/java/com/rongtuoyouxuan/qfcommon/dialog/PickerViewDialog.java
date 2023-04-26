package com.rongtuoyouxuan.qfcommon.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnDismissListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.rongtuoyouxuan.qfcommon.R;

import java.util.List;

/**
 * @Description :
 * @Author : jianbo
 * @Date : 2023/2/24  16:45
 */
public class PickerViewDialog<T> {

    private OptionsPickerView<T> pickerView;

    public PickerViewDialog(Context context, String title, List<T> options1Items, int selectPosition1, OnOptionsSelectListener timeSelectListener) {
        init(context, title, options1Items, selectPosition1, timeSelectListener);
    }

    private void init(Context context, String title, List<T> options1Items, int selectPosition1, OnOptionsSelectListener timeSelectListener) {
        pickerView = new OptionsPickerBuilder(context, timeSelectListener)
                .isDialog(true)
                .setContentTextSize(15)//内容区文字
                .setDividerColor(Color.TRANSPARENT)
                .setCyclic(false, false, false)
                .setOutSideCancelable(true)
                .setSelectOptions(selectPosition1, 0, 0)
                .isAlphaGradient(false)//梯式渐变
                .setItemVisibleCount(9)//可见个数
                .setLayoutRes(R.layout.dialog_pickerview_options, new CustomListener() {
                            @Override
                            public void customLayout(View v) {
                                TextView tvTitle = v.findViewById(R.id.tvTitle);
                                tvTitle.setText(title);
                            }
                        }
                )
                .setLineSpacingMultiplier(1.6f)//用于控制 Item 高度间隔
                .isCenterLabel(false)//是否只显示中间的label,默认每个item都显示
                .build();
        pickerView.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(Object o) {
                pickerView.returnData();
            }
        });
        pickerView.setPicker(options1Items, null, null);
    }

    public void show() {
        if (pickerView != null) {
            pickerView.show();
            Dialog dialog = pickerView.getDialog();
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) pickerView.getDialogContainerLayout().getLayoutParams();
            layoutParams.leftMargin = 0;
            layoutParams.rightMargin = 0;
            pickerView.getDialogContainerLayout().setLayoutParams(layoutParams);
            if (dialog != null) {
                Window dialogWindow = dialog.getWindow();
                if (dialogWindow != null) {
                    WindowManager.LayoutParams attributes = dialogWindow.getAttributes();
                    attributes.dimAmount = 0.3f;
                    attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
                    attributes.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    attributes.gravity = Gravity.BOTTOM;
                    dialogWindow.setAttributes(attributes);
                }
            }
        }
    }


}
