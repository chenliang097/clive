package com.rongtuoyouxuan.chatlive.stream.view.beauty.model

data class ResourcePath(val id :Int, val path: String, val iconPath: String?, val name: String?, val remind:String?) {


    companion object {
        val defaultSticker = ResourcePath(-1,"","","","");
    }


}