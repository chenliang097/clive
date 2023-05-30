package com.rongtuoyouxuan.chatlive.crtimage.util;

/*
 *Create by {Mr秦} on 2021/9/26
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.core.util.Consumer;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;

import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.integration.webp.decoder.WebpDrawable;
import com.bumptech.glide.integration.webp.decoder.WebpDrawableTransformation;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CenterInside;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.Target;

import java.io.File;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleWithBorderTransformation;
import jp.wasabeef.glide.transformations.GrayscaleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Glide工具类
 */
public class GlideUtils {
    /*** 占位图 */
    private static int placeholderImage = 0;

    /**
     * 加载图片(默认)
     *
     * @param context       上下文
     * @param path          链接
     * @param imageView     ImageView
     * @param placeholderId 占位符
     */
    public static <T> void loadImage(Context context, T path, ImageView imageView, @DrawableRes int placeholderId) {
        RequestOptions options = new RequestOptions()
                .placeholder(placeholderId) //占位图
                .error(placeholderId);    //错误图
        Glide.with(context).load(path).apply(options).into(imageView);
    }

    public static <T> void loadImageNoAnimate(Context context, T path, ImageView imageView, @DrawableRes int placeholderId) {
        RequestOptions options = new RequestOptions()
                .placeholder(placeholderId) //占位图
                .error(placeholderId);    //错误图
        Glide.with(context).load(path).dontAnimate().placeholder(imageView.getDrawable()).error(placeholderId).into(imageView);
    }

    public static <T> void loadImage(Context context, T path, ImageView imageView) {
        loadImage(context, path, imageView, placeholderImage);
    }


    /**
     * 设置加载动画
     *
     * @param context       上下文
     * @param path          链接
     * @param anim          动画
     * @param imageView     ImageView
     * @param placeholderId 占位符
     */
    public static <T> void loadImageAnim(Context context, T path, int anim, ImageView imageView, @DrawableRes int placeholderId) {
        RequestOptions options = new RequestOptions()
                .placeholder(placeholderId) //占位图
                .error(placeholderId);            //错误图
        Glide.with(context).load(path)
                .apply(options)
                .transition(GenericTransitionOptions.with(anim))
                .into(imageView);
    }

    public static <T> void loadImageAnim(Context context, T path, int anim, ImageView imageView) {
        loadImageAnim(context, path, anim, imageView, placeholderImage);
    }


    /**
     * 指定图片大小;使用override()方法指定了一个图片的尺寸。
     * Glide现在只会将图片加载成width*height像素的尺寸，而不会管你的ImageView的大小是多少了。
     * 如果你想加载一张图片的原始尺寸的话，可以使用Target.SIZE_ORIGINAL关键字----override(Target.SIZE_ORIGINAL)
     *
     * @param context       上下文
     * @param path          链接
     * @param imageView     ImageView
     * @param width         图片宽度
     * @param height        图片高度
     * @param placeholderId 占位符
     */
    public static <T> void loadImageSize(Context context, T path, ImageView imageView, int width, int height, @DrawableRes int placeholderId) {
        RequestOptions options = new RequestOptions()
                .placeholder(placeholderId) //占位图
                .error(placeholderId)             //错误图
                .override(width, height)
                .priority(Priority.HIGH);
        Glide.with(context).load(path).apply(options).into(imageView);
    }

    public static <T> void loadImageSize(Context context, T path, ImageView imageView, int width, int height) {
        loadImageSize(context, path, imageView, width, height, placeholderImage);
    }


    /**
     * 禁用内存缓存功能
     *
     * @param context       上下文
     * @param path          链接
     * @param imageView     ImageView
     * @param placeholderId 占位符
     */
    public static <T> void loadImageKipMemoryCache(Context context, T path, ImageView imageView, @DrawableRes int placeholderId) {
        RequestOptions options = new RequestOptions()
                .placeholder(placeholderId) //占位图
                .error(placeholderId)             //错误图
                .skipMemoryCache(true);          //禁用掉Glide的内存缓存功能
        Glide.with(context).load(path).apply(options).into(imageView);
    }

    public static <T> void loadImageKipMemoryCache(Context context, T path, ImageView imageView) {
        loadImageKipMemoryCache(context, path, imageView, placeholderImage);
    }


    /**
     * 禁用磁盘缓存功能
     *
     * @param context       上下文
     * @param path          链接
     * @param imageView     ImageView
     * @param placeholderId 占位符
     */
    public static <T> void loadImageKipDiskCache(Context context, T path, ImageView imageView, @DrawableRes int placeholderId) {
        RequestOptions options = new RequestOptions()
                .placeholder(placeholderId) //占位图
                .error(placeholderId)             //错误图
                .diskCacheStrategy(DiskCacheStrategy.NONE);//禁用掉Glide的磁盘缓存功能
        Glide.with(context).load(path).apply(options).into(imageView);
    }

