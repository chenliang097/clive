package com.rongtuoyouxuan.chatlive.crtuikit.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Outline
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.core.view.isVisible
import com.rongtuoyouxuan.chatlive.crtuikit.dialog.factory.BaseDialog
import com.rongtuoyouxuan.chatlive.crtuikit.dialog.factory.DialogFactory
import com.rongtuoyouxuan.chatlive.crtuikit.dialog.factory.DialogRender
import com.rongtuoyouxuan.chatlive.crtuikit.dp
import com.rongtuoyouxuan.chatlive.stream.R

class ItemsBottomDialog private constructor(items: List<Item>) {
    companion object {
        @JvmStatic
        fun create(vararg items: Item): ItemsBottomDialog = ItemsBottomDialog(items.toList())
    }

    val itemsDialogRender = ItemsDialogRender(items)

    fun setTitle(titleRes: Int): ItemsBottomDialog {
        itemsDialogRender.titleRes = titleRes
        return this
    }

    fun setTitleString(titleRes: String): ItemsBottomDialog {
        itemsDialogRender.titleString = titleRes
        return this
    }

    fun show(context: Context) {
        DialogFactory.createBottomDialog(
            context,
            itemsDialogRender,
            BaseDialog.Config(widthFactor = 1.0f, heightFactor = WindowManager.LayoutParams.WRAP_CONTENT.toFloat())
        ).show()
    }
}

data class Item(val textRes: Int? = null, val click: (() -> Unit)? = null)

class ItemsDialogRender(private val items: List<Item>) : DialogRender {
    private lateinit var titleTv: TextView
    private lateinit var cancelTv: TextView
    lateinit var itemsWrapper: ViewGroup
    lateinit var contentWrapper: View
    var titleRes: Int? = null
    var titleString: String? = null

    override fun getLayoutResId(): Int = R.layout.uikit_common_items_dialog

    override fun render(savedInstanceState: Bundle?, rootView: View, dialog: Dialog) {
        titleTv = rootView.findViewById(R.id.title)
        cancelTv = rootView.findViewById(R.id.tv_dialog_cancel)
        itemsWrapper = rootView.findViewById(R.id.itemsWrapper)
        contentWrapper = rootView.findViewById(R.id.content)
        contentWrapper.outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View, outline: Outline) {
                outline.setRoundRect(0, 0, view.width, view.height, 8.dp)
            }
        }
        contentWrapper.clipToOutline = true
        val inflater = rootView.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        items.forEach { item ->
            val itemView = inflater.inflate(R.layout.uikit_common_items_dialog_item, itemsWrapper, false)
                .findViewById<TextView>(R.id.text)
            if (item.textRes != null) itemView.setText(item.textRes)
            itemView.setOnClickListener {
                item.click?.invoke()
                dialog.dismiss()
            }
            itemsWrapper.addView(itemView)
        }
        cancelTv.setOnClickListener {
            dialog.dismiss()
        }

        titleRes?.apply {
            if (this > 0) {
                titleTv.isVisible = true
                titleTv.setText(this)
            }
        }

        titleString?.apply {
            if (this.isNotEmpty()) {
                titleTv.isVisible = true
                titleTv.text = this
            }
        }
    }
}