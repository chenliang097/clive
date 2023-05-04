package com.rongtuoyouxuan.chatlive.stream.view.activity

import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.BarUtils
import com.rongtuoyouxuan.chatlive.router.constants.RouterConstant
import com.rongtuoyouxuan.chatlive.stream.R
import com.rongtuoyouxuan.chatlive.stream.view.fragment.SetManagerListFragment
import com.rongtuoyouxuan.chatlive.util.UIUtils
import com.rongtuoyouxuan.libuikit.LanguageActivity
import com.rongtuoyouxuan.libuikit.SimpleActivity
import kotlinx.android.synthetic.main.qf_stream_activity_anchor_center.*
import kotlinx.android.synthetic.main.qf_stream_activity_end.*
import kotlinx.android.synthetic.main.rt_activity_set_manager.*

@Route(path = RouterConstant.PATH_SET_MANAGER)
class SettingManagerActivity: LanguageActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rt_activity_set_manager)
        setWindowLocation()
        initView()
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

    fun initView(){
        supportFragmentManager //
            .beginTransaction()
            .add(
                R.id.fl_fragment,
                SetManagerListFragment.newInstance(intent.getStringExtra("roomId"))
            ) // 此处的R.id.fragment_container是要盛放fragment的父容器
            .commit()
        backImg.setOnClickListener {
            finish()
        }
    }

}