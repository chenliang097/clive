package com.rongtuoyouxuan.chatlive.stream.view.layout

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.rongtuoyouxuan.chatlive.stream.R
open class FansLightInflateView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0):
    LinearLayout(context, attributeSet, defStyleAttr){

    private var txt:TextView? = null

    init {
        View.inflate(getContext(), R.layout.qf_stream_layout_fans_light,this)
        initView()
    }

    fun initView(){
        txt = findViewById(R.id.fangLightTxt)
    }

    fun setData(fansName:String){
        txt?.text = fansName
    }

}