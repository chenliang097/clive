package com.rongtuoyouxuan.chatlive.crtuikit.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.rongtuoyouxuan.chatlive.crtuikit.dialog.factory.DialogRender
import com.rongtuoyouxuan.chatlive.stream.R

class CommonItemDialogRender : DialogRender {

    override fun getLayoutResId(): Int = R.layout.common_more_item_dialog

    private var mTvDialogTitle: TextView? = null
    private var mTvItem1: TextView? = null
    private var mTvItem2: TextView? = null
    private var mTvItem3: TextView? = null
    private var mTvItem4: TextView? = null
    private var mTvItem5: TextView? = null
    private var mTvCancel: TextView? = null
    private var mDividerTitle: View? = null
    private var mDivider1: View? = null
    private var mDivider2: View? = null
    private var mDivider3: View? = null
    private var mDivider4: View? = null

    private var mDialog: Dialog? = null

    private var title: CharSequence? = null
    private var item1String: CharSequence? = null
    private var item2String: CharSequence? = null
    private var item3String: CharSequence? = null
    private var item4String: CharSequence? = null
    private var item5String: CharSequence? = null

    private var methodItem1: (() -> Unit)? = null
    private var methodItem2: (() -> Unit)? = null
    private var methodItem3: (() -> Unit)? = null
    private var methodItem4: (() -> Unit)? = null
    private var methodItem5: (() -> Unit)? = null

    override fun render(savedInstanceState: Bundle?, rootView: View, dialog: Dialog) {
        this.mDialog = dialog
        mTvDialogTitle = rootView.findViewById<TextView>(R.id.tv_dialog_title)
        mTvItem1 = rootView.findViewById<TextView>(R.id.tv_item_1)
        mTvItem2 = rootView.findViewById<TextView>(R.id.tv_item_2)
        mTvItem3 = rootView.findViewById<TextView>(R.id.tv_item_3)
        mTvItem4 = rootView.findViewById<TextView>(R.id.tv_item_4)
        mTvItem5 = rootView.findViewById<TextView>(R.id.tv_item_5)
        mTvCancel = rootView.findViewById<TextView>(R.id.tv_dialog_cancel)
        mDividerTitle = rootView.findViewById<View>(R.id.divider_title)
        mDivider1 = rootView.findViewById<View>(R.id.divider_1)
        mDivider2 = rootView.findViewById<View>(R.id.divider_2)
        mDivider3 = rootView.findViewById<View>(R.id.divider_3)
        mDivider4 = rootView.findViewById<View>(R.id.divider_4)

        mTvDialogTitle?.apply {
            if (!title.isNullOrEmpty()) {
                visibility = View.VISIBLE
                text = title
                mDividerTitle?.visibility = View.VISIBLE
            }
        }
        mTvItem1?.apply {
            if (!item1String.isNullOrEmpty()) {
                visibility = View.VISIBLE
                text = item1String
                mDivider1?.visibility = View.VISIBLE
                setOnClickListener {
                    methodItem1?.invoke()
                    mDialog?.dismiss()
                }
            }
        }
        mTvItem2?.apply {
            if (!item2String.isNullOrEmpty()) {
                visibility = View.VISIBLE
                text = item2String
                mDivider2?.visibility = View.VISIBLE
                setOnClickListener {
                    methodItem2?.invoke()
                    mDialog?.dismiss()
                }
            }
        }
        mTvItem3?.apply {
            if (!item3String.isNullOrEmpty()) {
                visibility = View.VISIBLE
                text = item3String
                mDivider3?.visibility = View.VISIBLE
                setOnClickListener {
                    methodItem3?.invoke()
                    mDialog?.dismiss()
                }
            }
        }
        mTvItem4?.apply {
            if (!item4String.isNullOrEmpty()) {
                visibility = View.VISIBLE
                text = item4String
                mDivider4?.visibility = View.VISIBLE
                setOnClickListener {
                    methodItem4?.invoke()
                    mDialog?.dismiss()
                }
            }
        }
        mTvItem5?.apply {
            if (!item5String.isNullOrEmpty()) {
                visibility = View.VISIBLE
                text = item5String
                setOnClickListener {
                    methodItem5?.invoke()
                    mDialog?.dismiss()
                }
            }
        }

        mTvCancel?.setOnClickListener {
            dialog.dismiss()
        }
    }

    fun setTitle(title: String): CommonItemDialogRender {
        this.title = title
        return this
    }

    fun setItem1String(itemString: CharSequence?, method: () -> Unit): CommonItemDialogRender {
        this.item1String = itemString
        this.methodItem1 = method
        return this
    }

    fun setItem2String(itemString: CharSequence?, method: () -> Unit): CommonItemDialogRender {
        this.item2String = itemString
        this.methodItem2 = method
        return this
    }

    fun setItem3String(itemString: CharSequence?, method: () -> Unit): CommonItemDialogRender {
        this.item3String = itemString
        this.methodItem3 = method
        return this
    }

    fun setItem4String(itemString: CharSequence?, method: () -> Unit): CommonItemDialogRender {
        this.item4String = itemString
        this.methodItem4 = method
        return this
    }

    fun setItem5String(itemString: CharSequence?, method: () -> Unit): CommonItemDialogRender {
        this.item5String = itemString
        this.methodItem5 = method
        return this
    }
}