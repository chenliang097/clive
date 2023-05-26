package com.rongtuoyouxuan.chatlive.crtuikit.dialog

import android.app.Dialog
import android.content.Context
import android.content.ContextWrapper
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent

open class AutoDismissDialog(private val contextParent: Context, themeResId: Int = 0) : Dialog(contextParent, themeResId), LifecycleObserver {
    init {
        getLifecycleOwner(contextParent)?.lifecycle?.addObserver(this)
    }

    override fun dismiss() {
        removeLifecycle()
        super.dismiss()
    }

    private fun removeLifecycle() {
        getLifecycleOwner(contextParent)?.lifecycle?.removeObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public fun onDestroy() {
        if (isShowing) dismiss()
    }

    private fun getLifecycleOwner(context: Context): LifecycleOwner? {
        if (context is LifecycleOwner) return context
        if (contextParent is ContextWrapper) {
            val c = contextParent.baseContext
            if (c is LifecycleOwner) {
                return c
            }
        }
        return null
    }
}