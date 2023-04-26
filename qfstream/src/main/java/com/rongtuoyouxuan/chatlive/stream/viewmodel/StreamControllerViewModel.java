package com.rongtuoyouxuan.chatlive.stream.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.rongtuoyouxuan.chatlive.arch.LiveEvent;
import com.rongtuoyouxuan.chatlive.base.utils.LiveStreamInfo;
import com.rongtuoyouxuan.chatlive.base.viewmodel.ControllerViewModel;
import com.rongtuoyouxuan.chatlive.biz2.user.UserBiz;
import com.rongtuoyouxuan.chatlive.biz2.model.login.response.UploadFileResponse;
import com.rongtuoyouxuan.chatlive.net2.RequestListener;

import java.io.File;
import java.net.FileNameMap;
import java.net.URLConnection;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class StreamControllerViewModel extends ControllerViewModel {

    public LiveEvent<Boolean> mNetworkSpeedVisibility = new LiveEvent<>();//网络控件
    public LiveEvent<Boolean> mControllerVisibility = new LiveEvent<>();//控制层控件
    public LiveEvent<Boolean> mClearLayoutVisibility = new LiveEvent<>();//清屏控件
    public LiveEvent<Void> mOutDialog = new LiveEvent<>();//退出房间dialog
    public LiveEvent<String> mShareFbAndLive = new LiveEvent<>();//分享Facebook并开始直播
    private LiveEvent<Boolean> mBeautySettingVisibility = new LiveEvent<>(false);//控制美颜view显示
    private LiveEvent<Boolean> mInteractionVisibility = new LiveEvent<>(false);//控制交互页view显示
    public MutableLiveData<UploadFileResponse> uploadLiveData = new MutableLiveData<>();//上传文件

    public StreamControllerViewModel(@NonNull LiveStreamInfo liveStreamInfo) {
        super(liveStreamInfo);
    }

    public void uploadIcon(File file) {
        RequestBody fileBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse(guessMimeType(file.toString())), file))
                .build();
        UserBiz.INSTANCE.uploadFile(fileBody, new RequestListener<UploadFileResponse>() {
            @Override
            public void onSuccess(String reqId, UploadFileResponse result) {
                uploadLiveData.setValue(result);
            }

            @Override
            public void onFailure(String reqId, String errCode, String msg) {
                try{
                    UploadFileResponse uploadFileResponse = new UploadFileResponse();
                    uploadFileResponse.errCode = Integer.parseInt(errCode);
                    uploadFileResponse.errMsg = msg;
                    uploadLiveData.setValue(uploadFileResponse);
                }catch (Exception e){
                    e.printStackTrace();
                }


            }
        });
    }

    private String guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }

    public void getRoomOnlineList(String hostid) {

    }

    public LiveEvent<Boolean> getBeautySettingVisibility() {
        return mBeautySettingVisibility;
    }

    public void setBeautySettingVisibility(boolean visibility) {
        mBeautySettingVisibility.setValue(visibility);
    }

    public LiveEvent<Boolean> getInteractionVisibility() {
        return mInteractionVisibility;
    }

}