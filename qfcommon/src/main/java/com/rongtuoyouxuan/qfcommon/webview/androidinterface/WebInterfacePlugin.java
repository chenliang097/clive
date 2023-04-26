package com.rongtuoyouxuan.qfcommon.webview.androidinterface;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Pair;
import android.webkit.JavascriptInterface;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ArrayUtils;
import com.blankj.utilcode.util.ClipboardUtils;
import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.rongtuoyouxuan.chatlive.util.DirectoryUtils;
import com.rongtuoyouxuan.libuikit.TransferLoadingUtil;
import com.rongtuoyouxuan.qfcommon.R;
import com.rongtuoyouxuan.qfcommon.permission.RxPermissionProxy;
import com.rongtuoyouxuan.chatlive.util.LaToastUtil;
import com.rongtuoyouxuan.qfcommon.viewmodel.CommonViewModel;
import com.rongtuoyouxuan.qfcommon.webview.model.ComInterfaceData;
import com.just.agentweb.AgentWeb;
import com.yalantis.ucrop.UCrop;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MatisseUtil;

import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.List;

/*
 *Create by {Mr秦} on 2022/8/24
 */
public class WebInterfacePlugin extends AndroidInterfacePlugin {
    private static final int IMAGE_REQ = 1001;
    private CommonViewModel commonViewModel;
    private ComInterfaceData openPhoneAlbumData;
    private FragmentActivity activity;
    private Lifecycle lifecycle;


    public WebInterfacePlugin(AgentWeb mAgentWeb,
                              Lifecycle lifecycle,
                              CommonViewModel commonViewModel) {
        super(mAgentWeb, ActivityUtils.getTopActivity());
        this.commonViewModel = commonViewModel;
        this.lifecycle = lifecycle;
        this.activity = (FragmentActivity) ActivityUtils.getTopActivity();
        initData();
    }

    /**
     * 粘贴板赋值
     */
    @JavascriptInterface
    public void copyLinkUrl(String json) {
        ComInterfaceData data = GsonUtils.fromJson(json, ComInterfaceData.class);
        ClipboardUtils.copyText(data.getAccess_url());
        LaToastUtil.showShort(StringUtils.getString(R.string.rt_copy_suc));
    }

    /**
     * 打开相册
     */
    @JavascriptInterface
    public void openPhoneAlbum(String json) {
        try {
            openPhoneAlbumData = GsonUtils.fromJson(json, ComInterfaceData.class);
            ThreadUtils.runOnUiThread(() ->
                    new RxPermissionProxy(activity).requestPermission(accept -> {
                        if (accept) {
                            MatisseUtil.navigation2Album(
                                    ActivityUtils.getTopActivity(), 1,
                                    IMAGE_REQ
                            );
                        }
                    }));
        } catch (Exception e) {
            LogUtils.e("Exception:" + e);
        }
    }

    //facebook:0,twitter:1,line:2
    @JavascriptInterface
    public void showChannelShare(String json) {
        ComInterfaceData data = GsonUtils.fromJson(json, ComInterfaceData.class);
        try {
            switch (data.getType()) {
                case "0":
                    break;

                case "1":
                    break;
                case "2":
                    break;
            }
        } catch (Exception e) {
        }
    }


    /**
     * activity 回调
     */
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        switch (requestCode) {
            case IMAGE_REQ:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    List<String> pathList = Matisse.obtainPathResult(data);
                    if (!CollectionUtils.isEmpty(pathList)) {
                        String path = pathList.get(0);
                        Uri destinationUri = Uri.fromFile(new
                                File(DirectoryUtils.getCacheFilesDirFile(DirectoryUtils.DIRECTORY_IMAGES),
                                System.currentTimeMillis() + ".jpg")
                        );
                        UCrop.of(Uri.fromFile(new File(path)), destinationUri)
                                .withAspectRatio(6f, 6f)
                                .start(ActivityUtils.getTopActivity());
                    }
                }
                break;
            case UCrop.REQUEST_CROP:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    Uri croppedFileUri = UCrop.getOutput(data);
                    if (croppedFileUri != null
                            && !StringUtils.isTrimEmpty(croppedFileUri.getPath())
                            && openPhoneAlbumData != null
                    ) {
                        TransferLoadingUtil.showDialogLoading(activity);
                        commonViewModel.uploadFile(activity, ArrayUtils.asList(croppedFileUri.getPath()));
                    }
                } else if (resultCode == UCrop.RESULT_ERROR) {
                    LaToastUtil.showShort(R.string.pl_libcenter_crop_error);
                }
                break;
        }
    }

    private void initData() {
        commonViewModel.uploadSuccessData.observe(activity, strings -> {
            TransferLoadingUtil.dismissDialogLoading(activity);
            if (strings.size() > 0) {
                callJs(openPhoneAlbumData.getJsCallback(),
                        new Pair<>("imageUrl", strings.get(0)),
                        new Pair<>("type", openPhoneAlbumData.getType()));
            }
        });
        commonViewModel.uploadFailData.observe(activity, strings -> TransferLoadingUtil.dismissDialogLoading(activity));
    }
}