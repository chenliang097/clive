package com.rongtuoyouxuan.chatlive.qfcommon.permission;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.blankj.utilcode.util.ArrayUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.rongtuoyouxuan.chatlive.stream.R;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.rongtuoyouxuan.chatlive.crtmatisse.dialog.DiySystemDialog;

/*
 *Create by {Mr秦} on 2022/7/29
 */
public class RxPermissionProxy {
    private RxPermissions mPermissions;
    private Context mContext;

    public RxPermissionProxy(@NonNull FragmentActivity activity) {
        mContext = activity;
        mPermissions = new RxPermissions(activity);
    }

    public RxPermissionProxy(@NonNull Fragment fragment) {
        mContext = fragment.getActivity();
        mPermissions = new RxPermissions(fragment);
    }

    /**
     * @param permission 是否被授权
     */
    public boolean isGranted(String permission) {
        return mPermissions.isGranted(permission);
    }

    /**
     * 请求相机和存储权限
     */
    public void requestPermission( @NonNull final PermissionResult permissionResult) {
        mPermissions.requestEachCombined(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(permission -> {
                    if (permission.granted) {
                        // 用户已经同意该权限
                        permissionResult.accept(true);
                    } else if (permission.shouldShowRequestPermissionRationale) {
                        permissionResult.accept(false);
                    } else {
                        // 用户拒绝了该权限，并且选中『不再询问』
                        DiySystemDialog.Builder builder = new DiySystemDialog.Builder(mContext);
                        builder.setTitle(mContext.getString(R.string.permission_camrea));
                        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                permissionResult.accept(false);
                                dialog.dismiss();
                            }
                        });
                        builder.setPositiveButton(mContext.getString(R.string.to_allow), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                //跳转系统权限设置
                                PermissionUtils.launchAppDetailsSettings();
                            }
                        });
                        builder.create().show();
                    }
                });
    }

    /**
     * 请求获取存储权限
     */
    public void requestStorage(PermissionResult permissionResult) {
        if (permissionResult == null) return;
        String[] permissionArray = ArrayUtils.newArray(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        mPermissions.requestEachCombined(permissionArray)
                .subscribe(permission -> {
                    if (permission.granted) {
                        // 用户已经同意该权限
                        permissionResult.accept(true);
                    } else if (permission.shouldShowRequestPermissionRationale) {
                        permissionResult.accept(false);
                    } else {
                        // 用户拒绝了该权限，并且选中『不再询问』
                        DiySystemDialog.Builder builder = new DiySystemDialog.Builder(mContext);
                        builder.setTitle(mContext.getString(R.string.permission_camrea));
                        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                permissionResult.accept(false);
                                dialog.dismiss();
                            }
                        });
                        builder.setPositiveButton(mContext.getString(R.string.to_allow), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                //跳转系统权限设置
                                PermissionUtils.launchAppDetailsSettings();
                            }
                        });
                        builder.create().show();
                    }
                });



    }
}