package com.rongtuoyouxuan.chatlive.stream.view.dialog

import android.os.Bundle
import android.view.*
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.rongtuoyouxuan.chatlive.crtdatabus.liveeventbus.LiveDataBus
import com.rongtuoyouxuan.chatlive.crtdatabus.liveeventbus.constansts.LiveDataBusConstants
import com.rongtuoyouxuan.chatlive.crtrouter.bean.ISource
import com.rongtuoyouxuan.chatlive.crtrouter.constants.RouterConstant
import com.rongtuoyouxuan.chatlive.stream.R
import com.rongtuoyouxuan.chatlive.stream.view.fragment.GifListFragment
import com.rongtuoyouxuan.chatlive.stream.viewmodel.GifListViewModel
import com.rongtuoyouxuan.chatlive.crtutil.util.UIUtils
import com.rongtuoyouxuan.chatlive.crtuikit.LanguageActivity

@Route(path = RouterConstant.PATH_DIALOG_GIF_PANEL)
class GifListDialog: LanguageActivity() {

    var gifListViewModel:GifListViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.qf_stream_dialog_gif_list)
        setWindowLocation()
        supportFragmentManager
            .beginTransaction()
            .add(R.id.gifListFragment, GifListFragment.newInstance(intent.getStringExtra("streamId"), intent.getStringExtra("fromSource")))
            .commit()
        initObserver()
    }

    fun initObserver(){
        gifListViewModel = ViewModelProvider(this).get(GifListViewModel::class.java)
        gifListViewModel?.sendLiveData?.observe(this
        ) {
//            finish()
        }
        LiveDataBus.getInstance().with(LiveDataBusConstants.EVENT_KEY_TO_FINISH_ROOM_ACTIVITY).observe(this){
            if(this != null && !isFinishing && !isDestroyed){
                finish()
            }
        }
    }

    override fun finish() {
        super.finish()
        var fromSource = intent.getStringExtra("fromSource")
        if(ISource.FROM_LIVE_ROOM == fromSource){
            LiveDataBus.getInstance().with(LiveDataBusConstants.EVENT_KEY_TO_ADJUST_PUBLIC_CHAT).value = false
        }
    }

    private fun setWindowLocation() {
        val win = this.window
        win?.decorView?.setPadding(0, 0, 0, 0)
        val lp = win?.attributes
        lp?.width = (UIUtils.screenWidth(this))
        lp?.height = UIUtils.dip2px(this, 287)
        lp?.gravity = Gravity.BOTTOM
        win?.attributes = lp
        win?.decorView?.setBackgroundResource(R.drawable.corner_circle_white_top)
//        win?.setWindowAnimations(R.style.CommonDialogStyleAnimation)
    }
}