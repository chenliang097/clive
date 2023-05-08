package com.rongtuoyouxuan.chatlive.stream.view.dialog

import android.app.Dialog
import android.content.Context
import com.rongtuoyouxuan.chatlive.stream.R
import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.GridLayoutManager
import com.rongtuoyouxuan.chatlive.net2.RequestListener
import com.rongtuoyouxuan.chatlive.util.UIUtils
import com.rongtuoyouxuan.chatlive.biz2.model.user.ReportBean
import com.rongtuoyouxuan.chatlive.biz2.model.user.ReportRequest
import com.rongtuoyouxuan.chatlive.biz2.user.UserRelationBiz
import com.rongtuoyouxuan.chatlive.databus.DataBus
import com.rongtuoyouxuan.chatlive.live.view.adapter.ReportReasonAdapter
import com.rongtuoyouxuan.chatlive.net2.BaseModel
import com.rongtuoyouxuan.chatlive.util.LaToastUtil
import kotlinx.android.synthetic.main.qf_stream_dialog_report.*

class ReportDialog: Dialog{

    private var mContext:Context? = null
    private var tUserId:String? = ""
    private var tNickName:String? = ""
    private var roomId:String? = ""
    private var reportBarrage:String? = ""
    private var intType = arrayOf(1,2,3,4,5,6,7,8)
    private var intContent = arrayOf(R.string.stream_report_reson_1,
        R.string.stream_report_reson_2, R.string.stream_report_reson_3, R.string.stream_report_reson_4, R.string.stream_report_reson_5, R.string.stream_report_reson_6, R.string.stream_report_reson_7, R.string.stream_report_reson_8)
    private var lists: MutableList<ReportBean> = ArrayList()
    private var map:MutableMap<Int,Boolean>? = HashMap()
    private var reportTypePressed = 1

    constructor(mContext: Context, roomId:String?, tUserId:String?, tNickName:String?, reportBarrage:String?):super(mContext, R.style.commenDialogStyle){
        this.mContext = mContext
        this.roomId = roomId
        this.tUserId = tUserId
        this.tNickName = tNickName
        this.reportBarrage = reportBarrage
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.qf_stream_dialog_report)
        setWindowLocation()
        initView()
    }

    private fun setWindowLocation() {
        val win = this.window
        win?.decorView?.setPadding(0, 0, 0, 0)
        val lp = win?.attributes
        lp?.width = (UIUtils.screenWidth(mContext) * 0.8).toInt()
        lp?.height = WindowManager.LayoutParams.WRAP_CONTENT
        lp?.gravity = Gravity.CENTER
        win?.attributes = lp
        win?.decorView?.setBackgroundResource(R.drawable.corner_circle_white_bg)
//        win?.setWindowAnimations(R.style.CommonDialogStyleAnimation)
    }

    private fun initView() {
        for (i in 0..7){
            var bean = ReportBean()
            bean.data.report_type = intType[i]
            bean.data.report_content = mContext?.getString(intContent[i])
            lists.add(bean)
            map?.put(bean.data.report_type, false)
        }
        var adapter = ReportReasonAdapter()
        var layoutManager = GridLayoutManager(mContext, 3)
        reportResonRecyclerView.layoutManager = layoutManager
        adapter.setList(lists)
        reportResonRecyclerView.adapter = adapter

        adapter.setOnReportReasonListener(object :ReportReasonAdapter.OnReportReasonListener{
            override fun onClick(item: ReportBean) {
                lists.forEach {
                    if(item.data.report_type == it.data.report_type){
                        map?.put(item.data.report_type, true)
                        reportTypePressed = it.data.report_type
                    }else{
                        map?.put(item.data.report_type, false)
                    }
                }
                map?.let { adapter.updateMapData(it) }
            }

        })

        reportResonBtn.setOnClickListener {
            sendReportData(reportTypePressed)
        }
    }

    fun sendReportData(reportType:Int){
        var request =
            tUserId?.let {
                reportBarrage?.let { it1 ->
                    tNickName?.let { it2 ->
                        roomId?.let { it3 ->
                            ReportRequest(DataBus.instance().USER_ID, it, it1, reportType,
                                "", DataBus.instance().USER_NAME, it2, it3
                            )
                        }
                    }
                }
            }
        if (request != null) {
            UserRelationBiz.instance?.reportUser(null, request, object :RequestListener<BaseModel?>{
                override fun onSuccess(reqId: String?, result: BaseModel?) {
                    LaToastUtil.showShort(R.string.stream_report_suc)
                    dismiss()
                }

                override fun onFailure(reqId: String?, errCode: String?, msg: String?) {
                    LaToastUtil.showShort(msg)
                }

            })
        }
    }
}