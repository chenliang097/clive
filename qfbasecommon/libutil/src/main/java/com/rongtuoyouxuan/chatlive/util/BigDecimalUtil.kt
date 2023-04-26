package com.rongtuoyouxuan.chatlive.util

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*
import kotlin.math.pow

/**
 * 
 * date:2022/8/1-10:27
 * des:
 */
object BigDecimalUtil {

    fun decimalFormat(f: Long, pattern: String = "###.##"): String {
        val df = DecimalFormat(pattern, DecimalFormatSymbols(Locale.US))
        df.roundingMode = RoundingMode.DOWN
        return df.format(f)
    }

    fun decimalFormat(f: Float, pattern: String = "###.##"): String {
        val df = DecimalFormat(pattern, DecimalFormatSymbols(Locale.US))
        df.roundingMode = RoundingMode.DOWN
        return df.format(f)
    }

    fun decimalFormat(f: Double, pattern: String = "###.##"): String {
        val df = DecimalFormat(pattern, DecimalFormatSymbols(Locale.US))
        df.roundingMode = RoundingMode.DOWN
        return df.format(f)
    }

    // 格式化金额(10,000)
    fun decimalFormat2(f: Long, pattern: String = ",###,###"): String {
        val df = DecimalFormat(pattern, DecimalFormatSymbols(Locale.US))
        df.roundingMode = RoundingMode.DOWN
        return df.format(f)
    }

    // 格式化金额(10,000)
    fun decimalFormat2(f: Int, pattern: String = ",###,###"): String {
        val df = DecimalFormat(pattern, DecimalFormatSymbols(Locale.US))
        df.roundingMode = RoundingMode.DOWN
        return df.format(f)
    }

    // 格式化金额(10,000)
    fun decimalFormat2(f: Float, pattern: String = ",###,###"): String {
        val df = DecimalFormat(pattern, DecimalFormatSymbols(Locale.US))
        df.roundingMode = RoundingMode.DOWN
        return df.format(f)
    }

    fun decimalFormat3(f: Float, pattern: String = "###"): String {
        val df = DecimalFormat(pattern, DecimalFormatSymbols(Locale.US))
        df.roundingMode = RoundingMode.DOWN
        return df.format(f)
    }

    // 2个数相乘
    fun decimalMultiply(number: String, number2: String): Double {
        return try {
            val bd = BigDecimal(number)
            val bd1 = BigDecimal(number2)
            bd.multiply(bd1).toDouble()
        } catch (e: Exception) {
            0.0
        }
    }

    // 格式化点赞数，播放数等数字
    fun setNumber(number: Long): String {
        return when {
            number <= 0 -> {
                "0"
            }
            number < 1000 -> {
                decimalFormat(number.toFloat())
            }
            number < 1000 * 1000 -> {
                decimalFormat(number.toFloat() / 1000f).plus("K")
            }
            else -> {
                decimalFormat(number.toFloat() / 1000f / 1000f).plus("M")
            }
        }
    }

    // 格式化游戏--hot-余额
    fun setGameNumber(number: Long): String {
        if (number <= 0) {
            return "0"
        }
        if (number <= 10 * 10000) {
            return number.toString()
        }
        return when {
            number >= 10.0.pow(12.0) -> {
                decimalFormat(number / 10.0.pow(12.0)).plus("T")
            }
            number >= 10.0.pow(9.0) -> {
                decimalFormat(number / 10.0.pow(9.0)).plus("B")
            }
            number >= 10.0.pow(6.0) -> {
                decimalFormat(number / 10.0.pow(6.0)).plus("M")
            }
            else -> {
                decimalFormat(number / 10.0.pow(3.0)).plus("K")
            }
        }
    }
}