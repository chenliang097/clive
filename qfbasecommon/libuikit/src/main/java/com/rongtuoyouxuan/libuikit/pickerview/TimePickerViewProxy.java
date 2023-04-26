package com.rongtuoyouxuan.libuikit.pickerview;

import android.content.Context;
import android.graphics.Color;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.StringUtils;
import com.rongtuoyouxuan.libuikit.R;

import java.util.Calendar;

/**
 * Created by Mr.Z
 */
public class TimePickerViewProxy {

    private TimePickerView pvTime;

    public TimePickerViewProxy(Context context, OnTimeSelectListener timeSelectListener) {

        Calendar selectedDate = Calendar.getInstance();
        selectedDate.set(2015, 0, 1);

        Calendar startDate = Calendar.getInstance();
        startDate.set(1900, 0, 1);

        Calendar endDate = Calendar.getInstance();
        endDate.set(endDate.get(Calendar.YEAR), endDate.get(Calendar.MONTH), endDate.get(Calendar.DATE));

        init(context, selectedDate, startDate, endDate, timeSelectListener);

    }

    public TimePickerViewProxy(Context context, Calendar selectedDate, OnTimeSelectListener timeSelectListener) {
        Calendar startDate = Calendar.getInstance();
        startDate.set(1900, 0, 1);

        Calendar endDate = Calendar.getInstance();
        endDate.set(endDate.get(Calendar.YEAR), endDate.get(Calendar.MONTH), endDate.get(Calendar.DATE));

        //为空则给默认值
        if (selectedDate == null) {
            selectedDate = Calendar.getInstance();
            selectedDate.set(2015, 0, 1);
        }
        init(context, selectedDate, startDate, endDate, timeSelectListener);
    }

    private void init(Context context, Calendar selectedDate, Calendar startDate, Calendar endDate, OnTimeSelectListener timeSelectListener) {
        pvTime = new TimePickerBuilder(context, (date, v) -> {//选中事件回调
            if (timeSelectListener != null) {
                timeSelectListener.onTimeSelect(date, v);
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})
                .setSubmitText(StringUtils.getString(R.string.login_save))
                .setCancelText(StringUtils.getString(R.string.login_cancel))
                .setSubCalSize(17)//确定和取消文字大小
                .setContentTextSize(18)//内容区文字
                .setSubmitColor(ColorUtils.string2Int("#8000FF"))//确定按钮文字颜色
                .setCancelColor(ColorUtils.string2Int("#666666"))//取消按钮文字颜色
                .setDividerColor(Color.TRANSPARENT)
                .setOutSideCancelable(false)
                .isCyclic(false)
                .isAlphaGradient(true)
                .setItemVisibleCount(5)
//                .setLayoutRes(R.layout.profession_lib_pickerview_custom_time, (CustomListener) v -> {
//
//                    LinearLayout timePicker = v.findViewById(R.id.timepicker);
//                    TextView tvSubmit = v.findViewById(R.id.tv_finish);
//                    TextView tvCancel = v.findViewById(R.id.tv_cancel);
//                    timePicker.setPadding(0, 10, 0, BarUtils.getNavBarHeight());
//
//                    tvSubmit.setOnClickListener(v12 -> {
//                        pvTime.returnData();
//                        pvTime.dismiss();
//                    });
//                    tvCancel.setOnClickListener(v1 -> pvTime.dismiss());
//                })
                .setLineSpacingMultiplier(2f)//用于控制 Item 高度间隔
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setLabel("", "", "", "", "", "")
                .isCenterLabel(false)//是否只显示中间的label,默认每个item都显示
                .isDialog(false)
                .build();
    }

    public void show() {
        if (pvTime != null) pvTime.show();
    }


}