    public static <T> void loadImageKipDiskCache(Context context, T path, ImageView imageView) {
        loadImageKipDiskCache(context, path, imageView, placeholderImage);
    }


    /**
     * 禁用内存和磁盘缓存功能
     *
     * @param context       上下文
     * @param path          链接
     * @param imageView     ImageView
     * @param placeholderId 占位符
     */

    public static <T> void loadImageKipAllCache(Context context, T path, ImageView imageView, @DrawableRes int placeholderId) {
        RequestOptions options = new RequestOptions()
                .placeholder(placeholderId) //占位图
                .error(placeholderId)             //错误图
                .skipMemoryCache(true)          //禁用掉Glide的内存缓存功能
                .diskCacheStrategy(DiskCacheStrategy.NONE);//禁用掉Glide的磁盘缓存功能
        Glide.with(context).load(path).apply(options).into(imageView);
    }

    public static <T> void loadImageKipAllCache(Context context, T path, ImageView imageView) {
        loadImageKipAllCache(context, path, imageView, placeholderImage);
    }


    /**
     * 预先加载图片
     * 在使用图片之前，预先把图片加载到缓存，调用了预加载之后，我们以后想再去加载这张图片就会非常快了，
     * 因为Glide会直接从缓存当中去读取图片并显示出来
     *
     * @param context 上下文
     * @param path    链接
     */
    public static <T> void preloadImage(Context context, T path) {
        Glide.with(context).load(path).preload();
    }

