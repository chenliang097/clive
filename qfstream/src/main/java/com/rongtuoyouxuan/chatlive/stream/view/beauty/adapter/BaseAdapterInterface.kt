package com.rongtuoyouxuan.chatlive.stream.view.beauty.adapter

import com.rongtuoyouxuan.chatlive.stream.view.beauty.listener.OnItemClickListener
import com.rongtuoyouxuan.chatlive.stream.view.beauty.model.BottomMenuItem

interface BaseAdapterInterface {
    fun setData(dataList: List<BottomMenuItem>)
    fun setOnItemClickListener(listener: OnItemClickListener)
}