package com.rongtuoyouxuan.chatlive.live.view.adapter

import android.widget.RelativeLayout
import android.widget.TextView
import com.rongtuoyouxuan.chatlive.stream.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.rongtuoyouxuan.chatlive.crtbiz2.model.user.ReportBean

class ReportReasonAdapter : BaseQuickAdapter<ReportBean, BaseViewHolder> {

    private var map:MutableMap<Int,Boolean>? = null
    private var onReportReasonListener:OnReportReasonListener? = null

    constructor():super(R.layout.qf_stream_adapter_report_reason)

    override fun convert(holder: BaseViewHolder, item: ReportBean) {
        var layout = holder.getView<RelativeLayout>(R.id.reportReasonLayout)
        var content = holder.getView<TextView>(R.id.reportReasonTxt)
        content.text = item.data.report_content
        if(map?.get(item.data.report_type) == true){
            layout.setBackgroundResource(R.drawable.rt_common_btn)
            content.setTextColor(context.resources.getColor(R.color.white))
        }else{
            layout.setBackgroundResource(R.drawable.rt_bg_report_content)
            content.setTextColor(context.resources.getColor(R.color.c_50_white))
        }
        layout.setOnClickListener {
            onReportReasonListener?.onClick(item)
        }
    }

    fun updateMapData(map: MutableMap<Int,Boolean>){
        this.map = map
        notifyDataSetChanged()
    }

    fun setOnReportReasonListener(onReportReasonListener:OnReportReasonListener){
        this.onReportReasonListener = onReportReasonListener
    }

    interface OnReportReasonListener{
        fun onClick(item: ReportBean)
    }

}