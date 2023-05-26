package com.rongtuoyouxuan.chatlive.base.view.activity

import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.rongtuoyouxuan.chatlive.crtbiz2.model.stream.LiveChatRoomUserEntity
import com.rongtuoyouxuan.chatlive.live.view.dialog.ContributionMemberDialog
import com.rongtuoyouxuan.chatlive.stream.R
import com.lxj.xpopup.XPopup
import kotlinx.coroutines.*

/**
 * 
 * date:2022/8/17-16:12
 * des: 直播间-处理右上角在线人数
 */
class LiveRoomOnlineLogic(
    private val activity: FragmentActivity,
    private val streamID: String? = "",
    private val anchorId: String? = "",
    private val infoIncomeLayout: LinearLayout? = null,
    private val layout: LinearLayout? = null
) {
    private var tvOnline4: TextView? = null
    private var allList = ArrayList<LiveChatRoomUserEntity>()
    private val newList = ArrayList<LiveChatRoomUserEntity>()
    private var total: String = "0"

    private var job: Job? = null

    fun init(fragment: Fragment? = null) {
        allList.clear()
        tvOnline4 = layout?.findViewById(R.id.tvOnline4)
        layout?.setOnClickListener {
            openCmDialog(1)
        }

        infoIncomeLayout?.setOnClickListener {
            openCmDialog()
        }

        initData(fragment)
    }

    fun initData(fragment: Fragment? = null) {
        val lifecycleOwner = fragment ?: activity

//        if (streamID?.isNotEmpty() == true && anchorId?.isNotEmpty() == true) {
//            StreamBiz.liveChatroomUsers(streamID, 1, 5, anchorId, activity, object :
//                RequestListener<LiveChatRoomUserData> {
//                override fun onSuccess(reqId: String?, result: LiveChatRoomUserData?) {
//                    if (null != result?.data) {
//                        val list = result.data.list
//                        if (list?.isNotEmpty() == true) {
//                            allList.clear()
//                            allList.addAll(list.filter {
//                                it.userId != (anchorId.toLongOrNull() ?: 0L)
//                            })
//                            total = "${result.data.total}"
//                            sort()
//                        } else {
//                            sort()
//                        }
//                    } else {
//                        sort()
//                    }
//                }
//
//                override fun onFailure(reqId: String?, errCode: String?, msg: String?) {
//                    sort()
//                }
//            })
//        }
    }

    fun refresh(
        isJoin: Boolean = true,
        uid: Long,
        nickName: String,
        avatar: String,
        score: Long,
        total: Long
    ) {
        val entity = LiveChatRoomUserEntity(nickName, uid, avatar, 0, score)
        if (isJoin) {
            val filter = allList.filter {
                it.userId == uid
            }
            if (filter.isEmpty()) {
                allList.add(entity)
            } else {
                allList.remove(entity)
                allList.add(entity)
            }
        } else {
            allList.remove(entity)
        }
        this.total = "$total"
        sort()
    }

    private fun getTotal(): Long = (total.toLongOrNull() ?: 0L)

    private fun sort() {

        val totalLong = this.total.toLongOrNull() ?: 0
        if (totalLong > 4) {
            tvOnline4?.text = if (totalLong >= 999) "999+" else "$totalLong"
        }
    }

    private fun openCmDialog(position: Int = 0) {
        streamID?.toLongOrNull()?.let {
            XPopup.Builder(activity)
                .enableDrag(false)
                .asCustom(
                    ContributionMemberDialog(
                        activity,
                        it,
                        anchorId,
                        getTotal(),
                        position
                    )
                )
                .show()
        }
    }
}