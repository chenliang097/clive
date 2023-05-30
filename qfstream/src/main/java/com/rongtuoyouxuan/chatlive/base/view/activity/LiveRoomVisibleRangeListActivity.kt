package com.rongtuoyouxuan.chatlive.base.view.activity

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.SPUtils
import com.rongtuoyouxuan.chatlive.base.utils.ViewModelUtils
import com.rongtuoyouxuan.chatlive.base.view.adapter.LiveRoomVisibleRangeAdapter
import com.rongtuoyouxuan.chatlive.base.view.adapter.LiveRoomVisibleRangeAdapter.OnSelectContactsListener
import com.rongtuoyouxuan.chatlive.base.viewmodel.LiveRoomVisibleRangeListViewModel
import com.rongtuoyouxuan.chatlive.crtbiz2.model.stream.LiveRoomVisibleRangeListBean.FansItemBean
import com.rongtuoyouxuan.chatlive.crtdatabus.DataBus
import com.rongtuoyouxuan.chatlive.crtrouter.constants.RouterConstant
import com.rongtuoyouxuan.chatlive.stream.R
import com.rongtuoyouxuan.chatlive.crtutil.util.LaToastUtil
import com.rongtuoyouxuan.chatlive.crtuikit.LanguageActivity
import com.rongtuoyouxuan.chatlive.crtutil.sp.SPConstants
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.android.synthetic.main.rt_libstream_select_common_title.*
import kotlinx.android.synthetic.main.rt_stream_select_contacts_activity.*

@Route(path = RouterConstant.PATH_START_LIVE_VISIBLE_RANGE_list)
class LiveRoomVisibleRangeListActivity : LanguageActivity(), View.OnClickListener {
    private var imgBack: ImageView? = null
    private var txtTitle: TextView? = null
    private var mSmartRefreshLayout: SmartRefreshLayout? = null
    private var mRecyclerView: RecyclerView? = null
    private var noDataLayout: LinearLayout? = null
    private val noDataImg: ImageView? = null
    private var noDataTxt: TextView? = null
    private var mViewModel: LiveRoomVisibleRangeListViewModel? = null
    private var mSelectContactsAdapter: LiveRoomVisibleRangeAdapter? = null
    private var pageNo = 1
    private val mAllDataLists: MutableList<FansItemBean> = ArrayList()
    private var mSelectList: MutableList<String> = ArrayList() //已选择的列表
    private var roomId //主播id
            : String? = null
    private var sceneId //场次id
            : String? = null
    private var anchorImageUrl //主播封面
            : String? = null

