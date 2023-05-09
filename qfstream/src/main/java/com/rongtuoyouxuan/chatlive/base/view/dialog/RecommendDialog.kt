package com.rongtuoyouxuan.chatlive.base.view.dialog

import android.annotation.SuppressLint
import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SPUtils
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
import com.rongtuoyouxuan.chatlive.biz2.model.stream.LiveRoomListBean
import com.rongtuoyouxuan.chatlive.biz2.model.stream.RecommenListRequestBean
import com.rongtuoyouxuan.chatlive.databus.DataBus
import kotlinx.android.synthetic.main.layout_recomend.view.*

@SuppressLint("ViewConstructor")
class RecommendDialog(context: Context, var base64SceneIds: String?) : DrawerPopupView(context) {
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
        requestServer(1)
    }

    private fun initView() {
        recommendViewModel = LaViewModelUtil.get(ActivityUtils.getTopActivity() as FragmentActivity,
            RecommendViewModel::class.java)
        recommendViewModel.liveVM = MutableLiveData()
        recommendViewModel.liveErrorVM = MutableLiveData()


        recy.layoutManager = GridLayoutManager(context, 2)
        adapter = RecommendAdapter()
        recy.adapter = adapter
    }

    private fun initListener() {
        recommendViewModel.liveVM.observe(ActivityUtils.getTopActivity() as FragmentActivity) {
            LogUtils.e(" liveVM:)")
            val lives = it.data.rooms_info
            SPUtils.getInstance().put("recomond_base_scene_id", it.data.base_64_scene_ids)
            if (lives == null) {
                if (page == 1) adapter.setEmptyView(R.layout.empty_recommend)
                return@observe
            }
            if (page == 1) adapter.data.clear()
            //设置总页码
            pageTotal = it.data.total ?: 1

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
            requestServer(1)
        }

        refresh.setOnLoadMoreListener {
//            if (page >= pageTotal) {
//                refresh.finishLoadMoreWithNoMoreData()
//                return@setOnLoadMoreListener
//            }
            page++
            requestServer(2)
        }

        adapter.setOnItemClickListener { _, _, position ->
            val item = adapter.data[position]
            val list = arrayListOf<LiveRoomListBean.RoomItemBean>()
            adapter.data.forEach {
                it?.let { result ->
                    var roomItemBean = LiveRoomListBean.RoomItemBean()
                    roomItemBean.stream_id = result.streamId.toString()
                    roomItemBean.room_id = result.roomId!!
                    roomItemBean.room_id_str = result.roomIdStr.toString()
                    roomItemBean.scene_id = result.sceneId!!
                    roomItemBean.scene_id_str = result.sceneIdStr.toString()
                    roomItemBean.anchor_id = result.anchorId.toString()
                    roomItemBean.anchor_name = result.anchorName.toString()
                    roomItemBean.anchor_avatar = result.avatar.toString()
                    list.add(roomItemBean)
                }
            }
            val dataGson = GsonUtils.toJson(list)
            Router.toLiveRoomActivity(dataGson, "${item.roomIdStr}", "${item.streamId}", "${item.sceneIdStr}", "${item.anchorId}", ISource.FROM_LIVE_ROOM)
        }
    }

    private fun requestServer(type:Int) {
        base64SceneIds = if(type == 1){
            ""
        }else{
            SPUtils.getInstance().getString("recomond_base_scene_id")
        }
        var request = base64SceneIds?.let { RecommenListRequestBean(it, DataBus.instance().USER_ID) }
        if (request != null) {
            recommendViewModel?.liveHot(lifecycleScope, request, page)
        }
    }

    companion object {
        fun showDialog(
            context: Context
        ) {
            XPopup.Builder(context)
                .dismissOnTouchOutside(true)
                .dismissOnBackPressed(true)
                .popupPosition(PopupPosition.Right)
                .hasNavigationBar(false)
                .hasShadowBg(false)
                .hasStatusBar(false)
                .isViewMode(true)
                .asCustom(RecommendDialog(context,""))
                .show()
        }
    }
}
