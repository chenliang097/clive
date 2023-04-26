package com.rongtuoyouxuan.qfcommon.player

import android.content.Context
import android.media.MediaPlayer
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.blankj.utilcode.util.ThreadUtils
import com.rongtuoyouxuan.chatlive.biz2.model.gift.GiftLargerAnimEntity
import com.rongtuoyouxuan.chatlive.image.util.GlideUtils
import com.rongtuoyouxuan.libuikit.dp
import com.rongtuoyouxuan.qfcommon.R
import com.rongtuoyouxuan.qfcommon.player.exo.texture.EPlayerTextureView
import com.rongtuoyouxuan.qfcommon.player.exo.texture.EPlayerTextureView2
import com.rongtuoyouxuan.qfcommon.util.AnimUtil
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.libpag.*

/**
 *
 * date:2022/8/4-14:04
 * des: 播放座驾/礼物Mp$
 */
class GiftLargerAnimPlayView : FrameLayout {
    //mp4需要动态添加-礼物大动画/座驾
    private var containerMp4: FrameLayout? = null

    private var job: Job? = null
    private var delayTime = 200L
    private var playMp4View: EPlayerTextureView? = null
    private var mp4Player: SimpleExoPlayer? = null

    //兼容方案
    private var playMp4View2: EPlayerTextureView2? = null
    private var mp4Player2: MediaPlayer? = null

