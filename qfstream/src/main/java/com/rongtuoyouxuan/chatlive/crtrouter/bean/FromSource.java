package com.rongtuoyouxuan.chatlive.crtrouter.bean;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@StringDef({ISource.LIST_RECENT, ISource.USER_CENTER, ISource.FROM_LIVE_ROOM, ISource.FROM_PRIVATE_CHAT, ISource.FROM_GROUP_CHAT, ISource.FROM_PUSH, ISource.FROM_RANK, ISource.FROM_LIVE_MARQUEE, ISource.FROM_MAIN_TAB})
@Retention(RetentionPolicy.SOURCE)
public @interface FromSource {
}
