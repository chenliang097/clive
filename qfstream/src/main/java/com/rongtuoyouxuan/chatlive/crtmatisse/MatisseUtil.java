package com.rongtuoyouxuan.chatlive.crtmatisse;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.rongtuoyouxuan.chatlive.stream.R;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.rongtuoyouxuan.chatlive.crtmatisse.dialog.DiySystemDialog;
import com.rongtuoyouxuan.chatlive.crtmatisse.engine.impl.GlideEngine;
import com.rongtuoyouxuan.chatlive.crtmatisse.filter.Filter;
import com.rongtuoyouxuan.chatlive.crtmatisse.internal.entity.CaptureStrategy;
import com.rongtuoyouxuan.chatlive.crtmatisse.internal.entity.IncapableCause;
import com.rongtuoyouxuan.chatlive.crtmatisse.internal.entity.Item;
import com.rongtuoyouxuan.chatlive.crtmatisse.listener.OnCheckedListener;
import com.rongtuoyouxuan.chatlive.crtmatisse.listener.OnSelectedListener;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.reactivex.functions.Consumer;


public class MatisseUtil {
    public static final int ALBUM = 10000;
    public static final int CAMERA = 10001;
    public static final int VIDEO = 10002;
    public static final int MIN_IMAGE_HEIGHT = 1080;
    public static final int MIN_IMAGE_WIDTH = 1080;

    public static void navigation2Album(final Activity activity, int maxNum, int requestCode, int minwidth, int minheight) {
        navigation2Album(activity, maxNum, requestCode, minwidth, minheight, activity.getString(R.string.min_select_upload_image_size), IncapableCause.TOAST);
    }

