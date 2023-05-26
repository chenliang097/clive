package com.rongtuoyouxuan.chatlive.rtstream.streaming;

import android.opengl.EGLContext;

public interface IBaseStreaming {
    void setPushUrl(String url);

    void setStreamId(String url);

    void setRoomInfo(String roomId, String token, String userId);

    void startStreaming();

    void stopStreaming();

    void removePublishCdnUrl();

    float getDrawFrameRate();

    float getSendFrameRate();

    void setStreamStateListener(StreamStateChangedListener listener);

    void setStreamNetworkSpeedListener(StreamNetworkSpeedListener listener);

    void setStreamTextureListener(StreamTextureListener listener);

    interface StreamStateChangedListener {
        void onPlaySuccess(String id);

        void onPlayError();

        void getPushStreamInfo(String logs);

        void pushStreamHeartbeat(String id, String msg);

        void pushStreamStatus(String id);

//        void onGetFlashlightInfo(boolean enable);
//
//        void onSwitchCameraEnd(int cameraId);
    }

    interface StreamNetworkSpeedListener {
        void onNetworkSpeed(int val);
    }

    interface StreamTextureListener {
        void onTextureAvailable(EGLContext eglContext, int texture, float[] matrix, int width , int height);
    }
}
