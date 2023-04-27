package com.rongtuoyouxuan.chatlive.stream.view.dialog

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.alibaba.android.arouter.facade.annotation.Route
import com.rongtuoyouxuan.chatlive.base.view.viewmodel.BuyGoldViewModel
import com.rongtuoyouxuan.chatlive.base.viewmodel.LiveRoomVisibleRangeListViewModel
import com.rongtuoyouxuan.chatlive.databus.DataBus
import com.rongtuoyouxuan.chatlive.router.Router
import com.rongtuoyouxuan.chatlive.router.constants.RouterConstant
import com.rongtuoyouxuan.chatlive.stream.R
import com.rongtuoyouxuan.chatlive.util.LaToastUtil
import com.rongtuoyouxuan.libuikit.LanguageActivity
import kotlinx.android.synthetic.main.rt_dialog_start_live_visible_range.*

@Route(path = RouterConstant.PATH_START_LIVE_VISIBLE_RANGE)
class StartLiveVisibleRangeDialog : LanguageActivity(), View.OnClickListener {

    private var sceneId:String? = ""
    private var liveRoomVisibleRangeListViewModel:LiveRoomVisibleRangeListViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.commenDialogStyle)
        setContentView(R.layout.rt_dialog_start_live_visible_range)
        sceneId = intent.getStringExtra("sceneId")
        setWindowLocation()
        initListener()
        initObserver()
    }

    private fun setWindowLocation() {
        try {
            val win = this.window
            win.decorView.setPadding(0, 0, 0, 0)
            val lp = win.attributes
            lp.width = WindowManager.LayoutParams.MATCH_PARENT
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT
            lp.gravity = Gravity.BOTTOM //设置对话框底部显示
            win.attributes = lp
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun initListener() {
        startLiveVisiblePublicLayout?.setOnClickListener(this)
        startLiveVisibleSeeLayout?.setOnClickListener(this)
        startLiveVisibleSeeNoLayout?.setOnClickListener(this)
    }

    fun initObserver(){
        liveRoomVisibleRangeListViewModel = ViewModelProvider(this).get(LiveRoomVisibleRangeListViewModel::class.java)
        liveRoomVisibleRangeListViewModel?.setUserAllowRangLiveData?.observe(this){
            if(it.errCode == 0){
                finish()
            }
            LaToastUtil.showShort(it?.errMsg)
        }
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.startLiveVisiblePublicLayout->{
                var list:MutableList<String> = ArrayList()
                sceneId?.let {
                    liveRoomVisibleRangeListViewModel?.setUserAllowRange(0, DataBus.instance().USER_ID,
                        it, list)
                }
            }
            R.id.startLiveVisibleSeeLayout->{
                Router.toLiveRoomVisibleRangeListActivity("1", sceneId)
            }
            R.id.startLiveVisibleSeeNoLayout->{
                Router.toLiveRoomVisibleRangeListActivity("2", sceneId)
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1003) {
            //充值金币不做刷新
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(0, 0)
    }
}