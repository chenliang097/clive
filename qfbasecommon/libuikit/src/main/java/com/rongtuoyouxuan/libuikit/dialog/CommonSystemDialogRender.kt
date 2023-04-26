package com.rongtuoyouxuan.libuikit.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorInt
import com.rongtuoyouxuan.libuikit.R
import com.rongtuoyouxuan.libuikit.dialog.factory.DialogRender

class CommonSystemDialogRender(val layoutRes: Int = R.layout.common_sysytem_dialog_style_1) : DialogRender {

    private var mDialog: Dialog? = null
    private var mTvContent: TextView? = null
    private var mTvTitle: TextView? = null
    private var mDialogCancel: TextView? = null
    private var mDialogOk: TextView? = null
    private var mTitleStr: String? = null
    private var mContentStr: String? = null
    private var mCancelStr: String? = null
    private var mOkStr: String? = null

    @ColorInt
    private var mTitleColor: Int? = null

    @ColorInt
    private var mContentColor: Int? = null

    @ColorInt
    private var mCancelColor: Int? = null

    @ColorInt
    private var mOkColor: Int? = null

    private var cancelClick: DialogInterface.OnClickListener? = null
    private var okClick: DialogInterface.OnClickListener? = null

    override fun getLayoutResId(): Int = layoutRes

    override fun render(savedInstanceState: Bundle?, rootView: View, dialog: Dialog) {
        this.mDialog = dialog
        var mContext = rootView.context
        mTvTitle = rootView.findViewById<TextView>(R.id.tv_title)
        mTvContent = rootView.findViewById<TextView>(R.id.tv_content)
        mDialogCancel = rootView.findViewById<TextView>(R.id.diy_dialog_cancle)
        mDialogOk = rootView.findViewById<TextView>(R.id.diy_dialog_ok)
        if (mTitleStr.isNullOrEmpty()){
            mTvTitle?.visibility = View.GONE
        }else{
            mTvTitle?.let {
                it.visibility = View.VISIBLE
                it.text = mTitleStr
                it.setTextColor(mTitleColor!!)
            }
        }

        mTvContent?.run {
            text = mContentStr ?: ""
            setTextColor(mContentColor!!)
        }

        mDialogCancel?.run {
            text = mCancelStr ?: mContext.getString(R.string.cancel)
            setTextColor(mCancelColor ?: mContext.resources.getColor(R.color.c_666666))
            setOnClickListener {
                cancelClick?.onClick(mDialog, DialogInterface.BUTTON_NEGATIVE)
                mDialog?.dismiss()
            }
        }

        mDialogOk?.run {
            text = mOkStr ?: mContext.getString(R.string.search_start)
            setTextColor(mOkColor ?: mContext.resources.getColor(R.color.c_common_appcolor))
            setOnClickListener {
                okClick?.onClick(mDialog, DialogInterface.BUTTON_POSITIVE)
                mDialog?.dismiss()
            }
        }
    }

    fun setTitle(titleStr: String?, @ColorInt titleColor: Int = Color.parseColor("#8c8c8c")): CommonSystemDialogRender {
        this.mTitleStr = titleStr
        this.mTitleColor = titleColor
        return this
    }

    fun setContent(contentStr: String, @ColorInt contentColor: Int = Color.parseColor("#8c8c8c")): CommonSystemDialogRender {
        this.mContentStr = contentStr
        this.mContentColor = contentColor
        return this
    }

    fun setCancelAction(cancelStr: String? = null, @ColorInt color: Int = Color.parseColor("#FFFFFF"), click: DialogInterface.OnClickListener = DialogInterface.OnClickListener { dialog, which -> }): CommonSystemDialogRender {
        this.mCancelStr = cancelStr
        this.mCancelColor = color
        this.cancelClick = click
        return this
    }

    fun setOkAction(okStr: String? = null, @ColorInt color: Int = Color.parseColor("#f4521e"), click: DialogInterface.OnClickListener): CommonSystemDialogRender {
        this.mOkStr = okStr
        this.mOkColor = color
        this.okClick = click
        return this
    }
}