package com.rongtuoyouxuan.chatlive.base.utils

import android.Manifest
import android.annotation.SuppressLint
import com.tbruyelle.rxpermissions2.RxPermissions

class CameraAndMicPermissonUtlils {
    private var rxPermissions:RxPermissions? = null
    private var permissonListener:PermissonListener? = null

    companion object{
        val instance : CameraAndMicPermissonUtlils  by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED){
            CameraAndMicPermissonUtlils()
        }
    }

    @SuppressLint("CheckResult")
    fun requestPermisson(rxPermissions: RxPermissions, permissonListener:PermissonListener){
        if (isHasPermisson(rxPermissions)){
            permissonListener?.onISHaveListener()
        }else{
            rxPermissions!!.requestEachCombined(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
                .subscribe { permission ->
                    if (permission.granted) {
                        permissonListener.onISHaveListener()
                    } else if (permission.shouldShowRequestPermissionRationale) {
                    } else {
                        permissonListener.onErrorListener()
                    }
                }
        }
    }

    fun isHasPermisson(rxPermissions: RxPermissions):Boolean{
        if(rxPermissions != null) {
            return (rxPermissions?.isGranted(Manifest.permission.CAMERA) && rxPermissions?.isGranted(Manifest.permission.RECORD_AUDIO)!!)
        }
        return false
    }

    fun setPermissonListener(permissonListener:PermissonListener){
        this.permissonListener = permissonListener
    }

    interface PermissonListener{
        fun onISHaveListener()
        fun onErrorListener()
    }

}