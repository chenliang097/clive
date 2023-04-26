package com.rongtuoyouxuan.libgift.view.layout

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupWindow
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rongtuoyouxuan.libgift.R
import com.rongtuoyouxuan.libgift.adapter.GiftSelectNumAdapter
import com.rongtuoyouxuan.libgift.viewmodel.GiftHelper
import com.rongtuoyouxuan.libuikit.dp
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener

/**
 * 
 * date:2022/8/1-20:23
 * des:
 */
class GiftNumberSelectPop//加载的PopupWindow 的样式布局
    (
    context: Context,
    private val numList: List<Int>,
    private val onClickCallBack: (num: Int) -> Unit
) :
    PopupWindow(context), OnItemClickListener {

    private val numAdapter by lazy { GiftSelectNumAdapter() }

    init {
        height = 32f.dp.toInt().times(numList.size)
        width = 80.dp.toInt()
        isOutsideTouchable = true
        isFocusable = true
        setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.gift_select_number_bg))
        val contentView = LayoutInflater.from(context).inflate(
            R.layout.gift_dialog_select_number,
            null, false
        )
        setContentView(contentView)
        val recyclerView = contentView.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = numAdapter
            numAdapter.setNewInstance(numList.toMutableList())
            numAdapter.setOnItemClickListener(this@GiftNumberSelectPop)
        }
    }

    override fun dismiss() {
        super.dismiss()
        GiftHelper.isShowDialog.postValue(false)
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        onClickCallBack((adapter?.data?.get(position) as? Int) ?: 0)
        dismiss()
    }
}