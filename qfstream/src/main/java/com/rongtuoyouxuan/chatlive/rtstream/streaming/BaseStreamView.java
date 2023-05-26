package com.rongtuoyouxuan.chatlive.rtstream.streaming;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class BaseStreamView extends FrameLayout implements Sdk.StreamApi,Sdk.StreamInitData {

    public static final String TYPE_ST = "st";
    public static final String TYPE_ALI = "ali";

    protected Sdk.InitParams mParams;

    public BaseStreamView(Context context) {
        super(context);
    }

    public BaseStreamView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseStreamView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BaseStreamView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void setPushUrl(String url) {

    }

    @Override
    public void setStreamId(String channel) {

    }

    @Override
    public void setRoomInfo(String roomId, String token, String userId) {

    }

    @Override
    public void startStreaming() {

    }

    @Override
    public void stopStreaming() {

    }

    @Override
    public void removePublishCdnUrl() {

    }

    @Override
    public float getDrawFrameRate() {
        return 0;
    }

    @Override
    public float getSendFrameRate() {
        return 0;
    }

    @Override
    public void setStreamStateListener(StreamStateChangedListener listener) {

    }

    @Override
    public void setStreamNetworkSpeedListener(StreamNetworkSpeedListener listener) {

    }

    @Override
    public void setStreamTextureListener(StreamTextureListener listener) {

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onRestart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void switchCamera() {

    }

    @Override
    public void setUseFrontCamera(boolean useFrontCamera) {

    }

    @Override
    public void mute(boolean mute) {

    }

    @Override
    public void turnLightOn() {

    }

    @Override
    public void turnLightOff() {

    }

    @Override
    public void setEncodingMirror(boolean b) {

    }

    @Override
    public void setFps(int fps) {

    }

    @Override
    public void setBitrate(int bitrate) {

    }

    @Override
    public void setEncodingSize(int width, int height) {

    }

    @Override
    public void takeScreenShot(ScreenShotListener listener) {

    }

    @Override
    public void useBuiltinBeauty(boolean b) {

    }

    @Override
    public void setBeautyWhite(float val) {

    }

    @Override
    public void setBeautyRed(float val) {

    }

    @Override
    public void setBeautySmooth(float val) {

    }

    @Override
    public void setBeautyLargeEye(float val) {

    }

    @Override
    public void setBeautyShrinkFace(float val) {

    }

    @Override
    public void setBeautyShrinkJaw(float val) {

    }

    @Override
    public void selectFilter(int index) {

    }

    @Override
    public void setBeautysharpen(float val) {

    }

    @Override
    public String getSdkName() {
        return null;
    }

    @Override
    public Sdk.InitParams getInitParams() {
        if (mParams == null) {
            mParams = new Sdk.InitParams();
            mParams.sdkName = "ks";
            mParams.gop = 3;
            mParams.useHardEncode = true;
            mParams.verticalStream = true;
            mParams.useFrontCamera = true;
            mParams.frontCameraMirror = true;
            mParams.needWatermark = false;
        }
        return mParams;
    }

    public void setInitParams(Sdk.InitParams params){

    }
}
