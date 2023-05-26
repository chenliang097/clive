package com.rongtuoyouxuan.chatlive.crtuikit.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

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
		private boolean isCancle = true;
		private boolean isOnKeyCancle = false;
		private OnClickListener positiveButtonClickListener;
		private OnClickListener negativeButtonClickListener;

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

		public Builder setIsCancle(boolean isCancle){
			this.isCancle = isCancle;
			return this;
		}

		public Builder setOnKeyCancle(boolean isOnKeyCancle){
			this.isOnKeyCancle = !isOnKeyCancle;
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
			final DiySystemDialog dialog = new DiySystemDialog(context, R.style.pl_libgift_DiyDialog);
			View layout = inflater.inflate(R.layout.qf_gift_diy_sysytem_dialog, null);
			dialog.addContentView(layout, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			dialog.setCanceledOnTouchOutside(isCancle);
			dialog.setOnKeyListener(new OnKeyListener() {
				@Override
				public boolean onKey(DialogInterface arg0, int keyCode, KeyEvent arg2) {
					if(keyCode == KeyEvent.KEYCODE_BACK){
						return isOnKeyCancle;
					}else{
						return false;
					}
				}
			});
			// set the dialog title
			if(title != null){
				((TextView) layout.findViewById(R.id.diy_dialog_title)).setText(title);
			}else{
				((TextView) layout.findViewById(R.id.diy_dialog_title)).setVisibility(View.GONE);
			}
			// set the confirm button
			if (positiveButtonText != null) {
				((TextView) layout.findViewById(R.id.diy_dialog_ok)).setText(positiveButtonText);
				if (positiveButtonClickListener != null) {
					((TextView) layout.findViewById(R.id.diy_dialog_ok))
							.setOnClickListener(new View.OnClickListener() {
								public void onClick(View v) {
									positiveButtonClickListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
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
				((LinearLayout) layout.findViewById(R.id.diy_dialog_msg_ll)).addView( contentView, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			}
			dialog.setContentView(layout);
			return dialog;
		}

	}
}
