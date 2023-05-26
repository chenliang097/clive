package com.rongtuoyouxuan.chatlive.rtstream.streaming;

import android.graphics.Bitmap;

public interface IStreamingOperation {
    void switchCamera();

    void setUseFrontCamera(boolean useFrontCamera);

    void mute(boolean mute);

    void turnLightOn();

    void turnLightOff();

    void setEncodingMirror(boolean b);

    void setFps(int fps);

    void setBitrate(int bitrate);

    void setEncodingSize(int width, int height);

    void takeScreenShot(ScreenShotListener listener);

    interface ScreenShotListener {
        void onBitmapAvailable(Bitmap bitmap);
    }
}
