package com.rongtuoyouxuan.chatlive.rtstream.qfzego;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.rongtuoyouxuan.chatlive.crtlog.upload.ULog;
import com.rongtuoyouxuan.chatlive.rtstream.streaming.BaseStreamView;
import com.rongtuoyouxuan.chatlive.stream.R;
import com.rongtuoyouxuan.chatlive.crtutil.util.MyLifecycleHandler;
import org.json.JSONObject;
import java.util.ArrayList;

import im.zego.effects.entity.ZegoEffectsVideoFrameParam;
import im.zego.effects.enums.ZegoEffectsVideoFrameFormat;
import com.rongtuoyouxuan.chatlive.rtstream.sdkmanager.SDKManager;
import com.rongtuoyouxuan.chatlive.rtstream.sdkmanager.ZegoLicense;
import com.rongtuoyouxuan.chatlive.rtstream.sdkmanager.net.IGetLicenseCallback;
import com.rongtuoyouxuan.chatlive.rtstream.sdkmanager.net.License;
import com.rongtuoyouxuan.chatlive.rtstream.sdkmanager.net.LicenseAPI;

import im.zego.zegoexpress.ZegoExpressEngine;
import im.zego.zegoexpress.callback.IZegoApiCalledEventHandler;
import im.zego.zegoexpress.callback.IZegoEventHandler;
import im.zego.zegoexpress.callback.IZegoPublisherUpdateCdnUrlCallback;
import im.zego.zegoexpress.constants.ZegoPlayerState;
import im.zego.zegoexpress.constants.ZegoPublishChannel;
import im.zego.zegoexpress.constants.ZegoPublisherState;
import im.zego.zegoexpress.constants.ZegoRoomStateChangedReason;
import im.zego.zegoexpress.constants.ZegoScenario;
import im.zego.zegoexpress.constants.ZegoVideoBufferType;
import im.zego.zegoexpress.constants.ZegoVideoConfigPreset;
import im.zego.zegoexpress.constants.ZegoVideoMirrorMode;
import im.zego.zegoexpress.entity.ZegoCanvas;
import im.zego.zegoexpress.entity.ZegoCustomVideoProcessConfig;
import im.zego.zegoexpress.entity.ZegoEngineProfile;
import im.zego.zegoexpress.entity.ZegoPublishStreamQuality;
import im.zego.zegoexpress.entity.ZegoRoomConfig;
import im.zego.zegoexpress.entity.ZegoStreamRelayCDNInfo;
import im.zego.zegoexpress.entity.ZegoUser;
import im.zego.zegoexpress.entity.ZegoVideoConfig;

public class ZegoStreamView extends BaseStreamView {
    private final String TAG = "ZEGOStreamView";
    private static final int DEFAULT_VIDEO_WIDTH =360;
    private static final int DEFAULT_VIDEO_HEIGHT = 640;
    private static final int MAX_RETRY = 3;
    final StringBuilder logs = new StringBuilder();
    private String pushUrl;
    private View mMeasureView;
    private Long appID;
    private String userID;
    private boolean useFontCamera = true;
    private String publishStreamID = "0001";
    private int pushHeartbeatCount = 0;
    private Context mContext;
    private String roomID = "0001";
    private String pushToken = "";
    private int captureOrigin = 0;
    private boolean isBackGround = false;

    private StreamStateChangedListener mStreamStateChangedListener;
    private ZegoExpressEngine mZegoEngine;
    private ZegoCanvas mZegoCanvas;
    private ZegoVideoConfig mZegoVideoConfig;

    private TextureView mPreviewView;
    private ZegoCustomVideoProcessConfig mZegoCustomVideoCaptureConfig;

    private ZegoEffectsVideoFrameParam effectsVideoFrameParam;

//    private Handler handler = new Handler(Looper.getMainLooper()){
//        @Override
//        public void handleMessage(@NonNull Message msg) {
//            super.handleMessage(msg);
//            if(mStreamStateChangedListener != null){
//                mStreamStateChangedListener.getPushStreamInfo(logs.toString());
//            }
//        }
//    };

    private static class MyHandler extends Handler {

        public MyHandler(@NonNull Looper looper) {
            super(looper);
        }

