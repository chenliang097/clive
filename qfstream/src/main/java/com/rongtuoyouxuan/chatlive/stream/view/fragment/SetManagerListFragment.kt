package com.rongtuoyouxuan.chatlive.stream.view.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.rongtuoyouxuan.chatlive.biz2.model.list.LoadEvent
import com.rongtuoyouxuan.chatlive.biz2.model.stream.RoomManagerListBean
import com.rongtuoyouxuan.chatlive.stream.R
import com.rongtuoyouxuan.chatlive.stream.view.adapter.SetManagerListAdapter
import com.rongtuoyouxuan.chatlive.stream.viewmodel.SetManagerListViewModel
import com.rongtuoyouxuan.libuikit.BaseRefreshListFragment
import com.rongtuoyouxuan.libuikit.layout.CommonStatusView
import com.rongtuoyouxuan.libuikit.layout.IStatusView
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import kotlinx.android.synthetic.main.qf_stream_fragment_set_manager_list.*

class SetManagerListFragment:BaseRefreshListFragment<SetManagerListViewModel, RoomManagerListBean>() {

    private var setManagerListViewModel: SetManagerListViewModel? = null
    private var roomId:String? = ""

    companion object{
        fun newInstance(roomId: String?): SetManagerListFragment {
            val args = Bundle()
            args.putString("roomId", roomId)
            val fragment = SetManagerListFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun initdata() {
        roomId = arguments?.get("roomId") as String?
        viewModel.data.observe(this) { it ->
            if(it.event == LoadEvent.EVENT_LOAD_INIT_OR_RETRY) {
                setManagerInsTxt.visibility = View.VISIBLE
                setManagerInsTxt.text = (context?.resources?.getString(
                    R.string.stream_anchor_manager_set_manager_ins,
                    it.data?.total
                ))
            }
        }
    }

    override fun initObserver() {
        super.initObserver()
//        IMSocketImpl.getInstance().chatRoom(streamId).liveOptmicCallback.observe(liveOptmicObserver)
        viewModel.delManagerLiveData.observe(activity as FragmentActivity){
            if(adapter != null){
                adapter.remove(position = it)
                setManagerInsTxt.text = (context?.resources?.getString(
                    R.string.stream_anchor_manager_set_manager_ins,
                    adapter.data.size
                ))
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

    override fun createAdapter(): BaseQuickAdapter<RoomManagerListBean.ItemBean, BaseViewHolder> {
        var adapter = SetManagerListAdapter(R.layout.qf_stream_adapter_item_set_manager, 1);
        adapter.addChildClickViewIds(R.id.itemBtn)
        adapter.setOnItemChildClickListener { adapter, view, position ->
            var bean = adapter.data[position] as RoomManagerListBean.ItemBean
            when(view.id){
                R.id.itemBtn -> bean.user_id?.let { viewModel.deleteRoomManagerList(it, position) }

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

    override fun createViewModel(): SetManagerListViewModel? {
        roomId = arguments?.get("roomId") as String?
        setManagerListViewModel = ViewModelProviders.of(activity as FragmentActivity).get(
            SetManagerListViewModel::class.java)
        roomId?.let { setManagerListViewModel?.setRoomId(it) }
        return setManagerListViewModel
    }

    override fun createSmartRefreshLayout(): SmartRefreshLayout {
        return mRootView.findViewById<SmartRefreshLayout>(R.id.smartRefreshLayout)
    }
}