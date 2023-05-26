package com.rongtuoyouxuan.chatlive.base.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.rongtuoyouxuan.chatlive.crtcommonbiz.model.list.LoadEvent
import com.rongtuoyouxuan.chatlive.crtbiz2.model.stream.PopolarityRankBean
import com.rongtuoyouxuan.chatlive.crtbiz2.stream.StreamBiz
import com.rongtuoyouxuan.chatlive.crtnet.RequestListener
import com.rongtuoyouxuan.chatlive.stream.R
import com.rongtuoyouxuan.chatlive.crtutil.util.LaToastUtil
import com.rongtuoyouxuan.chatlive.crtuikit.viewmodel.BaseListFragmentViewModel

open class PopularityRankViewModel(application: Application):
    BaseListFragmentViewModel<PopolarityRankBean>(application) {

    private var roomId:String = ""
    private var userId:String = ""
    private var mContext:Context? = null
    var rankLiveData: MutableLiveData<PopolarityRankBean> = MutableLiveData<PopolarityRankBean>()

    fun setRoomId(roomId:String, mContext:Context){
        this.roomId = roomId
        this.mContext = mContext

    }
    override fun doLoadData(mPage: Int, event: LoadEvent?) {
        StreamBiz.getPopularityRank(roomId, mPage, 20, object :
            RequestListener<PopolarityRankBean> {
            override fun onSuccess(reqId: String?, result: PopolarityRankBean?) {
                result?.event = event
                _getData().value = result
                if(result?.data?.rank_list?.size == 0){
                    LaToastUtil.showShort(mContext?.getString(R.string.no_datas))
                }
                if(result?.data?.show == true && mPage == 1) {
                    rankLiveData.value = result
                }
            }

            override fun onFailure(reqId: String?, errCode: String?, msg: String?) {

            }

        })
    }
}