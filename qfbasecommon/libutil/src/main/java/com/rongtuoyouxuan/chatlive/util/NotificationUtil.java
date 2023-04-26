package com.rongtuoyouxuan.chatlive.util;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

/**
 * Created by fengjian on 2019/1/10.
 */

public class NotificationUtil extends Notification.Builder {
    public static final String CHANNEL_ID_UPDATE = "update";
    public static final String CHANNEL_NAME_UPDATE = "升级";
    public static final String CHANNEL_ID_RECORD = "record";
    public static final String CHANNEL_NAME_RECORD = "录屏";
    private Context mContext;

    public NotificationUtil(Context context) {
        super(context);
        mContext = context;
    }

    public Notification.Builder setFixChannelId(String channelId, String channelName) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setChannelId(createChannelID(channelId, channelName));
        }
        return this;
    }

    @TargetApi(26)
    public String createChannelID(String channelID, String channelName) {
        NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_DEFAULT);
        channel.enableLights(false);
        channel.enableVibration(false);
        channel.setVibrationPattern(new long[]{0});
        channel.setSound(null, null);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(channel);
        }
        return channelID;
    }
}
