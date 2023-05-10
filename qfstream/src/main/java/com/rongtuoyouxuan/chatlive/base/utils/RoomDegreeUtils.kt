package com.rongtuoyouxuan.chatlive.base.utils

import android.view.View
import java.math.BigDecimal

object RoomDegreeUtils {

    fun getDegree(degree: Int):String {
        if(degree < 10000) {
            return degree.toString()
        }else{
            var cDegree = (degree.toDouble()/10000)
            val b = BigDecimal(cDegree)
            val f1: Float = b.setScale(1, BigDecimal.ROUND_HALF_UP).toFloat()
            return ("" + f1).plus("W")
        }
    }
}