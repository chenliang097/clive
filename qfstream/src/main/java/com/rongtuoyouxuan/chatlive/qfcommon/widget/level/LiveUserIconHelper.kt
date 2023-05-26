package com.rongtuoyouxuan.chatlive.qfcommon.widget.level

import androidx.annotation.DrawableRes
import com.rongtuoyouxuan.chatlive.stream.R

object LiveUserIconHelper {
    /**
     * @param ulevel
     * @return
     */
    fun getUserLevelBg(ulevel: Int): Int {
        return 0
    }

    /**
     * 主播等级
     * @param vlevel
     * @return
     */
    fun getAnchorLevelBg(vlevel: Int): Int {
        return 0
    }

    /**
     * 11-管理员
     */
    @DrawableRes
    fun getIdentity(role: Int): Int {
        if (99 == role) {
            return R.drawable.rt_res_icon_manager_identify;
        }else {
            return R.drawable.rt_res_icon_manager_identify;
        }
    }

    //获取主播-用于资料卡/个人中心
    fun getHostLevelTitle(level: Int): Int {
        return 0
    }

    //获取主播-用于资料卡/个人中心
    fun getHostLevelBg(level: Int): Int {
        if (level == 0) {
            return R.drawable.shape_host_level_bg_0
        }
        return when (level) {
            in 1..10 -> {
                R.drawable.shape_host_level_bg_1
            }
            in 11..20 -> {
                R.drawable.shape_host_level_bg_2
            }
            in 21..30 -> {
                R.drawable.shape_host_level_bg_3
            }
            in 31..40 -> {
                R.drawable.shape_host_level_bg_4
            }
            else -> {
                R.drawable.shape_host_level_bg_0
            }
        }
    }


}