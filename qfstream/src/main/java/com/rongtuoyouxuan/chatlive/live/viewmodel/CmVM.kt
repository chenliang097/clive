package com.rongtuoyouxuan.chatlive.live.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.rongtuoyouxuan.chatlive.biz2.model.stream.LiveAudienceRankData
import com.rongtuoyouxuan.chatlive.biz2.model.stream.LiveAudienceRankRes
import com.rongtuoyouxuan.chatlive.biz2.stream.StreamBiz
import com.rongtuoyouxuan.chatlive.net2.RequestListener

/**
 * 
 * date:2022/11/14-11:13
 * des: 贡献榜
 */
class CmVM(application: Application) : AndroidViewModel(application) {
    val errorVM: MutableLiveData<String> = MutableLiveData()
    val contributionVM: MutableLiveData<LiveAudienceRankRes> = MutableLiveData()

    fun getData(
        position: Int,
        anchorId: Long,
        nextKey: String,
        isRefresh: Boolean
    ) {
        when (position) {
            0 -> {
                StreamBiz.cmDay(anchorId, nextKey, null, object :
                    RequestListener<LiveAudienceRankData> {
                    override fun onSuccess(reqId: String?, result: LiveAudienceRankData?) {
                        if (null != result?.data) {
                            result.data.isRefresh = isRefresh
                            contributionVM.value = result.data
                        } else {
                            errorVM.value = ""
                        }
                    }

                    override fun onFailure(reqId: String?, errCode: String?, msg: String?) {
                        errorVM.value = msg
                    }
                })
            }
            1 -> {
                StreamBiz.cmMonth(anchorId, nextKey, null, object :
                    RequestListener<LiveAudienceRankData> {
                    override fun onSuccess(reqId: String?, result: LiveAudienceRankData?) {
                        if (result?.data?.list?.isNotEmpty() == true) {
                            result.data.isRefresh = isRefresh
                            contributionVM.value = result.data
                        } else {
                            errorVM.value = ""
                        }
                    }

                    override fun onFailure(reqId: String?, errCode: String?, msg: String?) {
                        errorVM.value = msg
                    }
                })
            }
            2 -> {
                StreamBiz.cmTotal(anchorId, nextKey, null, object :
                    RequestListener<LiveAudienceRankData> {
                    override fun onSuccess(reqId: String?, result: LiveAudienceRankData?) {
                        if (result?.data?.list?.isNotEmpty() == true) {
                            result.data.isRefresh = isRefresh
                            contributionVM.value = result.data
                        } else {
                            errorVM.value = ""
                        }
                    }

                    override fun onFailure(reqId: String?, errCode: String?, msg: String?) {
                        errorVM.value = msg
                    }
                })
            }
        }
    }
}