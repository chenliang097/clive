package com.rongtuoyouxuan.chatlive.crtoss;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.rongtuoyouxuan.chatlive.crtbiz2.oss.OssBiz;
import com.rongtuoyouxuan.chatlive.crtbiz2.oss.OssPathModel;
import com.rongtuoyouxuan.chatlive.crtlog.upload.ULog;
import com.rongtuoyouxuan.chatlive.crtnet.RequestListener;

/**
 * create by Administrator on 2019/6/4
 */
public class OssFileUploadClient {

    private static volatile OssFileUploadClient instance;
    private static OSSClient mOssClient = null;
    private static String mBucketName = null;
    private static String mBasePath = null;
    private String accessKeyId = "";
    private String accessKeySecret = "";
    private String securityToken = "";
    private String endpoint = "";

    private OssFileUploadClient() {
    }

    public static OssFileUploadClient getInstance() {
        if (instance == null) {
            synchronized (OssFileUploadClient.class) {
                if (instance == null) {
                    instance = new OssFileUploadClient();
                }
            }
        }
        return instance;
    }

    public void init(final Context context, String positon, final RequestListener<OssPathModel> listener) {
        OssBiz.getInstance().getOssNewConfig(null, positon, new RequestListener<OssPathModel>() {
            @Override
            public void onSuccess(String reqId, OssPathModel ossPathModel) {
                mBasePath = "assets.rongtuolive.com";
                mBucketName = "rt-assets";
                accessKeyId = ossPathModel.getData().getAccess_key_id();
                accessKeySecret = ossPathModel.getData().getAccess_key_secret();
                securityToken = ossPathModel.getData().getToken();
                endpoint = "oss-cn-beijing.aliyuncs.com";
                initAliOss(context, accessKeyId, accessKeySecret, securityToken, endpoint);
                listener.onSuccess(reqId, ossPathModel);
            }

            @Override
            public void onFailure(String reqId, String errCode, String msg) {
                listener.onFailure(reqId, errCode, msg);
                ULog.e("clll", errCode + msg);
            }
        });
    }

    /**
     * 初始化上传配置信息
     */
    private static void initAliOss(Context context, String accessKeyId, String accessKeySecret, String securityToken, String endpoint) {
        // 在移动端建议使用STS的方式初始化OSSClient，更多信息参考：[访问控制]
        OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(accessKeyId, accessKeySecret, securityToken);
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        mOssClient = new OSSClient(context, endpoint, credentialProvider, conf);
    }

    public String getBasePath() {
        return mBasePath;
    }

    public OSSClient getOssClient() {
        return mOssClient;
    }

    public String getBucketName() {
        return mBucketName;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public String securityToken() {
        return securityToken;
    }

    public boolean isInit() {
        return !TextUtils.isEmpty(accessKeyId) && !TextUtils.isEmpty(accessKeySecret) && !TextUtils.isEmpty(securityToken);
    }

}
