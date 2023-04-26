package com.rongtuoyouxuan.libuikit.layout

import android.content.Context
import android.content.ContextWrapper
import android.util.AttributeSet
import androidx.annotation.CallSuper
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import java.lang.RuntimeException

open class ActivityLifecycleConstraintLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), LifecycleObserver {
    val _lifecycleOwner : LifecycleOwner
    init {
        _lifecycleOwner = getLifecycleOwner(context) ?: throw RuntimeException("current context is not LifecycleOwner")
        _lifecycleOwner.lifecycle.addObserver(getLifecycleObserver())
    }

    private fun getLifecycleObserver(): LifecycleObserver = this

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    open fun onCreate() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    open fun onStart() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    open fun onResume() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    open fun onPause() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    open fun onStop() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    @CallSuper
    open fun onDestroy() {
        removeLifecycle()
    }

    private fun removeLifecycle() {
        getLifecycleOwner(context)?.lifecycle?.removeObserver(this)
    }

    private fun getLifecycleOwner(context: Context): LifecycleOwner? {
        if (context is LifecycleOwner) return context
        if (context is ContextWrapper) {
            val c = context.baseContext
            if (c is LifecycleOwner) {
                return c
            }
        }
        return null
    }
}