package com.rongtuoyouxuan.chatlive.crtmatisse.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ResourceUtils;
import com.rongtuoyouxuan.chatlive.crtutil.util.ShapeFactory;
import com.rongtuoyouxuan.chatlive.stream.R;


public class DiySystemDialog extends Dialog {

    public DiySystemDialog(Context context) {
        super(context);
    }

    public DiySystemDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private Context context;
        private String title;
        private String message;
        private String positiveButtonText;
        private String negativeButtonText;
        private View contentView;
        private boolean isCancle = false;
        private boolean isOnKeyCancle = false;
        private OnClickListener positiveButtonClickListener;
        private CustomCallback positiveButtonClickOtherListener;
        private OnClickListener negativeButtonClickListener;
        private boolean isDisplayClose = false;
        private boolean isSelectMessageItem = false;
        private int positiveButtonTextColor;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        /**
         * Set the Dialog message from resource
         *
         * @param message
         * @return
         */
        public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
        }

        /**
         * Set the Dialog title from resource
         *
         * @param title
         * @return
         */
        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        /**
         * Set the Dialog title from String
         *
         * @param title
         * @return
         */

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        public Builder setIsCancle(boolean isCancle) {
            this.isCancle = isCancle;
            return this;
        }

        public Builder setOnKeyCancle(boolean isOnKeyCancle) {
            this.isOnKeyCancle = !isOnKeyCancle;
            return this;
        }

        public Builder setDisplayClose(boolean isDisplay) {
            this.isDisplayClose = isDisplay;
            return this;
        }

        /**
         * Set the positive button resource and it's listener
         *
         * @param positiveButtonText
         * @return
         */
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

        public Builder setPositiveButtonOther(String positiveButtonText, CustomCallback listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickOtherListener = listener;
            return this;
        }

        public Builder setPositiveButton(int positiveButtonText, int color, OnClickListener listener) {
            this.positiveButtonText = (String) context.getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            this.positiveButtonTextColor = color;
            return this;
        }

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


        public DiySystemDialog create() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final DiySystemDialog dialog = new DiySystemDialog(context, R.style.DiyDialog);
            final View layout = inflater.inflate(R.layout.zj_libmain_diy_sysytem_dialog, null);
            dialog.addContentView(layout, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
            dialog.setCanceledOnTouchOutside(isCancle);
            dialog.setCancelable(false);
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
            // set the dialog title
            if (title != null) {
                ((TextView) layout.findViewById(R.id.diy_dialog_title)).setText(title);
            } else {
                ((TextView) layout.findViewById(R.id.diy_dialog_title)).setVisibility(View.GONE);
            }
            // set the confirm button
            if (positiveButtonText != null) {
                ((TextView) layout.findViewById(R.id.diy_dialog_ok)).setText(positiveButtonText);
                if (positiveButtonTextColor != 0) {
                    ((TextView) layout.findViewById(R.id.diy_dialog_ok)).setTextColor(positiveButtonTextColor);
                }
                if (positiveButtonClickListener != null) {
                    ((TextView) layout.findViewById(R.id.diy_dialog_ok))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    positiveButtonClickListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);

                                }
                            });
                }
                if (positiveButtonClickOtherListener != null) {
                    final View viewById = layout.findViewById(R.id.circle);
                    ((TextView) layout.findViewById(R.id.diy_dialog_ok))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    positiveButtonClickOtherListener.click(viewById.isSelected(), dialog, DialogInterface.BUTTON_POSITIVE);
                                }
                            });
                }

            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.diy_dialog_ok).setVisibility(View.GONE);
            }
            // set the cancel button
            if (negativeButtonText != null) {
                ((TextView) layout.findViewById(R.id.diy_dialog_cancle)).setText(negativeButtonText);
                if (negativeButtonClickListener != null) {
                    ((TextView) layout.findViewById(R.id.diy_dialog_cancle))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    negativeButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_NEGATIVE);
                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.diy_dialog_cancle).setVisibility(View.GONE);
            }
            // set the content message
            if (message != null) {
                ((TextView) layout.findViewById(R.id.diy_dialog_msg)).setText(message);
            } else if (contentView != null) {
                // if no message set
                // add the contentView to the dialog body
                ((LinearLayout) layout.findViewById(R.id.diy_dialog_msg_ll)).removeAllViews();
                ((LinearLayout) layout.findViewById(R.id.diy_dialog_msg_ll)).addView(contentView, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            } else {
                ((LinearLayout) layout.findViewById(R.id.diy_dialog_msg_ll)).setVisibility(View.GONE);
            }
            if (isDisplayClose) {
                layout.findViewById(R.id.close).setVisibility(View.VISIBLE);
                layout.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            } else {
                layout.findViewById(R.id.close).setVisibility(View.GONE);
            }
            if (isSelectMessageItem) {
                layout.findViewById(R.id.diy_dialog_msg_ll).setVisibility(View.GONE);
                layout.findViewById(R.id.diy_dialog_msg_select).setVisibility(View.VISIBLE);
                layout.findViewById(R.id.circle).setBackground(ShapeFactory.newGeneralSelector()
                        .setDefaultDrawable(ResourceUtils.getDrawable(R.drawable.icon_message_unselected))
                        .setSelectedDrawable(ResourceUtils.getDrawable(R.drawable.icon_message_selected))
                        .create());
                final View viewById = layout.findViewById(R.id.circle);
                layout.findViewById(R.id.diy_dialog_msg_select).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewById.setSelected(!viewById.isSelected());
                    }
                });

            }
            dialog.setContentView(layout);
            return dialog;
        }

        public void setVisibleSelectItem() {
            isSelectMessageItem = true;
        }
    }

    public interface CustomCallback {
        void click(boolean isSelected, DialogInterface dialog, int which);
    }
}
