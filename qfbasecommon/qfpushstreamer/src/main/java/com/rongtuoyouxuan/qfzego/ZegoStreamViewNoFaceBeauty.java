package com.rongtuoyouxuan.qfzego;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ConvertUtils;
import com.rongtuoyouxuan.chatlive.log.upload.ULog;
import com.rongtuoyouxuan.chatlive.streaming.BaseStreamView;
import com.rongtuoyouxuan.qfpushstreamer.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import org.json.JSONObject;

import im.zego.zegoexpress.ZegoExpressEngine;
import im.zego.zegoexpress.callback.IZegoApiCalledEventHandler;
import im.zego.zegoexpress.callback.IZegoEventHandler;
import im.zego.zegoexpress.callback.IZegoPublisherUpdateCdnUrlCallback;
import im.zego.zegoexpress.constants.ZegoPublisherState;
import im.zego.zegoexpress.constants.ZegoRoomStateChangedReason;
import im.zego.zegoexpress.constants.ZegoScenario;
import im.zego.zegoexpress.constants.ZegoVideoConfigPreset;
import im.zego.zegoexpress.constants.ZegoVideoMirrorMode;
import im.zego.zegoexpress.constants.ZegoViewMode;
import im.zego.zegoexpress.entity.ZegoCanvas;
import im.zego.zegoexpress.entity.ZegoEffectsBeautyParam;
import im.zego.zegoexpress.entity.ZegoEngineProfile;
import im.zego.zegoexpress.entity.ZegoRoomConfig;
import im.zego.zegoexpress.entity.ZegoUser;
import im.zego.zegoexpress.entity.ZegoVideoConfig;
import jp.wasabeef.glide.transformations.BlurTransformation;

public class ZegoStreamViewNoFaceBeauty extends BaseStreamView {
    private final String TAG = "ZegoStreamViewNoFaceBeauty";
    StreamStateChangedListener mStreamStateChangedListener;
    private Context mContext;
    private TextureView mPreviewView;
    private TextView nickNameTxt;
    private ImageView voiceImg;
    private ImageView coverImg;
    private ImageView coverLayout;

    private ZegoExpressEngine mZegoEngine;
    private ZegoCanvas mZegoCanvas;
    private ZegoVideoConfig mZegoVideoConfig;
    private ZegoEffectsBeautyParam mpZegoEffectsBeautyParam;
    private String pushUrl;
    private String streamId;
    private View mMeasureView;
    private Long appID;
    private String userID;
    private boolean useFontCamera = true;

    private String token;
    private String roomID;
    private String publishStreamID = "0001";
    private String playStreamID = "0001";
    private IZegoEventHandler mIZegoEventHandler =  new IZegoEventHandler(){
        @Override
        public void onPublisherStateUpdate(String streamID, ZegoPublisherState state, int errorCode, JSONObject extendedData) {
            super.onPublisherStateUpdate(streamID, state, errorCode, extendedData);
            if (state == ZegoPublisherState.PUBLISHING) {
                ULog.d(TAG, "mIZegoEventHandler:" + "onPublisherStateUpdate:开始推流");

            } else if (state == ZegoPublisherState.NO_PUBLISH){
                ULog.d(TAG, "mIZegoEventHandler:" + "onPublisherStateUpdate:停止推流");
            }
        }


        @Override
        public void onRoomStateChanged(String roomID, ZegoRoomStateChangedReason reason, int errorCode, JSONObject extendedData) {
            ULog.d(TAG, "mIZegoEventHandler:" + "roomID:"+roomID + "-errorCode:" + errorCode);
        }

    };

