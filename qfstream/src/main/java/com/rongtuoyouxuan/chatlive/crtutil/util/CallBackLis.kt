package com.rongtuoyouxuan.chatlive.crtutil.util

/**
 * 
 * date:2022/8/3-20:20
 * des:
 */
interface ICallFileResult {
    fun onSuccess()
    fun onFail() {}
}

interface ICallProgressFileResult {
    fun onProgress(progress: Int) {}
    fun onSuccess()
    fun onFail() {}
}