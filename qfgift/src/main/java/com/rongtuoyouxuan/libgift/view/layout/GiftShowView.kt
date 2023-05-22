package com.rongtuoyouxuan.libgift.view.layout

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.makeramen.roundedimageview.RoundedImageView
import com.rongtuoyouxuan.chatlive.biz2.model.gift.GiftEntity
import com.rongtuoyouxuan.chatlive.databus.DataBus
import com.rongtuoyouxuan.chatlive.image.util.GlideUtils
import com.rongtuoyouxuan.chatlive.log.upload.ULog
import com.rongtuoyouxuan.chatlive.util.ViewTools
import com.rongtuoyouxuan.libgift.AnimListener
import com.rongtuoyouxuan.libgift.BaseEffects
import com.rongtuoyouxuan.libgift.GiftAnimator
import com.rongtuoyouxuan.libgift.R
import com.rongtuoyouxuan.libgift.SwitchAnimationListener
import com.rongtuoyouxuan.libgift.SwitchLayout
import com.rongtuoyouxuan.libgift.interfaces.IGiftShowManager
import com.rongtuoyouxuan.libgift.widget.GiftColorTextView
import java.util.concurrent.ConcurrentLinkedQueue

/**
 * Created by mah on 2021/6/1.
 */
class GiftShowView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    companion object {
        const val TAG = "GiftShowView"
    }

    val taskQueue = ConcurrentLinkedQueue<ConcurrentLinkedQueue<GiftEntity>>()

    private var rlOne: RelativeLayout
    private var civAvatarOne: RoundedImageView
    private var tvUserNameOne: TextView
    private var tvGiftInfoOne: TextView
    private var ivGiftImgOne: ImageView
    private var mtvGiftNumOne: GiftColorTextView
    private var ivGiftInfoOne: ImageView

    var hostId = ""
    private var oneAnim: Animation? = null
    private var oneGiftShow = false
    private var currentHandleTask: ConcurrentLinkedQueue<GiftEntity>? = null
    private var currentGiftShow: GiftEntity? = null
    private var isWaitOutStatus = false
    private var startBgOutAnimRunnable: Runnable = Runnable { startBgOutAnim() }

    init {
        val rootView = View.inflate(context, R.layout.pl_libgift_gift_show, this)
        rlOne = rootView.findViewById(R.id.rl_gift_one)
        civAvatarOne = rootView.findViewById(R.id.civ_user_avatar_one)
        tvUserNameOne = rootView.findViewById(R.id.tv_user_name_one)
        tvGiftInfoOne = rootView.findViewById(R.id.tv_gift_info_one)
        ivGiftImgOne = rootView.findViewById(R.id.tv_gift_img_one)
        mtvGiftNumOne = rootView.findViewById(R.id.tv_gift_num_one)
        ivGiftInfoOne = rootView.findViewById(R.id.rb_gift_info_one)
        resetViewState()
    }

    fun action() {
        if (isWaitOutStatus && isHaveComboGift(currentGiftShow)) { //连击等待状态, 唤醒连击
            removeCallbacks(startBgOutAnimRunnable)
            checkPlayMagicQueue(currentGiftShow!!) {
                postDelayed(startBgOutAnimRunnable, IGiftShowManager.GIFT_EXIT_DURATION_DELAY)
            }
        }
        if (oneGiftShow) return//正在显示礼物
        ULog.d(TAG, "action 前 $taskQueue")
        currentHandleTask = taskQueue.peek() ?: return
        val oneGift: GiftEntity = currentHandleTask?.peek() ?: return
        ULog.d(TAG, "action 后 $taskQueue")
        oneGiftShow = true
        currentGiftShow = oneGift
        setGiftInfo(oneGift)
        startPlayBgInAnim(oneGift)
    }

    /**
     * 设置礼物信息
     *
     * @param giftShow
     */
    private fun setGiftInfo(giftShow: GiftEntity) {
        GlideUtils.loadCircleImage(
            DataBus.instance().appContext,
            giftShow.userHead,
            civAvatarOne,
            R.drawable.icon_default_avatar
        )
        tvUserNameOne.text = giftShow.userName
        tvGiftInfoOne.text = context.getString(R.string.gift_to_to, giftShow.giftName)
        giftShow.thumbnail?.let { showImg(ivGiftImgOne, it) }
    }

    /**
     * 播放背景进场动画
     *
     * @param giftShow
     * @return
     */
    private fun startPlayBgInAnim(giftShow: GiftEntity) {
        oneAnim = GiftAnimator.createFlyFromLtoR(IGiftShowManager.GIFT_BG_IN_DURATION, BaseEffects.overInter)
        oneAnim?.setAnimationListener(object : AnimListener() {
            override fun onAnimationStart(animation: Animation) {
                ViewTools.VISIBLE(rlOne)
            }

            override fun onAnimationEnd(animation: Animation) {
                startPlayMagicTextAnim(giftShow)
            }
        })
        rlOne.startAnimation(oneAnim)

    }

    /**
     * 开始播放数字增加动画
     */
    private fun startPlayMagicTextAnim(giftShow: GiftEntity) {
        currentGiftShow = giftShow
        val scaleAnimation = ScaleAnimation(
            IGiftShowManager.MagicTextBigScale,
            IGiftShowManager.MagicTextNormalScale,
            IGiftShowManager.MagicTextBigScale,
            IGiftShowManager.MagicTextNormalScale,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        scaleAnimation.duration = IGiftShowManager.GIFT_NUM_ADD_DURATION
        scaleAnimation.setAnimationListener(object : AnimListener() {
            override fun onAnimationEnd(animation: Animation) {
                checkPlayMagicQueue(giftShow) {
                    isWaitOutStatus = true
                    postDelayed(startBgOutAnimRunnable, IGiftShowManager.GIFT_EXIT_DURATION_DELAY)
                }
            }
        })
        giftShow.giftNum?.let { mtvGiftNumOne.startAnimation(it, scaleAnimation) }
    }

    private fun checkPlayMagicQueue(giftShow: GiftEntity, callback: () -> Unit) {
        ULog.d(TAG, "startPlayMagicTextAnim 前 $currentHandleTask")
        if (currentHandleTask?.size ?: 0 > 2) {//避免缓存过大
            currentHandleTask?.poll()
        }
        val task = currentHandleTask?.firstOrNull { it.giftNum!! > giftShow.giftNum!! }
        ULog.d(TAG, "startPlayMagicTextAnim 后 $currentHandleTask")
        if (task == null) {
            callback()
        } else {
            startPlayMagicTextAnim(task)
        }
    }

    private fun isHaveComboGift(giftShow: GiftEntity?): Boolean {
        giftShow ?: return false
        val task = currentHandleTask?.firstOrNull {
            it.giftNum!! > giftShow.giftNum!! &&
                    it.userId == giftShow.userId
        }

        return task != null
    }

    /**
     * 礼物退出动画
     */
    private fun startBgOutAnim() {
        val builder = SwitchLayout.Builder()
        builder.view = rlOne
        builder.duration = IGiftShowManager.GIFT_EXIT_DURATION
        builder.animationListener = object : SwitchAnimationListener {
            override fun onAnimationStart() {
                isWaitOutStatus = false
            }

            override fun onAnimationEnd() {
                resetViewState()
                finishTask()
                action()
            }
        }
        SwitchLayout.getSlideToLeft(builder)

    }

    /**
     * 重置控件状态
     */
    fun resetViewState() {
        ViewTools.INVISIBLE(rlOne)
        mtvGiftNumOne.text = ""
        rlOne.tag = ""
    }

    private fun showImg(imageView: ImageView, url: String) {
        Glide.with(DataBus.instance().appContext).load(url)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.default_gift_icon)
                    .error(R.drawable.default_gift_icon)
                    .dontAnimate()
            )
            .into(imageView)
    }

    private fun finishTask() {
        ULog.d(TAG, "finishTask")
        taskQueue.poll()
        currentHandleTask = null
        oneGiftShow = false
        isWaitOutStatus = false
    }
}