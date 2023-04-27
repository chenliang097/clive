package com.rongtuoyouxuan.chatlive.base.view.adapter

import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.makeramen.roundedimageview.RoundedImageView
import com.rongtuoyouxuan.chatlive.biz2.model.stream.LiveRoomVisibleRangeListBean.FansItemBean
import com.rongtuoyouxuan.chatlive.stream.R
import com.rongtuoyouxuan.chatlive.util.NumUtils

class LiveRoomVisibleRangeAdapter(layoutResId: Int) :
    BaseQuickAdapter<FansItemBean, BaseViewHolder>(layoutResId) {
    private var mOnSelectContactsListener: OnSelectContactsListener? = null

    //用于存储CheckBox选中状态
    private var mCBFlag: MutableMap<Int, Boolean>? = HashMap()
    private var isShowCheckbox = true

    init {
        mCBFlag = HashMap()
    }

    fun init(lists: List<FansItemBean>) {
        for (i in lists.indices) {
            mCBFlag?.set(NumUtils.parseInt(lists[i].id), false)
        }
    }

    override fun convert(helper: BaseViewHolder, item: FansItemBean) {
        val position = helper.layoutPosition
        val checkBox = helper.getView<CheckBox>(R.id.itemCheck)
        val avatar = helper.getView<RoundedImageView>(R.id.itemAvatar)
        helper.getView<TextView>(R.id.itemName).text = item.nick_name
        if (isShowCheckbox) {
            checkBox.visibility = View.VISIBLE
        } else {
            checkBox.visibility = View.GONE
        }
        checkBox.setOnClickListener {
            if (checkBox.isChecked) {
                checkBox.isChecked = true
                mCBFlag?.set(item.id.toInt(), true)
                mOnSelectContactsListener!!.onItemCheck(position, item, true)
            } else {
                checkBox.isChecked = false
                mCBFlag?.set(item.id.toInt(), false)
                mOnSelectContactsListener!!.onItemCheck(position, item, false)
            }
        }
    }

    fun updateItemCheckBox(uid: String, isAdd: Boolean) {
        try {
            mCBFlag?.set(uid.toInt(), isAdd)
            notifyDataSetChanged()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setShowCheckbox(isShowCheckbox: Boolean) {
        this.isShowCheckbox = isShowCheckbox
        notifyDataSetChanged()
    }

    fun setOnSelectContactsListener(mOnSelectContactsListener: OnSelectContactsListener?) {
        this.mOnSelectContactsListener = mOnSelectContactsListener
    }

    interface OnSelectContactsListener {
        fun onItemCheck(position: Int, item: FansItemBean?, isAdd: Boolean)
    }
}