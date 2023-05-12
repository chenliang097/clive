package com.rongtuoyouxuan.qfcommon.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.CollectionUtils;
import com.rongtuoyouxuan.chatlive.biz2.model.user.UserCardInfoBean;
import com.rongtuoyouxuan.chatlive.biz2.model.user.UserCardInfoRequest;
import com.rongtuoyouxuan.chatlive.biz2.stream.StreamBiz;
import com.rongtuoyouxuan.chatlive.biz2.stream.UserCardBiz;
import com.rongtuoyouxuan.chatlive.biz2.user.UserBiz;
import com.rongtuoyouxuan.chatlive.biz2.model.login.response.UploadFileResponse;
import com.rongtuoyouxuan.chatlive.databus.DataBus;
import com.rongtuoyouxuan.chatlive.net2.BaseModel;
import com.rongtuoyouxuan.chatlive.net2.RequestListener;
import com.rongtuoyouxuan.qfcommon.permission.RxPermissionProxy;

import java.io.File;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class CommonViewModel extends AndroidViewModel {
    public MutableLiveData<List<String>> uploadSuccessData = new MutableLiveData<>();
    public MutableLiveData<List<String>> uploadFailData = new MutableLiveData<>();


    private volatile Map<Integer, UploadFileResponse.DataBean> uploadSuccessMap = new HashMap<>();
    private volatile Map<Integer, String> uploadFailMap = new HashMap<>();
    private int uploadFileCount = -1;//上传文件个数

    public CommonViewModel(@NonNull Application application) {
        super(application);
    }

    public void uploadFile(FragmentActivity activity, List<String> fileList) {
        RxPermissionProxy permissionProxy = new RxPermissionProxy(activity);
        permissionProxy.requestStorage(accept -> {
            if (accept) {
                uploadFileCount = fileList.size();
                realUploadFile(fileList);
            }
        });
    }

    public void uploadFile(Fragment fragment, List<String> fileList) {
        RxPermissionProxy permissionProxy = new RxPermissionProxy(fragment);
        permissionProxy.requestStorage(accept -> {
            if (accept) {
                uploadFileCount = fileList.size();
                realUploadFile(fileList);
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

    private void realUploadFile(List<String> fileList) {
        uploadSuccessMap.clear();
        uploadFailMap.clear();
        if (CollectionUtils.isEmpty(fileList)) return;
        for (int i = 0; i < fileList.size(); i++) {
            int finalI = i;
            String filePath = fileList.get(finalI);
            File file = new File(filePath);
            if (!file.exists()) {
                uploadFailMap.put(finalI, "file is not exist");
                break;
            }
            RequestBody fileBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse(guessMimeType(filePath)), file))
                    .build();


            UserBiz.INSTANCE.uploadFile(fileBody, new RequestListener<UploadFileResponse>() {
                @Override
                public void onSuccess(String reqId, UploadFileResponse result) {
                    uploadSuccessMap.put(finalI, result.getData());
                    backResult();
                }

                @Override
                public void onFailure(String reqId, String errCode, String msg) {
                    uploadFailMap.put(finalI, msg);
                    backResult();
                }
            });
        }
    }

    private void backResult() {
        List<String> successes = new ArrayList<>();
        List<String> failedList = new ArrayList<>();
        if (uploadSuccessMap.size() + uploadFailMap.size() == uploadFileCount) {
            for (int i = 0; i < uploadSuccessMap.size(); i++) {
                successes.add(uploadSuccessMap.get(i).getUrl());
            }
            for (int i = 0; i < uploadFailMap.size(); i++) {
                failedList.add(uploadFailMap.get(i));
            }
            if (uploadSuccessMap.size() == uploadFileCount) {
                uploadSuccessData.setValue(successes);
            } else {
                uploadFailData.setValue(failedList);
            }
        }
    }
}