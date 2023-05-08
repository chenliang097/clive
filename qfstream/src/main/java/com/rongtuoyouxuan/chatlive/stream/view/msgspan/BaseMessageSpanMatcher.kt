package com.rongtuoyouxuan.chatlive.stream.view.msgspan

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.text.*
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import android.widget.TextView
import com.blankj.utilcode.util.ConvertUtils
import com.rongtuoyouxuan.chatlive.base.utils.LiveRoomHelper
import com.rongtuoyouxuan.chatlive.biz2.model.im.BaseRoomMessage
import com.rongtuoyouxuan.chatlive.biz2.model.im.msg.BaseMsg
import com.rongtuoyouxuan.chatlive.biz2.model.im.msg.MessageContent
import com.rongtuoyouxuan.chatlive.biz2.model.im.msg.textmsg.GiftMsg
import com.rongtuoyouxuan.chatlive.databus.DataBus
import com.rongtuoyouxuan.chatlive.log.upload.ULog
import com.rongtuoyouxuan.chatlive.stream.R
import com.rongtuoyouxuan.chatlive.stream.view.layout.FansLightInflateView
import com.rongtuoyouxuan.chatlive.stream.view.layout.IdentifyInflateView
import com.rongtuoyouxuan.chatlive.stream.view.layout.LevelInflateView
import com.rongtuoyouxuan.libuikit.widget.ClickiTextSpan
import com.rongtuoyouxuan.libuikit.widget.ClickiTextSpan.SpanClick
import com.rongtuoyouxuan.libuikit.widget.CustomImageSpan
import com.rongtuoyouxuan.qfcommon.widget.UrlDrawableLoader
import com.rongtuoyouxuan.qfcommon.widget.level.LiveUserIconHelper
import kotlin.math.ceil

abstract class BaseMessageSpanMatcher(context: Context) : IMessageSpanMatcher {

    protected var mContext: Context = context
    protected var uid = DataBus.instance().userInfo.value?.user_info?.userId

//    protected var imViewModel: IMLiveViewModel = ViewModelProviders.of((mContext as FragmentActivity)).get(
//        IMLiveViewModel::class.java)
//    protected var builder: UserCardDialog.Builder? = null


    open fun createMsgBackground(textView: TextView, common: BaseRoomMessage) {
        textView.setBackgroundResource(R.drawable.room_chat_bg_normal)
    }
    open fun createMsgBackgroundWithBlue(textView: TextView, common: BaseMsg) {
        textView.setBackgroundResource(R.drawable.room_chat_bg_normal_blue)
    }

    /**
     * 添加角色图标  0-游客 1-普通用户 10-主播 11-管理员 99-超管
     */
    open fun addRoleDrawableSpan(spanString: SpannableStringBuilder, roomManger: Boolean, superManger: Boolean, imageHeight: Int) {

        when (roomManger) {
            true -> {
                var res:Int = LiveUserIconHelper.getIdentity(10)
                addImageSpan(imageHeight, spanString, ConvertUtils.drawable2Bitmap(mContext.getDrawable(res)))
            }
        }
        when (superManger) {
            true -> {
                var res:Int = LiveUserIconHelper.getIdentity(10)
                addImageSpan(imageHeight, spanString, ConvertUtils.drawable2Bitmap(mContext.getDrawable(res)))
            }
        }
    }

    /**
     * 给item添加拍好顺序的所有图标(Guard 消息类型特殊)
     */
    open fun addCommonIcons(textView: TextView, common: BaseRoomMessage, spanString: SpannableStringBuilder, imageHeight: Int) {
        if(common.isRoomAdmin || common.isSuperAdmin) {
            addRoleDrawableSpan(spanString, common.isRoomAdmin, common.isSuperAdmin, imageHeight)
        }
    }

    /**
     * 添加nickname
     */
    open fun addNickName(textView: TextView, common:BaseRoomMessage, spanString: SpannableStringBuilder, nameColor: Int, addMaohao: Boolean) {
        addTextSpan(spanString, textView, common.userName + if (addMaohao) ": " else " ", nameColor, ClickiTextSpan.SpanClick {
            LiveRoomHelper.openUserCardVM.post(common.userIdStr)
            ULog.d("clll", "addNickName")
        })
    }

    /**
     * 添加nickname 礼物
     */
    open fun addNickName(textView: TextView, common:BaseRoomMessage, spanString: SpannableStringBuilder, addMaohao: Boolean) {
        addTextSpan(spanString, textView, common.userName + if (addMaohao) ": " else " ", mContext.resources.getColor(
            R.color.c_stream_msg_common), ClickiTextSpan.SpanClick {
            LiveRoomHelper.openUserCardVM.post(common.userIdStr)
        })
    }

