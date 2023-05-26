package com.rongtuoyouxuan.chatlive.qfcommon.util

import android.content.Context
import com.blankj.utilcode.util.StringUtils
import com.rongtuoyouxuan.chatlive.crtmatisse.dialog.DiySystemDialog
import com.rongtuoyouxuan.chatlive.stream.R

/*
*Create by {Mr秦} on 2022/8/6
*/
object DiySystemDialogUtil {

    fun showOkDialog(
        context: Context,
        title: String?,
        content: String?,
        callBack: () -> Unit,
    ) {
        val builder = DiySystemDialog.Builder(context)
        if (!StringUtils.isTrimEmpty(title)) builder.setTitle(title)
        if (!StringUtils.isTrimEmpty(content)) builder.setMessage(content)
        builder.setNegativeButton(
            R.string.dialog_fou
        ) { dialog, _ ->
            dialog.dismiss()
        }
        builder.setPositiveButton(
            StringUtils.getString(R.string.dialog_ok)
        ) { dialog, _ ->
            callBack.invoke()
            dialog.dismiss()
        }
        builder.create().show()
    }

    fun showConfirmDialog(
        context: Context,
        title: String?,
        content: String?,
        callBack: () -> Unit,
    ) {
        val builder = DiySystemDialog.Builder(context)
        if (!StringUtils.isTrimEmpty(title)) builder.setTitle(title)
        if (!StringUtils.isTrimEmpty(content)) builder.setMessage(content)
        builder.setNegativeButton(R.string.cancel
        ) { dialog, _ ->
            dialog.dismiss()
        }
        builder.setPositiveButton(StringUtils.getString(R.string.login_ok)
        ) { dialog, _ ->
            callBack.invoke()
            dialog.dismiss()
        }
        builder.create().show()
    }

    fun showConfirmDialogWithBtnStr(
        context: Context,
        title: String?,
        content: String?,
        positiveBtnStr: String?,
        callBack: () -> Unit,
    ) {
        val builder = DiySystemDialog.Builder(context)
        if (!StringUtils.isTrimEmpty(title)) builder.setTitle(title)
        if (!StringUtils.isTrimEmpty(content)) builder.setMessage(content)
        builder.setNegativeButton(R.string.cancel
        ) { dialog, _ ->
            dialog.dismiss()
        }

        builder.setPositiveButton(
            if (!StringUtils.isTrimEmpty(positiveBtnStr)) {
                positiveBtnStr
            } else {
                StringUtils.getString(R.string.login_ok)
            }
        ) { dialog, _ ->
            callBack.invoke()
            dialog.dismiss()
        }
        builder.create().show()
    }

    fun showConfirmDialogWithBtnStr(
        context: Context,
        title: String?,
        content: String?,
        negativeBtnStr: String?,
        positiveBtnStr: String?,
        callBackNegative: () -> Unit = {},
        callBack: () -> Unit = {},

        ) {
        val builder = DiySystemDialog.Builder(context)
        if (!StringUtils.isTrimEmpty(title)) builder.setTitle(title)
        if (!StringUtils.isTrimEmpty(content)) builder.setMessage(content)
        builder.setNegativeButton(if (!StringUtils.isTrimEmpty(negativeBtnStr)) {
            negativeBtnStr
        } else {
            StringUtils.getString(R.string.cancel)
        }) { dialog, _ ->
            callBackNegative.invoke()
            dialog.dismiss()
        }

        builder.setPositiveButton(
            if (!StringUtils.isTrimEmpty(positiveBtnStr)) {
                positiveBtnStr
            } else {
                StringUtils.getString(R.string.login_ok)
            }
        ) { dialog, _ ->
            callBack.invoke()
            dialog.dismiss()
        }
        builder.create().show()
    }

    fun showConfirmDialogWithBtnStrAndMessage(
        context: Context,
        title: String?,
        content: String?,
        negativeBtnStr: String?,
        positiveBtnStr: String?,
        callBackNegative: () -> Unit = {},
        callBack: (bo: Boolean) -> Unit = {},

        ) {
        val builder = DiySystemDialog.Builder(context)
        if (!StringUtils.isTrimEmpty(title)) builder.setTitle(title)
        if (!StringUtils.isTrimEmpty(content)) builder.setMessage(content)
        builder.setNegativeButton(if (!StringUtils.isTrimEmpty(negativeBtnStr)) {
            negativeBtnStr
        } else {
            StringUtils.getString(R.string.cancel)
        }) { dialog, _ ->
            callBackNegative.invoke()
            dialog.dismiss()
        }
        builder.setVisibleSelectItem()
        builder.setPositiveButtonOther(
            if (!StringUtils.isTrimEmpty(positiveBtnStr)) {
                positiveBtnStr
            } else {
                StringUtils.getString(R.string.login_ok)
            }
        ) { isSelected, dialog, _ ->
            callBack.invoke(isSelected)
            dialog?.dismiss()
        }
        builder.create().show()
    }

    fun showOneTapConfirmDialog(
        context: Context,
        title: String?,
        btnTxt: String?,
        callBack: (() -> Unit)?,
    ) {
        val builder = DiySystemDialog.Builder(context)
        if (!StringUtils.isTrimEmpty(title)) builder.setTitle(title)
        builder.setPositiveButton(StringUtils.getString(R.string.login_ok)
        ) { dialog, _ ->
            callBack?.invoke()
            dialog.dismiss()
        }
        builder.create().show()
    }
}