package com.rongtuoyouxuan.chatlive.live.view.activity

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.NetworkUtils
import com.rongtuoyouxuan.chatlive.base.utils.LiveRoomHelper
import com.rongtuoyouxuan.chatlive.biz2.model.stream.CmMultiEntity
import com.rongtuoyouxuan.chatlive.biz2.model.stream.LiveAudienceRankEntity
import com.rongtuoyouxuan.chatlive.biz2.stream.StreamBiz
import com.rongtuoyouxuan.chatlive.databus.DataBus
import com.rongtuoyouxuan.chatlive.databus.liveeventbus.LiveDataBus
import com.rongtuoyouxuan.chatlive.databus.liveeventbus.constansts.LiveDataBusConstants
import com.rongtuoyouxuan.chatlive.live.view.adapter.ContributionAdapter
import com.rongtuoyouxuan.chatlive.live.viewmodel.CmVM
import com.rongtuoyouxuan.chatlive.net2.BaseModel
import com.rongtuoyouxuan.chatlive.net2.RequestListener
import com.rongtuoyouxuan.chatlive.stream.R
import com.rongtuoyouxuan.chatlive.util.BigDecimalUtil
import com.rongtuoyouxuan.libuikit.SimpleFragment
import kotlinx.android.synthetic.main.fragment_contribution_member.*

/**
 * 
 * date:2022/8/9-19:28
 * des: 贡献榜
 */
class ContributionFragment : SimpleFragment() {
    private var position = 0
    private var anchorId: Long = 0
    private var nextKey = ""
    private var contributionAdapter: ContributionAdapter? = null
    private var vm: CmVM? = null

    private var loginUID = 0L

    private var isLoadedData = false

    companion object {
        fun newInstance(position: Int, anchorId: Long, userId: Long) =
            ContributionFragment().apply {
                arguments = Bundle().apply {
                    putInt("position", position)
                    putLong("anchorId", anchorId)
                    putLong("userId", userId)
                }
            }
    }

    override fun getLayoutResId(): Int = R.layout.fragment_contribution_member

    override fun initData() {
        vm = getViewModel(CmVM::class.java)
        position = arguments?.getInt("position", 0) ?: 0
        anchorId = arguments?.getLong("anchorId", 0) ?: 0
        val uid = arguments?.getLong("userId", 0) ?: 0

        loginUID = DataBus.instance().userInfo.value?.user_info?.userId ?: 0L
        tvDiamondTip?.text = when (position) {
            0 -> {
                getString(R.string.live_cm_contribution_income_day)
            }
            1 -> {
                getString(R.string.live_cm_contribution_income_month)
            }
            else -> {
                getString(R.string.live_cm_contribution_income_total)
            }
        }

        refresh?.apply {
            setEnableRefresh(true)
            setEnableLoadMore(true)
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

            contributionAdapter = ContributionAdapter(context, { entity ->
                entity?.userId?.let {
                    LiveRoomHelper.cmDismissVM.post(1)
                    LiveRoomHelper.openUserCardVM.post(it)
                }
            }, { entity, position ->
                if (null != entity) {
                    follow(entity, position, 4)
                }
            })
            contributionAdapter?.loginUID = uid
            contributionAdapter?.setHasStableIds(true)
            adapter = contributionAdapter
        }
    }

    override fun initListener() {
        vm?.contributionVM?.observe(this) {
            val allList = arrayListOf<CmMultiEntity>()

            val list = it.list
//            list.add(LiveAudienceRankEntity(diamondAmount = 100f, nickname = "123123"))
            if (it.isRefresh) {
                tvDiamond?.text = BigDecimalUtil.decimalFormat3(it.totalDiamond ?: 0f)
                if (list?.isNotEmpty() == true) {
                    if ((flEmpty?.childCount ?: 0) > 0) {
                        flEmpty?.removeAllViews()
                    }
                    val topList = arrayListOf<LiveAudienceRankEntity>()
                    list.forEachIndexed { index, item ->
                        if (null != item) {
                            if (index == 0 || index == 1 || index == 2) {
                                topList.add(item)
                            } else {
                                allList.add(CmMultiEntity(CmMultiEntity.item_content).apply {
                                    this.liveItemEntity = item
                                })
                            }
                        }
                    }
                    allList.add(0, CmMultiEntity(CmMultiEntity.item_top).apply {
                        this.topList.addAll(topList)
                    })

                    contributionAdapter?.setList(allList)
                } else {
                    vm?.errorVM?.postValue("")
                }
            } else {
                if (list?.isNotEmpty() == true) {
                    list.forEachIndexed { _, item ->
                        if (null != item) {
                            allList.add(CmMultiEntity(CmMultiEntity.item_content).apply {
                                this.liveItemEntity = item
                            })
                        }
                    }
                    contributionAdapter?.addData(allList)
                }
            }
            nextKey = it.nextKey ?: ""
        }

        vm?.errorVM?.observe(this) {
            if (contributionAdapter?.data?.isEmpty() == true) {
                completeEmptyDataBy23()
            }
        }

        contributionAdapter?.clickTop = object : ContributionAdapter.IClickTop {
            override fun click(uid: Long) {
                LiveRoomHelper.cmDismissVM.post(1)
                LiveRoomHelper.openUserCardVM.post(uid)
            }

            override fun clickFollow(position: Int, ranking: Int, item: LiveAudienceRankEntity) {
                follow(item, position, ranking)
            }
        }
    }

    override fun requestLazyData() {
        super.requestLazyData()
        if (isLoadedData) {
            return
        }
        isLoadedData = true
        loadData(true)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            requestLazyData()
        }
    }

    private fun loadData(isRefresh: Boolean) {
        if (isRefresh) {
            nextKey = ""
        }
        vm?.getData(position, anchorId, nextKey, isRefresh)
    }

    private fun follow(entity: LiveAudienceRankEntity, position: Int, ranking: Int) {
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