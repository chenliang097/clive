package com.rongtuoyouxuan.chatlive.stream.view.activity

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import com.alibaba.android.arouter.facade.annotation.Route
import com.rongtuoyouxuan.chatlive.crtrouter.Router
import com.rongtuoyouxuan.chatlive.crtrouter.constants.RouterConstant
import com.rongtuoyouxuan.chatlive.stream.R
import com.rongtuoyouxuan.chatlive.stream.viewmodel.AnchorManagerViewModel
import com.rongtuoyouxuan.chatlive.crtutil.util.UIUtils
import com.rongtuoyouxuan.chatlive.crtuikit.LanguageActivity
import kotlinx.android.synthetic.main.rt_dialog_anchor_manager.*

@Route(path = RouterConstant.PATH_ANCHOE_MANAGER)
class AnchorManagerDialog : LanguageActivity(), View.OnClickListener {
    private var banlanceTxt: TextView? = null
    private var btn: Button? = null
    private var anchorManagerViewModel: AnchorManagerViewModel? = null
    private var roomId = ""
    private var sceneId = ""
    private var type = 1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.commenDialogStyle)
        setContentView(R.layout.rt_dialog_anchor_manager)
        roomId = intent.getStringExtra("roomId").toString()
        sceneId = intent.getStringExtra("sceneId").toString()
        type = intent.getIntExtra("type", 1)
        setWindowLocation()
        initView()
        initListener()
        initData()
        initObserver()
    }

    private fun setWindowLocation() {
        try {
            val win = this.window
            win.decorView.setPadding(0, 0, 0, 0)
            val lp = win.attributes
            lp.width = WindowManager.LayoutParams.MATCH_PARENT
            lp.height = (UIUtils.screenHeight(this) * 0.5).toInt()
            lp.gravity = Gravity.BOTTOM //设置对话框底部显示
            win.attributes = lp
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun initView() {
        when(type){
            1->{
                anchorManagerSetManagerLayout.visibility = View.VISIBLE
            }
            2->{
                anchorManagerSetManagerLayout.visibility = View.GONE
            }
        }
    }

    private fun initListener() {
        anchorManagerBAndMLayout?.setOnClickListener(this)
        anchorManagerSetManagerLayout?.setOnClickListener(this)
    }

    private fun initObserver() {
        anchorManagerViewModel = obtainStreamViewModel()
    }

    private fun initData() {
        anchorManagerSetWordsLayout.setData(roomId)
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.anchorManagerSetManagerLayout->{
                Router.toSettingManagerActivity(roomId, sceneId)
            }
            R.id.anchorManagerBAndMLayout->{
                Router.toAnchorManngerBlackAndMuteActivity(roomId, sceneId)
            }
        }

    }

    private fun obtainStreamViewModel(): AnchorManagerViewModel {
        return ViewModelProviders.of(this).get(AnchorManagerViewModel::class.java)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(0, 0)
    }

    override fun onResume() {
        super.onResume()
        startWakeLock()
    }

    override fun onPause() {
        super.onPause()
        stopWakeLock()
    }

    protected fun startWakeLock() {
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    protected fun stopWakeLock() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }
}