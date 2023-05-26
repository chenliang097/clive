package com.rongtuoyouxuan.chatlive.crtbiz2.model.mine.response

import com.google.gson.annotations.SerializedName

/**
 * 
 * date:2022/8/27-09:59
 * des:
 */
data class AccountBalanceRes(
    @SerializedName("balance")
    val balance: Long? = 0,
    @SerializedName("gold_bean_balance")
    val goldBalance: Long? = 0,
    @SerializedName("gold_coin_balance")
    val coinBalance: Long? = 0,
    var payType: String? = ""//本地使用
)

data class AccountBalanceResData(val data: AccountBalanceRes) : com.rongtuoyouxuan.chatlive.crtnet.BaseModel()