    /**
     * 添加礼物缩略图
     */
    @SuppressLint("CheckResult")
    open fun addGiftImg(imgHeight: Int, textView:TextView, spanString: SpannableStringBuilder, giftMsg:GiftMsg) {
        val span = SpannableString("$")
        val urlDrawableLoader = UrlDrawableLoader(mContext, textView, imgHeight, giftMsg.giftImgUrl)
        val ispan = CustomImageSpan(urlDrawableLoader.drawable)
        span.setSpan(ispan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spanString.append(span)
        spanString.append(" ")
//        addKnownContent(textView, "×" + giftMsg.num.toString(), spanString, R.color.white)

    }

    open fun addKnownContent(textView: TextView, contentResID: Int, spanString: SpannableStringBuilder, contentColor: Int) {
        addKnownContent(textView, mContext.resources.getString(contentResID), spanString, contentColor)
    }

    open fun addKnownContent(textView: TextView, content: String, spanString: SpannableStringBuilder, contentColor: Int) {
        addTextSpan(spanString, textView, content, mContext.resources.getColor(contentColor))
    }

    //判断是否是自己
    open fun isSelf(message: BaseRoomMessage): Boolean {
        if(message.userId != null){
            return uid.toString() == message.userIdStr
        }
        return false
    }

    /**
     * 没有改变过的drawable大小
     */
    open fun addDrawableImageSpan(builder: SpannableStringBuilder, resId: Int) {
        if (resId == 0) {
            return
        }
        val span = SpannableString("$")
        val d = mContext.resources.getDrawable(resId)
        d.setBounds(0, 0, d.intrinsicWidth, d.intrinsicHeight)
        val ispan = CustomImageSpan(d)
        span.setSpan(ispan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        builder.append(span)
        builder.append(" ")
    }

    /**
     * 拼接 bitmap 到文本内容内
     */
    open fun addImageSpan(mImageHeight: Int, builder: SpannableStringBuilder, bitmap: Bitmap) {
        val span = SpannableString("$")
        val d: Drawable = BitmapDrawable(mContext.resources, bitmap)
        val ratio = d.intrinsicWidth.toFloat() / d.intrinsicHeight
        d.setBounds(0, 0, (mImageHeight * ratio).toInt(), mImageHeight)
        val ispan = CustomImageSpan(d)
        span.setSpan(ispan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        builder.append(span)
        builder.append(" ")
    }

    /**
     * 拼接文本 不支持点击事件
     */
    open fun addTextSpan(builder: SpannableStringBuilder, textView: TextView, text: CharSequence, text_color: Int) {
        addTextSpan(builder, textView, text, text_color, null)
    }

    /**
     * 拼接文本 支持文本的点击事件
     */
    open fun addTextSpan(builder: SpannableStringBuilder, textView: TextView, text: CharSequence, text_color: Int, click: SpanClick?) {
        val userName = SpannableString(text)
        if (click != null) {
            userName.setSpan(ClickiTextSpan(text_color, click),
                    0, text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        } else {
            userName.setSpan(ForegroundColorSpan(text_color),
                    0, text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        val fontSize = textView.textSize
        userName.setSpan(
                AbsoluteSizeSpan(fontSize.toInt()), 0, text.length,
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        val span = StyleSpan(Typeface.NORMAL)
        userName.setSpan(span, 0, text.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        builder.append(userName)
    }

    /**
     * View转成Bitmap
     *
     * @param view 要转的View
     * @return Bitmap
     */
    open fun makeView2Bitmap(view: View, height: Int): Bitmap? {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.UNSPECIFIED))
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
        view.buildDrawingCache()
        return view.drawingCache
    }

    /**
     * 获取当前TextView 文字绘制高度
     */
    open fun getFontHeight(tv: TextView): Int {
        val paint = Paint()
        paint.textSize = tv.textSize
        val fm = paint.fontMetrics
        return ceil(fm.bottom - fm.top.toDouble()).toInt()
    }

    /**
     * 获取指定文本的宽度
     * @param text
     * @param textSize
     * @return
     */
    open fun getTextWidth(text: String, tv: TextView): Int {
        if (TextUtils.isEmpty(text)) {
            return 0
        }
        val paint = Paint() //创建一个画笔对象
        paint.textSize = tv.textSize //设置画笔的文字大小
        return paint.measureText(text).toInt() //利用画笔丈量指定文本的宽度
    }
}