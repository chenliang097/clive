package com.rongtuoyouxuan.chatlive.crtutil.util

import android.text.InputFilter
import android.text.Spanned

class LengthFilter(val max: Int, val method: (() -> Unit)? = null) : InputFilter {

    override fun filter(source: CharSequence, start: Int, end: Int, dest: Spanned,
                        dstart: Int, dend: Int): CharSequence? {
        var keep = max - (dest.length - (dend - dstart))
        return if (keep <= 0) {
            // 超长了
            method?.invoke()
            ""
        } else if (keep >= end - start) {
            null // keep original
        } else {
            keep += start
            if (Character.isHighSurrogate(source[keep - 1])) {
                --keep
                if (keep == start) {
                    return ""
                }
            }
            //超长了
            method?.invoke()
            source.subSequence(start, keep)
        }
    }
}