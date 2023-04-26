package com.rongtuoyouxuan.chatlive.live.view.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import androidx.lifecycle.lifecycleScope
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.alibaba.android.arouter.facade.annotation.Route
import com.rongtuoyouxuan.chatlive.base.utils.ViewModelUtils
import com.rongtuoyouxuan.chatlive.base.view.activity.BaseLiveStreamActivity
import com.rongtuoyouxuan.chatlive.biz2.stream.StreamBiz
import com.rongtuoyouxuan.chatlive.databus.DataBus
import com.rongtuoyouxuan.chatlive.databus.live.LiveManager
import com.rongtuoyouxuan.chatlive.live.view.ZegoLiveplay
import com.rongtuoyouxuan.chatlive.live.view.floatwindow.FloatWindowsService
import com.rongtuoyouxuan.chatlive.live.view.fragment.LiveRoomFragment
import com.rongtuoyouxuan.chatlive.live.viewmodel.LiveRoomListViewModel
import com.rongtuoyouxuan.chatlive.log.upload.ULog
import com.rongtuoyouxuan.chatlive.net2.BaseModel
import com.rongtuoyouxuan.chatlive.net2.RequestListener
import com.rongtuoyouxuan.chatlive.router.constants.RouterConstant
import com.rongtuoyouxuan.chatlive.router.constants.RouterParams
import com.rongtuoyouxuan.chatlive.stream.R
import com.rongtuoyouxuan.chatlive.util.KeyBoardUtils
import com.rongtuoyouxuan.chatlive.util.LaToastUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.rongtuoyouxuan.chatlive.biz2.model.stream.LiveRoomListBean
import com.rongtuoyouxuan.chatlive.router.Router
import com.rongtuoyouxuan.chatlive.stream.view.layout.StreamPreviewLayout
import com.rongtuoyouxuan.libgift.viewmodel.GiftHelper
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.qf_stream_live_activity_live_room.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Route(path = RouterConstant.PATH_ACTIVITY_LIVEROOM)
class LiveRoomActivity : BaseLiveStreamActivity() {
    private val TAG = "LiveRoomActivity"
    private var recoverIs: Boolean? = false
    private var fromSource: String? = null
    private var sceneId: String? = null
    private var isToMainActivity: Boolean? = false

