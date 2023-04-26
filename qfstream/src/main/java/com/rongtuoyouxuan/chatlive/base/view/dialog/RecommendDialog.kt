package com.rongtuoyouxuan.chatlive.base.view.dialog

import android.annotation.SuppressLint
import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.LogUtils
import com.rongtuoyouxuan.chatlive.base.view.adapter.RecommendAdapter
import com.rongtuoyouxuan.chatlive.base.view.viewmodel.RecommendViewModel
import com.rongtuoyouxuan.chatlive.biz2.model.main.LiveItemEntity
import com.rongtuoyouxuan.chatlive.router.Router
import com.rongtuoyouxuan.chatlive.router.bean.ISource
import com.rongtuoyouxuan.chatlive.stream.R
import com.rongtuoyouxuan.chatlive.util.LaToastUtil
import com.rongtuoyouxuan.qfcommon.util.LaViewModelUtil
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.DrawerPopupView
import com.lxj.xpopup.enums.PopupPosition
import kotlinx.android.synthetic.main.layout_recomend.view.*

/*
*Create by {Mr秦} on 2023/1/28
*/
@SuppressLint("ViewConstructor")
class RecommendDialog(context: Context, val liveId: String?) : DrawerPopupView(context) {
    private lateinit var recommendViewModel: RecommendViewModel
    private lateinit var adapter: RecommendAdapter
    private var page = 1
    private var pageTotal: Int = 1 //页数

    override fun getImplLayoutId(): Int {
        return R.layout.layout_recomend
    }

    override fun onCreate() {
        initView()
        initListener()
        requestServer()
    }

    private fun initView() {
        recommendViewModel = LaViewModelUtil.get(ActivityUtils.getTopActivity() as FragmentActivity,
            RecommendViewModel::class.java)
        recommendViewModel.liveVM = MutableLiveData()
        recommendViewModel.liveErrorVM = MutableLiveData()


        recy.layoutManager = LinearLayoutManager(context)
        adapter = RecommendAdapter()
        recy.adapter = adapter
    }

    private fun initListener() {

        recommendViewModel.liveVM.observe(ActivityUtils.getTopActivity() as FragmentActivity) {
            LogUtils.e(" liveVM:)")
            val lives = it.data.lives
            if (lives == null) {
                if (page == 1) adapter.setEmptyView(R.layout.empty_recommend)
                return@observe
            }
            if (page == 1) adapter.data.clear()
            //设置总页码
            pageTotal = it.data.total ?: 1


            lives.forEach { result ->
                result.live?.isSelected = result.live?.id == liveId
            }
            adapter.addData(lives)
            refresh.finishRefresh()
            refresh.finishLoadMore()
        }

        recommendViewModel.liveErrorVM.observe(ActivityUtils.getTopActivity() as FragmentActivity) {
            LaToastUtil.showShort(it)
            refresh.finishRefresh()
            refresh.finishLoadMore()
        }

        refresh.setOnRefreshListener {
            page = 1
            requestServer()
        }

        refresh.setOnLoadMoreListener {
            if (page >= pageTotal) {
                refresh.finishLoadMoreWithNoMoreData()
                return@setOnLoadMoreListener
            }
            page++
            requestServer()
        }

        adapter.setOnItemClickListener { _, _, position ->
            val item = adapter.data[position]
            val list = arrayListOf<LiveItemEntity>()
            adapter.data.forEach {
                it.live?.let { result ->
                    list.add(result)
                }
            }
            val liveId = item.live?.id ?: 0L
            val anchorId = item.live?.anchorId ?: 0L
            val dataGson = GsonUtils.toJson(list)
            Router.toLiveRoomActivity(
                dataGson, "$liveId", "$anchorId", ISource.LIST_RECENT, false
            )
        }
    }

    private fun requestServer() {
        recommendViewModel.liveHot(lifecycleScope, page)
    }

    companion object {
        fun showDialog(
            context: Context,
            liveId: String,
        ) {
            XPopup.Builder(context)
                .dismissOnTouchOutside(true)
                .dismissOnBackPressed(true)
                .popupPosition(PopupPosition.Right)
                .hasNavigationBar(false)
                .hasShadowBg(false)
                .hasStatusBar(false)
                .isViewMode(true)
                .asCustom(RecommendDialog(context, liveId))
                .show()
        }
    }
}
