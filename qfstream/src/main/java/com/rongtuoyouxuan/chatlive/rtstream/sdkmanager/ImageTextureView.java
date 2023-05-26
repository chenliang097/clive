package com.rongtuoyouxuan.chatlive.rtstream.sdkmanager;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.view.TextureView;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;

import im.zego.effects.entity.ZegoEffectsVideoFrameParam;
import im.zego.effects.enums.ZegoEffectsVideoFrameFormat;

public class ImageTextureView extends TextureView implements TextureView.SurfaceTextureListener{

    private HashMap<String,Bitmap> bitmapCache = new HashMap<>();
    private Paint mPaint;

    private String mPath;
    private Uri mUri;
    private Bitmap mBitmap;
    private Rect mSrcRect;
    private Rect mDstRect;

    private static boolean isShowEffects = true;

    private int bitmapWidth = 1080;
    private int bitmapHeight = 1920;

    private boolean isRunning = false;//线程的控制开关
    private HandlerThread mThread;//用于绘制的线程
    private volatile Handler imageThreadHandler = null;
    private Canvas canvas;
    private int fps = 30;

    private Runnable imageRunnable = new Runnable() {
        @Override
        public void run() {
            long startMs = System.currentTimeMillis();
            draw();
            long endMs = System.currentTimeMillis();
            long needTime = 1000 / fps;
            long usedTime = endMs - startMs;
            if (usedTime < needTime) {
                if(imageThreadHandler != null)
                {
                    try {
                        imageThreadHandler.postDelayed(imageRunnable,needTime - usedTime);
                    }catch (Exception e){

                    }
                }
            }else {
                if(imageThreadHandler != null)
                {
                    try {
                        imageThreadHandler.postDelayed(imageRunnable,needTime);
                    }catch (Exception e){

                    }
                }
            }
        }
    };

    public ImageTextureView(Context context) {
        this(context, null);
    }

    public ImageTextureView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageTextureView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setOpaque(false);//设置背景 不为 不透明

        setSurfaceTextureListener(this);//设置监听

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);//创建画笔
        mSrcRect = new Rect();
        mDstRect = new Rect();
    }


    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        //当TextureView初始化时调用，事实上当你的程序退到后台它会被销毁，你再次打开程序的时候它会被重新初始化
