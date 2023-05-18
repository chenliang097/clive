package com.rongtuoyouxuan.chatlive.stream.view.activity

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import cn.dreamtobe.kpswitch.util.KeyboardUtil
import com.rongtuoyouxuan.chatlive.stream.R
import com.rongtuoyouxuan.chatlive.stream.view.layout.RoomSetMaskInputLayout.OnSetMaskWordListener
import com.rongtuoyouxuan.chatlive.stream.viewmodel.AnchorManagerViewModel
import com.rongtuoyouxuan.chatlive.util.KeyBoardUtils
import com.rongtuoyouxuan.chatlive.util.StringUtils
import com.rongtuoyouxuan.chatlive.util.UIUtils
import kotlinx.android.synthetic.main.qf_stream_layout_set_mask_input.*

class RoomSetMaskWordInputDialog : Dialog, View.OnClickListener {
    private var anchorManagerViewModel: AnchorManagerViewModel? = null
    private var roomId = ""
    private var sceneId = ""
    private var mContext:Context
    private var onSetMaskWordListener:OnSetMaskWordListener? = null

    constructor(context: Context, onSetMaskWordListener:OnSetMaskWordListener):super(context, R.style.commenDialogStyleNoDim){
        this.mContext = context
        this.onSetMaskWordListener = onSetMaskWordListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rt_dialog_mask_word_input)
        setWindowLocation()
        initView()
        initListener()
        initData()
        initObserver()
    }

    private fun setWindowLocation() {
        try {
            val win = this.window
            win?.decorView?.setPadding(0, 0, 0, 0)
            val lp = win?.attributes
            lp?.width = (UIUtils.screenWidth(context))
            lp?.height = UIUtils.dip2px(context, 52)
            lp?.gravity = Gravity.BOTTOM
            win?.attributes = lp
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun initView() {
    }

    private fun initListener() {
        room_message_sent.setOnClickListener(this)
    }

    private fun initObserver() {
        anchorManagerViewModel = obtainStreamViewModel()
    }

    private fun initData() {
//        anchorManagerSetWordsLayout.setData(roomId)
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.room_message_sent->{
                val msg: String = room_message_input.text.toString()
                if (StringUtils.isEmpty(msg)) {
                    Toast.makeText(context, R.string.message_room_chat_empty, Toast.LENGTH_SHORT).show()
                } else {
                    onSetMaskWordListener?.onSetMaskWord(msg)
                    clearMessage()
                }
            }
        }

    }

    fun clearMessage() {
        //清除缓存的聊天内容
        room_message_input.setText("")
        ll_msg_container.visibility = View.GONE
        KeyboardUtil.hideKeyboard(room_message_input)
    }

    private fun obtainStreamViewModel(): AnchorManagerViewModel {
        return ViewModelProviders.of(mContext as FragmentActivity).get(AnchorManagerViewModel::class.java)
    }

    override fun dismiss() {
        KeyBoardUtils.hideSoftInput(mContext as Activity?)
        super.dismiss()

    }
}