        private StreamStateChangedListener mStreamStateChangedListener;

        public void setStreamStateListener(StreamStateChangedListener listener) {
            mStreamStateChangedListener = listener;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                if (mStreamStateChangedListener != null) {
                    mStreamStateChangedListener.getPushStreamInfo((String) msg.obj);
                }
            }
        }
    }

    public ZegoStreamView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

//    public ZegoStreamView(Context context, View measureView) {
//        super(context);
//        mMeasureView = measureView;
//        init(context);
//    }

    public ZegoStreamView(Context context, View measureView) {
        super(context);
        mMeasureView = measureView;
        init(context);
    }

    private void init(Context context) {
        mContext = context;
//        handler = new MyHandler(Looper.getMainLooper());
        MyLifecycleHandler.getInstance().removeListener(appLifecycleListener);
        MyLifecycleHandler.getInstance().addListener(appLifecycleListener);
    }

    @SuppressLint("WrongViewCast")
    private void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.qf_libzego_stream, this, true);

        mPreviewView = findViewById(R.id.preview_view);
        final FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mPreviewView.getLayoutParams();
        mMeasureView.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {

                    @Override
                    public boolean onPreDraw() {
                        mMeasureView.getViewTreeObserver().removeOnPreDrawListener(this);
                        lp.height = mMeasureView.getMeasuredHeight();
                        lp.width = mMeasureView.getMeasuredWidth();
                        mPreviewView.setLayoutParams(lp);
                        return true;
                    }
                });
    }

    private final IZegoEventHandler mIZegoEventHandler =  new IZegoEventHandler(){

        @Override
        public void onPublisherStateUpdate(String streamID, ZegoPublisherState state, int errorCode, JSONObject extendedData) {
            super.onPublisherStateUpdate(streamID, state, errorCode, extendedData);
            if (state == ZegoPublisherState.PUBLISHING) {
                ULog.d(TAG, "onPublisherStateUpdate:" + "开始推流" + "--errorCode:" + errorCode);
            } else if (state == ZegoPublisherState.NO_PUBLISH){
                ULog.d(TAG, "onPublisherStateUpdate:" + "停止推流" + "--errorCode:" + errorCode);
            }

            ULog.d(TAG, "onPublisherStateUpdate:" + "开始推流" + "--errorCode:" + errorCode + "--state：" + state);
            logs.append("onPublisherStateUpdate---" + "streamID:" + streamID + "--state:" + state + "--errorCode:" + errorCode + "\n");
//            if (null != handler){
//                Message message = Message.obtain();
//                message.what = 0;
//                message.obj = logs.toString();
//                handler.sendMessageDelayed(message,5000);
////                handler.sendEmptyMessageDelayed(0, 5000);
//            }
//            if(errorCode != 0) {
//                if (mStreamStateChangedListener != null) {
//                    mStreamStateChangedListener.pushStreamStatus(streamID);
//                }
//            }
        }

        @Override
        public void onRoomStateChanged(String roomID, ZegoRoomStateChangedReason reason, int errorCode, JSONObject extendedData) {
            ULog.d(TAG, "onRoomStateChanged:" + "roomID:"+roomID + "-errorCode:" + errorCode);
            logs.append("onRoomStateChanged---" + "roomID:" + roomID + "--errorCode:" + errorCode + "\n");

        }

        @Override
        public void onPlayerStateUpdate(String s, ZegoPlayerState zegoPlayerState, int errorCode, JSONObject jsonObject) {
            super.onPlayerStateUpdate(s, zegoPlayerState, errorCode, jsonObject);
            ULog.d(TAG, "onPlayerStateUpdate:" + "streamID:"+roomID + "-errorCode:" + errorCode);
            if (errorCode != 0 && zegoPlayerState == ZegoPlayerState.NO_PLAY) {
                if(mStreamStateChangedListener != null){
                    mStreamStateChangedListener.onPlayError();
                }
            } else {
                if(errorCode == 0 && zegoPlayerState == ZegoPlayerState.PLAYING){
                    mStreamStateChangedListener.onPlaySuccess(s);
                }

            }
            logs.append("onPlayerStateUpdate---" + "streamID:" + s + "--state:" + zegoPlayerState + "--errorCode:" + errorCode + "\n");

        }

        @Override
        public void onPublisherRelayCDNStateUpdate(String streamID, ArrayList<ZegoStreamRelayCDNInfo> arrayList) {
            super.onPublisherRelayCDNStateUpdate(streamID, arrayList);
            ULog.d(TAG, "onPublisherRelayCDNStateUpdate:" + "streamID:"+streamID);
            logs.append("onPublisherRelayCDNStateUpdate---" + "streamID:" + streamID + "\n");
        }

        @Override
        public void onPublisherQualityUpdate(String streamID, ZegoPublishStreamQuality zegoPublishStreamQuality) {
            super.onPublisherQualityUpdate(streamID, zegoPublishStreamQuality);
            logs.append("onPublisherQualityUpdate---" + "streamID:" + streamID + "--level:" + zegoPublishStreamQuality.level + "\n");
            ULog.d(TAG, "onPublisherQualityUpdate:" + "streamID:"+streamID + "--level:" + zegoPublishStreamQuality.level);
            if(isBackGround)return;
            if (pushHeartbeatCount < MAX_RETRY) {
                pushHeartbeatCount++;
            }else{
                pushHeartbeatCount = 0;
                mStreamStateChangedListener.pushStreamHeartbeat(streamID, logs.toString());
            }
        }
    };

    public void setCreateEngine(){
        ZegoEngineProfile profile = new ZegoEngineProfile();
        profile.appID = appID;
        profile.scenario = ZegoScenario.BROADCAST;
        profile.application = (Application) mContext.getApplicationContext();
        mZegoEngine = ZegoExpressEngine.createEngine(profile, mIZegoEventHandler);
        mZegoEngine.enableHardwareEncoder(true);

        effectsVideoFrameParam = new ZegoEffectsVideoFrameParam();
        effectsVideoFrameParam.format = ZegoEffectsVideoFrameFormat.RGBA32;

        mZegoVideoConfig = new ZegoVideoConfig(ZegoVideoConfigPreset.PRESET_360P);
        mZegoVideoConfig.bitrate = 1500;
        mZegoVideoConfig.fps = 15;
//        mZegoVideoConfig.setCaptureResolution(DEFAULT_VIDEO_WIDTH, DEFAULT_VIDEO_HEIGHT);
//        mZegoVideoConfig.setEncodeResolution(DEFAULT_VIDEO_WIDTH, DEFAULT_VIDEO_HEIGHT);
        mZegoEngine.setVideoConfig(mZegoVideoConfig);
        mZegoEngine.setVideoMirrorMode(ZegoVideoMirrorMode.NO_MIRROR);
        mZegoEngine.enableCamera(true);

        mZegoCustomVideoCaptureConfig = new ZegoCustomVideoProcessConfig();
        mZegoCustomVideoCaptureConfig.bufferType = ZegoVideoBufferType.GL_TEXTURE_2D;
        mZegoEngine.enableCustomVideoProcessing(true, mZegoCustomVideoCaptureConfig, ZegoPublishChannel.MAIN);

        setApiCalledResult();
//        mZegoCanvas = new ZegoCanvas(null);
//        mZegoCanvas.viewMode = ZegoViewMode.ASPECT_FILL;
//        mZegoEngine.startPreview(mZegoCanvas);
    }

    private void initBeauty(){
        String encryptInfo = SDKManager.getAuthInfo(mContext);
//        ZegoUtil.copyFileFromAssets(mContext);
        SDKManager.sharedInstance().initResource(mContext);
        LicenseAPI.getLicense(encryptInfo, new IGetLicenseCallback() {
            @Override
            public void onGetLicense(int code, String message, License license) {
                if (license != null) {
                    ULog.e("clll", "message:" + message + "---license:" + license.getLicense());
                    ZegoLicense.effectsLicense = license.getLicense();
                    SDKManager.sharedInstance().initEvn(mZegoEngine, mPreviewView, mContext, effectsVideoFrameParam);
                }

            }
        });
    }


    @Override
    public void onCreate() {
        super.onCreate();
        initView();
        getAppID();
        setCreateEngine();
        initBeauty();
    }

    private void pushCdn(){
        mZegoEngine.addPublishCdnUrl(publishStreamID, pushUrl, new IZegoPublisherUpdateCdnUrlCallback() {
            @Override
            public void onPublisherUpdateCdnUrlResult(int errorCode) {
//                ToastUtils.showLong("推流成功--" + errorCode);
                ULog.d(TAG, "addPublishCdnUrl:" + errorCode);
                logs.append("onPublisherUpdateCdnUrlResult:" + errorCode + "\n");

            }
        });
    }

    public void setApiCalledResult() {
        ZegoExpressEngine.setApiCalledCallback(new IZegoApiCalledEventHandler() {
            @Override
            public void onApiCalledResult(int errorCode, String funcName, String info) {
                super.onApiCalledResult(errorCode, funcName, info);
                if (errorCode == 0) {
                    ULog.d(TAG, "funcName:" + funcName + "---info:" + info);
                } else {
                    ULog.d(TAG, "errorCode" + errorCode + "--funcName:" + funcName + "---info:" + info);
                }
            }
        });
    }

    public void getAppID(){
        appID = KeyCenter.getInstance().getAppID();
    }

    public void loginRoom(){
        //create the user
        ZegoUser user = new ZegoUser(userID);
        //login room
        ZegoRoomConfig config = new ZegoRoomConfig();
        config.token = pushToken;
        mZegoEngine.loginRoom(roomID, user, config);
        ULog.d(TAG, "LoginRoom: " + roomID);
    }

    //用于主播连麦端回调
    @Override
    public void setStreamStateListener(StreamStateChangedListener listener) {
        mStreamStateChangedListener = listener;
//        if (null != handler) {
//            handler.setStreamStateListener(mStreamStateChangedListener);
//        }
    }

    @Override
    public void setPushUrl(String url) {
        this.pushUrl = url;
    }

    @Override
    public void setStreamId(String streamId) {
        this.publishStreamID = streamId;
    }

    @Override
    public void setRoomInfo(String roomId, String token, String userId) {
        super.setRoomInfo(roomId, token, userId);
        this.roomID = roomId;
        this.userID = userId;
        this.pushToken = token;
    }

    @Override
    public void startStreaming() {

        if (!TextUtils.isEmpty(publishStreamID)) {
            try {
                ULog.d("clll", "Start Publishing Stream:" + pushUrl);

//                mCameraRenderer.closeCamera();
//                mCameraRenderer.reopenCamera();
                logs.append("appid:" + KeyCenter.getInstance().getAppID() + "\n");
                logs.append("Start Publishing Stream:" + publishStreamID + "\n");
                loginRoom();
                mZegoEngine.startPublishingStream(publishStreamID);
                pushCdn();
                ULog.d(TAG, "Start Publishing Stream:" + publishStreamID);
            } catch (Exception e) {
                ToastUtils.showShort(R.string.push_stream_error);
                e.printStackTrace();
            }
        }else{
            ToastUtils.showShort(R.string.push_stream_error);
        }
    }

    @Override
    public void stopStreaming() {
        try {
            mZegoEngine.stopPreview();
            mZegoEngine.stopPublishingStream();
            mZegoEngine.removePublishCdnUrl(publishStreamID, pushUrl, new IZegoPublisherUpdateCdnUrlCallback() {
                @Override
                public void onPublisherUpdateCdnUrlResult(int errorCode) {
                    ULog.d(TAG, "removePublishCdnUrl:" + errorCode);
                    logs.append("removePublishCdnUrl:" + errorCode + "\n");

                }
            });
            logs.append("Stop Publishing Stream:" + publishStreamID + "\n");

            ULog.d(TAG, "Stop Publishing Stream");
        } catch (Exception e) {

        }
    }

    @Override
    public void removePublishCdnUrl() {
        super.removePublishCdnUrl();
        mZegoEngine.removePublishCdnUrl(publishStreamID, pushUrl, new IZegoPublisherUpdateCdnUrlCallback() {
            @Override
            public void onPublisherUpdateCdnUrlResult(int errorCode) {
                ULog.d(TAG, "remove原cdn推流:" + errorCode);
            }
        });
    }


    @Override
    public void onPause() {
        if (mZegoEngine != null) {
            try {
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void switchCamera() {
        if (mZegoEngine != null) {
            try {
                mZegoEngine.useFrontCamera(useFontCamera);
            } catch (Exception e) {

            }
        }
    }

    @Override
    public void setUseFrontCamera(boolean useFrontCamera) {
        this.useFontCamera = useFrontCamera;
    }

    @Override
    public void setEncodingMirror(boolean b) {
    }

    @Override
    public void onResume() {
        if (mZegoEngine != null) {
            try {
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        //停止推流
        if(mZegoEngine != null) {
            logoutLiveRoom();
//            SDKManager.sharedInstance().stopCamera();
        }
//        if (null != handler) {
//            handler.removeCallbacksAndMessages(null);
//            handler = null;
//        }
    }

    public void logoutLiveRoom() {
        mZegoEngine.stopPreview();
        mZegoEngine.logoutRoom(roomID);
        mZegoEngine.enableCustomVideoProcessing(false, mZegoCustomVideoCaptureConfig, ZegoPublishChannel.MAIN);
        ZegoExpressEngine.setApiCalledCallback(null);
        ZegoExpressEngine.destroyEngine(null);
        pushUrl = "";
    }

    @Override
    public void setBeautyWhite(float val) {
        if (mZegoEngine != null) {
            try {
                //美白0-100
//                mpZegoEffectsBeautyParam.whitenIntensity = (int) val;
//                mZegoEngine.setEffectsBeautyParam(mpZegoEffectsBeautyParam);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setBeautyLargeEye(float val) {
        if (mZegoEngine != null) {
            try {
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setBeautysharpen(float val) {
        if (mZegoEngine != null) {
            try {
                //锐化0-100
//                mpZegoEffectsBeautyParam.sharpenIntensity = (int) val;
//                mZegoEngine.setEffectsBeautyParam(mpZegoEffectsBeautyParam);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setBeautyRed(float val) {
        if (mZegoEngine != null) {
            try {
                // 红润0-100
//                mpZegoEffectsBeautyParam.rosyIntensity = (int) val;
//                mZegoEngine.setEffectsBeautyParam(mpZegoEffectsBeautyParam);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setBeautyShrinkFace(float val) {
        if (mZegoEngine != null) {
            try {
                // 瘦脸设置范围0-100
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setBeautyShrinkJaw(float val) {
        if (mZegoEngine != null) {
            try {
                // 收下巴设置范围0-100
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void mute(boolean mute) {
        mZegoEngine.muteMicrophone(mute);
        if (mute){
            ToastUtils.showShort(R.string.stream_mic_off);
        } else {
            ToastUtils.showShort(R.string.stream_mic_on);
        }
    }

    @Override
    public void setBeautySmooth(float val) {
        if (mZegoEngine != null) {
            try {
                // 磨皮0-100
//                mpZegoEffectsBeautyParam.smoothIntensity = (int) val;
//                mZegoEngine.setEffectsBeautyParam(mpZegoEffectsBeautyParam);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void setScreenType(int screenType) {
        FrameLayout.LayoutParams layoutParams = (LayoutParams) mPreviewView.getLayoutParams();
        if(screenType == 0){
            layoutParams.width = LayoutParams.MATCH_PARENT;
            layoutParams.height = LayoutParams.MATCH_PARENT;
        } else if(screenType == 1){
            layoutParams.width = LayoutParams.MATCH_PARENT;
            layoutParams.height = ScreenUtils.getScreenHeight()/2;
        }else if(screenType == 2){
            layoutParams.width = ConvertUtils.dp2px(70f);
            layoutParams.height = ConvertUtils.dp2px(105f);
        }
        mPreviewView.setLayoutParams(layoutParams);


    }

    private MyLifecycleHandler.Listener appLifecycleListener = new MyLifecycleHandler.Listener() {

        @Override
        public void onBecameForeground(Activity activity) {
            isBackGround = false;
        }

        @Override
        public void onBecameBackground(Activity activity) {
            isBackGround = true;
        }
    };


}