    public static void navigation2Album(final Activity activity, int maxNum, int requestCode, int minwidth, int minheight, final String msg, @IncapableCause.Form final int type) {
        final int theWidth = minwidth > 0 ? minwidth : MIN_IMAGE_WIDTH;
        final int theHeight = minheight > 0 ? minheight : MIN_IMAGE_HEIGHT;
        Matisse.from(activity)
                .choose(MimeType.of(MimeType.JPEG, MimeType.PNG))
                .addFilter(new Filter() {
                    @Override
                    protected Set<MimeType> constraintTypes() {
                        return MimeType.of(MimeType.JPEG, MimeType.PNG);
                    }

                    @Override
                    public IncapableCause filter(Context context, Item item) {
                        if (item.uri != null) {
                            try {
                                InputStream ims = context.getContentResolver().openInputStream(item.uri);
                                BitmapFactory.Options opts = new BitmapFactory.Options();
                                opts.inJustDecodeBounds = true;
                                BitmapFactory.decodeStream(ims, null, opts);
                                if (opts.outWidth < theWidth || opts.outHeight < theHeight) {
                                    return new IncapableCause(type, msg);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        return null;
                    }
                })
                .showSingleMediaType(true)
                .countable(false)
                .capture(false)
                .setJump2Pre(true)
                .originalEnable(false)
                .captureStrategy(
//                        new CaptureStrategy(true, "com.haiwaizj.chatlive2.file.path.share"))
                        new CaptureStrategy(true, activity.getPackageName() + ".file.path.share"))
                .maxSelectable(maxNum)
//                    .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
//                    .gridExpectedSize(
//                            getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.85f)
//                                            .imageEngine(new GlideEngine())  // for glide-V3
                .imageEngine(new GlideEngine())    // for glide-V4
                .setOnSelectedListener(new OnSelectedListener() {
                    @Override
                    public void onSelected(
                            @NonNull List<Uri> uriList, @NonNull List<String> pathList) {
                        // DO SOMETHING IMMEDIATELY HERE
                        Log.e("onSelected", "onSelected: pathList=" + pathList);

                    }
                })
                .originalEnable(true)
                .maxOriginalSize(10)
                .autoHideToolbarOnSingleTap(true)
                .setOnCheckedListener(new OnCheckedListener() {
                    @Override
                    public void onCheck(boolean isChecked) {
                        // DO SOMETHING IMMEDIATELY HERE
                        Log.e("isChecked", "onCheck: isChecked=" + isChecked);
                    }
                })
                .forResult(requestCode);
    }

    public static void navigation2Album(final Activity activity, int maxNum, int requestCode) {
        Matisse.from(activity)
                .choose(MimeType.of(MimeType.JPEG, MimeType.PNG))
                .showSingleMediaType(true)
                .countable(false)
                .capture(false)
                .setJump2Pre(true)
                .originalEnable(false)
                .captureStrategy(
//                        new CaptureStrategy(true, "com.haiwaizj.chatlive2.file.path.share"))
                        new CaptureStrategy(true, activity.getPackageName() + ".file.path.share"))
                .maxSelectable(maxNum)
//                    .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
//                    .gridExpectedSize(
//                            getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.85f)
//                                            .imageEngine(new GlideEngine())  // for glide-V3
                .imageEngine(new GlideEngine())    // for glide-V4
                .setOnSelectedListener(new OnSelectedListener() {
                    @Override
                    public void onSelected(
                            @NonNull List<Uri> uriList, @NonNull List<String> pathList) {
                        // DO SOMETHING IMMEDIATELY HERE
                        Log.e("onSelected", "onSelected: pathList=" + pathList);

                    }
                })
                .originalEnable(true)
                .maxOriginalSize(10)
                .autoHideToolbarOnSingleTap(true)
                .setOnCheckedListener(new OnCheckedListener() {
                    @Override
                    public void onCheck(boolean isChecked) {
                        // DO SOMETHING IMMEDIATELY HERE
                        Log.e("isChecked", "onCheck: isChecked=" + isChecked);
                    }
                })
                .forResult(requestCode);
    }

    //私聊界面 使用添加照相
    public static void natigationAddCamera(final Activity activity, int maxNum, int requestCode) {
        Matisse.from(activity)
                .choose(MimeType.of(MimeType.JPEG, MimeType.PNG))
                .addFilter(new Filter() {
                    @Override
                    protected Set<MimeType> constraintTypes() {
                        return MimeType.of(MimeType.JPEG, MimeType.PNG);
                    }

                    @Override
                    public IncapableCause filter(Context context, Item item) {
                        if (item.uri != null) {
                            try {
                                File file = new File(getPath(activity, item.uri));
                                if (file.isFile() && file.exists()) {
                                    if (file.length() > 1024 * 25 * 1024) {
                                        return new IncapableCause(context.getString(R.string.max_select_upload_video_size));
                                    }
                                } else {
                                    return new IncapableCause(context.getString(R.string.pl_liboss_file_empty));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        return null;
                    }
                })
                .showSingleMediaType(true)
                .countable(false)
                .capture(true)
                .setJump2Pre(true)
                .originalEnable(false)
                .captureStrategy(
//                        new CaptureStrategy(true, "com.haiwaizj.chatlive2.file.path.share"))
                        new CaptureStrategy(true, activity.getPackageName() + ".file.path.share"))
                .maxSelectable(maxNum)
//                    .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
//                    .gridExpectedSize(
//                            getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.85f)
//                                            .imageEngine(new GlideEngine())  // for glide-V3
                .imageEngine(new GlideEngine())    // for glide-V4
                .setOnSelectedListener(new OnSelectedListener() {
                    @Override
                    public void onSelected(
                            @NonNull List<Uri> uriList, @NonNull List<String> pathList) {
                        // DO SOMETHING IMMEDIATELY HERE
                        Log.e("onSelected", "onSelected: pathList=" + pathList);

                    }
                })
                .originalEnable(true)
                .maxOriginalSize(10)
                .autoHideToolbarOnSingleTap(true)
                .setOnCheckedListener(new OnCheckedListener() {
                    @Override
                    public void onCheck(boolean isChecked) {
                        // DO SOMETHING IMMEDIATELY HERE
                        Log.e("isChecked", "onCheck: isChecked=" + isChecked);
                    }
                })
                .forResult(requestCode);
    }

    //私聊界面 使用发送视频
    public static void natigationVideo(final Activity activity, int maxNum, int requestCode) {
        Matisse.from(activity)
                .choose(MimeType.of(MimeType.MPEG, MimeType.MP4, MimeType.QUICKTIME, MimeType.THREEGPP, MimeType.THREEGPP2, MimeType.MKV, MimeType.WEBM, MimeType.TS, MimeType.AVI))
                .addFilter(new Filter() {
                    @Override
                    protected Set<MimeType> constraintTypes() {
                        return MimeType.of(MimeType.MPEG, MimeType.MP4, MimeType.QUICKTIME, MimeType.THREEGPP, MimeType.THREEGPP2, MimeType.MKV, MimeType.WEBM, MimeType.TS, MimeType.AVI);
                    }

                    @Override
                    public IncapableCause filter(Context context, Item item) {
                        if (item.uri != null) {
                            try {
                                File file = new File(getPath(activity, item.uri));
                                if (file.isFile() && file.exists()) {
                                    if (file.length() > 1024 * 25 * 1024) {
                                        return new IncapableCause(context.getString(R.string.max_select_upload_video_size));
                                    }
                                } else {
                                    return new IncapableCause(context.getString(R.string.pl_liboss_file_empty));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        return null;
                    }
                })
                .showSingleMediaType(true)
                .countable(false)
                .capture(false)
                .setJump2Pre(true)
                .originalEnable(false)
                .captureStrategy(
//                        new CaptureStrategy(true, "com.haiwaizj.chatlive2.file.path.share"))
                        new CaptureStrategy(true, activity.getPackageName() + ".file.path.share"))
                .maxSelectable(maxNum)
//                    .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
//                    .gridExpectedSize(
//                            getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.85f)
//                                            .imageEngine(new GlideEngine())  // for glide-V3
                .imageEngine(new GlideEngine())    // for glide-V4
                .setOnSelectedListener(new OnSelectedListener() {
                    @Override
                    public void onSelected(
                            @NonNull List<Uri> uriList, @NonNull List<String> pathList) {
                        // DO SOMETHING IMMEDIATELY HERE
                        Log.e("onSelected", "onSelected: pathList=" + pathList);

                    }
                })
                .originalEnable(true)
                .maxOriginalSize(10)
                .autoHideToolbarOnSingleTap(true)
                .setOnCheckedListener(new OnCheckedListener() {
                    @Override
                    public void onCheck(boolean isChecked) {
                        // DO SOMETHING IMMEDIATELY HERE
                        Log.e("isChecked", "onCheck: isChecked=" + isChecked);
                    }
                })
                .forResult(requestCode);
    }

    public static void videoCover(@NonNull final Fragment fragment,
                                  int maxNumVideo,
                                  int requestCode,
                                  final String videoShortErrorHint,
                                  final String videoLengthErrorHint,
                                  final long minTime,
                                  final long maxTime) {

        Filter filter = new Filter() {
            @Override
            protected Set<MimeType> constraintTypes() {
                return MimeType.ofVideo();
            }

            @Override
            public IncapableCause filter(Context context, Item item) {
                if (!needFiltering(context, item)) {
                    return null;
                }
                if (item.duration < minTime * 1000) {//小于3s
                    return new IncapableCause(videoShortErrorHint);
                } else if (item.duration > maxTime * 1000 * 60 * 10) {
                    return new IncapableCause(videoLengthErrorHint);
                }
                return null;
            }
        };
        navigation2AlbumVideo(fragment, Build.VERSION.SDK_INT >= 21 ? maxNumVideo : 0, Collections.singletonList(filter), false, requestCode);

    }

    /**
     * 仅发布动态使用
     */
    public static void dynamicPublish(@NonNull final Activity activity,
                                      int maxNumImage,
                                      int maxNumVideo,
                                      int requestCode,
                                      final String videoShortErrorHint,
                                      final String videoLengthErrorHint) {
        Filter filter = new Filter() {
            @Override
            protected Set<MimeType> constraintTypes() {
                return MimeType.ofVideo();
            }

            @Override
            public IncapableCause filter(Context context, Item item) {
                if (!needFiltering(context, item)) {
                    return null;
                }
                if (item.duration < 3000) {//小于3s
                    return new IncapableCause(videoShortErrorHint);
                } else if (item.duration > 1000 * 60 * 10) {//大于10min
                    return new IncapableCause(videoLengthErrorHint);
                }
                return null;
            }
        };
        navigation2AlbumVideo(activity, maxNumImage, Build.VERSION.SDK_INT >= 21 ? maxNumVideo : 0, Collections.singletonList(filter), false, requestCode);
    }

    public static void navigation2AlbumVideo(@NonNull final Activity activity,
                                             int maxNumImage,
                                             int maxNumVideo,
                                             @Nullable List<Filter> filters,
                                             boolean videoPreviewEnable,
                                             int requestCode) {
        Set<MimeType> mimeTypes = new HashSet<>();
        if (maxNumImage > 0) {
            mimeTypes.addAll(MimeType.of(MimeType.JPEG, MimeType.PNG));
        }
        if (maxNumVideo > 0) {
            mimeTypes.addAll(MimeType.ofVideo());
        }
        navigation(activity,
                maxNumImage,
                maxNumVideo,
                mimeTypes,
                filters,
                videoPreviewEnable,
                requestCode);
    }

    public static void navigation2AlbumVideo(@NonNull final Fragment fragment,
                                             int maxNumVideo,
                                             @Nullable List<Filter> filters,
                                             boolean videoPreviewEnable,
                                             int requestCode) {
        Set<MimeType> mimeTypes = new HashSet<>();
        mimeTypes.addAll(MimeType.ofVideo());
        navigation(fragment,
                maxNumVideo,
                mimeTypes,
                filters,
                videoPreviewEnable,
                requestCode);
    }

    public static void navigation(@NonNull final Fragment fragment,
                                  int maxNumVideo,
                                  @NonNull Set<MimeType> mimeTypes,
                                  @Nullable List<Filter> filters,
                                  boolean videoPreviewEnable,
                                  int requestCode) {
        SelectionCreator selectionCreator = Matisse.from(fragment)
                .choose(mimeTypes)
                .showSingleMediaType(true)
                .countable(false)
                .capture(false)
                .setJump2Pre(true)
                .originalEnable(false)
                .captureStrategy(
                        new CaptureStrategy(true, fragment.getActivity().getPackageName() + ".file.path.share"))
                .maxSelectable(maxNumVideo)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())    // for glide-V4
                .setOnSelectedListener(new OnSelectedListener() {
                    @Override
                    public void onSelected(
                            @NonNull List<Uri> uriList, @NonNull List<String> pathList) {
                        // DO SOMETHING IMMEDIATELY HERE
                        Log.e("onSelected", "onSelected: pathList=" + pathList);

                    }
                })
                .originalEnable(true)
                .maxOriginalSize(10)
                .autoHideToolbarOnSingleTap(true)
                .setOnCheckedListener(new OnCheckedListener() {
                    @Override
                    public void onCheck(boolean isChecked) {
                        // DO SOMETHING IMMEDIATELY HERE
                        Log.e("isChecked", "onCheck: isChecked=" + isChecked);
                    }
                })
                .videoPreViewEnable(videoPreviewEnable);

        if (filters != null) {
            for (Filter filter : filters) {
                selectionCreator.addFilter(filter);
            }
        }

        selectionCreator.forResult(requestCode);
    }

    public static void navigation(@NonNull final Activity activity,
                                  int maxNumImage,
                                  int maxNumVideo,
                                  @NonNull Set<MimeType> mimeTypes,
                                  @Nullable List<Filter> filters,
                                  boolean videoPreviewEnable,
                                  int requestCode) {
        SelectionCreator selectionCreator = Matisse.from(activity)
                .choose(mimeTypes)
                .showSingleMediaType(true)
                .countable(false)
                .capture(false)
                .setJump2Pre(true)
                .originalEnable(false)
                .captureStrategy(
                        new CaptureStrategy(true, activity.getPackageName() + ".file.path.share"))
                .maxSelectable(maxNumImage)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())    // for glide-V4
                .setOnSelectedListener(new OnSelectedListener() {
                    @Override
                    public void onSelected(
                            @NonNull List<Uri> uriList, @NonNull List<String> pathList) {
                        // DO SOMETHING IMMEDIATELY HERE
                        Log.e("onSelected", "onSelected: pathList=" + pathList);

                    }
                })
                .originalEnable(true)
                .maxOriginalSize(10)
                .autoHideToolbarOnSingleTap(true)
                .setOnCheckedListener(new OnCheckedListener() {
                    @Override
                    public void onCheck(boolean isChecked) {
                        // DO SOMETHING IMMEDIATELY HERE
                        Log.e("isChecked", "onCheck: isChecked=" + isChecked);
                    }
                })
                .videoPreViewEnable(videoPreviewEnable);

        if (maxNumVideo > 0 && maxNumImage > 0) {
            selectionCreator.maxSelectablePerMediaType(maxNumImage, maxNumVideo);
        } else if (maxNumImage > 0) {
            selectionCreator.maxSelectable(maxNumImage);
        } else {
            selectionCreator.maxSelectable(maxNumVideo);
        }

        if (filters != null) {
            for (Filter filter : filters) {
                selectionCreator.addFilter(filter);
            }
        }

        selectionCreator.forResult(requestCode);
    }

    public static String openCamera(Activity activity, int requestCode) {
        String mPhotoPath = newDcimFile().getAbsolutePath();
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        //这里我们插入一条数据，ContentValues是我们希望这条记录被创建时包含的数据信息
        //这些数据的名称已经作为常量在MediaStore.Images.Media中,有的存储在MediaStore.MediaColumn中了
        ContentValues values = new ContentValues(1);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getUriForFile(activity, new File(mPhotoPath)));
        activity.startActivityForResult(intent, requestCode);
        return mPhotoPath;
    }

    public static Uri getUriForFile(Context context, File file) {
        Uri fileUri;
        if (Build.VERSION.SDK_INT >= 24) {
            fileUri = getUriForFile24(context, file);
        } else {
            fileUri = Uri.fromFile(file);
        }
        return fileUri;
    }

    private static Uri getUriForFile24(Context context, File file) {
        Uri fileUri = androidx.core.content.FileProvider.getUriForFile(context,
                context.getPackageName() + ".file.path.share",
                file);
        return fileUri;
    }

    @SuppressLint("Range")
    private static String getPath(Context context, Uri uri) {
        String path = null;
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        if (cursor == null) {
            return null;
        }
        if (cursor.moveToFirst()) {
            try {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        cursor.close();
        return path;
    }


    public static File newDcimFile() {
        //图片名称 时间命名
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date(System.currentTimeMillis());
        String filename = format.format(date);

        //创建File对象用于存储拍照的图片 SD卡根目录
        //File outputImage = new File(Environment.getExternalStorageDirectory(),test.jpg);
        //存储至DCIM文件夹
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        return new File(path, filename + ".jpg");
    }


    /**
     * 请求权限
     */
    @SuppressLint("CheckResult")
    public static void requestPermission(final FragmentActivity activity, final int requestCode, @NonNull final onPermissionListener permissionListener) {
        RxPermissions rxPermission = new RxPermissions(activity);
        rxPermission.requestEachCombined(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) {
                        if (permission.granted) {
                            // 用户已经同意该权限
                            permissionListener.onSuccess();
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                            DiySystemDialog.Builder builder = new DiySystemDialog.Builder(activity);
                            builder.setTitle(activity.getString(R.string.permission_camrea));
                            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    permissionListener.onCancel();
                                    dialog.dismiss();
                                }
                            });
                            builder.setPositiveButton(activity.getString(R.string.to_allow), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    requestPermission(activity, requestCode, permissionListener);
                                }
                            });
                            builder.create().show();
                        } else {
                            // 用户拒绝了该权限，并且选中『不再询问』
                            DiySystemDialog.Builder builder = new DiySystemDialog.Builder(activity);
                            builder.setTitle(activity.getString(R.string.permission_camrea));
                            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    permissionListener.onCancel();
                                    dialog.dismiss();
                                }
                            });
                            builder.setPositiveButton(activity.getString(R.string.to_allow), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    //跳转系统权限设置
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    intent.setData(Uri.parse("package:" + activity.getPackageName()));
                                    activity.startActivityForResult(intent, requestCode);
                                }
                            });
                            builder.create().show();
                        }
                    }
                });
    }


    public static void navigationFragment2Album(final Fragment fragment, int maxNum, int requestCode, int minwidth, int minheight, final String msg, @IncapableCause.Form final int type) {
        final int theWidth = minwidth > 0 ? minwidth : MIN_IMAGE_WIDTH;
        final int theHeight = minheight > 0 ? minheight : MIN_IMAGE_HEIGHT;
        Matisse.from(fragment)
                .choose(MimeType.of(MimeType.JPEG, MimeType.PNG))
                .addFilter(new Filter() {
                    @Override
                    protected Set<MimeType> constraintTypes() {
                        return MimeType.of(MimeType.JPEG, MimeType.PNG);
                    }

                    @Override
                    public IncapableCause filter(Context context, Item item) {
                        if (item.uri != null) {
                            try {
                                InputStream ims = context.getContentResolver().openInputStream(item.uri);
                                BitmapFactory.Options opts = new BitmapFactory.Options();
                                opts.inJustDecodeBounds = true;
                                BitmapFactory.decodeStream(ims, null, opts);
                                if (opts.outWidth < theWidth || opts.outHeight < theHeight) {
                                    if (TextUtils.isEmpty(msg)) {
                                        return new IncapableCause(context.getString(R.string.min_select_upload_image_size));
                                    } else {
                                        return new IncapableCause(type, msg);
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        return null;
                    }
                })
                .showSingleMediaType(true)
                .countable(false)
                .capture(false)
                .setJump2Pre(true)
                .captureStrategy(
//                        new CaptureStrategy(true, "com.haiwaizj.chatlive2.file.path.share"))
                        new CaptureStrategy(true, fragment.getActivity().getPackageName() + ".file.path.share"))
                .maxSelectable(maxNum)
//                    .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
//                    .gridExpectedSize(
//                            getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.85f)
//                                            .imageEngine(new GlideEngine())  // for glide-V3
                .imageEngine(new GlideEngine())    // for glide-V4
                .setOnSelectedListener(new OnSelectedListener() {
                    @Override
                    public void onSelected(
                            @NonNull List<Uri> uriList, @NonNull List<String> pathList) {
                        // DO SOMETHING IMMEDIATELY HERE
                        Log.e("onSelected", "onSelected: pathList=" + pathList);

                    }
                })
                .originalEnable(true)
                .maxOriginalSize(10)
                .autoHideToolbarOnSingleTap(true)
                .setOnCheckedListener(new OnCheckedListener() {
                    @Override
                    public void onCheck(boolean isChecked) {
                        // DO SOMETHING IMMEDIATELY HERE
                        Log.e("isChecked", "onCheck: isChecked=" + isChecked);
                    }
                })
                .forResult(requestCode);
    }

    /**
     * fragment 选择相册 ************************************
     *
     * @param fragment    fragment
     * @param maxNum
     * @param requestCode
     */
    public static void navigationFragment2Album(Fragment fragment, int maxNum, int requestCode, int minwidth, int minheight) {
        navigationFragment2Album(fragment, maxNum, requestCode, minwidth, minheight, "", IncapableCause.DIALOG);
    }

    /**
     * fragment 选择相册 ************************************
     *
     * @param fragment    fragment
     * @param maxNum
     * @param requestCode
     */
    public static void navigationFragment2Album(Fragment fragment, int maxNum, int requestCode) {
        Matisse.from(fragment)

                .choose(MimeType.of(MimeType.JPEG, MimeType.PNG))
                .showSingleMediaType(true)
                .countable(false)
                .capture(false)
                .setJump2Pre(true)
                .captureStrategy(
//                        new CaptureStrategy(true, "com.haiwaizj.chatlive2.file.path.share"))
                        new CaptureStrategy(true, fragment.getActivity().getPackageName() + ".file.path.share"))
                .maxSelectable(maxNum)
//                    .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
//                    .gridExpectedSize(
//                            getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.85f)
//                                            .imageEngine(new GlideEngine())  // for glide-V3
                .imageEngine(new GlideEngine())    // for glide-V4
                .setOnSelectedListener(new OnSelectedListener() {
                    @Override
                    public void onSelected(
                            @NonNull List<Uri> uriList, @NonNull List<String> pathList) {
                        // DO SOMETHING IMMEDIATELY HERE
                        Log.e("onSelected", "onSelected: pathList=" + pathList);

                    }
                })
                .originalEnable(true)
                .maxOriginalSize(10)
                .autoHideToolbarOnSingleTap(true)
                .setOnCheckedListener(new OnCheckedListener() {
                    @Override
                    public void onCheck(boolean isChecked) {
                        // DO SOMETHING IMMEDIATELY HERE
                        Log.e("isChecked", "onCheck: isChecked=" + isChecked);
                    }
                })
                .forResult(requestCode);
    }

    /**
     * fragment 从相机选择图片 ************************************
     *
     * @param fragment    fragment
     * @param requestCode
     */
    public static String openFragment2Camera(Fragment fragment, int requestCode) {
        String mPhotoPath = newDcimFile().getAbsolutePath();
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        //这里我们插入一条数据，ContentValues是我们希望这条记录被创建时包含的数据信息
        //这些数据的名称已经作为常量在MediaStore.Images.Media中,有的存储在MediaStore.MediaColumn中了
        ContentValues values = new ContentValues(1);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getUriForFile(fragment.getContext(), new File(mPhotoPath)));
        fragment.startActivityForResult(intent, requestCode);
        return mPhotoPath;
    }

    /**
     * Fragment请求权限
     */
    public static void fragmentRequestPermission(final Fragment fragment, final int requestCode, @NonNull final onPermissionListener permissionListener) {
        RxPermissions rxPermission = new RxPermissions(fragment);
        rxPermission.requestEachCombined(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) {
                        if (permission.granted) {
                            // 用户已经同意该权限
                            permissionListener.onSuccess();
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                            DiySystemDialog.Builder builder = new DiySystemDialog.Builder(fragment.getActivity());
                            builder.setMessage(fragment.getActivity().getString(R.string.permission_camrea));
                            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    permissionListener.onCancel();
                                    dialog.dismiss();
                                }
                            });
                            builder.setPositiveButton(fragment.getActivity().getString(R.string.to_allow), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    fragmentRequestPermission(fragment, requestCode, permissionListener);
                                }
                            });
                            builder.create().show();
                        } else {
                            // 用户拒绝了该权限，并且选中『不再询问』
                            DiySystemDialog.Builder builder = new DiySystemDialog.Builder(fragment.getActivity());
                            builder.setMessage(fragment.getActivity().getString(R.string.permission_camrea));
                            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    permissionListener.onCancel();
                                    dialog.dismiss();
                                }
                            });
                            builder.setPositiveButton(fragment.getActivity().getString(R.string.to_allow), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    //跳转系统权限设置
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    intent.setData(Uri.parse("package:" + fragment.getActivity().getPackageName()));
                                    fragment.startActivityForResult(intent, requestCode);
                                }
                            });
                            builder.create().show();
                        }
                    }
                });
    }


    public interface onPermissionListener {
        void onSuccess();

        void onCancel();
    }

}
