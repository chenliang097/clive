package com.rongtuoyouxuan.libgift.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.rongtuoyouxuan.chatlive.biz2.gift.GiftNewBiz
import com.rongtuoyouxuan.chatlive.biz2.model.gift.GiftBagData
import com.rongtuoyouxuan.chatlive.biz2.model.gift.GiftEntity
import com.rongtuoyouxuan.chatlive.biz2.model.gift.GiftPanelRes
import com.rongtuoyouxuan.chatlive.biz2.model.gift.GiftPanelResData
import com.rongtuoyouxuan.chatlive.net2.RequestListener

/**
 * 
 * date:2022/8/4-14:33
 * des:
 */
class GiftVM(application: Application) : AndroidViewModel(application) {

    //主页-最新-列表数据
    val giftSucVM: MutableLiveData<GiftPanelRes> = MutableLiveData()
    val giftErrorVM: MutableLiveData<String> = MutableLiveData()

    fun getPanel() {
        GiftNewBiz.getPanel(object : RequestListener<GiftPanelResData> {
            override fun onSuccess(reqId: String?, result: GiftPanelResData?) {
                if (null != result?.data) {
                    giftSucVM.value = result.data
                } else {
                    giftErrorVM.value = ""
                }
            }

            override fun onFailure(reqId: String?, errCode: String?, msg: String?) {
                giftErrorVM.value = msg
            }
        })
    }
}