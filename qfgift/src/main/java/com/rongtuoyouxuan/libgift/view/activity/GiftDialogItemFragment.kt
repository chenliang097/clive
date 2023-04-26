package com.rongtuoyouxuan.libgift.view.activity

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.rongtuoyouxuan.chatlive.biz2.model.gift.GiftEntity
import com.rongtuoyouxuan.libgift.R
import com.rongtuoyouxuan.libgift.adapter.GiftPanelItemAdapter
import com.rongtuoyouxuan.libgift.view.layout.GiftPanelDecoration
import com.rongtuoyouxuan.libgift.viewmodel.GiftHelper
import com.rongtuoyouxuan.libgift.viewmodel.GiftVM
import com.rongtuoyouxuan.libuikit.SimpleFragment
import com.rongtuoyouxuan.libuikit.dp
import kotlinx.android.synthetic.main.fragment_gift_vp_fragment.*

class GiftDialogItemFragment : SimpleFragment() {

    private var giftVm: GiftVM? = null


    private val panelAdapter: GiftPanelItemAdapter by lazy {
        GiftPanelItemAdapter()
    }

    companion object {
        fun newInstance(position: Int = 0) = GiftDialogItemFragment().apply {
            arguments = Bundle().apply {
                putInt("position", position)
            }
        }

        fun newInstance() = GiftDialogItemFragment()
    }

    override fun getLayoutResId(): Int = R.layout.fragment_gift_vp_fragment

    override fun initData() {
        if (null == activity) {
            return
        }

        giftRecyclerView.layoutManager = GridLayoutManager(context, 4)
        giftRecyclerView.addItemDecoration(GiftPanelDecoration(3.dp.toInt(), 3.dp.toInt()))

        giftVm = ViewModelProvider(requireActivity()).get(GiftVM::class.java)
        val list = giftVm?.giftSucVM?.value?.list
        if (list?.isNotEmpty() == true) {
            giftRecyclerView.adapter = panelAdapter
            panelAdapter.setNewInstance(list)
            panelAdapter.setOnItemClickListener { adapter, view, position ->
                val entity = adapter.data[position] as GiftEntity
                GiftHelper.giftSelected.value = entity
                panelAdapter.change(position)
            }

        } else {
            completeEmptyDataBy23()
        }
    }

    override fun initListener() {
    }

    private fun completeEmptyDataBy23() {
        if ((flEmpty?.childCount ?: 0) > 0) {
            flEmpty?.removeAllViews()
        }
        val emptyView =
            layoutInflater.inflate(
                R.layout.layout_gift_common_empty,
                flEmpty,
                false
            )
        if (null == emptyView.parent) {
            flEmpty?.addView(emptyView)
        } else {
            flEmpty?.removeView(emptyView)
        }
    }
}