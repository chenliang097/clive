package com.rongtuoyouxuan.libgift.view.activity

import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.rongtuoyouxuan.chatlive.router.constants.RouterConstant
import com.rongtuoyouxuan.libgift.R
import com.rongtuoyouxuan.libuikit.SimpleActivity
import kotlinx.android.synthetic.main.activity_gift_desc.*

/**
 *
 * date:2022/11/18-14:54
 * des: 简介
 */
@Route(path = RouterConstant.PATH_ACTIVITY_GIFT_DESC)
class GiftDescActivity : SimpleActivity() {

    @Autowired
    @JvmField
    var title: String = ""

    @Autowired
    @JvmField
    var content: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        isHasStatusBar = false
        super.onCreate(savedInstanceState)

        overridePendingTransition(R.anim.common_bottom_dialog_in, R.anim.common_bottom_dialog_out)

        window?.decorView?.setPadding(0, 0, 0, 0)

        val params = window?.attributes
        params?.gravity = Gravity.BOTTOM
        params?.width = ViewGroup.LayoutParams.MATCH_PARENT
        params?.height = ViewGroup.LayoutParams.WRAP_CONTENT
        window?.attributes = params
    }

    override fun getLayoutResId(): Int = R.layout.activity_gift_desc

    override fun initData() {
        tvTitle?.text = title
        tvContent?.text = content
    }

    override fun initListener() {
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.common_bottom_dialog_in, R.anim.common_bottom_dialog_out)
    }
}