package com.rongtuoyouxuan.chatlive.base.view.adapter

import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.makeramen.roundedimageview.RoundedImageView
import com.rongtuoyouxuan.chatlive.biz2.model.stream.LiveRoomVisibleRangeListBean
import com.rongtuoyouxuan.chatlive.stream.R
import com.rongtuoyouxuan.chatlive.util.NumUtils
import kotlinx.android.synthetic.main.rt_libstream_select_common_title.*

class LiveRoomVisibleRangeAdapter:
    BaseQuickAdapter<LiveRoomVisibleRangeListBean.FansItemBean, BaseViewHolder> {
    private var mOnSelectContactsListener: OnSelectContactsListener? = null

    //用于存储CheckBox选中状态
    private var mCBFlag: MutableMap<Int, Boolean>? = HashMap()
    private var isShowCheckbox = true
    private var type = ""

    constructor(layoutResId: Int, type:String?):super(layoutResId){
        if (type != null) {
            this.type = type
        }
    }
    init {
        mCBFlag = HashMap()
    }

    fun init(lists: List<LiveRoomVisibleRangeListBean.FansItemBean>) {
        for (i in lists.indices) {
            mCBFlag?.set(NumUtils.parseInt(lists[i].id), false)
        }
    }

    override fun convert(helper: BaseViewHolder, item: LiveRoomVisibleRangeListBean.FansItemBean) {
        val position = helper.layoutPosition
        val checkBox = helper.getView<CheckBox>(R.id.itemCheck)
        val avatar = helper.getView<RoundedImageView>(R.id.itemAvatar)
        helper.getView<TextView>(R.id.itemName).text = item.nick_name
        if (isShowCheckbox) {
            checkBox.visibility = View.VISIBLE
        } else {
            checkBox.visibility = View.GONE
        }
        when(type){
            "1"->{
                if(item.allow){
                    checkBox.isChecked = true
                    mCBFlag?.set(item.id.toInt(), true)
                    mOnSelectContactsListener!!.onItemCheck(position, item, true)
                }
            }
            "2"->{
                if(!item.allow){
                    checkBox.isChecked = true
                    mCBFlag?.set(item.id.toInt(), true)
                    mOnSelectContactsListener!!.onItemCheck(position, item, true)
                }
            }
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
        fun onItemCheck(position: Int, item: LiveRoomVisibleRangeListBean.FansItemBean?, isAdd: Boolean)
    }
}