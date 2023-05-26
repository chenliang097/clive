package com.rongtuoyouxuan.chatlive.qfcommon.notification;

import android.app.Dialog
import android.content.Context;
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Html
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.blankj.utilcode.util.ConvertUtils
import com.rongtuoyouxuan.chatlive.crtimage.util.GlideUtils
import com.rongtuoyouxuan.chatlive.stream.R

/**
 * 通知的自定义Dialog
 */
class NotificationDialog(
    context: Context,
    var title: String,
    var content: String,
    var imgUrl: String,
) : Dialog(context, R.style.dialog_notifacation_top) {

    private val TAG = "NotificationDialog"

    private var mListener: OnNotificationClick? = null
    private var mStartY: Float = 0F
    private var mView: View? = null
    private var mHeight: Int? = 0

    init {
        mView = LayoutInflater.from(context).inflate(R.layout.common_layout_notifacation, null)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mView!!)
        window?.setGravity(Gravity.TOP)
        val layoutParams = window?.attributes
        // 获取屏幕宽度
        val d = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            context.display!!
        } else {
            window?.windowManager?.defaultDisplay!!
        }
//        layoutParams?.y = ConvertUtils.dp2px(38.5f)
//        layoutParams?.width = ViewGroup.LayoutParams.MATCH_PARENT
        layoutParams?.width = d.width - ConvertUtils.dp2px(15.0f)
        layoutParams?.height = ViewGroup.LayoutParams.WRAP_CONTENT
        layoutParams?.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        window?.attributes = layoutParams
        window?.setWindowAnimations(R.style.dialog_animation)
        //按空白处不能取消
        setCanceledOnTouchOutside(false)
        //初始化界面数据
        initData()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun initData() {
        val tvTitle = findViewById<TextView>(R.id.tv_title)
        val tvContent = findViewById<TextView>(R.id.tv_content)
        val imageView = findViewById<ImageView>(R.id.imageView)
        if (title.isNotEmpty()) {
            tvTitle.text =
                Html.fromHtml("<font color='#222222' size='28'>" + context.getString(R.string.live_start_notice)
                        + "</font><font color='#8000FF' size='28'> $title</font>")
        }

        if (content.isNotEmpty()) {
            tvContent.text = content
        }

        if (imgUrl.isNotEmpty()) {
            GlideUtils.loadCircleWithBorderImage(context,
                imgUrl,
                imageView,
                3,
                context.getColor(R.color.c_7627FF),
                R.mipmap.chat_ic_onlive)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (isOutOfBounds(event)) {
                    mStartY = event.y
                    Log.d(TAG, "mStartY : " + mStartY)
                }
            }

            MotionEvent.ACTION_UP -> {
                if (mStartY > 0 && isOutOfBounds(event)) {
                    val moveY = event.y
                    Log.d(TAG, "moveY : " + moveY + " mStartY - moveY : " + (mStartY - moveY))
                    if (Math.abs(mStartY - moveY) >= 20) { //滑动超过20认定为滑动事件
//                        //Dialog消失
//                        Toast.makeText(context, "滑出", Toast.LENGTH_SHORT).show()
                    } else {                //认定为点击事件
                        //Dialog的点击事件
                        mListener?.onClick()
                    }
                    dismiss()
                }
            }
        }
        return false
    }

    /**
     * 点击是否在范围外
     */
    private fun isOutOfBounds(event: MotionEvent): Boolean {
        val yValue = event.y
        Log.d(TAG, "event : " + MotionEvent.actionToString(event.action)
                + " yValue : " + yValue + " mHeight : " + mHeight)
//        if (yValue > 0 && yValue <= (mHeight ?: (0 + 40))) {
//            return true
//        }
        if (Math.abs(yValue) <= (mHeight ?: 40)) {
            return true
        }
        return false
    }


    private fun setDialogSize() {
        mView?.addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
            mHeight = v?.height
        }
    }

    /**
     * 显示Dialog但是不会自动退出
     */
    fun showDialog() {
        if (!isShowing) {
            show()
            setDialogSize()
        }
    }

    /**
     * 显示Dialog,3000毫秒后自动退出
     */
    fun showDialogAutoDismiss() {
        if (!isShowing) {
            show()
            setDialogSize()
            //延迟3000毫秒后自动消失
            Handler(Looper.getMainLooper()).postDelayed({
                if (isShowing) {
                    dismiss()
                }
            }, 3000L)
        }
    }

    //处理通知的点击事件
    fun setOnNotificationClickListener(listener: OnNotificationClick) {
        mListener = listener
    }

    interface OnNotificationClick {
        fun onClick()
    }
}