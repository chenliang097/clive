package com.rongtuoyouxuan.chatlive.base.view.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.MutableLiveData
import com.rongtuoyouxuan.chatlive.crtbiz2.model.main.LiveResponse
import com.rongtuoyouxuan.chatlive.crtbiz2.model.stream.RecommenListRequestBean
import com.rongtuoyouxuan.chatlive.crtbiz2.stream.StreamBiz
import com.rongtuoyouxuan.chatlive.crtnet.RequestListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecommendViewModel(application: Application) : AndroidViewModel(application) {
    var liveVM: MutableLiveData<LiveResponse> = MutableLiveData()
    var liveErrorVM: MutableLiveData<String> = MutableLiveData()

    //热门
    fun liveHot(
        lifecycleCoroutineScope: LifecycleCoroutineScope,
        request: RecommenListRequestBean,
        page: Int,
        isRefresh: Boolean = true
    ) {
        StreamBiz.getRecommendList(lifecycleCoroutineScope, request, object :
            RequestListener<LiveResponse> {
            override fun onSuccess(reqId: String?, result: LiveResponse?) {
                lifecycleCoroutineScope.launch(Dispatchers.Main) {
                    if (null != result?.data) {
                        result.isRefresh = isRefresh
                        liveVM.value = result
                    } else {
                        liveErrorVM.value = ""
                    }
                }
            }

            override fun onFailure(reqId: String?, errCode: String?, msg: String?) {
                lifecycleCoroutineScope.launch(Dispatchers.Main) {
                    if (msg?.isNotEmpty() == true) {
                        liveErrorVM.value = msg
                    } else {
                        liveErrorVM.value = ""
                    }
                }
            }
        })
    }

}