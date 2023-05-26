package com.rongtuoyouxuan.chatlive.crtuikit.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.rongtuoyouxuan.chatlive.stream.R;


/**
 * 底部弹窗
 */
public class BottomDialog extends Dialog {

    public BottomDialog(Context context) {
        super(context);
    }

    public BottomDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private Context context;
        private String titleString;
        private String positiveButtonText;
        private String positiveButtonTextTwo;
        private String positiveButtonTextThree;
        private String positiveButtonTextFour;
        private String positiveButtonTextFive;
        private String negativeButtonText;
        private boolean isCancle = true;
        private boolean isOnKeyCancle = false;
        private boolean isShowTitle = false;
        private OnClickListener positiveButtonClickListener;
        private OnClickListener positiveButtonClickListenerTwo;
        private OnClickListener positiveButtonClickListenerThree;
        private OnClickListener positiveButtonClickListenerFour;
        private OnClickListener positiveButtonClickListenerFive;
        private OnClickListener negativeButtonClickListener;
        private int colorFour;
        private int colorTwo;
        private int colorThree;
        private int colorOne;
        private int bgOne;

        private int colorFive;

        public Builder(Context context) {
            this.context = context;
            colorFive = context.getResources().getColor(R.color.qf_libutils_color_333333);
            colorFour = context.getResources().getColor(R.color.qf_libutils_color_333333);
            colorTwo = context.getResources().getColor(R.color.qf_libutils_color_333333);
            colorThree = context.getResources().getColor(R.color.qf_libutils_color_333333);
            colorOne = context.getResources().getColor(R.color.qf_libutils_color_333333);
            bgOne = R.drawable.bg_page_more_unit;
        }


        public Builder setIsCancle(boolean isCancle) {
            this.isCancle = isCancle;
            return this;
        }

        public Builder setOnKeyCancle(boolean isOnKeyCancle) {
            this.isOnKeyCancle = !isOnKeyCancle;
            return this;
        }

        /**
         * Set the positive button resource and it's listener
         *
         * @return
         */
        //标题
        public Builder setTitle(int titleRes) {
            this.titleString = (String) context.getText(titleRes);
            return this;
        }

        public Builder setTitle(String titleString) {
            this.titleString = titleString;
            return this;
        }

