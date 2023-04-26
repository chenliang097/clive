package com.rongtuoyouxuan.chatlive.stream.view.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rongtuoyouxuan.chatlive.biz2.model.gif.GifListBean
import com.rongtuoyouxuan.chatlive.databus.liveeventbus.LiveDataBus
import com.rongtuoyouxuan.chatlive.databus.liveeventbus.constansts.LiveDataBusConstants
import com.rongtuoyouxuan.chatlive.router.bean.ISource.FROM_GROUP_CHAT
import com.rongtuoyouxuan.chatlive.router.bean.ISource.FROM_PRIVATE_CHAT
import com.rongtuoyouxuan.chatlive.stream.R
import com.rongtuoyouxuan.chatlive.stream.view.adapter.GifListAdapter
import com.rongtuoyouxuan.chatlive.stream.viewmodel.GifListViewModel
import com.rongtuoyouxuan.libuikit.BaseRefreshListFragment
import com.rongtuoyouxuan.libuikit.layout.CommonStatusView
import com.rongtuoyouxuan.libuikit.layout.IStatusView
import com.rongtuoyouxuan.libuikit.layout.decoration.GridSpaceItemDecoration
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import net.lucode.hackware.magicindicator.buildins.UIUtil

class GifListFragment:BaseRefreshListFragment<GifListViewModel, GifListBean>() {

    private var gifListViewModel:GifListViewModel? = null
    private var streamId:String? = ""

    companion object{
        fun newInstance(streamId: String?, fromSource: String?): GifListFragment{
            val args = Bundle()
            args.putString("streamId", streamId)
            args.putString("fromSource", fromSource)
            val fragment = GifListFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun initdata() {
        streamId = arguments?.get("streamId") as String?
    }

    override fun initObserver() {
        super.initObserver()
    }

    override fun createStatusView(): IStatusView {
        return CommonStatusView(activity)
    }

    override fun getLayoutRes(): Int {
        return R.layout.qf_stream_fragment_gif_list
    }

    override fun createAdapter(): BaseQuickAdapter<GifListBean.GifItemBean, BaseViewHolder> {
        var adapter = GifListAdapter(R.layout.qf_stream_adapter_gif_list);
        adapter.addChildClickViewIds(R.id.gifItemImg)
        var fromSource = arguments?.get("fromSource") as String
        adapter.setOnItemChildClickListener(object :OnItemChildClickListener{

            override fun onItemChildClick(
                adapter: BaseQuickAdapter<*, *>,
                view: View,
                position: Int
            ) {
                var gifItemBean = adapter.getItem(position) as GifListBean.GifItemBean
                gifItemBean.targetId = streamId
                if (fromSource == FROM_PRIVATE_CHAT || fromSource == FROM_GROUP_CHAT){
                    LiveDataBus.getInstance().with(LiveDataBusConstants.EVENT_KEY_TO_SEND_IM_GIF_CLICK).value = gifItemBean
                }else{
                    gifListViewModel?.sendGifMessage(streamId, gifItemBean?.gif_id, gifItemBean.gif_url, fromSource)
                }
            }

        })
        adapter.loadMoreModule.isEnableLoadMore = false
        return adapter
    }

    override fun createRecyclerView(): RecyclerView {
        var recyclerView = mRootView.findViewById<RecyclerView>(R.id.gifRecyclerView)
        recyclerView.addItemDecoration(GridSpaceItemDecoration(6, UIUtil.dip2px(context, 10.0), UIUtil.dip2px(context, 10.0)))
        return recyclerView
    }

    override fun createLayoutManager(): RecyclerView.LayoutManager {
//        var gridLayoutManager:GridLayoutManager = GridLayoutManager(activity, 6)
        return GridLayoutManager(activity, 6)
    }

    override fun createViewModel(): GifListViewModel? {
        gifListViewModel = ViewModelProviders.of(activity as FragmentActivity).get(GifListViewModel::class.java)
        return gifListViewModel
    }

    override fun createSmartRefreshLayout(): SmartRefreshLayout {
        return mRootView.findViewById<SmartRefreshLayout>(R.id.gifSmartRefreshLayout)
    }
}