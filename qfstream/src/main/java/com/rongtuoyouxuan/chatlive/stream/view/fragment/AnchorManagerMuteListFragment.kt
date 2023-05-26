package com.rongtuoyouxuan.chatlive.stream.view.fragment

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.rongtuoyouxuan.chatlive.crtbiz2.model.stream.RoomManagerMuteListBean
import com.rongtuoyouxuan.chatlive.crtdatabus.DataBus
import com.rongtuoyouxuan.chatlive.stream.R
import com.rongtuoyouxuan.chatlive.stream.view.adapter.MuteListAdapter
import com.rongtuoyouxuan.chatlive.stream.viewmodel.AnchorManagerMuteListViewModel
import com.rongtuoyouxuan.chatlive.crtuikit.BaseRefreshListFragment
import com.rongtuoyouxuan.chatlive.crtuikit.layout.CommonStatusView
import com.rongtuoyouxuan.chatlive.crtuikit.layout.IStatusView
import com.scwang.smartrefresh.layout.SmartRefreshLayout

class AnchorManagerMuteListFragment:
    BaseRefreshListFragment<AnchorManagerMuteListViewModel, RoomManagerMuteListBean>() {

    private var mViewModel: AnchorManagerMuteListViewModel? = null
    private var roomId:String? = ""
    private var sceneId:String? = ""

    companion object{
        fun newInstance(roomId: String?, sceneId: String?): AnchorManagerMuteListFragment {
            val args = Bundle()
            args.putString("roomId", roomId)
            args.putString("sceneId", sceneId)
            val fragment = AnchorManagerMuteListFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun initdata() {
        roomId = arguments?.get("roomId") as String?
        sceneId = arguments?.get("sceneId") as String?
    }

    override fun initObserver() {
        super.initObserver()
//        IMSocketImpl.getInstance().chatRoom(streamId).liveOptmicCallback.observe(liveOptmicObserver)
        viewModel.delManagerLiveData.observe(activity as FragmentActivity){
            if(adapter != null){
                adapter.remove(position = it)
                if(adapter.data.size == 0){
                    showEmpty()
                }
            }
        }
    }

    override fun createStatusView(): IStatusView {
        return CommonStatusView(activity)
    }

    override fun getLayoutRes(): Int {
        return R.layout.qf_stream_fragment_set_manager_list
    }

    override fun createAdapter(): BaseQuickAdapter<RoomManagerMuteListBean.ItemBean, BaseViewHolder> {
        var adapter = MuteListAdapter(R.layout.qf_stream_adapter_item_set_manager, 1);
        adapter.addChildClickViewIds(R.id.itemBtn)
        adapter.setOnItemChildClickListener { adapter, view, position ->
            var bean = adapter.data[position] as RoomManagerMuteListBean.ItemBean
            when(view.id){
                R.id.itemBtn -> viewModel?.deleteRoomMute(DataBus.instance().USER_ID, bean.user_id, position)

            }
        }
        return adapter
    }

    override fun createRecyclerView(): RecyclerView {
        return mRootView.findViewById(R.id.recyclerView)
    }

    override fun createLayoutManager(): RecyclerView.LayoutManager {
//        var gridLayoutManager:GridLayoutManager = GridLayoutManager(activity, 6)
        return LinearLayoutManager(activity)
    }

    override fun createViewModel(): AnchorManagerMuteListViewModel? {
        roomId = arguments?.get("roomId") as String?
        sceneId = arguments?.get("sceneId") as String?
        mViewModel = ViewModelProviders.of(activity as FragmentActivity).get(
            AnchorManagerMuteListViewModel::class.java)
        roomId?.let { mViewModel?.setRoomId(it) }
        sceneId?.let { mViewModel?.setSceneId(it) }
        return mViewModel
    }

    override fun createSmartRefreshLayout(): SmartRefreshLayout {
        return mRootView.findViewById<SmartRefreshLayout>(R.id.smartRefreshLayout)
    }
}