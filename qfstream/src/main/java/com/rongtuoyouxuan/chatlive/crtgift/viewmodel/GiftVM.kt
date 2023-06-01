package com.rongtuoyouxuan.chatlive.crtgift.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.rongtuoyouxuan.chatlive.crtbiz2.gift.GiftNewBiz
import com.rongtuoyouxuan.chatlive.crtbiz2.model.gift.GiftPanelRes
import com.rongtuoyouxuan.chatlive.crtbiz2.model.gift.GiftPanelResData
import com.rongtuoyouxuan.chatlive.crtbiz2.model.user.WalletBean
import com.rongtuoyouxuan.chatlive.crtbiz2.user.PayBiz
import com.rongtuoyouxuan.chatlive.crtdatabus.DataBus
import com.rongtuoyouxuan.chatlive.crtnet.RequestListener

/**
 * 
 * date:2022/8/4-14:33
 * des:
 */
class GiftVM(application: Application) : AndroidViewModel(application) {

    //主页-最新-列表数据
    val giftSucVM: MutableLiveData<GiftPanelRes> = MutableLiveData()
    val giftErrorVM: MutableLiveData<String> = MutableLiveData()
    val balanceLiveData: MutableLiveData<Int> = MutableLiveData()


    fun getPanel(userId:String) {
        GiftNewBiz.getPanel(userId, object : RequestListener<GiftPanelResData> {
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

    fun getBalance() {
        PayBiz.getBalance(DataBus.instance().USER_ID, object : RequestListener<WalletBean> {
            override fun onSuccess(reqId: String?, result: WalletBean?) {
                if (null != result?.data) {
                    balanceLiveData.value = result.data.balance
                } else {
                    balanceLiveData.value = 0
                }
            }

            override fun onFailure(reqId: String?, errCode: String?, msg: String?) {
                balanceLiveData.value = 0
            }
        })
    }
}