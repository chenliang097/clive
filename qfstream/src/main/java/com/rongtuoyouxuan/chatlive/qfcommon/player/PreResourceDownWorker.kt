package com.rongtuoyouxuan.chatlive.qfcommon.player

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.blankj.utilcode.util.SPUtils
import com.rongtuoyouxuan.chatlive.crtbiz2.gift.GiftNewBiz
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

/**
 *
 * date:2022/8/3-15:52
 * des: 下载礼物及座驾资源
 */
class PreResourceDownWorker(context: Context, parameters: WorkerParameters) :
    CoroutineWorker(context, parameters) {
    override suspend fun doWork(): Result {
        val array = getResource()
        if (array.isNotEmpty()) {
            array.forEach {
                GiftResourceManager.downFile(it)
            }
        }
        return Result.success()
    }

    private fun getResource(): Array<String> = runBlocking {
        val giftTimestamp = SPUtils.getInstance().getLong("gift_resource_time", 0L)
        val carTimestamp = SPUtils.getInstance().getLong("car_resource_time", 0L)
        val giftResource = async {
            GiftNewBiz.getService().getGiftResource(giftTimestamp, GiftNewBiz.resourceTypes)
        }
        val carResource = async {
            GiftNewBiz.getService2().getCarResource(carTimestamp, GiftNewBiz.resourceTypes)
        }
        val giftData = giftResource.await().data
        val carData = carResource.await().data
        val list = arrayListOf<String>()
        if (giftData?.gift?.isNotEmpty() == true) {
            list.addAll(giftData.gift!!)
            GiftResourceManager.setGiftResourceTime(giftData.timestamp ?: 0L)
        }
        if (carData?.car?.isNotEmpty() == true) {
            list.addAll(carData.car!!)
            GiftResourceManager.setCarResourceTime(carData.timestamp ?: 0L)
        }
        val preGiftIdList = list.toArray(arrayOf<String>())

        preGiftIdList
    }
}