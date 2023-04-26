package com.rongtuoyouxuan.libuikit

object ChatMoreListener {
    var mChatMoreDialogListener: ChatMoreListener1? = null

    fun setChatMoreDialogListener(mChatMoreDialogListener: ChatMoreListener1?) {
        ChatMoreListener.mChatMoreDialogListener = mChatMoreDialogListener
    }

    fun getChatMoreDialogListener() {
        mChatMoreDialogListener
    }

    fun onBlack(data: Boolean) {
        if(mChatMoreDialogListener != null){
            mChatMoreDialogListener?.onBlack(data)
        }
    }

    fun onTop(data: Boolean) {
        if(mChatMoreDialogListener != null){
            mChatMoreDialogListener?.onTop(data)
        }
    }

    interface ChatMoreListener1{
        fun onTop(boolean: Boolean)
        fun onBlack(boolean: Boolean)
    }
}