package com.rongtuoyouxuan.libgift.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.blankj.utilcode.util.ThreadUtils
import com.rongtuoyouxuan.chatlive.biz2.model.gift.GiftEntity
import com.rongtuoyouxuan.chatlive.image.util.GlideUtils
import com.rongtuoyouxuan.libgift.R
import com.rongtuoyouxuan.libuikit.dp
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit

/**
 * 
 * date:2022/8/3-09:46
 * des:礼物通道管理，500ms获取一次数据
 */
class GiftSideManager {
    //是否继续添加播放
    private var isHanding = false

    //所有礼物数据
    private val giftList = LinkedList<GiftEntity>()

    //每隔200ms去一次数据
    private var msgTimer: ScheduledExecutorService? = null
    private var futures: ScheduledFuture<*>? = null
    private var timeTask: Runnable? = null

    //x个通道
    private val hashMap: HashMap<Int, View> = hashMapOf()

    private val handler = ThreadUtils.getMainHandler()

    //item默认宽度
    private var defaultItemWidth = 0f

    fun init(context: Context, lifecycle: Lifecycle, rootView: ViewGroup, sidebarNum: Int) {
        giftList.clear()
        defaultItemWidth = context.resources.getDimension(R.dimen.dp_190)
        createSide(context, rootView, sidebarNum)
        startReceive()
        val value = LifecycleEventObserver { _, _ ->
            if (lifecycle.currentState == Lifecycle.State.DESTROYED) {
                stopReceive()
                rootView.removeAllViews()
            }
        }
        lifecycle.removeObserver(value)
        lifecycle.addObserver(value)
    }

    private fun startReceive() {
        if (isHanding) {
            return
        }
        isHanding = true
        timeTask = Runnable {
            synchronized(giftList) {
                if (giftList.isEmpty()) {
                    return@Runnable
                }
                hashMap.forEach {
                    val tag = it.value.tag
                    if (null == tag) {
                        handler?.post {
                            startAnimation(it.value)
                        }
                    }
                }
            }
        }
        msgTimer = Executors.newScheduledThreadPool(2)
        futures = msgTimer?.scheduleAtFixedRate(
            timeTask, 500, 500, TimeUnit.MILLISECONDS
        )
    }

    private fun stopReceive() {
        isHanding = false
        if (giftList.size > 0) {
            giftList.clear()
        }
        futures?.cancel(true)
        msgTimer = null
        futures = null
        timeTask = null
        if (hashMap.size > 0) {
            hashMap.clear()
        }
        handler.removeCallbacksAndMessages(null)
    }

    //暂停添加并清除数据
    fun pauseReceive() {
        isHanding = false
        giftList.clear()
    }

    //恢复添加
    fun resumeReceive() {
        isHanding = true
    }

    //自己优先加入队列
    @Synchronized
    fun addChildGiftFirst(item: GiftEntity?) {
        if (!isHanding) {
            return
        }
        if (null == item) {
            return
        }
        giftList.offerFirst(item)
    }

    @Synchronized
    fun addChildGift(item: GiftEntity?) {
        if (!isHanding) {
            return
        }
        if (null == item) {
            return
        }
        giftList.offer(item)
    }

    private fun createSide(context: Context, rootView: ViewGroup, sidebarNum: Int) {
        val list = arrayListOf<View>()
        for (i in 0 until sidebarNum) {
            val itemView =
                LayoutInflater.from(context).inflate(R.layout.gift_item_sidebar, rootView, false)
            rootView.addView(
                itemView,
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    leftMargin = 10.dp.toInt()
                    bottomMargin = 10.dp.toInt()
                })
            itemView.tag = null
            itemView.visibility = View.INVISIBLE
            list.add(itemView)
        }
        list.reversed().forEachIndexed { index, view ->
            hashMap[index] = view
        }
    }

    @SuppressLint("SetTextI18n")
    private fun startAnimation(itemView: View) {
        if (giftList.isEmpty()) {
            return
        }
        val entity = giftList.poll() ?: return
        itemView.tag = entity
        val ivHeader = itemView.findViewById<ImageView>(R.id.ivHeader)
        if (entity.userHead?.isNotEmpty() == true) {
            GlideUtils.loadCircleImage(
                ivHeader.context,
                entity.userHead,
                ivHeader,
                R.drawable.icon_default_avatar
            )
        }

        val tvName = itemView.findViewById<TextView>(R.id.tvName)
        tvName?.text = "${entity.userName}"

        val tvTip = itemView.findViewById<TextView>(R.id.tvTip)
        tvTip?.text = tvTip.context.getString(R.string.gift_to_to, entity.giftName)

        val ivGiftImage = itemView.findViewById<ImageView>(R.id.ivGiftImage)
        if (entity.thumbnail?.isNotEmpty() == true) {
            GlideUtils.loadCircleImage(
                ivHeader.context,
                entity.thumbnail,
                ivGiftImage,
                R.drawable.icon_gift_default
            )
        }

        val tvNum = itemView.findViewById<TextView>(R.id.tvNum)
        tvNum?.text = "${entity.giftNum}"

        itemView.visibility = View.VISIBLE

        var width = itemView.width.toFloat()
        if (width <= defaultItemWidth) {
            width = defaultItemWidth
        }
        com.rongtuoyouxuan.qfcommon.util.AnimUtil.startInAnimation(itemView, width = width) {
            com.rongtuoyouxuan.qfcommon.util.AnimUtil.startNumberAnimation(tvNum)
        }
        handler.postDelayed({
            com.rongtuoyouxuan.qfcommon.util.AnimUtil.startOutAnimation(itemView, width = width) {
                itemView.tag = null
                itemView.visibility = View.INVISIBLE
            }
        }, 2000)
    }

    //数字丛0到num递增
    private fun startCountAnimation(num: Int, tv: TextView) {
        val animator = ValueAnimator.ofInt(0, num)
        animator.duration = 1000
        animator.addUpdateListener { animation -> tv.text = animation?.values?.toString() }
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                animator.removeListener(this)
            }
        })
        animator.start()
    }
}