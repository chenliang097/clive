package com.rongtuoyouxuan.chatlive.stream.view.beauty.listener

/**
 * RecyclerView的元素点击监听器
 * author: qingyingliu
 * date: 3/23/21
 */
@FunctionalInterface
interface OnItemClickListener {
    fun onItemClick(position: Int)
}