    public ZegoStreamViewNoFaceBeauty(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ZegoStreamViewNoFaceBeauty(Context context, View measureView) {
        super(context);
        mMeasureView = measureView;
        init(context);
    }

    public ZegoStreamViewNoFaceBeauty(Context context) {
        super(context);
        init(context);

    }

    @Override
    public void setStreamStateListener(StreamStateChangedListener listener) {
        mStreamStateChangedListener = listener;
    }

    private void init(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initView();
        getAppIDAndUserIDAndToken();
        setCreateEngine();
        setDefaultConfig();
        setApiCalledResult();
    }

    @SuppressLint("WrongViewCast")
    private void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.qf_libzego_link_mic_stream, this, true);
        mPreviewView = findViewById(R.id.preview_view);
        nickNameTxt = findViewById(R.id.mcMicTxt);
        voiceImg = findViewById(R.id.mcMicImg);
        coverImg = findViewById(R.id.mcCoverImg);
        coverLayout = findViewById(R.id.mcCoverLayout);
        final RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mPreviewView.getLayoutParams();
    }

    public void getAppIDAndUserIDAndToken(){
        appID = KeyCenter.getInstance().getAppID();
    }

    public void setCreateEngine(){
        ZegoEngineProfile profile = new ZegoEngineProfile();
        profile.appID = appID;
        profile.scenario = ZegoScenario.BROADCAST;
        profile.application = (Application) mContext.getApplicationContext();
        mZegoEngine = ZegoExpressEngine.getEngine();
        // 初始化 Effects 美颜环境
//        mZegoEngine.startEffectsEnv();
        //enable the camera
        mZegoEngine.enableCamera(true);
        //enable the microphone
        mZegoEngine.muteMicrophone(false);
        //enable the speaker
        mZegoEngine.muteSpeaker(false);
        setApiCalledResult();
    }

    public void setDefaultConfig(){
        mZegoCanvas = new ZegoCanvas(mPreviewView);
        mZegoCanvas.viewMode = ZegoViewMode.ASPECT_FILL;
        mZegoVideoConfig = new ZegoVideoConfig(ZegoVideoConfigPreset.PRESET_360P);
        mZegoVideoConfig.bitrate = 600;
        mZegoVideoConfig.fps = 15;
        mZegoEngine.setVideoConfig(mZegoVideoConfig);
        mZegoEngine.setVideoMirrorMode(ZegoVideoMirrorMode.BOTH_MIRROR);

        mpZegoEffectsBeautyParam = new ZegoEffectsBeautyParam();
        mpZegoEffectsBeautyParam.whitenIntensity = 50;
        mpZegoEffectsBeautyParam.rosyIntensity = 50;
        mpZegoEffectsBeautyParam.smoothIntensity = 50;
        mpZegoEffectsBeautyParam.sharpenIntensity = 50;
        mZegoEngine.setEffectsBeautyParam(mpZegoEffectsBeautyParam);
        mZegoEngine.startPreview(mZegoCanvas);
    }

    public void loginRoom(){
        //create the user
        ZegoUser user = new ZegoUser(userID);
        //login room
        ZegoRoomConfig config = new ZegoRoomConfig();
        config.token = token;
        mZegoEngine.loginRoom(roomID, user, config);
        //enable the camera
        mZegoEngine.enableCamera(true);
        //enable the microphone
        mZegoEngine.muteMicrophone(false);
        //enable the speaker
        mZegoEngine.muteSpeaker(false);
        ULog.d(TAG, "LoginRoom: " + roomID);
    }

    public void setApiCalledResult(){
        ZegoExpressEngine.setApiCalledCallback(new IZegoApiCalledEventHandler() {
            @Override
            public void onApiCalledResult(int errorCode, String funcName, String info) {
                super.onApiCalledResult(errorCode, funcName, info);
                if (errorCode == 0){
                    ULog.d(TAG, "setApiCalledResult:" + funcName + "---info:" + info);
                } else {
                    ULog.d(TAG, "errorCode" + errorCode + "--funcName:" + funcName + "---info:" + info);
                }
            }
        });
    }

    public void setDefaultValue(String publishStreamID, String roomID, String userId, String token){
        //set default publish StreamID
        this.publishStreamID = publishStreamID;
        this.roomID = roomID;
        this.token  = token;
        this.userID = userId;
    }

    @Override
    public void setPushUrl(String url) {
        this.pushUrl = url;
    }

    @Override
    public void setStreamId(String streamId) {
        this.streamId = streamId;
    }

    public void setNickName(String nickName){
        if(nickNameTxt != null){
            nickNameTxt.setText(nickName);
        }
    }

    public void switchCamera(boolean isOn){
        if(mZegoEngine != null){
            mZegoEngine.enableCamera(isOn);
            if(isOn){
                coverLayout.setVisibility(GONE);
                coverImg.setVisibility(GONE);
            }else{
                coverLayout.setVisibility(VISIBLE);
                coverImg.setVisibility(VISIBLE);
            }
        }
    }

    public void setMCVoice(int drawable){
        if(voiceImg != null){
            voiceImg.setImageResource(drawable);
        }
    }

    @SuppressLint("CheckResult")
    public void setCoverImg(String url){
        if(coverImg != null){
            Glide.with(mContext)
                    .load(url)
                    .placeholder(R.drawable.icon_default_live_common)
                    .error(R.drawable.icon_default_live_common)
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .into(coverImg);

            Glide.with(mContext).asBitmap().load(url).placeholder(R.drawable.qf_stream_link_mic_bg)
                    .error(R.drawable.qf_stream_link_mic_bg).apply(new RequestOptions().transform(new BlurTransformation(25, 1))).into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onLoadStarted(@Nullable Drawable placeholder) {
                            super.onLoadStarted(placeholder);
                            coverLayout.setBackgroundResource(R.drawable.qf_stream_link_mic_bg);
                        }

                        @Override
                        public void onLoadFailed(@Nullable Drawable errorDrawable) {
                            super.onLoadFailed(errorDrawable);
                            coverLayout.setBackgroundResource(R.drawable.qf_stream_link_mic_bg);
                        }

                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            coverLayout.setBackground(ConvertUtils.bitmap2Drawable(resource));
                        }
                    });

        }
    }

    @Override
    public void startStreaming() {
        if (!TextUtils.isEmpty(publishStreamID)) {
            try {
//                loginRoom();
                mZegoEngine.startPublishingStream(publishStreamID);
                pushCdn();
//                mZegoEngine.addPublishCdnUrl(publishStreamID, pushUrl, new IZegoPublisherUpdateCdnUrlCallback() {
//                    @Override
//                    public void onPublisherUpdateCdnUrlResult(int errorCode) {
//                        ULog.d(TAG, "addPublishCdnUrl:" + errorCode);
//                    }
//                });
                ULog.d(TAG, "Start Publishing Stream:" + publishStreamID);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void pushCdn(){
        mZegoEngine.addPublishCdnUrl(publishStreamID, pushUrl, new IZegoPublisherUpdateCdnUrlCallback() {
            @Override
            public void onPublisherUpdateCdnUrlResult(int errorCode) {
//                ToastUtils.showLong("推流成功--" + errorCode);
                ULog.d(TAG, "addPublishCdnUrl:" + errorCode);
            }
        });
    }

    @Override
    public void stopStreaming() {
        try {
//            mZegoEngine.logoutRoom(roomID);
            mZegoEngine.stopPreview();
            mZegoEngine.stopPublishingStream();
            ULog.d(TAG, "Stop Publishing Stream");
        } catch (Exception e) {

        }
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
        if (mZegoEngine != null) {
            try {
                mZegoEngine.muteMicrophone(b);
            } catch (Exception e) {

            }
        }
    }

    @Override
    public void onDestroy() {
        //停止推流
        ZegoExpressEngine.destroyEngine(null);
        pushUrl = "";
    }

    @Override
    public void setBeautyWhite(float val) {
        if (mZegoEngine != null) {
            try {
                //美白0-100
                mpZegoEffectsBeautyParam.whitenIntensity = (int) val;
                mZegoEngine.setEffectsBeautyParam(mpZegoEffectsBeautyParam);
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
                mpZegoEffectsBeautyParam.sharpenIntensity = (int) val;
                mZegoEngine.setEffectsBeautyParam(mpZegoEffectsBeautyParam);
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
    public void setBeautyRed(float val) {
        if (mZegoEngine != null) {
            try {
                // 红润0-100
                mpZegoEffectsBeautyParam.rosyIntensity = (int) val;
                mZegoEngine.setEffectsBeautyParam(mpZegoEffectsBeautyParam);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setBeautySmooth(float val) {
        if (mZegoEngine != null) {
            try {
                // 磨皮0-100
                mpZegoEffectsBeautyParam.smoothIntensity = (int) val;
                mZegoEngine.setEffectsBeautyParam(mpZegoEffectsBeautyParam);
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

    //麦克风
    public void setMicrophoneSwitch(boolean isOn){
        mZegoEngine.muteMicrophone(isOn);
    }

}
