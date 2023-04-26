package com.rongtuoyouxuan.chatlive.base.view.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.MutableLiveData
import com.rongtuoyouxuan.chatlive.biz2.model.main.LiveResponse

class RecommendViewModel(application: Application) : AndroidViewModel(application) {
    var liveVM: MutableLiveData<LiveResponse> = MutableLiveData()
    var liveErrorVM: MutableLiveData<String> = MutableLiveData()

    //热门
    fun liveHot(
        lifecycleCoroutineScope: LifecycleCoroutineScope,
        page: Int,
        isRefresh: Boolean = true
    ) {

    }

}