    private var CBFlag: MutableMap<Int, Boolean>? = HashMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rt_stream_select_contacts_activity)
        roomId = intent.getStringExtra("roomId")
        sceneId = intent.getStringExtra("sceneId")
        anchorImageUrl = intent.getStringExtra("imageUrl")
        initView()
        initListener()
        initObserver()
    }

    private fun initView() {
        imgBack = findViewById(R.id.iv_top_back)
        txtTitle = findViewById(R.id.tv_top_title)
        mSmartRefreshLayout = findViewById(R.id.visibleRangeRefreshLayout)
        mRecyclerView = findViewById(R.id.visibleRangeRecyclerView)
        mRecyclerView?.layoutManager = LinearLayoutManager(this)
        noDataLayout = findViewById(R.id.visibleRangeNoDataLayout)
        noDataTxt = findViewById(R.id.visibleRangeNoDataTxt)
        when(intent.getStringExtra("type")){
            "1"->tv_top_title.text = resources.getString(R.string.rt_stream_visible_range_see)
            "2"->tv_top_title.text = resources.getString(R.string.rt_stream_visible_range_see_no)
        }
    }

    private fun initListener() {
        mSelectContactsAdapter = LiveRoomVisibleRangeAdapter(R.layout.rt_stream_select_contacts_item, intent.getStringExtra("type"))
        mRecyclerView!!.adapter = mSelectContactsAdapter
        imgBack?.setOnClickListener(this)
        visibleRangeBtn.setOnClickListener(this)
        mSmartRefreshLayout!!.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                ++pageNo
                roomId?.let {
                    sceneId?.let { it1 ->
                        mViewModel!!.getStartLiveFansList(
                            DataBus.instance().USER_ID,
                            it, it1, pageNo)
                    }
                }
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                pageNo = 1
                mSelectList = ArrayList()
                roomId?.let {
                    sceneId?.let { it1 ->
                        mViewModel!!.getStartLiveFansList(
                            DataBus.instance().USER_ID,
                            it, it1, pageNo)
                    }
                }
            }
        })
        mSelectContactsAdapter?.setOnSelectContactsListener(object : OnSelectContactsListener {
            override fun onItemCheck(mCBFlag: MutableMap<Int, Boolean>) {
                CBFlag = mCBFlag
            }

            override fun updateList(mCBFlag: MutableMap<Int, Boolean>) {
                CBFlag = mCBFlag
            }
        })
        mSelectContactsAdapter?.setOnItemClickListener { adapter, view, position ->
            val list: MutableList<FansItemBean> = ArrayList()
            val item = adapter.data[position] as FansItemBean
            list.add(item)
        }
    }

    private fun initObserver() {
        mViewModel = ViewModelUtils.get(this, LiveRoomVisibleRangeListViewModel::class.java)
        roomId?.let {
            sceneId?.let { it1 ->
                mViewModel?.getStartLiveFansList(
                    DataBus.instance().USER_ID,
                    it, it1, pageNo)
            }
        }
        mViewModel?.fansListLiveData?.observe(this) { liveRoomVisibleRangeListBean ->
            if (1 == pageNo) {
                mSmartRefreshLayout!!.finishRefresh()
            } else {
                mSmartRefreshLayout!!.finishLoadMore()
            }
            if (liveRoomVisibleRangeListBean != null) {
                val lists: List<FansItemBean> = liveRoomVisibleRangeListBean.data!!.fans_list
                mAllDataLists.addAll(lists)
                if (mAllDataLists.size > 0) {
                    visibleRangeFansNumTxt.text = resources.getString(R.string.rt_stream_visible_range_txt, liveRoomVisibleRangeListBean.data!!.total)
                    noDataLayout!!.visibility = View.GONE
                    mSmartRefreshLayout!!.visibility = View.VISIBLE
                    mSelectContactsAdapter!!.setList(lists)
                    mSelectContactsAdapter!!.init(lists)
                } else {
                    changeUIStatus(1)
                }
            }
        }

        mViewModel?.setUserAllowRangLiveData?.observe(this){
            if(it.errCode == 0){
                SPUtils.getInstance().put(SPConstants.BooleanConstants.IS_SETTING_VISIBLE, true)
                LaToastUtil.showShort(resources.getString(R.string.login_btn_ok))
                finish()
            }else{
                LaToastUtil.showShort(it?.errMsg)

            }

        }
    }

    //无数据状态处理
    private fun changeUIStatus(type: Int) {
        noDataLayout!!.visibility = View.VISIBLE
        mSmartRefreshLayout!!.visibility = View.GONE
        if (1 == type) { //关注没有数据
            noDataTxt!!.setText(R.string.no_datas)
        } else { //搜索没有数据
            noDataTxt!!.setText(R.string.no_datas)
        }
    }

    override fun onClick(view: View) {
        val id = view.id
        when (id) {
            R.id.iv_top_back->finish()
            R.id.visibleRangeBtn->{
                CBFlag?.forEach { (key, value)->
                    if(value){
                        mSelectList.add(key.toString())
                    }
                }
                if(mSelectList.size > 0) {
                    intent.getStringExtra("type")?.toInt()?.let {
                        mViewModel?.setUserAllowRange(
                            it, DataBus.instance().USER_ID,
                            sceneId!!, mSelectList
                        )
                    }
                }else{
                    LaToastUtil.showShort(resources.getString(R.string.rt_stream_visible_range_tip))
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}