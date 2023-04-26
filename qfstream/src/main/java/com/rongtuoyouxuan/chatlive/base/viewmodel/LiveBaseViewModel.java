package com.rongtuoyouxuan.chatlive.base.viewmodel;

import android.app.Application;

import com.rongtuoyouxuan.chatlive.base.utils.LiveStreamInfo;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

//直播专用
public class LiveBaseViewModel extends ViewModel {
    private LiveStreamInfo liveStreamInfo;

    public LiveBaseViewModel(@NonNull LiveStreamInfo liveStreamInfo) {
        this.liveStreamInfo = liveStreamInfo;
    }
    public Application getApplication() {
        return liveStreamInfo.application;
    }
    public String getPublishUrl() {
        return liveStreamInfo.publishUrl;
    }
    public void setPublishUrl(String publishUrl) {
        liveStreamInfo.publishUrl = publishUrl;
    }

    public String getStreamType() {
        return liveStreamInfo.streamType;
    }
    public void setStreamType(String streamType) {
        liveStreamInfo.streamType = streamType;
    }

    public String getChannelId() {
        return liveStreamInfo.channelId;
    }

    public void setChannelId(String channelId) {
        liveStreamInfo.channelId = channelId;
    }

    public String getStreamId() {
        return liveStreamInfo.streamId;
    }

    public void setStreamId(String streamId) {
        liveStreamInfo.streamId = streamId;
    }

    public String getRoomId() {
        return liveStreamInfo.roomId;
    }

    public void setRoomId(String roomId) {
        liveStreamInfo.roomId = roomId;
    }

    public String getAnchorId() {
        return liveStreamInfo.anchorId;
    }

    public void setAnchorId(String anchorId) {
        liveStreamInfo.anchorId = anchorId;
    }

    public String getStreamToken() {
        return liveStreamInfo.pushToken;
    }

    public void setStreamToken(String pushToken) {
        liveStreamInfo.pushToken = pushToken;
    }

    public String getStreamerType() {
        return liveStreamInfo.streamerType;
    }

    public void setStreamerType(String type) {
        liveStreamInfo.streamerType = type;
    }
}
