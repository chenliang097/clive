package com.rongtuoyouxuan.chatlive.base.view.activity

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.rongtuoyouxuan.chatlive.base.view.adapter.PopularityRankAdapter
import com.rongtuoyouxuan.chatlive.base.viewmodel.PopularityRankViewModel
import com.rongtuoyouxuan.chatlive.biz2.model.stream.PopolarityRankBean
import com.rongtuoyouxuan.chatlive.biz2.model.stream.RoomManagerListBean
import com.rongtuoyouxuan.chatlive.image.util.GlideUtils
import com.rongtuoyouxuan.chatlive.stream.R
import com.rongtuoyouxuan.chatlive.stream.view.adapter.SetManagerListAdapter
import com.rongtuoyouxuan.chatlive.stream.viewmodel.SetManagerListViewModel
import com.rongtuoyouxuan.libuikit.BaseRefreshListFragment
import com.rongtuoyouxuan.libuikit.layout.CommonStatusView
import com.rongtuoyouxuan.libuikit.layout.IStatusView
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import kotlinx.android.synthetic.main.qf_stream_fragment_set_manager_list.*

class PopolarityRankFragment:BaseRefreshListFragment<PopularityRankViewModel, PopolarityRankBean>() {

    private var popularityRankViewModel: PopularityRankViewModel? = null
    private var roomId:String? = ""

    companion object{
        fun newInstance(roomId: String?): PopolarityRankFragment {
            val args = Bundle()
            args.putString("roomId", roomId)
            val fragment = PopolarityRankFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun initdata() {
        roomId = arguments?.get("roomId") as String?
        viewModel.rankLiveData.observe(this){
            if(it.errCode == 0){
                updateSelf(it)
            }
        }
    }
    override fun createStatusView(): IStatusView {
        return CommonStatusView(activity)
    }

    override fun getLayoutRes(): Int {
        return R.layout.qf_stream_fragment_set_manager_list
    }

    override fun createAdapter(): BaseQuickAdapter<PopolarityRankBean.ItemBean, BaseViewHolder> {
        var adapter = PopularityRankAdapter(R.layout.qf_stream_adapter_item_popularity, 1);

        return adapter
    }

    override fun createRecyclerView(): RecyclerView {
        return mRootView.findViewById(R.id.recyclerView)
    }

    override fun createLayoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(activity)
    }

    override fun createViewModel(): PopularityRankViewModel? {
        roomId = arguments?.get("roomId") as String?
        popularityRankViewModel = ViewModelProviders.of(activity as FragmentActivity).get(
            PopularityRankViewModel::class.java)
        roomId?.let { popularityRankViewModel?.setRoomId(it) }
        return popularityRankViewModel
    }

    override fun createSmartRefreshLayout(): SmartRefreshLayout {
        return mRootView.findViewById<SmartRefreshLayout>(R.id.smartRefreshLayout)
    }

    fun updateSelf(it:PopolarityRankBean){
        if(it.data?.self_rank == null || it.data?.self_rank?.rank ==0){
            return
        }
        setManagerBottomLayout.visibility = View.VISIBLE
        when(it.data?.self_rank?.rank) {
            1 -> {
                itemRankImg.visibility = View.VISIBLE
                itemRankTxt.visibility = View.GONE
                itemRankImg.setImageResource(R.drawable.rt_icon_rank_one)
            }
            2 -> {
                itemRankImg.visibility = View.VISIBLE
                itemRankTxt.visibility = View.GONE
                itemRankImg.setImageResource(R.drawable.rt_icon_rank_two)
            }
            3 -> {
                itemRankImg.visibility = View.VISIBLE
                itemRankTxt.visibility = View.GONE
                itemRankImg.setImageResource(R.drawable.rt_icon_rank_three)
            }
            else -> {
                itemRankImg.visibility = View.GONE
                itemRankTxt.visibility = View.VISIBLE
                if(it.data?.self_rank?.rank!! > 100){
                    var rank = it.data?.self_rank?.rank
                    itemRankTxt.text = "100+"
                }else {
                    itemRankTxt.text = "" + it.data?.self_rank?.rank
                }
            }
        }
        GlideUtils.loadImage(context, it.data?.self_rank?.avatar, itemAvatar, R.drawable.rt_default_avatar)
        itemNickName.text = it.data?.self_rank?.nick_name
        itemNumTxt.text = "" + it.data?.self_rank?.degree
    }
}