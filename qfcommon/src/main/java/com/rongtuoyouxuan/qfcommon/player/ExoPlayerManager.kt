package com.rongtuoyouxuan.qfcommon.player

import android.content.Context
import android.util.Log
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory

/**
 * 
 * date:2022/8/5-09:53
 * des:
 */
class ExoPlayerManager(val context: Context) {

    var player: SimpleExoPlayer? = null
    private var mMediaSource: MediaSource? = null
    private var url: String? = ""

    var listener: PlayerListener? = null

    fun createSimpleExoPlayer(): ExoPlayerManager {
        player = SimpleExoPlayer.Builder(
            context,
            DefaultRenderersFactory(context),
        ).build().apply {
            playWhenReady = true
            repeatMode = Player.REPEAT_MODE_OFF
            addListener(object : Player.Listener {
                override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                    if (playbackState == Player.STATE_READY) {
                        listener?.onPlayerStateReady(player?.duration ?: 0L)
                    }
                    if (playbackState == Player.STATE_ENDED || playbackState == Player.STATE_IDLE) {
                        player?.removeListener(this)
                        this@ExoPlayerManager.stop()
                        listener?.onPlayerStateStop()
                        this@ExoPlayerManager.release()
                    }
                }

                override fun onPlayerError(error: PlaybackException) {
                    super.onPlayerError(error)
                    Log.d(
                        "ExoPlayerManager",
                        "onPlayerError>>>${error}"
                    )
                    listener?.onPlayerError()
                    player?.removeListener(this)
                    this@ExoPlayerManager.stop()
                    this@ExoPlayerManager.release()
                }
            })
        }
        return this
    }

    fun setDataSource(url: String?) {
        if (url?.isNotEmpty() == true) {
            this.url = url
            mMediaSource = ProgressiveMediaSource.Factory(DefaultDataSourceFactory(context))
                .createMediaSource(MediaItem.fromUri(url))
        }
    }

    fun prepareAsync() {
        mMediaSource?.let {
            player?.setMediaSource(it)
            player?.prepare()
            player?.playWhenReady = true
        }
    }

    fun start() {
        player?.playWhenReady = true
    }

    fun stop() {
        player?.stop()
        url = null
    }

    fun pause() {
        player?.playWhenReady = false
    }

    fun release() {
        player?.clearVideoSurface()
        player?.release()
        player = null
    }

    interface PlayerListener {
        fun onPlayerStateReady(duration: Long)

        fun onPlayerStateStop()

        fun onPlayerError()
    }
}