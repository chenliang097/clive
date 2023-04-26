package com.rongtuoyouxuan.chatlive.util;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.Image;
import android.media.ImageReader;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Handler;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 描述：截屏功能
 *
 * @time 2019/9/19
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class ScreenCapture {

    private final int REQUEST_CODE_ASK_PERMISSIONS = 7777;

    private int mResultCode;
    private Intent mResultData;

    private MediaProjectionManager mMediaProjectionManager;
    private MediaProjection mMediaProjection;
    private VirtualDisplay mVirtualDisplay;
    private int windowWidth;
    private int windowHeight;
    private int mScreenDensity;
    private int mRequsetCode;
    private String mAppDataFilePath;
    private ImageReader mImageReader;
    private ICaptureListener mICaptureListener;
    private Handler handler = new Handler();

    private String TAG = "ScreenCapture";
    private Thread thread;

    private ScreenCapture(MediaProjectionManager mMediaProjectionManager, int width, int height, int mScreenDensity, String mAppDataFilePath, int requsetCode) {
        this.mMediaProjectionManager = mMediaProjectionManager;
        windowWidth = width;
        windowHeight = height;
        this.mScreenDensity = mScreenDensity;
        this.mAppDataFilePath = mAppDataFilePath;
        mRequsetCode = requsetCode;
    }

    public void startScreenCapture(Activity activity) {
        if (activity == null) {
            Log.i(TAG, "activity == null");
            return;
        }

        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "checkSelfPermission");
            String [] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(activity, permissions, REQUEST_CODE_ASK_PERMISSIONS);
        } else if (mMediaProjection != null) {
            Log.i(TAG, "setUpVirtualDisplay");
            setUpVirtualDisplay();
        } else if (mResultCode != 0 && mResultData != null) {
            Log.i(TAG, "mResultCode != 0 && mResultData != null");
            setUpMediaProjection();
            setUpVirtualDisplay();
        } else {
            Log.i(TAG, "Requesting confirmation");
            // This initiates a prompt dialog for the user to confirm screen projection.
            activity.startActivityForResult(
                    mMediaProjectionManager.createScreenCaptureIntent(),
                    mRequsetCode);
        }
    }

    private void setUpVirtualDisplay() {
        if (mImageReader == null) {
            mImageReader = ImageReader.newInstance(windowWidth, windowHeight, 0x1, 2);
        }
        Log.i(TAG, "Setting up a VirtualDisplay: " +
                mImageReader.getWidth() + "x" + mImageReader.getHeight() +
                " (" + mScreenDensity + ")");
        mVirtualDisplay = mMediaProjection.createVirtualDisplay("ScreenCapture",
                windowWidth, windowHeight, mScreenDensity,
                DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                mImageReader.getSurface(), null, null);
    }

    private void setUpMediaProjection() {
        mMediaProjection = mMediaProjectionManager.getMediaProjection(mResultCode, mResultData);
    }

    private void stopScreenCapture() {
        if (mVirtualDisplay != null) {
            mVirtualDisplay.release();
            mVirtualDisplay = null;
        }
        if (mMediaProjection != null) {
            mMediaProjection.stop();
            mMediaProjection = null;
        }
        if (thread != null) {
            if (!thread.isInterrupted()) {
                thread.interrupt();
            }
        }
        mResultCode = 0;
        mResultData = null;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == mRequsetCode) {
            if (resultCode != Activity.RESULT_OK) {
//                Log.i(TAG, "User cancelled");
                return;
            }
//            Log.i(TAG, "Starting screen capture");
            mResultCode = resultCode;
            mResultData = data;
            setUpMediaProjection();
            setUpVirtualDisplay();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startCapture();
                }
            }, 1500);
        }

    }


    private void startCapture() {
        thread = new Thread() {
            @Override
            public void run() {
                Date date = new Date() ;
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss") ;
                final String filename = mAppDataFilePath+"screenshot"+ dateFormat.format(date);
                Image image = mImageReader.acquireLatestImage();
                if (image == null) {
                    return;
                }
                int width = image.getWidth();
                int height = image.getHeight();
                final Image.Plane[] planes = image.getPlanes();
                final ByteBuffer buffer = planes[0].getBuffer();
                int pixelStride = planes[0].getPixelStride();
                int rowStride = planes[0].getRowStride();
                int rowPadding = rowStride - pixelStride * width;
                Bitmap bitmap = Bitmap.createBitmap(width + rowPadding / pixelStride, height, Bitmap.Config.ARGB_8888);
                bitmap.copyPixelsFromBuffer(buffer);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height);
                image.close();

                if (bitmap != null) {
                    FileOutputStream out = null;
                    try {
                        File fileImage = new File(filename);
                        if (!fileImage.exists()) {
                            fileImage.createNewFile();
                        }
                        out = new FileOutputStream(fileImage);
                        if (out != null) {
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    mICaptureListener.onCapture(null,filename);
                                }
                            });
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (out != null) try {
                            out.flush();
                            out.close();
                            stopScreenCapture();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };
        thread.start();
    }

    public void setCaptureListener(ICaptureListener captureListener) {
        mICaptureListener = captureListener;
    }

    public void onPause() {
        stopScreenCapture();
    }

    public void onDestroy(){
        stopScreenCapture();
        mICaptureListener = null;
    }

    public interface ICaptureListener{
        void onCapture(Bitmap bitmap, String filepath);
    }

    public static class Builder {
        Application mApplication;

        public Builder(Application application) {
            mApplication = application;
        }

        public ScreenCapture creater(int requsetCode){
            MediaProjectionManager mMediaProjectionManager = (MediaProjectionManager) mApplication.getSystemService(Context.MEDIA_PROJECTION_SERVICE);
            WindowManager systemService = (WindowManager)mApplication.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics metrics = new DisplayMetrics();
            systemService.getDefaultDisplay().getMetrics(metrics);
            int mScreenDensity = metrics.densityDpi;
            String mAppDataFilePath =  mApplication.getApplicationContext().getExternalFilesDir(null) + File.separator;
            ScreenCapture screenCapture = new ScreenCapture(mMediaProjectionManager,
                    systemService.getDefaultDisplay().getWidth(),systemService.getDefaultDisplay().getHeight(),mScreenDensity, mAppDataFilePath,requsetCode);
            return screenCapture;
        }
    }

}