        //第一个
        public Builder setPositiveButton(int positiveButtonText, OnClickListener listener) {
            this.positiveButtonText = (String) context.getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText, OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText, OnClickListener listener, int color) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            this.colorOne = color;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText, OnClickListener listener, int color, int bg) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            this.colorOne = color;
            this.bgOne = bg;
            return this;
        }

        //第二个
        public Builder setPositiveButtonTwo(int positiveButtonTextTwo, OnClickListener listener) {
            this.positiveButtonTextTwo = (String) context.getText(positiveButtonTextTwo);
            this.positiveButtonClickListenerTwo = listener;
            return this;
        }

        public Builder setPositiveButtonTwo(String positiveButtonTextTwo, OnClickListener listener) {
            this.positiveButtonTextTwo = positiveButtonTextTwo;
            this.positiveButtonClickListenerTwo = listener;
            return this;
        }

        public Builder setPositiveButtonTwo(String positiveButtonTextTwo, OnClickListener listener, int color) {
            this.positiveButtonTextTwo = positiveButtonTextTwo;
            this.positiveButtonClickListenerTwo = listener;
            this.colorTwo = color;
            return this;
        }

        //第三个
        public Builder setPositiveButtonThree(int positiveButtonTextThree, OnClickListener listener) {
            this.positiveButtonTextThree = (String) context.getText(positiveButtonTextThree);
            this.positiveButtonClickListenerThree = listener;
            return this;
        }

        public Builder setPositiveButtonThree(String positiveButtonTextThree, OnClickListener listener) {
            this.positiveButtonTextThree = positiveButtonTextThree;
            this.positiveButtonClickListenerThree = listener;
            return this;
        }

        public Builder setPositiveButtonThree(String positiveButtonTextThree, OnClickListener listener, int color) {
            this.positiveButtonTextThree = positiveButtonTextThree;
            this.positiveButtonClickListenerThree = listener;
            this.colorThree = color;
            return this;
        }

        //第四个
        public Builder setPositiveButtonFour(int positiveButtonTextFour, OnClickListener listener) {
            this.positiveButtonTextFour = (String) context.getText(positiveButtonTextFour);
            this.positiveButtonClickListenerFour = listener;
            return this;
        }

        public Builder setPositiveButtonFour(String positiveButtonTextFour, OnClickListener listene) {
            this.positiveButtonTextFour = positiveButtonTextFour;
            this.positiveButtonClickListenerFour = listene;
            return this;
        }

        public Builder setPositiveButtonFour(String positiveButtonTextFour, OnClickListener listener, int color) {
            this.positiveButtonTextFour = positiveButtonTextFour;
            this.positiveButtonClickListenerFour = listener;
            this.colorFour = color;
            return this;
        }


        //第五个
        public Builder setPositiveButtonFive(int positiveButtonTextFive, OnClickListener listener) {
            this.positiveButtonTextFive = (String) context.getText(positiveButtonTextFive);
            this.positiveButtonClickListenerFive = listener;
            return this;
        }

        public Builder setPositiveButtonFive(String positiveButtonTextFive, OnClickListener listene, int color) {
            this.positiveButtonTextFive = positiveButtonTextFive;
            this.positiveButtonClickListenerFive = listene;
            this.colorFive = color;
            return this;
        }

        public Builder setPositiveButtonFive(String positiveButtonTextFive, OnClickListener listene) {
            this.positiveButtonTextFive = positiveButtonTextFive;
            this.positiveButtonClickListenerFive = listene;
            return this;
        }

        //取消
        public Builder setNegativeButton(int negativeButtonText, OnClickListener listener) {
            this.negativeButtonText = (String) context.getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String negativeButtonText, OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }


        public BottomDialog create() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final BottomDialog dialog = new BottomDialog(context, R.style.pl_libutil_BottomDialog);
            View layout = inflater.inflate(R.layout.page_more_dialog, null);
            dialog.addContentView(layout, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
            dialog.setCanceledOnTouchOutside(isCancle);
            dialog.setCancelable(isCancle);
            dialog.setOnKeyListener(new OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface arg0, int keyCode, KeyEvent arg2) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        return isOnKeyCancle;
                    } else {
                        return false;
                    }
                }
            });
            //是否显示标题
            // set the confirm button
            if (titleString != null) {
                ((TextView) layout.findViewById(R.id.tv_dialog_title)).setText(titleString);
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.tv_dialog_title).setVisibility(View.GONE);
                layout.findViewById(R.id.title_view).setVisibility(View.GONE);
            }
            // set the confirm button
            if (positiveButtonText != null) {
                ((TextView) layout.findViewById(R.id.tv_dialog_album)).setText(positiveButtonText);
                ((TextView) layout.findViewById(R.id.tv_dialog_album)).setTextColor(colorOne);
                ((TextView) layout.findViewById(R.id.tv_dialog_album)).setBackgroundResource(bgOne);

                if (positiveButtonClickListener != null) {
                    ((TextView) layout.findViewById(R.id.tv_dialog_album))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    positiveButtonClickListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.tv_dialog_album).setVisibility(View.GONE);
            }
            //第二个
            if (positiveButtonTextTwo != null) {
                ((TextView) layout.findViewById(R.id.tv_dialog_zuzhi)).setText(positiveButtonTextTwo);
                ((TextView) layout.findViewById(R.id.tv_dialog_zuzhi)).setTextColor(colorTwo);
                if (positiveButtonClickListenerTwo != null) {
                    ((TextView) layout.findViewById(R.id.tv_dialog_zuzhi))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    positiveButtonClickListenerTwo.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.page_more_line).setVisibility(View.GONE);
                layout.findViewById(R.id.tv_dialog_zuzhi).setVisibility(View.GONE);
            }
            //第三个
            if (positiveButtonTextThree != null) {
                ((TextView) layout.findViewById(R.id.tv_dialog_jubao)).setText(positiveButtonTextThree);
                ((TextView) layout.findViewById(R.id.tv_dialog_jubao)).setTextColor(colorThree);
                if (positiveButtonClickListenerThree != null) {
                    ((TextView) layout.findViewById(R.id.tv_dialog_jubao))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    positiveButtonClickListenerThree.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.page_more_line).setVisibility(View.GONE);
                layout.findViewById(R.id.tv_dialog_jubao).setVisibility(View.GONE);
            }

            //第四个
            if (positiveButtonTextFour != null) {
                ((TextView) layout.findViewById(R.id.tv_dialog_lahei)).setText(positiveButtonTextFour);
                ((TextView) layout.findViewById(R.id.tv_dialog_lahei)).setTextColor(colorFour);
                if (positiveButtonClickListenerFour != null) {
                    ((TextView) layout.findViewById(R.id.tv_dialog_lahei))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    positiveButtonClickListenerFour.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.page_lahei_line).setVisibility(View.GONE);
                layout.findViewById(R.id.tv_dialog_lahei).setVisibility(View.GONE);
            }

            //第五个
            if (positiveButtonTextFive != null) {
                ((TextView) layout.findViewById(R.id.tv_dialog_five)).setText(positiveButtonTextFive);
                ((TextView) layout.findViewById(R.id.tv_dialog_five)).setTextColor(colorFive);
                if (positiveButtonClickListenerFive != null) {
                    ((TextView) layout.findViewById(R.id.tv_dialog_five))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    positiveButtonClickListenerFive.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.page_five_line).setVisibility(View.GONE);
                layout.findViewById(R.id.tv_dialog_five).setVisibility(View.GONE);
            }

            // set the cancel button
            if (negativeButtonText != null) {
                ((TextView) layout.findViewById(R.id.tv_dialog_cancel)).setText(negativeButtonText);
                if (negativeButtonClickListener != null) {
                    ((TextView) layout.findViewById(R.id.tv_dialog_cancel))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    negativeButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_NEGATIVE);
                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.tv_dialog_cancel).setVisibility(View.GONE);
            }

            Window dialogWindow = dialog.getWindow();
            dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);

	        /*
             * 将对话框的大小按屏幕大小的百分比设置
	         */
            WindowManager m = dialogWindow.getWindowManager();
            Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
            WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
            p.width = (int) (d.getWidth() * 1); // 宽度设置为屏幕的0.95
            dialogWindow.setAttributes(p);

            dialog.setContentView(layout);
            return dialog;
        }

    }
}
