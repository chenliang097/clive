package com.rongtuoyouxuan.chatlive.base.view.dialog

import android.annotation.SuppressLint
import androidx.fragment.app.FragmentActivity
import com.rongtuoyouxuan.chatlive.stream.R
import com.rongtuoyouxuan.libgift.viewmodel.GiftHelper
import com.lxj.xpopup.core.BottomPopupView
import kotlinx.android.synthetic.main.dialog_live_anim_config.view.*

/**
 *
 * date:2022/10/19-11:16
 * des:
 */
@SuppressLint("ViewConstructor")
class LiveAnimConfigDialog(
    val activity: FragmentActivity,
    private val isPlayGift: Boolean = true,
    private val isPlayCar: Boolean = true
) : BottomPopupView(activity) {

    override fun getImplLayoutId(): Int = R.layout.dialog_live_anim_config

    override fun onCreate() {
        super.onCreate()

        ivGiftAnim?.isChecked = isPlayGift
        ivCarAnim?.isChecked = isPlayCar

        ivGiftAnim?.setOnCheckedChangeListener { _, isChecked ->
            GiftHelper.giftConfigAnim.post(if (isChecked) 1 else 0)
        }

        ivCarAnim?.setOnCheckedChangeListener { _, isChecked ->
            GiftHelper.carConfigAnim.post(if (isChecked) 1 else 0)
        }
    }
}