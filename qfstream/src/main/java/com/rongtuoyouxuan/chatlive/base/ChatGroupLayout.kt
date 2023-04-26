package com.rongtuoyouxuan.chatlive.base

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rongtuoyouxuan.chatlive.biz2.model.im.ImErrorCode
import com.rongtuoyouxuan.chatlive.biz2.model.im.msg.BaseMsg
import com.rongtuoyouxuan.chatlive.biz2.model.im.msg.MessageContent
import com.rongtuoyouxuan.chatlive.biz2.model.im.msg.cmdMsg.LiveJoinRoomMsg
import com.rongtuoyouxuan.chatlive.biz2.model.im.msg.textmsg.TxtMsg
import com.rongtuoyouxuan.chatlive.databus.DataBus
import com.rongtuoyouxuan.chatlive.stream.R
import com.rongtuoyouxuan.chatlive.stream.view.adapter.LivePublicChatAreaListAdapter
import com.rongtuoyouxuan.chatlive.util.LaToastUtil
import com.rongtuoyouxuan.libuikit.dp
import kotlinx.android.synthetic.main.layout_chat_group.view.*

/**
 * 
 * date:2022/8/25-17:08
 * des: 聊天区域
 */
class ChatGroupLayout : FrameLayout {
    private val MAX_RETRY = 5
    private var retryJoinGroupCount = 0

    private var chatAdapter: LivePublicChatAreaListAdapter? = null
    private var roomId: String? = ""
    private var loginUID = 0L
    private var isChatSuc = false

    var isBack = true

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        inflate(context, R.layout.layout_chat_group, this)
    }

    fun init(activity: FragmentActivity) {
        loginUID = DataBus.instance().userInfo.value?.user_info?.userId ?: 0L

        rvList?.apply {
            layoutManager =
                LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    outRect.bottom = 6f.dp.toInt()
                }
            })
            chatAdapter = LivePublicChatAreaListAdapter(activity)
            adapter = chatAdapter
        }

        btSend?.setOnClickListener {
            val msg = etMessage?.text?.toString()
            if (msg?.isEmpty() == true) {
                LaToastUtil.showShort(R.string.message_room_chat_empty)
                return@setOnClickListener
            }
            sendTxtMsg(roomId, msg!!)
        }

        etMessage?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            @SuppressLint("SetTextI18n")
            override fun afterTextChanged(s: Editable?) {
                tvLength?.text = "${s?.length ?: 0}/40"
            }

        })
    }

    fun initChat(activity: FragmentActivity, rId: String?) {
        if (rId?.isEmpty() == true) {
            return
        }
        if (isChatSuc) {
            return
        }
        roomId = rId
        joinChatRoom(roomId)

//        IMSocketImpl.getInstance()
//            .chatRoom(roomId).joinLiveRoomCallback?.observe(activity) { entity ->
//                //插入一条加入直播间消息并播放座驾
//                addJoinConvention(entity)
//            }
//
//        IMSocketImpl.getInstance().chatRoom(roomId).textCallback.observe(activity) {
//            val baseMsg = BaseMsg()
//            baseMsg.messageType = MessageContent.MSG_TEXT.type
//            baseMsg.body = it
//            addMessageToList(baseMsg)
//
//            if (loginUID != it.from?.userId?.toLong()) {
//                LiveEventBus.get<TxtMsg>("game_web_chat_txt_msg").post(it)
//            }
//        }
    }

    //发送公聊区聊天信息
    private fun sendTxtMsg(roomId: String?, msg: String) {
        if (roomId?.isEmpty() == true) {
            LaToastUtil.showShort(R.string.send_fail)
            return
        }
        etMessage?.setText("")
        val txtMsg = TxtMsg()
        txtMsg.content = msg
//        IMSocketImpl.getInstance()
//            .sendChatRoomMessage(roomId, txtMsg, DataBus.instance().userInfo.value,
//                object : ISendMsgCallBack {
//                    override fun onSuccess(
//                        message: Message,
//                        msgBody: BaseMsg.MsgBody?
//                    ) {
//                        IMSocketImpl.getInstance().chatRoom(roomId).textCallback.setValue(txtMsg)
//                    }
//
//                    override fun onFail(errorCode: ImErrorCode) {
//                        LaToastUtil.showShort(R.string.send_fail)
//                    }
//                })
    }

    //进入直播间消息
    private fun addJoinConvention(msg: LiveJoinRoomMsg?) {
        val baseMsg = BaseMsg()
        baseMsg.messageType = MessageContent.MSG_LIVE_JOIN.type
        baseMsg.body = msg
        addMessageToList(baseMsg)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun addMessageToList(message: BaseMsg) {
        chatAdapter?.addItem(message)
        val size = chatAdapter?.data?.size ?: 1
        rvList?.layoutManager?.scrollToPosition(size - 1)
    }

    private fun addTxtMsg(message: String) {
        val baseMsg = BaseMsg()
        baseMsg.messageType = MessageContent.MSG_TEXT.type
        val txtMsg = TxtMsg()
        txtMsg.type = 1
        txtMsg.content = message
        baseMsg.body = txtMsg
        addMessageToList(baseMsg)
    }

    private fun joinChatRoom(roomId: String?) {
        isBack = false
//        IMSocketImpl.getInstance().joinChatRoom(false, roomId, object : IOperateCallback {
//            override fun onSuccess() {
//                isChatSuc = true
//            }
//
//            override fun onFail(code: String, desc: String) {
//                isChatSuc = false
//                if (retryJoinGroupCount < MAX_RETRY) {
//                    retryJoinGroupCount++
//                    handler.postDelayed(
//                        { joinChatRoom(roomId) },
//                        (retryJoinGroupCount * 1000).toLong()
//                    )
//                }
//                addTxtMsg(context.getString(R.string.socket_join_chatroom_ing_tips))
//            }
//        })
    }

    fun quitChatRoom(rid: String) {
        roomId = ""
        isChatSuc = false
        isBack = true
        chatAdapter?.setNewInstance(arrayListOf())
//        IMSocketImpl.getInstance().quitChatRoom(rid, object : IOperateCallback {
//            override fun onSuccess() {
//
//            }
//
//            override fun onFail(code: String?, desc: String?) {
//            }
//        })
    }
}