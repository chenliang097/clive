package com.rongtuoyouxuan.chatlive.base.view.activity

import android.os.Bundle
import android.provider.ContactsContract.Data
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.rongtuoyouxuan.chatlive.base.view.adapter.FansListAdapter
import com.rongtuoyouxuan.chatlive.base.viewmodel.FansListViewModel
import com.rongtuoyouxuan.chatlive.biz2.model.stream.FansListBean
import com.rongtuoyouxuan.chatlive.biz2.model.stream.RoomManagerListBean
import com.rongtuoyouxuan.chatlive.databus.DataBus
import com.rongtuoyouxuan.chatlive.stream.R
import com.rongtuoyouxuan.chatlive.stream.view.adapter.SetManagerListAdapter
import com.rongtuoyouxuan.chatlive.stream.viewmodel.AnchorManagerBlackListViewModel
import com.rongtuoyouxuan.chatlive.stream.viewmodel.SetManagerListViewModel
import com.rongtuoyouxuan.libuikit.BaseRefreshListFragment
import com.rongtuoyouxuan.libuikit.layout.CommonStatusView
import com.rongtuoyouxuan.libuikit.layout.IStatusView
import com.scwang.smartrefresh.layout.SmartRefreshLayout

class FansListFragment:BaseRefreshListFragment<FansListViewModel, FansListBean>() {

    private var mViewModel: FansListViewModel? = null
    private var userId:String? = ""

    companion object{
        fun newInstance(userId: String?): FansListFragment {
            val args = Bundle()
            args.putString("userId", userId)
            val fragment = FansListFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun initdata() {
        userId = arguments?.get("userId") as String?
    }

    override fun initObserver() {
        super.initObserver()
//        IMSocketImpl.getInstance().chatRoom(streamId).liveOptmicCallback.observe(liveOptmicObserver)
        viewModel.addFollowLiveData.observe(activity as FragmentActivity){
            if(adapter != null){
                it?.data?.position?.let { it1 -> adapter.data.set(it1, it?.data?.followBean) }
                adapter.setList(adapter.data)
            }
        }

        viewModel.cancelFollowLiveData.observe(activity as FragmentActivity){
            if(adapter != null){
                it?.data?.position?.let { it1 -> adapter.data.set(it1, it?.data?.followBean) }
                adapter.setList(adapter.data)
            }
        }
    }

    override fun createStatusView(): IStatusView {
        return CommonStatusView(activity)
    }

    override fun getLayoutRes(): Int {
        return R.layout.qf_stream_fragment_set_manager_list
    }

    override fun createAdapter(): BaseQuickAdapter<FansListBean.ItemBean, BaseViewHolder> {
        var adapter = FansListAdapter(R.layout.qf_stream_adapter_item_fans, 1)
        adapter.addChildClickViewIds(R.id.itemBtn)
        adapter.setOnItemChildClickListener { adapter, view, position ->
            var bean = adapter.data[position] as FansListBean.ItemBean
            when(view.id){
                R.id.itemBtn -> {
                    if(bean.status){
                        bean.status = !bean.status
                        viewModel?.cancelFollow(DataBus.instance().USER_ID, bean.id.toString(), position, bean)
                    }else{
                        bean.status = !bean.status
                        viewModel?.addFollow(DataBus.instance().USER_ID, bean.id.toString(), position, bean)
                    }
                }

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

    override fun createViewModel(): FansListViewModel? {
        userId = arguments?.get("userId") as String?
        mViewModel = ViewModelProviders.of(activity as FragmentActivity).get(
            FansListViewModel::class.java)
        userId?.let { mViewModel?.setToUserId(it) }
        return mViewModel
    }

    override fun createSmartRefreshLayout(): SmartRefreshLayout {
        return mRootView.findViewById<SmartRefreshLayout>(R.id.smartRefreshLayout)
    }
}