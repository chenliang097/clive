package com.rongtuoyouxuan.chatlive.base.view.activity

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.rongtuoyouxuan.chatlive.base.view.adapter.FollowListAdapter
import com.rongtuoyouxuan.chatlive.base.viewmodel.FollowListViewModel
import com.rongtuoyouxuan.chatlive.crtbiz2.model.stream.FansListBean
import com.rongtuoyouxuan.chatlive.crtbiz2.model.stream.FollowListBean
import com.rongtuoyouxuan.chatlive.crtdatabus.DataBus
import com.rongtuoyouxuan.chatlive.stream.R
import com.rongtuoyouxuan.chatlive.crtuikit.BaseRefreshListFragment
import com.rongtuoyouxuan.chatlive.crtuikit.layout.CommonStatusView
import com.rongtuoyouxuan.chatlive.crtuikit.layout.IStatusView
import com.scwang.smartrefresh.layout.SmartRefreshLayout

class FollowListFragment: BaseRefreshListFragment<FollowListViewModel, FollowListBean>() {

    private var mViewModel: FollowListViewModel? = null
    private var userId:String? = ""

    companion object{
        fun newInstance(userId: String?): FollowListFragment {
            val args = Bundle()
            args.putString("userId", userId)
            val fragment = FollowListFragment()
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
                it?.data?.position?.let { it1 -> adapter.notifyItemChanged(it1) }
            }
        }

        viewModel.cancelFollowLiveData.observe(activity as FragmentActivity){
            if(adapter != null){
                it?.data?.position?.let { it1 -> adapter.data.set(it1, it?.data?.followBean) }
                it?.data?.position?.let { it1 -> adapter.notifyItemChanged(it1) }
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
        userId = arguments?.get("userId") as String?
        var adapter = FollowListAdapter(R.layout.qf_stream_adapter_item_fans, 1, userId)
        adapter.addChildClickViewIds(R.id.itemBtn)
        adapter.setOnItemChildClickListener { adapter, view, position ->
            var bean = adapter.data[position] as FansListBean.ItemBean
            when(view.id){
                R.id.itemBtn -> {
                    if(bean.status){
                        if(DataBus.instance().USER_ID != userId) {
                            bean.status = !bean.status
                        }
                        if(bean.isFollow){
                            viewModel?.cancelFollow(DataBus.instance().USER_ID, bean.id.toString(), position, bean)
                        }else{
                            viewModel?.addFollow(DataBus.instance().USER_ID, bean.id.toString(), position, bean)
                        }
                    }else{
                        if(DataBus.instance().USER_ID != userId) {
                            bean.status = !bean.status
                        }
                        if(bean.isFollow){
                            viewModel?.cancelFollow(DataBus.instance().USER_ID, bean.id.toString(), position, bean)
                        }else{
                            viewModel?.addFollow(DataBus.instance().USER_ID, bean.id.toString(), position, bean)
                        }
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

    override fun createViewModel(): FollowListViewModel? {
        userId = arguments?.get("userId") as String?
        mViewModel = ViewModelProviders.of(activity as FragmentActivity).get(
            FollowListViewModel::class.java)
        userId?.let { mViewModel?.setToUserId(it) }
        return mViewModel
    }

    override fun createSmartRefreshLayout(): SmartRefreshLayout {
        return mRootView.findViewById<SmartRefreshLayout>(R.id.smartRefreshLayout)
    }
}