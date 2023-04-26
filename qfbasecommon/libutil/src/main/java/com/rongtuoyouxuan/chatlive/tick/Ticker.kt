package com.rongtuoyouxuan.chatlive.tick

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rongtuoyouxuan.chatlive.log.PLog
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class Ticker {
    companion object {
        const val TAG = "Ticker"
    }

    private val compositeDisposable = CompositeDisposable()

    /**
     * 定时
     */
    fun delayNewCountDown(delayMs: Long): TickerJob {
        val tickerLiveData = MutableLiveData<TickerData>()
        val disposable = Observable.timer(delayMs, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.computation())
            .doOnSubscribe {
                PLog.d(TAG, "ticker ${System.currentTimeMillis()} start ")
                tickerLiveData.postValue(TickerData(delayMs, delayMs, 0, TickerState.START))
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                PLog.d(TAG, "ticker ${System.currentTimeMillis()} next $it")
                tickerLiveData.postValue(TickerData(delayMs, 0, 1, TickerState.TICKER))
            }, {
                it.printStackTrace()
            }, {
                PLog.d(TAG, "ticker ${System.currentTimeMillis()} complete ")
                tickerLiveData.postValue(TickerData(delayMs, 0, 1, TickerState.COMPLETE))
            })
        compositeDisposable.add(disposable)
        return TickerJob(tickerLiveData, disposable)
    }

    /**
     * 开始new的倒计时
     */
    @JvmOverloads
    fun startNewCountDown(durationSec: Long, period: Long = 1, unit: TimeUnit = TimeUnit.SECONDS): TickerJob {
        val tickerLiveData = MutableLiveData<TickerData>()
        val disposable = Observable.interval(0, period, unit)
            .take(if (durationSec >= Long.MAX_VALUE) durationSec else durationSec + 1)
            .subscribeOn(Schedulers.computation())
            .doOnSubscribe {
                PLog.d(TAG, "ticker ${System.currentTimeMillis()} start ")
                tickerLiveData.postValue(TickerData(durationSec, durationSec, 0, TickerState.START))
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                PLog.d(TAG, "ticker ${System.currentTimeMillis()} next $it")
                tickerLiveData.postValue(TickerData(durationSec, durationSec - it, it, TickerState.TICKER))
            }, {
                it.printStackTrace()
            }, {
                PLog.d(TAG, "ticker ${System.currentTimeMillis()} complete ")
                tickerLiveData.postValue(TickerData(durationSec, 0, durationSec, TickerState.COMPLETE))
            })
        compositeDisposable.add(disposable)
        return TickerJob(tickerLiveData, disposable)
    }

    /**
     * 取消所有ticker
     */
    fun cancel() {
        compositeDisposable.dispose()
    }

    /**
     * ticker job
     */
    class TickerJob(val liveData: LiveData<TickerData>, private val disposable: Disposable) {

        fun observe(
            lifecycleOwner: LifecycleOwner,
            start: () -> Unit = {},
            ticker: (data: TickerData) -> Unit = {},
            complete: () -> Unit = {}
        ) {
            liveData.observe(lifecycleOwner, {
                when (it.state) {
                    TickerState.START -> start()
                    TickerState.TICKER -> ticker(it)
                    TickerState.COMPLETE -> complete()
                }
            })
        }

        fun cancel() {
            if (!disposable.isDisposed) {
                disposable.dispose()
            }
        }
    }

    data class TickerData(
        /*总时长*/
        val duration: Long,
        /*剩余时长*/
        val residue: Long,
        /* 用时，滴答了多少下 */
        val ticker: Long,
        /*当前状态*/
        val state: TickerState
    )

    enum class TickerState { START, TICKER, COMPLETE }
}