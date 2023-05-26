package com.rongtuoyouxuan.chatlive.crtimage;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.RequestBuilder;
import com.rongtuoyouxuan.chatlive.crtimage.glide.GlideApp;
import com.rongtuoyouxuan.chatlive.crtimage.glide.GlideRequests;
import com.rongtuoyouxuan.chatlive.crtimage.glide.ImgRequestOptions;
import com.rongtuoyouxuan.chatlive.crtimage.glide.NoOpGlideRequests;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

/**
 * 图片加载 - Glide包装
 * <p>建议使用{@link #with(Activity)}、{@link #with(FragmentActivity)}、{@link #with(Fragment)}
 * 使用 {@link #with(ImageView)}会递归查询ImageView所属的Fragment或Activity并绑定生命周期。
 * <p>
 */
public final class ImgLoader {
    private ImgLoader() {
    }

    /**
     * <p>建议使用{@link #with(Activity)}、{@link #with(FragmentActivity)}、{@link #with(Fragment)}
     * 使用 {@link #with(ImageView)}会递归查询ImageView所属的Fragment或Activity并绑定生命周期。
     * 如果在Fragment里面使用此方法，Context传递进来的是Activity,不能和相应的Fragment绑定生命周期。
     *
     * @param context Context
     * @return GlideRequests
     */
    public static GlideRequests with(@NonNull Context context) {
        if (checkActivityIsDestroyed(context)) {
            return new NoOpGlideRequests(context);
        }
        return GlideApp.with(context);
    }

    /**
     * @param imageView ImageView
     * @return GlideRequests
     */
    public static GlideRequests with(@NonNull ImageView imageView) {
        if (checkActivityIsDestroyed(imageView.getContext())) {
            return new NoOpGlideRequests(imageView.getContext());
        }
        return GlideApp.with(imageView);
    }

    /**
     * @param activity Activity
     * @return GlideRequests
     */
    public static GlideRequests with(@NonNull Activity activity) {
        if (checkActivityIsDestroyed(activity)) {
            return new NoOpGlideRequests(activity);
        }
        return GlideApp.with(activity);
    }

    /**
     * @param activity FragmentActivity
     * @return GlideRequests
     */
    public static GlideRequests with(@NonNull FragmentActivity activity) {
        if (checkActivityIsDestroyed(activity)) {
            return new NoOpGlideRequests(activity);
        }
        return GlideApp.with(activity);
    }

    /**
     * @param fragment Fragment
     * @return GlideRequests
     */
    public static GlideRequests with(@NonNull Fragment fragment) {
        Context context = fragment.getContext();
        if (context != null && checkActivityIsDestroyed(context)) {
            return new NoOpGlideRequests(context);
        }
        return GlideApp.with(fragment);
    }

    /**
     * @param imageView ImageView
     * @param url       url
     */
    public static void load(@NonNull ImageView imageView, String url) {
        if (!checkActivityIsDestroyed(imageView.getContext())) {
            GlideApp.with(imageView).load(url).into(imageView);
        }
    }

    /**
     * @param activity  Activity
     * @param imageView ImageView
     * @param url       url
     */
    public static void load(@NonNull Activity activity, ImageView imageView, String url) {
        if (!checkActivityIsDestroyed(imageView.getContext())) {
            GlideApp.with(activity).load(url).into(imageView);
        }
    }

    /**
     * @param activity  activity
     * @param imageView ImageView
     * @param url       url
     */
    public static void load(@NonNull FragmentActivity activity, ImageView imageView, String url) {
        if (!checkActivityIsDestroyed(imageView.getContext())) {
            GlideApp.with(activity).load(url).into(imageView);
        }
    }

    /**
     * @param fragment  Fragment
     * @param imageView fragment
     * @param url       url
     */
    public static void load(@NonNull Fragment fragment, ImageView imageView, String url) {
        if (!checkActivityIsDestroyed(imageView.getContext())) {
            GlideApp.with(fragment).load(url).into(imageView);
        }
    }

    /**
     * @param imageView         ImageView
     * @param url               url
     * @param imgRequestOptions ImgRequestOptions
     */
    public static void load(@NonNull ImageView imageView, String url, ImgRequestOptions... imgRequestOptions) {
        if (checkActivityIsDestroyed(imageView.getContext())) {
            return;
        }
        RequestBuilder rb = GlideApp.with(imageView).load(url);
        if (imgRequestOptions != null && imgRequestOptions.length > 0) {
            for (ImgRequestOptions ro : imgRequestOptions) {
                rb = rb.apply(ro);
            }
        }
        rb.into(imageView);
    }

    /**
     * @param imageView imageView
     * @param drawbleRes drawbleRes
     */
    public static void loadLocalRes(ImageView imageView, int drawbleRes) {
        if (!checkActivityIsDestroyed(imageView.getContext())) {
            GlideApp.with(imageView).load(drawbleRes).into(imageView);
        }
    }


    private static boolean checkActivityIsDestroyed(@NonNull Context context) {
        return context instanceof Activity && ((Activity) context).isDestroyed();
    }
}
