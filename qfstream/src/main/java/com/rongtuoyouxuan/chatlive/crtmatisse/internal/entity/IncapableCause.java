/*
 * Copyright 2017 Zhihu Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an &quot;AS IS&quot; BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.rongtuoyouxuan.chatlive.crtmatisse.internal.entity;

import android.content.Context;
import androidx.annotation.IntDef;
import androidx.fragment.app.FragmentActivity;

import android.content.DialogInterface;
import android.widget.Toast;

import com.rongtuoyouxuan.chatlive.stream.R;
import com.rongtuoyouxuan.chatlive.crtmatisse.dialog.DiySystemDialog;
import com.rongtuoyouxuan.chatlive.crtmatisse.internal.ui.widget.IncapableDialog;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

@SuppressWarnings("unused")
public class IncapableCause {
    public static final int TOAST = 0x00;
    public static final int DIALOG = 0x01;
    public static final int NONE = 0x02;
    private int mForm = TOAST;
    private String mTitle;
    private String mMessage;
    public IncapableCause(String message) {
        mMessage = message;
    }

    public IncapableCause(String title, String message) {
        mTitle = title;
        mMessage = message;
    }

    public IncapableCause(@Form int form, String message) {
        mForm = form;
        mMessage = message;
    }

    public IncapableCause(@Form int form, String title, String message) {
        mForm = form;
        mTitle = title;
        mMessage = message;
    }

    public static void handleCause(Context context, IncapableCause cause) {
        if (cause == null)
            return;

        switch (cause.mForm) {
            case NONE:
                // do nothing.
                break;
            case DIALOG:
                DiySystemDialog.Builder builder = new DiySystemDialog.Builder(context);
                builder.setTitle(cause.mTitle);
                builder.setMessage(cause.mMessage);
                builder.setNegativeButton(context.getString(R.string.reupload_photo), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                DiySystemDialog dialog = builder.create();
                dialog.setCancelable(true);
                dialog.show();
//                IncapableDialog incapableDialog = IncapableDialog.newInstance(cause.mTitle, cause.mMessage);
//                incapableDialog.show(((FragmentActivity) context).getSupportFragmentManager(),
//                        IncapableDialog.class.getName());
                break;
            case TOAST:
            default:
                Toast.makeText(context, cause.mMessage, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Retention(SOURCE)
    @IntDef({TOAST, DIALOG, NONE})
    public @interface Form {
    }
}