//        SDKManager.zegoEffects.initEnv(1080,1920);

    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        //当TextureView的大小改变时调用
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        //当TextureView被销毁时调用
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        //当TextureView更新时调用，也就是当我们调用unlockCanvasAndPost方法时
    }

    private Bitmap getPathBitmap() {
        if(mPath == null || mUri == null) {
            return null;
        }
        Bitmap cacheBitmap = bitmapCache.get(mPath);
        if(cacheBitmap != null)
        {
            return cacheBitmap;
        }
        try {

            Bitmap temp = readBitmap2(mUri);
            Bitmap bitmap =  rotateBitmap(getOrientationDegree(mPath), temp);
            bitmapCache.clear();
            bitmapCache.put(mPath,bitmap);
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }




    private Bitmap readBitmap(Uri uri) throws IOException {
        ParcelFileDescriptor fd = getContext().getContentResolver().openFileDescriptor(uri, "r");
        if (fd != null) {
            Bitmap bitmap = BitmapFactory.decodeFileDescriptor(fd.getFileDescriptor());
            fd.close();
            return bitmap;
        }
        return null;
    }

    private Bitmap readBitmap2(Uri uri) throws IOException {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bitmap = null;
        ParcelFileDescriptor fd = getContext().getContentResolver().openFileDescriptor(uri, "r");
        if (fd != null) {
            bitmap = BitmapFactory.decodeFileDescriptor(fd.getFileDescriptor());
            fd.close();
        }
        if(bitmap == null)
        {
            return null;
        }

        int scale = calculateInSampleSize(bitmap.getWidth(),bitmap.getHeight(),getWidth(),getHeight());

        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        ParcelFileDescriptor fd2 = getContext().getContentResolver().openFileDescriptor(uri, "r");
        if (fd2 != null) {
            Bitmap bitmap2 = BitmapFactory.decodeFileDescriptor(fd2.getFileDescriptor());
            fd2.close();
            return bitmap2;
        }
        return null;
    }

    private static int calculateInSampleSize(int srcWidth, int srcHeight, int reqWidth, int reqHeight) {
        int inSampleSize = 1;
        if (srcHeight > reqHeight || srcWidth > reqWidth) {
            float scaleW = (float) srcWidth / (float) reqWidth;
            float scaleH = (float) srcHeight / (float) reqHeight;
            float sample = scaleW > scaleH ? scaleW : scaleH;// 只能是2的次幂
            if (sample < 3)
                inSampleSize = (int) sample;
            else if (sample < 6.5)
                inSampleSize = 4;
            else if (sample < 8)
                inSampleSize = 8;
            else
                inSampleSize = (int) sample;
        }
        return inSampleSize;
    }


    //从Assets读取图片
    private Bitmap readBitmapFromAssets(String path) throws IOException {
        return BitmapFactory.decodeStream(getResources().getAssets().open(path));
    }

    //将图片画到画布上，图片将被以宽度为比例画上去
    private void drawBitmap(Bitmap bitmap) {

    }

    private void draw() {
        //View被销毁，但是子线程可能还在进行操作，可能抛出一些异常
        try {
            if(mPath == null || mUri == null)
            {
                return;
            }



//            mBitmap = readBitmapFromAssets("test.jpeg");
            mBitmap = getPathBitmap();

            if(mBitmap == null)
            {
                return;
            }

            synchronized(this) {
                if (mBitmap.getHeight() > 0 && mBitmap.getWidth() > 0 && (bitmapHeight != mBitmap.getHeight() || bitmapWidth != mBitmap.getWidth()) && isRunning) {
                    bitmapHeight = mBitmap.getHeight();
                    bitmapWidth = mBitmap.getWidth();

                    SDKManager.sharedInstance().uninitEnv();

                    SDKManager.sharedInstance().initEnv(bitmapWidth, bitmapHeight);

                }
            }

            int bytes = mBitmap.getByteCount();
            ByteBuffer buf = ByteBuffer.allocate(bytes);
            mBitmap.copyPixelsToBuffer(buf);
            byte[] byteArray = buf.array();

            ZegoEffectsVideoFrameParam zegoEffectsVideoFrameParam = new ZegoEffectsVideoFrameParam();
            zegoEffectsVideoFrameParam.setHeight(mBitmap.getHeight());
            zegoEffectsVideoFrameParam.setWidth(mBitmap.getWidth());
            zegoEffectsVideoFrameParam.setFormat(ZegoEffectsVideoFrameFormat.RGBA32);

            if(isShowEffects) {
                SDKManager.sharedInstance().processImageBufferRGB(byteArray, zegoEffectsVideoFrameParam);
            }

            Bitmap stitchBmp = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_8888);

            stitchBmp.copyPixelsFromBuffer(ByteBuffer.wrap(byteArray));


            canvas = lockCanvas(new Rect(0, 0, getWidth(), getHeight()));
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

            mSrcRect.set(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
            int dstHeight = mBitmap.getHeight() * getWidth() / mBitmap.getWidth();
            mDstRect.set(0, (getHeight()-dstHeight)/2, getWidth(),
                    dstHeight+ (getHeight()-dstHeight)/2);
            if(isRunning) {
                canvas.drawBitmap(stitchBmp, mSrcRect, mDstRect, mPaint);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (canvas != null) {
                try {

                    unlockCanvasAndPost(canvas);//解锁画布同时提交
                }catch (Exception e){

                }
            }
        }
    }

    private static void recycleBitmap(Bitmap bitmap) {
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
    }

    public void setPath(String mPath) {
        this.mPath = mPath;
    }

    public void setUri(Uri uri) {
        this.mUri = uri;
    }

    /**
     * 获取图片旋转方向
     */
    private int getOrientationDegree(String file) {

        try {
            int degree2 = 0;
            return degree2 = getOrientation(mUri);
        } catch (Exception e) {

                int degree = 0;
                try {
                    ExifInterface exifInterface = new ExifInterface(file);
                    int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                    switch (orientation) {
                        case ExifInterface.ORIENTATION_ROTATE_90: {
                            degree = 90;
                        }
                        break;
                        case ExifInterface.ORIENTATION_ROTATE_180: {
                            degree = 180;
                        }
                        break;
                        case ExifInterface.ORIENTATION_ROTATE_270: {
                            degree = 270;
                        }
                        break;
                    }
                    return degree;
                } catch (Exception e2) {
                    return 0;
                }



        }
    }

    public int getOrientation( Uri photoUri) {
        int orientation = 0;
        Cursor cursor = getContext().getContentResolver().query(photoUri,
                new String[] { MediaStore.Images.ImageColumns.ORIENTATION }, null, null, null);
        if (cursor != null) {
            if (cursor.getCount() != 1) {
                return -1;
            }
            cursor.moveToFirst();
            orientation = cursor.getInt(0);
            cursor.close();
        }
        return orientation;
    }

    /**
     * 旋转bitmap
     */
    private Bitmap rotateBitmap( float angle,Bitmap bitmap){
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        float scale;
        if(angle == 90 || angle == 270)
        {
            scale = getScale(bitmap.getHeight(), bitmap.getWidth(), getWidth(), getHeight());
        }else {
            scale = getScale(bitmap.getWidth(), bitmap.getHeight(), getWidth(), getHeight());
        }
        matrix.postScale(scale, scale);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(),
                matrix, true);
    }

    public synchronized void startImage()
    {
        if(isRunning)
        {
            return;
        }

        isRunning = true;
        mThread = new HandlerThread("image");
        mThread.start();


        bitmapWidth = 1080;
        bitmapHeight = 1920;
        SDKManager.sharedInstance().initEnv(1080,1920);

        imageThreadHandler = new Handler(mThread.getLooper());
        imageThreadHandler.post(imageRunnable);

    }

    public synchronized void stopImage()
    {
        if(!isRunning)
        {
            return;
        }

        isRunning = false;
        mThread.quit();
        mPath = null;
        mUri = null;

        SDKManager.sharedInstance().uninitEnv();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

    }

    public static boolean isShowEffects() {
        return isShowEffects;
    }

    public static void setShowEffects(boolean showEffects) {
        isShowEffects = showEffects;
    }

    /**
     * 获得宽高的缩放比例
     */
    private float getScale(float srcWidth,float srcHeight,float width,float height){
        if(srcWidth != 0 && width != 0f && srcHeight != 0 && height != 0f) {
            float scaleX = width / srcWidth;
            float scaleY = height / srcHeight;
           if(scaleX < scaleY) {
               return scaleX;
            }else{
               return scaleY;
            }

        }

        return 1f;
    }

}