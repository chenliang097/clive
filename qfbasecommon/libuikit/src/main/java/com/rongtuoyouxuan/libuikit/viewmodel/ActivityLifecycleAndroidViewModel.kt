package com.rongtuoyouxuan.libuikit.viewmodel

import android.app.Application
import androidx.annotation.CallSuper
import androidx.lifecycle.*

open class ActivityLifecycleAndroidViewModel(
    private val lifecycleOwner: LifecycleOwner,
    application: Application
) : AndroidViewModel(application), LifecycleObserver {

    var isBackground = false

    init {
        lifecycleOwner.lifecycle.addObserver(getLifecycleObserver())
    }

    private fun getLifecycleObserver(): LifecycleObserver = this

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    open fun onResume() {
        isBackground = false
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    open fun onPause() {
        isBackground = true
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    @CallSuper
    open fun onDestroy() {
        lifecycleOwner.lifecycle.removeObserver(getLifecycleObserver())
    }
}