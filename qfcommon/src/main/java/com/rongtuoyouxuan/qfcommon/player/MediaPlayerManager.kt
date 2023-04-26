package com.rongtuoyouxuan.qfcommon.player

import android.media.MediaPlayer

/**
 *
 * date:2022/9/2-19:55
 * des: 与EXO兼容
 */
class MediaPlayerManager {

    var player: MediaPlayer? = null
    private var url: String? = ""
    var listener: PlayerListener? = null

    fun createMediaPlayer(): MediaPlayerManager {
        player = MediaPlayer().apply {
            setOnCompletionListener {
                this@MediaPlayerManager.stop()
                listener?.onPlayerStateStop()
                this@MediaPlayerManager.release()
            }

            setOnErrorListener { _, what, extra ->
                listener?.onPlayerError()
                this@MediaPlayerManager.stop()
                this@MediaPlayerManager.release()
                false
            }
        }
        return this
    }

//    fun setDataSource(path: String?) {
//        if (url?.isNotEmpty() == true) {
//            this.url = path
//            player?.setDataSource(url)
//        }
//    }

//    fun prepareAsync() {
//        player?.setOnPreparedListener {
//            Log.d(
//                "MediaPlayerManager",
//                ">>>setOnPreparedListener>>>"
//            )
//            listener?.onPlayerStateReady()
//            player?.start()
//        }
//        player?.prepareAsync()
//    }

    private fun stop() {
        if (player?.isPlaying == true) {
            player?.stop()
            url = null
        }
    }

    private fun release() {
        player?.release()
        player = null
    }

    interface PlayerListener {
        fun onPlayerStateStop()

        fun onPlayerError()
    }
}