    private var vm: LiveRoomListViewModel? = null
    private val list = arrayListOf<LiveRoomListBean.RoomItemBean>()
    private var type = 1
    private var pageScrolledItem = 0
    private var liveCurrentItem = -1
    private var fragmentId = 0
    private var isPageScrolled = false
    private var isResetOpen = false

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (intent != null) {
            val theuid = intent.getStringExtra(ZegoLiveplay.STREAM_ID)
            if (theuid != streamID) {
                isResetOpen = true
                finish()
                startActivity(intent)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        if (DataBus.instance().floaT_IS_RUN) {
            removeFloatingWindow()
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.qf_stream_live_activity_live_room)
        LiveManager.instance().setLivestatusLiving()
        initData()
    }
    private fun initData() {
        sceneId = intent.getStringExtra("sceneId")
        fromSource = intent.getStringExtra(ZegoLiveplay.FROAM_SOURCE)
        recoverIs = intent.getBooleanExtra("recover_is", false)
        isToMainActivity = intent.getBooleanExtra(RouterParams.IS_TO_MAIN_ACTIVITY, false)
        type = intent?.getIntExtra("type", 1) ?: 1
        roomID = intent.getStringExtra(ZegoLiveplay.ROOM_ID)
        streamID = intent.getStringExtra(ZegoLiveplay.STREAM_ID)
        anchorId = intent.getStringExtra(ZegoLiveplay.ANCHORID)
        roomID?.let { updateLiveRoomID(it) }
        streamID?.let { updateLiveStreamID(it) }
        anchorId?.let { updateAnchorId(it) }
        vm = getViewModel(LiveRoomListViewModel::class.java)
        vm?.liveVM?.observe(this) {
            dismissDialogLoading()
            if(it.errCode == 0) {
                val lives = it.data?.rooms_info
                if (lives?.isNotEmpty() == true) {
                    lives.forEach {
//                        if (it.stream_id != streamID) {
//
//                        }
                        list.add(it)
                    }
                }
            }
            completeVP()
        }
        sceneId?.let { vm?.getLiveRoomLists(StreamPreviewLayout.USER_ID, it) }

        GiftHelper.clickGiftRecharge.observe(this) {
            Router.toGoldToBuyDialog("1")
        }
    }

    @SuppressLint("InflateParams")
    private fun completeVP() {
        val pagerItemView = LayoutInflater.from(this@LiveRoomActivity)
            .inflate(R.layout.live_pager_view_room_item, null)

        var isPageScrollToast = false
        var jobToast: Job? = null
        var jobLiveRoom: Job? = null

        vpPager?.apply {
            adapter = object : PagerAdapter() {
                override fun getCount(): Int = list.size

                override fun isViewFromObject(view: View, `object`: Any): Boolean {
                    return view == `object`
                }

                override fun instantiateItem(container: ViewGroup, position: Int): Any {
                    val view: View = LayoutInflater.from(container.context)
                        .inflate(R.layout.view_room_item, null)
                    view.id = position
                    list[position].anchor_avatar?.let {
                        updateCoverLayout(
                            it,
                            view.findViewById(R.id.liveroomCoverImg)
                        )
                    }
                    container.addView(view)
                    return view
                }

                override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
                    container.removeView(container.findViewById(position))
                }
            }
            this.adapter = adapter

            addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int,
                ) {
                    pageScrolledItem = position
                    if (pageScrolledItem + 1 == list.size) {
                        if (isPageScrollToast) {
                            if (jobToast?.isActive == true || null != jobToast) {
                                jobToast?.cancel()
                                jobToast = null
                            }
                            jobToast = lifecycleScope.launch(Dispatchers.Main) {
                                delay(100)
                                LaToastUtil.showShort(R.string.main_list_no_more)
                            }
                        }
                        isPageScrollToast = true
                    }
                }

                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    isPageScrolled = true
                }
            })

            setPageTransformer(
                false
            ) { page, position ->
                if (position < -1) {
                    page.alpha = 0f
                } else if (position <= 1) {
                    page.alpha = 1f
                    page.translationX = page.width * -position
                    val yPosition: Float = position * page.height
                    page.translationY = yPosition
                } else {
                    page.alpha = 0f
                }

                val viewGroup = page as ViewGroup

                if (pageScrolledItem == liveCurrentItem) {
                    return@setPageTransformer
                }

                if (position < 0f && viewGroup.id != pageScrolledItem) {
                    if (pagerItemView?.parent is ViewGroup) {
                        (pagerItemView.parent as ViewGroup).removeView(pagerItemView)
                    }
                }

                if (position == 0f && viewGroup.id == pageScrolledItem && pageScrolledItem != liveCurrentItem) {
                    if (fragmentId > 0) {
                        supportFragmentManager.findFragmentById(fragmentId)?.let {
                            supportFragmentManager.beginTransaction().remove(it)
                                .commitAllowingStateLoss()
                        }
                        fragmentId = 0
                        exitLiveFragment(list[liveCurrentItem].stream_id.toString())
                    }

                    jobLiveRoom = lifecycleScope.launch(Dispatchers.Main) {
                        delay(200)
                        Log.d("jobLiveRoom", ">>>lifecycleScope launch>>>>$pageScrolledItem")
                        if (pagerItemView?.parent is ViewGroup) {
                            (pagerItemView.parent as ViewGroup).removeView(pagerItemView)
                        }
                        val item = list[pageScrolledItem]

                        val fragment = LiveRoomFragment.newInstance(
                            "${item.scene_id_str}",
                            "${item.room_id_str}",
                            "${item.anchor_id}",
                            "${item.stream_id}",
                            recoverIs,
                            isPageScrolled,
                            1
                        )
                        pagerItemView?.id?.let {
                            supportFragmentManager.beginTransaction().add(it, fragment)
                                .show(fragment)
                                .commitAllowingStateLoss()
                            viewGroup.addView(pagerItemView)
                            liveCurrentItem = pageScrolledItem
                            fragmentId = fragment.id
                        }
                        vpPager.canSwipe = true
                    }
                }
            }
            currentItem = 0
        }
    }

    @SuppressLint("CheckResult")
    private fun updateCoverLayout(url: String, coverLayout: ImageView?) {
        Glide.with(this).asBitmap().load(url).placeholder(R.drawable.qf_stream_bg_live_down)
            .error(R.drawable.qf_stream_bg_live_down).apply(
                RequestOptions().transform(
                    BlurTransformation(25, 1)
                )
            ).into(object :
                SimpleTarget<Bitmap>() {
                override fun onLoadStarted(placeholder: Drawable?) {
                    super.onLoadStarted(placeholder)
                    coverLayout!!.setImageResource(R.drawable.qf_stream_bg_live_down)
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    super.onLoadFailed(errorDrawable)
                    coverLayout!!.setImageResource(R.drawable.qf_stream_bg_live_down)
                }

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    coverLayout!!.setImageBitmap(resource)
                }

            })
    }

    override fun onResume() {
        super.onResume()
        startWakeLock()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onPause() {
        super.onPause()
        stopWakeLock()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!isResetOpen) {
            ViewModelUtils.setLiveFragment(null)
        }
    }

    protected fun startWakeLock() {
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    protected fun stopWakeLock() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    private fun removeFloatingWindow() {
        val intent = Intent()
        intent.setClassName(
            this@LiveRoomActivity,
            FloatWindowsService::class.java.name
        )
        intent.type = "remove"
        startService(intent)
    }

    private fun exitLiveFragment(streamId: String) {
        StreamBiz.audienceExitRoom(streamId, "0", object :
            RequestListener<BaseModel> {
            override fun onSuccess(reqId: String?, result: BaseModel?) {
                ULog.d("clll", "exitLiveFragment----------")
            }

            override fun onFailure(reqId: String?, errCode: String?, msg: String?) {
            }
        })
    }

    override fun finish() {
        KeyBoardUtils.hideSoftInput(this)
        super.finish()
    }
}