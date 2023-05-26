package com.rongtuoyouxuan.chatlive.live.view.activity

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.NetworkUtils
import com.rongtuoyouxuan.chatlive.base.utils.LiveRoomHelper
import com.rongtuoyouxuan.chatlive.crtbiz2.model.stream.LiveChatRoomUserEntity
import com.rongtuoyouxuan.chatlive.crtdatabus.DataBus
import com.rongtuoyouxuan.chatlive.live.view.adapter.MemberAdapter
import com.rongtuoyouxuan.chatlive.stream.R
import com.rongtuoyouxuan.chatlive.crtuikit.SimpleFragment
import com.rongtuoyouxuan.chatlive.crtutil.util.LaToastUtil
import kotlinx.android.synthetic.main.fragment_contribution_member_1.*

/**
 * 
 * date:2022/8/9-19:28
 * des: 成员列表
 */
class MemberFragment : SimpleFragment() {
    private var liveId: String = ""
    private var anchorId: String = ""
    private var page = 1

    private var loginUID = 0L

    private val memberAdapter: MemberAdapter by lazy {
        MemberAdapter()
    }

    companion object {
        fun newInstance(liveId: String?, anchorId: String?, userId: Long) = MemberFragment().apply {
            arguments = Bundle().apply {
                putString("liveId", liveId)
                putString("anchorId", anchorId)
                putLong("userId", userId)
            }
        }
    }

    override fun getLayoutResId(): Int = R.layout.fragment_contribution_member_1

    override fun initData() {
        liveId = arguments?.getString("liveId", "") ?: ""
        anchorId = arguments?.getString("anchorId", "") ?: ""
        val uid = arguments?.getLong("userId") ?: 0L

        loginUID = DataBus.instance().userInfo.value?.user_info?.userId ?: 0L

        refresh?.apply {
            setEnableRefresh(true)
            setEnableLoadMore(true)
            setEnableAutoLoadMore(true)
            setEnableOverScrollBounce(true)
            setEnableLoadMoreWhenContentNotFull(false)

            setOnRefreshListener {
                loadData(true)
                finishRefresh(500)
            }

            setOnLoadMoreListener {
                loadData(false)
                finishLoadMore(500)
            }
        }

        rvList?.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            isNestedScrollingEnabled = true

            memberAdapter.loginUID = uid
            memberAdapter.setHasStableIds(true)
            adapter = memberAdapter
        }
    }

    override fun initListener() {
        memberAdapter.setOnItemChildClickListener { adapter, view, position ->
            val entity = adapter.data[position] as LiveChatRoomUserEntity
            if (entity.role == "guest") {
                LaToastUtil.showShort(R.string.toast_user_rols_guest_tip)
                return@setOnItemChildClickListener
            }
            when (view.id) {
                R.id.spaceClick -> {
                    entity.userId?.let {
                        LiveRoomHelper.cmDismissVM.post(1)
                        LiveRoomHelper.openUserCardVM.post(it.toString())
                    }
                }
                R.id.ivFollow -> {
                    follow(entity, position)
                }
            }
        }
    }

    override fun requestLazyData() {
        super.requestLazyData()
        loadData(true)
    }

    private fun loadData(isRefresh: Boolean) {
//        if (isRefresh) {
//            page = 1
//            refresh?.setEnableLoadMore(true)
//        }
//        StreamBiz.liveChatroomUsers(liveId, page, 20, anchorId, null, object :
//            RequestListener<LiveChatRoomUserData> {
//            override fun onSuccess(reqId: String?, result: LiveChatRoomUserData?) {
//                if (null != result?.data) {
//                    val list = result.data.list
//                    if (isRefresh) {
//                        LiveRoomHelper.cmMemberTabVM.post(result.data.total)
//                        if (list?.isNotEmpty() == true) {
//                            page += 1
//
//                            if ((flEmpty?.childCount ?: 0) > 0) {
//                                flEmpty?.removeAllViews()
//                            }
//
//                            memberAdapter.setNewInstance(list)
//                        } else {
//                            memberAdapter.setNewInstance(arrayListOf())
//                            completeEmptyDataBy23()
//                        }
//                    } else {
//                        if (list?.isNotEmpty() == true) {
//                            page += 1
//                            memberAdapter.addData(list)
//                        }
//                    }
//
//                    if (result.data.page == result.data.pageTotal) {
//                        refresh?.setEnableLoadMore(false)
//                    }
//                }
//            }
//
//            override fun onFailure(reqId: String?, errCode: String?, msg: String?) {
//                if (memberAdapter.data.isEmpty()) {
//                    LiveRoomHelper.cmMemberTabVM.post(0)
//
//                    completeEmptyDataBy23(false)
//                }
//            }
//        })
    }

    private fun follow(entity: LiveChatRoomUserEntity, position: Int) {
    }

    private fun completeEmptyDataBy23(isSuc: Boolean = true) {
        val emptyView =
            layoutInflater.inflate(
                R.layout.layout_common_empty,
                flEmpty,
                false
            )
        if (null == emptyView.parent) {
            flEmpty?.addView(emptyView)
        } else {
            flEmpty?.removeView(emptyView)
        }

        if (!NetworkUtils.isConnected()) {
            emptyView.findViewById<ImageView>(R.id.ivImage)
                .setImageResource(R.drawable.icon_empty_common_wifi)
            emptyView.findViewById<TextView>(R.id.tvTitle).text =
                getString(R.string.main_tab_empty_wifi_tips)
        } else if (isSuc) {
            emptyView.findViewById<ImageView>(R.id.ivImage)
                .setImageResource(R.drawable.icon_empty_common_zanwu)
            emptyView.findViewById<TextView>(R.id.tvTitle).text =
                getString(R.string.main_tab_empty_zanwu_tips)
        } else {
            emptyView.findViewById<ImageView>(R.id.ivImage)
                .setImageResource(R.drawable.icon_empty_common)
            emptyView.findViewById<TextView>(R.id.tvTitle).text =
                getString(R.string.no_datas)
        }
        emptyView.findViewById<TextView>(R.id.tvJumpHot).visibility = View.GONE
    }
}