    /**
     * 加载圆形图片
     *
     * @param context       上下文
     * @param path          链接
     * @param imageView     ImageView
     * @param placeholderId 占位符
     */
    public static <T> void loadCircleImage(Context context, T path, ImageView imageView, @DrawableRes int placeholderId) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .circleCrop()//设置圆形
                .placeholder(placeholderId) //占位图
                .error(placeholderId)             //错误图
                .priority(Priority.HIGH);
        Glide.with(context).load(path).apply(options)
                //使占位符也进行设置
                .thumbnail(Glide.with(context).load(placeholderId).apply(options))
                .into(imageView);
    }

    public static <T> void loadCircleImage(Context context, T path, ImageView imageView) {
        loadCircleImage(context, path, imageView, placeholderImage);
    }


    /**
     * 加载圆形带边框图片
     *
     * @param context       上下文
     * @param path          链接
     * @param imageView     ImageView
     * @param borderSize    边框宽度 px
     * @param borderColor   边框颜色
     * @param placeholderId 占位符
     */
    public static <T> void loadCircleWithBorderImage(Context context, T path, ImageView imageView,
                                                     int borderSize, @ColorInt int borderColor, @DrawableRes int placeholderId) {
        RequestOptions options = RequestOptions.bitmapTransform(
                        new MultiTransformation<>(
                                new CenterCrop(),
                                new CropCircleWithBorderTransformation(borderSize, borderColor)
                        ))
                .placeholder(placeholderId) //占位图
                .error(placeholderId);            //错误图
        Glide.with(context).load(path).apply(options)
                //使占位符也进行设置
                .thumbnail(Glide.with(context).load(placeholderId).apply(options))
                .into(imageView);
    }

    public static <T> void loadCircleWithBorderImage(Context context, T path, ImageView imageView,
                                                     int borderSize, @ColorInt int borderColor) {
        loadCircleWithBorderImage(context, path, imageView, borderSize, borderColor, placeholderImage);
    }


    /**
     * 加载圆角图片
     *
     * @param context       上下文
     * @param path          链接
     * @param imageView     ImageView
     * @param radius        圆角 px
     * @param placeholderId 占位符
     */
    public static <T> void loadRoundCircleImage(Context context, T path, ImageView imageView,
                                                int radius, @DrawableRes int placeholderId) {
        RequestOptions options = RequestOptions.bitmapTransform(
                        new MultiTransformation<>(
                                new CenterCrop(),
                                new RoundedCornersTransformation(radius, 0,
                                        RoundedCornersTransformation.CornerType.ALL)
                        ))
                .placeholder(placeholderId) //占位图
                .error(placeholderId);            //错误图
        Glide.with(context).load(path).apply(options)
                //使占位符也进行设置
                .thumbnail(Glide.with(context).load(placeholderId).apply(options))
                .into(imageView);
    }

    public static <T> void loadRoundCircleImage(Context context, T path, ImageView imageView,
                                                int radius) {
        loadRoundCircleImage(context, path, imageView, radius, placeholderImage);
    }


    /**
     * 加载圆角图片-指定任意部分圆角（图片上、下、左、右四个角度任意定义）
     *
     * @param context       上下文
     * @param path          链接
     * @param imageView     ImageView
     * @param radius        圆角 px
     * @param placeholderId 占位符
     * @param type          圆角位置
     */
    public static <T> void loadRoundCircleImage(Context context, T path, ImageView imageView,
                                                int radius, @DrawableRes int placeholderId, RoundedCornersTransformation.CornerType type) {
        RequestOptions options = RequestOptions.bitmapTransform(
                        new MultiTransformation<>(
                                new CenterCrop(),
                                new RoundedCornersTransformation(radius, 0, type)
                        ))
                .placeholder(placeholderId) //占位图
                .error(placeholderId);            //错误图
        Glide.with(context).load(path).apply(options)
                //使占位符也进行设置
                .thumbnail(Glide.with(context).load(placeholderId).apply(options))
                .into(imageView);
    }

    public static <T> void loadRoundCircleImage(Context context, T path, ImageView imageView,
                                                int radius, RoundedCornersTransformation.CornerType type) {
        loadRoundCircleImage(context, path, imageView, radius, placeholderImage, type);
    }

    public static <T> void loadRoundCircleImage2(Context context, T path, ImageView imageView,
                                                 int radius, @DrawableRes int placeholderId, int width, int height) {
        RequestOptions options = new RequestOptions()
                .placeholder(placeholderId)//占位图
                .override(width, height)
                .error(placeholderId);//错误图
        Glide.with(context).load(path)
                .dontTransform()
                .apply(options)
                //使占位符也进行设置
                .thumbnail(Glide.with(context).load(placeholderId).apply(options))
                .into(imageView);
    }


    /**
     * 加载模糊图片（自定义透明度）
     *
     * @param context       上下文
     * @param path          链接
     * @param imageView     ImageView
     * @param blur          模糊度，一般1-100够了，越大越模糊
     * @param placeholderId 占位符
     */

    public static <T> void loadBlurImage(Context context, T path, ImageView imageView, int blur, @DrawableRes int placeholderId) {
        RequestOptions options = RequestOptions.bitmapTransform(
                        new MultiTransformation<>(
                                new CenterCrop(),
                                new BlurTransformation(blur)
                        ))
                .placeholder(placeholderId) //占位图
                .error(placeholderId);            //错误图
        Glide.with(context).load(path).apply(options)
                //使占位符也进行设置
                .thumbnail(Glide.with(context).load(placeholderId).apply(options))
                .into(imageView);
    }

    public static <T> void loadBlurImage(Context context, T path, ImageView imageView, int blur) {
        loadBlurImage(context, path, imageView, blur, placeholderImage);
    }

    /**
     * 加载灰度(黑白)图片（自定义透明度）
     *
     * @param context       上下文
     * @param path          链接
     * @param imageView     ImageView
     * @param placeholderId 占位符
     */
    public static <T> void loadBlackImage(Context context, T path, ImageView imageView, @DrawableRes int placeholderId) {
        RequestOptions options = RequestOptions.bitmapTransform(
                        new MultiTransformation<>(
                                new CenterCrop(),
                                new GrayscaleTransformation()
                        ))
                .placeholder(placeholderId) //占位图
                .error(placeholderId);            //错误图
        Glide.with(context).load(path).apply(options)
                //使占位符也进行设置
                .thumbnail(Glide.with(context).load(placeholderId).apply(options))
                .into(imageView);
    }

    public static <T> void loadBlackImage(Context context, T path, ImageView imageView) {
        loadBlackImage(context, path, imageView, placeholderImage);
    }


    /**
     * Glide.with(this).asGif()    //强制指定加载动态图片
     * 如果加载的图片不是gif，则asGif()会报错， 当然，asGif()不写也是可以正常加载的。
     * 加入了一个asBitmap()方法，这个方法的意思就是说这里只允许加载静态图片，不需要Glide去帮我们自动进行图片格式的判断了。
     * 如果你传入的还是一张GIF图的话，Glide会展示这张GIF图的第一帧，而不会去播放它。
     *
     * @param context         上下文
     * @param path            链接
     * @param imageView       ImageView
     * @param placeholderId   占位符
     * @param requestListener 监听
     * @param <T>
     */
    public static <T> void loadImageGif(Context context, T path, ImageView imageView, @DrawableRes int placeholderId, @Nullable RequestListener<GifDrawable> requestListener) {
        RequestOptions options = new RequestOptions()
                .placeholder(placeholderId) //占位图
                .error(placeholderId);            //错误图
        Glide.with(context)
                .asGif()
//                .asBitmap()
                .load(path)
                .apply(options)
                .listener(requestListener)
                .into(imageView);

    }

    public static <T> void loadImageGif(Context context, T path, ImageView imageView, @Nullable RequestListener<GifDrawable> requestListener) {
        loadImageGif(context, path, imageView, placeholderImage, requestListener);
    }

    public static <T> void loadImageWebP(Context context, T path, ImageView imageView) {
        loadImageWebP(context, path, placeholderImage, imageView, WebpDrawable.LOOP_FOREVER, null);
    }

    public static <T> void loadImageWebP(Context context, T path, @DrawableRes int placeholderId, ImageView imageView,
                                         int loopCount, @Nullable Consumer<Boolean> callBack) {
        try {
            //webp动图
            if (context != null) {
                Transformation<Bitmap> transformation = new CenterInside();
                Glide.with(context)
                        .load(path)
                        .optionalTransform(transformation)
                        .optionalTransform(WebpDrawable.class, new WebpDrawableTransformation(transformation))
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                imageView.setImageResource(placeholderId);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                if (resource instanceof WebpDrawable) {
                                    WebpDrawable webpDrawable = (WebpDrawable) resource;
//                                try {
//                                    //已知三方库的bug，webp的动图每一帧的时间间隔于实际的有所偏差，需要反射三方库去修改
//                                    //https://github.com/zjupure/GlideWebpDecoder/issues/33
//                                    Field gifStateField = mWebpDrawable.getClass().getDeclaredField("state");
//                                    gifStateField.setAccessible(true);//开放权限
//                                    Class gifStateClass = Class.forName("com.bumptech.glide.integration.webp.decoder.WebpDrawable$WebpState");
//                                    Field gifFrameLoaderField = gifStateClass.getDeclaredField("frameLoader");
//                                    gifFrameLoaderField.setAccessible(true);
//
//                                    Class gifFrameLoaderClass = Class.forName("com.bumptech.glide.integration.webp.decoder.WebpFrameLoader");
//                                    Field gifDecoderField = gifFrameLoaderClass.getDeclaredField("webpDecoder");
//                                    gifDecoderField.setAccessible(true);
//
//                                    WebpDecoder webpDecoder = (WebpDecoder) gifDecoderField.get(gifFrameLoaderField.get(gifStateField.get(resource)));
//                                    Field durations = webpDecoder.getClass().getDeclaredField("mFrameDurations");
//                                    durations.setAccessible(true);
//                                    int[] args = (int[]) durations.get(webpDecoder);
//                                    if (args.length > 0) {
//                                        for (int i = 0; i < args.length; i++) {
//                                            if (args[i] > 30) {
//                                                //加载glide会比ios慢 这边把gif的间隔减少15s
//                                                args[i] = args[i] - 15;
//                                            }
//                                        }
//                                    }
//                                    durations.set(webpDecoder, args);
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }

                                    //需要设置为循环具体几次才会有onAnimationEnd回调
                                    webpDrawable.setLoopCount(loopCount);
                                    if (callBack != null) {
                                        webpDrawable.registerAnimationCallback(new Animatable2Compat.AnimationCallback() {
                                            @Override
                                            public void onAnimationStart(Drawable drawable) {
                                                super.onAnimationStart(drawable);
                                            }

                                            @Override
                                            public void onAnimationEnd(Drawable drawable) {
                                                super.onAnimationEnd(drawable);
                                                callBack.accept(true);
                                                webpDrawable.unregisterAnimationCallback(this);
                                            }
                                        });
                                    }

                                }
                                return false;
                            }
                        })
                        .into(imageView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 下载图片
     * 在RequestListener的onResourceReady方法里面获取下载File图片
     * new RequestListener<File>() {
     * *@Override
     * public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<File> target, boolean isFirstResource) {
     * return false;
     * }
     * <p>
     * *@Override
     * public boolean onResourceReady(File resource, Object model, Target<File> target, DataSource dataSource, boolean isFirstResource) {
     * //resource即为下载取得的图片File
     * return false;
     * }
     * }
     *
     * @param context         上下文
     * @param path            下载链接
     * @param requestListener 下载监听
     */
    public static <T> void downloadImage(Context context, T path, RequestListener<File> requestListener) {
        Glide.with(context)
                .downloadOnly()
                .load(path)
                .addListener(requestListener).preload();
    }

    public static <T> void playLocalGif(Context context, T path, ImageView imageView) {
        Glide.with(context).asGif().load(path).diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageView);
    }

    /**
     * @param url 通过URL得到 Drawable
     * @return
     */
    public static void getDrawableGlide(Context context, String url, CustomTarget<Drawable> customTarget) {
        Glide.with(context).load(url).skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE).into(customTarget);
    }

    /**
     * @param url 通过URL得到 Bitmap
     * @return
     */
    public static void getBitmapGlide(Context context, String url, CustomTarget<Bitmap> customTarget) {
        Glide.with(context).asBitmap().load(url).skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE).into(customTarget);
    }

}