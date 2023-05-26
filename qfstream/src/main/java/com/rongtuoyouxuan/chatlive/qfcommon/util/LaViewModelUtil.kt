package com.rongtuoyouxuan.chatlive.qfcommon.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider

/*
*Create by {Mr秦} on 2022/10/20
*/
object LaViewModelUtil {
    fun <T : AndroidViewModel> get(activity: FragmentActivity, tClass: Class<T>): T {
        return ViewModelProvider(activity).get(tClass)
    }

    fun <T : AndroidViewModel> get(fragment: Fragment, tClass: Class<T>): T {
        return ViewModelProvider(fragment).get(tClass)
    }
}