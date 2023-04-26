package com.rongtuoyouxuan.chatlive.base.view.model;

import androidx.annotation.IntDef;

public class SendEvent {

    public static final int TYPE_MESSAGE = 1;
    public static final int TYPE_FLYCHAT = 2;
    public static final int TYPE_NOTICE = 3;
    public static final int TYPE_AITE = 4;
    public @event int Event;
    public Object object;

    public SendEvent(int event, Object object) {
        Event = event;
        this.object = object;
    }

    @IntDef({TYPE_MESSAGE, TYPE_FLYCHAT,TYPE_NOTICE,TYPE_AITE})
    public @interface event {}
}
