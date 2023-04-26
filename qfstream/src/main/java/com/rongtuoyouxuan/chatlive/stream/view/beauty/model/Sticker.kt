package com.rongtuoyouxuan.chatlive.stream.view.beauty.model

data class Sticker(val id :Int,val path: String, val iconPath: String?, val name: String?,val remind:String?) {


    companion object {
        val defaultSticker = Sticker(-1,"","","","");
    }


}