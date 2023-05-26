package com.rongtuoyouxuan.chatlive.qfcommon.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.GridLayoutManager
import com.rongtuoyouxuan.chatlive.crtbiz2.model.gif.GifListBean
import com.rongtuoyouxuan.chatlive.crtdatabus.liveeventbus.LiveDataBus
import com.rongtuoyouxuan.chatlive.crtdatabus.liveeventbus.constansts.LiveDataBusConstants
import com.rongtuoyouxuan.chatlive.crtrouter.bean.ISource
import com.rongtuoyouxuan.chatlive.qfcommon.viewmodel.GifPannelViewModel
import com.rongtuoyouxuan.chatlive.qfcommon.widget.adapter.GifPannelAdapter
import com.rongtuoyouxuan.chatlive.stream.R
import com.rongtuoyouxuan.chatlive.crtuikit.layout.decoration.GridSpaceItemDecoration
import kotlinx.android.synthetic.main.layout_gif_pannel.view.*
import com.rongtuoyouxuan.chatlive.crtmagicindicator.buildins.UIUtil

class GifPannelView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) :LinearLayout(context, attrs, defStyleAttr) {

    private var gifPannelViewModel:GifPannelViewModel? = null
    private var toRoomId:String? = ""
    private var fromSource:String? = ""
    private var adapter:GifPannelAdapter? = null

    init {
        View.inflate(getContext(), R.layout.layout_gif_pannel,this)
        initView()
        initListener()
        initObserver()
    }

    fun initView(){
        gifRecyclerView.layoutManager = GridLayoutManager(context, 6)
        gifRecyclerView.addItemDecoration(GridSpaceItemDecoration(6, UIUtil.dip2px(context, 10.0), UIUtil.dip2px(context, 10.0)))
    }

    fun initListener(){
        gifSmartRefreshLayout.setOnRefreshListener {
            gifPannelViewModel?.getGifPannelList(1)

        }

        adapter = GifPannelAdapter(R.layout.qf_stream_adapter_gif_list);
        adapter?.addChildClickViewIds(R.id.gifItemImg)
        adapter?.setOnItemChildClickListener { adapter, view, position ->
            var gifItemBean = adapter.getItem(position) as GifListBean.GifItemBean
            gifItemBean.targetId = toRoomId
            if (fromSource == ISource.FROM_PRIVATE_CHAT || fromSource == ISource.FROM_GROUP_CHAT) {
                LiveDataBus.getInstance()
                    .with(LiveDataBusConstants.EVENT_KEY_TO_SEND_IM_GIF_CLICK).value = gifItemBean
            } else {
                fromSource?.let {
                    gifPannelViewModel?.sendGifMessage(
                        toRoomId, gifItemBean?.gif_id, gifItemBean.gif_url,
                        it
                    )
                }
            }
        }
        adapter?.loadMoreModule?.isEnableLoadMore = false
        gifRecyclerView.adapter = adapter
    }

    fun initObserver(){
        gifPannelViewModel = ViewModelProvider(context as ViewModelStoreOwner).get(GifPannelViewModel::class.java)
        gifPannelViewModel?.gifPannelListLiveData?.observe(context as LifecycleOwner){
            gifSmartRefreshLayout.finishRefresh()
            adapter?.setList(it.listData)
        }
        gifPannelViewModel?.getGifPannelList(1)
    }

    fun setRoomId(sourceId:String, fromSource:String){
        this.toRoomId = sourceId
        this.fromSource = fromSource
    }
}