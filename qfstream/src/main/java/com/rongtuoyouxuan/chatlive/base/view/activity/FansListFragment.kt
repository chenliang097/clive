package com.rongtuoyouxuan.chatlive.base.view.activity

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.StringUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.rongtuoyouxuan.chatlive.base.view.adapter.FansListAdapter
import com.rongtuoyouxuan.chatlive.base.viewmodel.FansListViewModel
import com.rongtuoyouxuan.chatlive.crtbiz2.model.stream.FansListBean
import com.rongtuoyouxuan.chatlive.crtdatabus.DataBus
import com.rongtuoyouxuan.chatlive.stream.R
import com.rongtuoyouxuan.chatlive.crtuikit.BaseRefreshListFragment
import com.rongtuoyouxuan.chatlive.crtuikit.layout.CommonStatusView
import com.rongtuoyouxuan.chatlive.crtuikit.layout.IStatusView
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.rongtuoyouxuan.chatlive.crtmatisse.dialog.DiySystemDialog

class FansListFragment: BaseRefreshListFragment<FansListViewModel, FansListBean>() {

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

        viewModel.removeFansLiveData.observe(activity as FragmentActivity){
            if(adapter != null){
                it?.data?.position?.let { it1 -> adapter.remove(position = it1) }
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

    override fun createAdapter(): BaseQuickAdapter<FansListBean.ItemBean, BaseViewHolder> {
        userId = arguments?.get("userId") as String?
        var adapter = FansListAdapter(R.layout.qf_stream_adapter_item_fans, 1, userId)
        adapter.addChildClickViewIds(R.id.itemBtn)
        adapter.addChildClickViewIds(R.id.itemRemoveImg)
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
                R.id.itemRemoveImg ->{
                    showRemoveFansDialog(bean, position)
                }

            }
        }
        return adapter
    }

    fun showRemoveFansDialog(bean:FansListBean.ItemBean, position:Int){
        DiySystemDialog.Builder(context)
            .setTitle(context?.resources?.getString(R.string.stream_fans_remove_tip_title))
            .setMessage(context?.resources?.getString(R.string.stream_fans_remove_tip_content))
            .setNegativeButton(R.string.cancel,
                DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })
            .setPositiveButton(
                StringUtils.getString(R.string.login_ok),
                DialogInterface.OnClickListener { dialog, which ->
                    viewModel?.removeFans(bean.id.toString(), DataBus.instance().USER_ID, position, bean)
                    dialog.dismiss()
                })
            .create().show()
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