    //PAG
    private var pagPlayView: PAGView? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        inflate(context, R.layout.gift_big_animation_view_layout, this)
        containerMp4 = findViewById(R.id.containerMp4)
    }

    fun playCsr(
        activity: FragmentActivity,
        bean: GiftLargerAnimEntity,
        back: AnimationStatusCallBack,
        playTimes: Int = 1
    ) {
        if (activity.isFinishing) {
            return
        }
        if (job?.isActive == true || job != null) {
            job?.cancel()
            stopView()
        }
        job = activity.lifecycleScope.launch(Dispatchers.Main) {
            delay(delayTime)
            if (bean.filePath.endsWith(".pag", true)) {
                playPagCsr(activity, bean, back, playTimes)
            } else if (bean.filePath.endsWith(".mp4", true)) {
                playMp4Csr(activity, bean, back, playTimes) {
                    playMp4CsrByMediaPlayer(activity, bean, back, playTimes)
                }
            }
        }
        activity.lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                if (activity.lifecycle.currentState == Lifecycle.State.DESTROYED) {
                    stopView()
                }
            }
        })
    }

    private fun playPagCsr(
        activity: FragmentActivity,
        bean: GiftLargerAnimEntity,
        back: AnimationStatusCallBack,
        playTimes: Int = 1
    ) {
        val view =
            LayoutInflater.from(context)
                .inflate(R.layout.gift_pag_playview_car, containerMp4, false)
        containerMp4?.addView(view)
        containerMp4?.visibility = View.VISIBLE

        val rlHeader = view?.findViewById<LinearLayout>(R.id.rlHeader)
        completeCarHeader(bean.carLevel, rlHeader)
        val ivHeader = view?.findViewById<ImageView>(R.id.ivHeader)
        if (!TextUtils.isEmpty(bean.userHead)) {
            if (!activity.isDestroyed) {
                GlideUtils.loadCircleImage(
                    activity,
                    bean.userHead,
                    ivHeader,
                    R.drawable.icon_default_avatar
                )
            }
        }
        val tvTitle = view?.findViewById<TextView>(R.id.tvTitle)
        tvTitle?.text =
            bean.userName.plus(context.resources.getString(R.string.stream_live_enter_room))
        var currentViewWidth = 0f

        pagPlayView = view?.findViewById(R.id.pagPlayVIew)
        pagPlayView?.setScaleMode(PAGScaleMode.Zoom)
        if (playTimes > 1) {
            pagPlayView?.setRepeatCount(0)
        } else {
            pagPlayView?.setRepeatCount(1)
        }
        containerMp4?.let {
            currentViewWidth = it.measuredWidth.toFloat()
        }
        var isStart = false
        pagPlayView?.addListener(object : PAGView.PAGViewListener {
            override fun onAnimationStart(view: PAGView?) {
                if (isStart) {
                    return
                }
                isStart = true
                back.animationStart()
                rlHeader?.let {
                    it.visibility = View.VISIBLE
                    AnimUtil.startCarInAnimation(it, width = currentViewWidth) {
                    }
                }

                val duration = view?.duration() ?: 0L
                if (duration > 600000) {
                    activity.lifecycleScope.launch {
                        delay((duration - 600000) / 1000)
                        rlHeader?.let {
                            AnimUtil.startCarOutAnimation(
                                it,
                                width = currentViewWidth
                            ) {
                            }
                        }
                    }
                }
            }

            override fun onAnimationEnd(view: PAGView?) {
                pagPlayView?.removeListener(this)
                back.animationEnd()
                stopView()
            }

            override fun onAnimationCancel(view: PAGView?) {
            }

            override fun onAnimationRepeat(view: PAGView?) {
            }

            override fun onAnimationUpdate(view: PAGView?) {
            }
        })
//        val pagComposition = PAGComposition.Make(width, height - 150f.dp.toInt())
//        val pagFile = PAGFile.Load(bean.filePath)
//        val matrix = Matrix()
//        val scaleX: Float = width * 1.0f / pagFile.width()
//        matrix.preScale(scaleX, scaleX)
//        pagFile.setMatrix(matrix)
//        pagComposition.addLayer(pagFile)
//        pagPlayView?.composition = pagFile
        pagPlayView?.path = bean.filePath
        pagPlayView?.play()
    }

    private fun playMp4Csr(
        activity: FragmentActivity,
        bean: GiftLargerAnimEntity,
        back: AnimationStatusCallBack,
        playTimes: Int = 1,
        playError: () -> Unit
    ) {
        val view =
            LayoutInflater.from(context)
                .inflate(R.layout.gift_exo_playview_car, containerMp4, false)
        playMp4View = view?.findViewById(R.id.exoPlayVIew)
        playMp4View?.setMinusHeight(260f.dp.toInt())
        val rlHeader = view?.findViewById<LinearLayout>(R.id.rlHeader)
        completeCarHeader(bean.carLevel, rlHeader)
        val ivHeader = view?.findViewById<ImageView>(R.id.ivHeader)
        if (!TextUtils.isEmpty(bean.userHead)) {
            if (!activity.isDestroyed) {
                GlideUtils.loadCircleImage(
                    activity,
                    bean.userHead,
                    ivHeader,
                    R.drawable.icon_default_avatar
                )
            }
        }
        val tvTitle = view?.findViewById<TextView>(R.id.tvTitle)
        tvTitle?.text =
            bean.userName.plus(context.resources.getString(R.string.stream_live_enter_room))
        var currentViewWidth = 0f
        val manager = ExoPlayerManager(context).createSimpleExoPlayer()
        if (playTimes > 1) {
            manager.player?.repeatMode = Player.REPEAT_MODE_ALL
        } else {
            manager.player?.repeatMode = Player.REPEAT_MODE_OFF
        }
        manager.listener = object : ExoPlayerManager.PlayerListener {
            override fun onPlayerStateReady(duration: Long) {
                back.animationStart()
                rlHeader?.let {
                    it.visibility = View.VISIBLE
                    AnimUtil.startCarInAnimation(it, width = currentViewWidth) {
                    }
                }

                if (duration > 600) {
                    activity.lifecycleScope.launch(Dispatchers.Main) {
                        delay(duration - 600)
                        rlHeader?.let {
                            AnimUtil.startCarOutAnimation(
                                it,
                                width = currentViewWidth
                            ) {
                            }
                        }
                    }
                }
            }

            override fun onPlayerStateStop() {
                stopView()
                back.animationEnd()
            }

            override fun onPlayerError() {
                stopView()
                playError()
            }
        }
        containerMp4?.let {
            currentViewWidth = it.measuredWidth.toFloat()
        }
        mp4Player = manager.player
        playMp4View?.setSimpleExoPlayer(mp4Player)
        manager.setDataSource(bean.filePath)
        manager.prepareAsync()
        containerMp4?.addView(view)
        playMp4View?.onResume()
        containerMp4?.visibility = View.VISIBLE
    }

    private fun playMp4CsrByMediaPlayer(
        activity: FragmentActivity,
        bean: GiftLargerAnimEntity,
        back: AnimationStatusCallBack,
        playTimes: Int = 1,
    ) {
        val view =
            LayoutInflater.from(context)
                .inflate(R.layout.gift_exo_playview_car_2, containerMp4, false)
        playMp4View2 = view?.findViewById(R.id.exoPlayVIew)
        playMp4View2?.setMinusHeight(260f.dp.toInt())
        val rlHeader = view?.findViewById<LinearLayout>(R.id.rlHeader)
        completeCarHeader(bean.carLevel, rlHeader)
        val ivHeader = view?.findViewById<ImageView>(R.id.ivHeader)
        if (!TextUtils.isEmpty(bean.userHead)) {
            if (!activity.isDestroyed) {
                GlideUtils.loadCircleImage(
                    activity,
                    bean.userHead,
                    ivHeader,
                    R.drawable.icon_default_avatar
                )
            }
        }
        val tvTitle = view?.findViewById<TextView>(R.id.tvTitle)
        tvTitle?.text =
            bean.userName.plus(context.resources.getString(R.string.stream_live_enter_room))
        var currentViewWidth = 0f
        val manager = MediaPlayerManager().createMediaPlayer()
        manager.player?.isLooping = playTimes > 1
        manager.listener = object : MediaPlayerManager.PlayerListener {
            override fun onPlayerStateStop() {
                stopView()
                back.animationEnd()
            }

            override fun onPlayerError() {

            }
        }
        containerMp4?.let {
            currentViewWidth = it.measuredWidth.toFloat()
        }
        mp4Player2 = manager.player
        playMp4View2?.setSimpleExoPlayer(mp4Player2)
        if (bean.filePath.isNotEmpty()) {
            mp4Player2?.setDataSource(bean.filePath)
        }
        mp4Player2?.setOnPreparedListener {
            back.animationStart()
            mp4Player2?.start()

            rlHeader?.let {
                it.visibility = View.VISIBLE
                AnimUtil.startCarInAnimation(it, width = currentViewWidth) {
                }
            }
            val duration = mp4Player2?.duration ?: 0
            if (duration > 600) {
                if (duration > 600) {
                    activity.lifecycleScope.launch(Dispatchers.Main) {
                        delay((duration - 600).toLong())
                        rlHeader?.let {
                            AnimUtil.startCarOutAnimation(
                                it,
                                width = currentViewWidth
                            ) {
                            }
                        }
                    }
                }
            }
        }
        mp4Player2?.prepareAsync()

        containerMp4?.addView(view)
        playMp4View2?.onResume()
        containerMp4?.visibility = View.VISIBLE
    }

    fun playGift(
        activity: FragmentActivity,
        bean: GiftLargerAnimEntity,
        back: AnimationStatusCallBack?,
        playTimes: Int = 1
    ) {
        if (activity.isFinishing) {
            return
        }
        if (job?.isActive == true || job != null) {
            stopView()
        }
        job = activity.lifecycleScope.launch(Dispatchers.Main) {
            delay(delayTime)
            if (bean.filePath.endsWith(".pag", true)) {
                playPagGift(bean, back, playTimes)
            } else if (bean.filePath.endsWith(".mp4", true)) {
                playMp4Gift(bean, back, playTimes) {
                    playMp4GiftByMediaPlayer(bean, back, playTimes)
                }
            }
        }
        activity.lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                if (activity.lifecycle.currentState == Lifecycle.State.DESTROYED) {
                    stopView()
                }
            }
        })
    }

    private fun playPagGift(
        bean: GiftLargerAnimEntity,
        back: AnimationStatusCallBack?,
        playTimes: Int = 1,
    ) {
        pagPlayView = PAGView(context)
        pagPlayView?.layoutParams = LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        pagPlayView?.setScaleMode(PAGScaleMode.Zoom)
        containerMp4?.addView(pagPlayView)
        containerMp4?.visibility = View.VISIBLE
        pagPlayView?.path = bean.filePath
        if (playTimes > 1) {
            pagPlayView?.setRepeatCount(0)
        } else {
            pagPlayView?.setRepeatCount(1)
        }

        pagPlayView?.addListener(object : PAGView.PAGViewListener {
            override fun onAnimationStart(view: PAGView?) {
                back?.animationStart()
            }

            override fun onAnimationEnd(view: PAGView?) {
                pagPlayView?.removeListener(this)
                back?.animationEnd()
                stopView()
            }

            override fun onAnimationCancel(view: PAGView?) {
            }

            override fun onAnimationRepeat(view: PAGView?) {
            }

            override fun onAnimationUpdate(view: PAGView?) {
            }
        })

        pagPlayView?.play()
    }

    private fun playMp4Gift(
        bean: GiftLargerAnimEntity,
        back: AnimationStatusCallBack?,
        playTimes: Int = 1,
        playError: () -> Unit
    ) {
        val view =
            LayoutInflater.from(context)
                .inflate(R.layout.gift_exo_playview_mp4, containerMp4, false)
        playMp4View = view?.findViewById(R.id.exoPlayVIew)
        val manager = ExoPlayerManager(context).createSimpleExoPlayer()
        if (playTimes > 1) {
            manager.player?.repeatMode = Player.REPEAT_MODE_ALL
        } else {
            manager.player?.repeatMode = Player.REPEAT_MODE_OFF
        }
        manager.listener = object : ExoPlayerManager.PlayerListener {
            override fun onPlayerStateReady(duration: Long) {
                back?.animationStart()
            }

            override fun onPlayerStateStop() {
                stopView()
                back?.animationEnd()
            }

            override fun onPlayerError() {
                stopView()
                playError()
            }
        }
        mp4Player = manager.player
        playMp4View?.setSimpleExoPlayer(mp4Player)
        manager.setDataSource(bean.filePath)
        manager.prepareAsync()
        containerMp4?.addView(view)
        playMp4View?.onResume()
        containerMp4?.visibility = View.VISIBLE
    }

    private fun playMp4GiftByMediaPlayer(
        bean: GiftLargerAnimEntity,
        back: AnimationStatusCallBack?,
        playTimes: Int = 1,
    ) {
        val view =
            LayoutInflater.from(context)
                .inflate(R.layout.gift_exo_playview_mp4_2, containerMp4, false)
        playMp4View2 = view?.findViewById(R.id.exoPlayVIew)
        val manager = MediaPlayerManager().createMediaPlayer()
        manager.player?.isLooping = playTimes > 1
        manager.listener = object : MediaPlayerManager.PlayerListener {
            override fun onPlayerStateStop() {
                stopView()
                back?.animationEnd()
            }

            override fun onPlayerError() {

            }
        }
        mp4Player2 = manager.player
        playMp4View2?.setSimpleExoPlayer(mp4Player2)
        if (bean.filePath.isNotEmpty()) {
            mp4Player2?.setDataSource(bean.filePath)
        }
        mp4Player2?.setOnPreparedListener {
            back?.animationStart()
            mp4Player2?.start()
        }
        mp4Player2?.prepareAsync()
        containerMp4?.addView(view)
        playMp4View?.onResume()
        containerMp4?.visibility = View.VISIBLE
    }

    private fun completeCarHeader(carLevel: Int, rlLayout: LinearLayout?) {
        when (carLevel) {
            1 -> {
                rlLayout?.setBackgroundResource(R.drawable.shape_gradient_20c0ff_0f20c0ff)
            }
            2 -> {
                rlLayout?.setBackgroundResource(R.drawable.shape_gradient_f4439a_0ff4439a)
            }
            3 -> {
                rlLayout?.setBackgroundResource(R.drawable.shape_gradient_a400bd_0fa400bd)
            }
            4 -> {
                rlLayout?.setBackgroundResource(R.drawable.shape_gradient_ff9b00_0fff9b00)
            }
            else -> {
                rlLayout?.setBackgroundResource(R.drawable.shape_gradient_20c0ff_0f20c0ff)
            }
        }
    }

    fun stopView() {
        ThreadUtils.runOnUiThread {
            playMp4View?.onPause()
            playMp4View2?.onPause()
            //job?.cancel()

            containerMp4?.visibility = View.INVISIBLE
            containerMp4?.removeAllViews()
            mp4Player = null

            mp4Player?.clearVideoSurface()
            mp4Player?.stop()
            mp4Player?.release()
            mp4Player = null
            playMp4View = null

            mp4Player2?.release()
            mp4Player2 = null
            playMp4View2 = null

            pagPlayView?.stop()
            pagPlayView = null
        }
    }

    interface AnimationStatusCallBack {
        //播放动画开始
        fun animationStart()

        //播放动画结束
        fun animationEnd()
    }
}