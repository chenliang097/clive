package com.rongtuoyouxuan.chatlive.base.view.activity

import android.graphics.Rect
import android.util.Log
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.blankj.utilcode.util.ScreenUtils

/**
 *
 * date:2022/10/10-11:16
 * des: 键盘监听，并调整相应布局
 */
class KeyBoardUILogic(
    private val activity: FragmentActivity,
    var keyBoardCallBack: (keyboardVisible: Boolean?) -> Unit = { }
) {
    private var keyboardVisible = false

    val listener = {
        val root = activity.findViewById<ViewGroup>(android.R.id.content)
        val r = Rect()
        root.getWindowVisibleDisplayFrame(r)
        val screenHeight = ScreenUtils.getScreenHeight()
        val keyboardHeight = screenHeight - (r.bottom - r.top)
        val isKeyboardShowing: Boolean = keyboardHeight > (screenHeight / 3)
        if (isKeyboardShowing) {
            if (!keyboardVisible) {
                Log.d("KeyBoardUILogic", "显示键盘 registerKeyBoardListener : true")
                keyboardVisible = true
                keyBoardCallBack(true)
            }
        } else {
            if (keyboardVisible) {
                Log.d("KeyBoardUILogic", "不显示键盘 registerKeyBoardListener : false")
                keyboardVisible = false
                keyBoardCallBack(false)
            }
        }
    }

    fun registerKeyBoardListener() {
        val root = activity.findViewById<ViewGroup>(android.R.id.content)
        root.post {
            root.viewTreeObserver?.addOnGlobalLayoutListener(listener)
        }
    }

    fun unRegisterKeyBoardListener() {
        val root = activity.findViewById<ViewGroup>(android.R.id.content)
        root.post {
            root.viewTreeObserver?.removeOnGlobalLayoutListener(null)
        }
    }
}