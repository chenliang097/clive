package com.rongtuoyouxuan.chatlive.crtoss;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.rongtuoyouxuan.chatlive.crtbiz2.oss.OssPathModel;
import com.rongtuoyouxuan.chatlive.crtimage.luban.CompressionPredicate;
import com.rongtuoyouxuan.chatlive.crtimage.luban.Luban;
import com.rongtuoyouxuan.chatlive.crtimage.luban.OnCompressListener;
import com.rongtuoyouxuan.chatlive.crtimage.luban.OnRenameListener;
import com.rongtuoyouxuan.chatlive.crtnet.RequestListener;
import com.rongtuoyouxuan.chatlive.crtutil.util.FileUtils;
import com.rongtuoyouxuan.chatlive.crtutil.util.UUIDUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * create by Administrator on 2019/6/4
 */
public class OssUploadManager {

    private static volatile OssUploadManager instance;
    private final Handler uiHandler = new Handler(Looper.getMainLooper());

    private OssUploadManager() {
    }

    public static OssUploadManager getInstance() {
        if (instance == null) {
            synchronized (OssUploadManager.class) {
                if (instance == null) {
                    instance = new OssUploadManager();
                }
            }
        }
        return instance;
    }

    private void withLs(final Context context, final List<String> photos, final String tag, final UploadListener listener) {
        try {
            Luban.with(context)
                    .load(photos)
                    .ignoreBy(200)
                    .setTargetDir(getPath(context))
                    .setFocusAlpha(false)
                    .filter(new CompressionPredicate() {
                        @Override
                        public boolean apply(String path) {
                            return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                        }
                    })
                    .setRenameListener(new OnRenameListener() {
                        @Override
                        public String rename(String filePath) {
                            return UUIDUtil.getUUID();
                        }
                    })
                    .setCompressListener(new OnCompressListener() {
                        @Override
                        public void onStart() {
                        }

                        @Override
                        public void onSuccess(File file) {
                            String path = file.getAbsolutePath();
                            if (path.equalsIgnoreCase(photos.get(0))) {
                                _uploadFile(context, true, path, tag, listener);
                            } else {
                                _uploadFile(context, false, path, tag, listener);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            _uploadFile(context, true, photos.get(0), tag, listener);
                        }
                    }).launch();
        }catch (Exception ex){
            _uploadFile(context, true, photos.get(0), tag, listener);
        }

    }

    private String getPath(Context context) {
        String path = new File(context.getExternalCacheDir(), "image_temp_cache").getAbsolutePath();
        File file = new File(path);
        if (file.mkdirs()) {
            return path;
        }
        return path;
    }

    public void uploadFile(Context context, String filePath, final String tag, final UploadListener listener) {
        if (TextUtils.isEmpty(filePath)) {
            return;
        }
        listener.onProgress(0.0f,tag);
        List<String> listFile = new ArrayList<>();
        listFile.add(filePath);
        try {
            withLs(context, listFile, tag, listener);
        } catch (Exception e) {
            listener.onFail("upload error", tag);
        }
    }

    /**
     * 此方法只支持图片文件上传
     *
     * @param filePath 文件路径
     * @param tag      上传回调tag
     * @param listener 上传回调监听
     */
    private void _uploadFile(Context context, boolean isOriginalPic, String filePath, final String tag, final UploadListener listener) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, opts);
        int outWidth = opts.outWidth;
        int outHeight = opts.outHeight;
        String ossPath = UUIDUtil.getUUID() + "_w" + outWidth + "_h" + outHeight + ".png";
        uploadFile(context, isOriginalPic, filePath, tag, ossPath, listener);
    }

    /**
     * @param isOriginalPic 上传的是否是原图  是否经过 Luban 压缩
     * @param filePath 文件路径
     * @param tag      上传回调tag
     * @param ossPath  阿里云上存放的路径
     * @param listener 上传回调监听
     */
    public void uploadFile(Context context, final boolean isOriginalPic, final String filePath, final String tag, final String ossPath, final UploadListener listener) {

        OssUploadClient.getInstance().init(context, new RequestListener<OssPathModel>() {
            @Override
            public void onSuccess(String reqId, OssPathModel result) {
                doUpload(ossPath, isOriginalPic, filePath, listener, tag);
            }

            @Override
            public void onFailure(String reqId, String errCode, String msg) {
                listener.onFail(msg, tag);
            }
        });

    }

    private void doUpload(final String ossPath, final boolean isOriginalPic, final String filePath, final UploadListener listener, final String tag) {
        OSSClient ossClient = OssUploadClient.getInstance().getOssClient();
        String bucketName = OssUploadClient.getInstance().getBucketName();
        final String basePath = OssUploadClient.getInstance().getBasePath();
        if (null != ossClient) {
            // 构造上传请求
            PutObjectRequest put = new PutObjectRequest(bucketName, ossPath, filePath);
            // 异步上传时可以设置进度回调
            put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
                @Override
                public void onProgress(PutObjectRequest request, final long currentSize, final long totalSize) {
                    //PLog.d("PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize);
                    //progress1 = (int) ((float) currentSize / totalSize * 100);
                    uiHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            float fraction = (currentSize * 1f / (totalSize) * 100) / 100;
                            if (fraction == 1.0f) {
                                fraction = 0.99f;
                            }
                            listener.onProgress(fraction, tag);
                        }
                    });
                }
            });
            OSSAsyncTask task = ossClient.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
                @Override
                public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                    final String fullpath = basePath + ossPath;
                    if (listener != null) {
                        uiHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                listener.onSuccess(ossPath, fullpath, tag);
                            }
                        });
                    }
                    if (!isOriginalPic) {
                        FileUtils.delFile(filePath);
                    }
                }

                @Override
                public void onFailure(PutObjectRequest request, final ClientException clientExcepion, final ServiceException serviceException) {
                    // 请求异常
                    if (clientExcepion != null) {
                        // 本地异常如网络异常等
                        clientExcepion.printStackTrace();
                        if (listener != null) {
                            uiHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    listener.onFail(clientExcepion.getMessage(), tag);
                                }
                            });

                        }
                    }
                    if (serviceException != null) {
                        if (listener != null) {
                            uiHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    listener.onFail(serviceException.getRawMessage(), tag);
                                }
                            });

                        }
                        // 服务异常
                        Log.e("ErrorCode", serviceException.getErrorCode());
                        Log.e("RequestId", serviceException.getRequestId());
                        Log.e("HostId", serviceException.getHostId());
                        Log.e("RawMessage", serviceException.getRawMessage());
                    }
                }
            });
        } else {
            listener.onFail("please init ossClient first", tag);
        }
    }

}
