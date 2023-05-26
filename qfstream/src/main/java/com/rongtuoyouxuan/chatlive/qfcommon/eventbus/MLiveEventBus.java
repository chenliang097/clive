package com.rongtuoyouxuan.chatlive.qfcommon.eventbus;

import android.content.Context;

import com.jeremyliao.liveeventbus.LiveEventBus;
import com.jeremyliao.liveeventbus.core.Observable;

/**
 * 提供对{@link LiveEventBus}的轻量级封装。
 */
public final class MLiveEventBus {
    private MLiveEventBus() {
    }

    public static void init(Context context) {
        LiveEventBus.config().setContext(context);
        //设置当activity 可见的时候再响应事件
        LiveEventBus.config().lifecycleObserverAlwaysActive(false);
        LiveEventBus.config().autoClear(true);
    }

    public static <T> Observable<T> get(LiveEventKey<T> liveEventKey) {
        return (Observable<T>) LiveEventBus.get(liveEventKey.getId());
    }

    public static <T> Observable<T> get(String key, Class<T> type) {
        return LiveEventBus.get(key, type);
    }

    //使消息处于活跃状态
    public static <T> void lifecycleObserverAlwaysActive(LiveEventKey<T> liveEventKey) {
        LiveEventBus.config(liveEventKey.getId())
                .lifecycleObserverAlwaysActive(true);
    }
}
