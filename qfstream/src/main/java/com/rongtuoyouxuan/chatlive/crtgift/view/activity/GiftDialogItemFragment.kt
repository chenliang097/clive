package com.rongtuoyouxuan.chatlive.crtgift.view.activity

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.rongtuoyouxuan.chatlive.crtbiz2.model.gift.GiftEntity
import com.rongtuoyouxuan.chatlive.crtgift.adapter.GiftPanelItemAdapter
import com.rongtuoyouxuan.chatlive.crtgift.view.layout.GiftPanelDecoration
import com.rongtuoyouxuan.chatlive.crtgift.viewmodel.GiftHelper
import com.rongtuoyouxuan.chatlive.crtgift.viewmodel.GiftVM
import com.rongtuoyouxuan.chatlive.stream.R
import com.rongtuoyouxuan.chatlive.crtuikit.SimpleFragment
import com.rongtuoyouxuan.chatlive.crtuikit.dp
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
        giftRecyclerView.addItemDecoration(GiftPanelDecoration(3.dp.toInt(), 6.dp.toInt()))

        giftVm = ViewModelProvider(requireActivity()).get(GiftVM::class.java)
        val list = giftVm?.giftSucVM?.value?.list
        if (list?.isNotEmpty() == true) {
            giftRecyclerView.adapter = panelAdapter
            panelAdapter.setNewInstance(list)
            val entity = list[0]
            GiftHelper.giftSelected.value = entity
            panelAdapter.change(0)
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