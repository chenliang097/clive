import com.rongtuoyouxuan.libuikit.audio.PlayerContract

/*
*Create by {Mr秦} on 2021/1/30
*/
abstract class SimplePlayerCallBack : PlayerContract.PlayerCallback {
    override fun onPreparePlay(){}
    override fun onStartPlay(){}
    override fun onPlayProgress(mills: Long){}
    override fun onCompletion(){}
    override fun onPausePlay(){}
    override fun onSeek(mills: Long){}
    override fun onError(throwable: Exception?){}
}