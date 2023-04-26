package com.rongtuoyouxuan.libuikit.dialog.factory

import android.app.Dialog
import android.os.Bundle
import android.view.View

interface DialogRender {
    fun getLayoutResId(): Int
    fun render(savedInstanceState: Bundle?, rootView: View, dialog: Dialog) = Unit
    fun onAttachedToWindow() = Unit
    fun onDetachedFromWindow() = Unit
    fun dismiss() = Unit
}