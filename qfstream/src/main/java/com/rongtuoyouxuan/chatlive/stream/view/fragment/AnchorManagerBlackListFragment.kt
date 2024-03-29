package com.rongtuoyouxuan.chatlive.stream.view.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.rongtuoyouxuan.chatlive.crtbiz2.model.stream.RoomManagerBlackListBean
import com.rongtuoyouxuan.chatlive.crtcommonbiz.model.list.LoadEvent
import com.rongtuoyouxuan.chatlive.crtdatabus.DataBus
import com.rongtuoyouxuan.chatlive.stream.R
import com.rongtuoyouxuan.chatlive.stream.view.adapter.BlackListAdapter
import com.rongtuoyouxuan.chatlive.stream.viewmodel.AnchorManagerBlackListViewModel
import com.rongtuoyouxuan.chatlive.crtuikit.BaseRefreshListFragment
import com.rongtuoyouxuan.chatlive.crtuikit.layout.CommonStatusView
import com.rongtuoyouxuan.chatlive.crtuikit.layout.IStatusView
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import kotlinx.android.synthetic.main.qf_stream_fragment_set_manager_list.*

class AnchorManagerBlackListFragment:
    BaseRefreshListFragment<AnchorManagerBlackListViewModel, RoomManagerBlackListBean>() {

    private var mViewModel: AnchorManagerBlackListViewModel? = null
    private var roomId:String? = ""
    private var sceneId:String? = ""

    companion object{
        fun newInstance(roomId: String?, sceneId: String?): AnchorManagerBlackListFragment {
            val args = Bundle()
            args.putString("roomId", roomId)
            args.putString("sceneId", sceneId)
            val fragment = AnchorManagerBlackListFragment()
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

    override fun createStatusView(): IStatusView {
        return CommonStatusView(activity)
    }

    override fun getLayoutRes(): Int {
        return R.layout.qf_stream_fragment_set_manager_list
    }

    override fun createAdapter(): BaseQuickAdapter<RoomManagerBlackListBean.ItemBean, BaseViewHolder> {
        var adapter = BlackListAdapter(R.layout.qf_stream_adapter_item_set_manager, 1);
        adapter.addChildClickViewIds(R.id.itemBtn)
        adapter.setOnItemChildClickListener { adapter, view, position ->
            var bean = adapter.data[position] as RoomManagerBlackListBean.ItemBean
            when(view.id){
                R.id.itemBtn -> viewModel?.deleteRoomBlack(DataBus.instance().USER_ID, bean.user_id, DataBus.instance().USER_NAME, bean.nick_name, position)

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

    override fun createViewModel(): AnchorManagerBlackListViewModel? {
        roomId = arguments?.get("roomId") as String?
        mViewModel = ViewModelProviders.of(activity as FragmentActivity).get(
            AnchorManagerBlackListViewModel::class.java)
        roomId?.let { mViewModel?.setRoomId(it) }
        return mViewModel
    }

    override fun createSmartRefreshLayout(): SmartRefreshLayout {
        return mRootView.findViewById<SmartRefreshLayout>(R.id.smartRefreshLayout)
    }
}