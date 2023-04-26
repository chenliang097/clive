/**
 * @file XListViewHeader.java
 * @create Apr 18, 2012 5:22:27 PM
 * @author Maxwin
 * @description XListView's header
 */
package com.rongtuoyouxuan.libuikit.layout.refreshheader;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.rongtuoyouxuan.libuikit.R;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.internal.InternalAbstract;
import com.scwang.smartrefresh.layout.util.DensityUtil;

import static android.view.View.MeasureSpec.AT_MOST;
import static android.view.View.MeasureSpec.EXACTLY;
import static android.view.View.MeasureSpec.getSize;
import static android.view.View.MeasureSpec.makeMeasureSpec;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * WaterDropHeader
 * Created by SCWANG on 2017/5/31.
 * from https://github.com/THEONE10211024/WaterDropListView
 */
public class WaterDropHeader extends InternalAbstract implements RefreshHeader {

    // <editor-fold desc="Field">
    protected static final float MAX_PROGRESS_ANGLE = 0.8f;
    protected RefreshState mState;
    protected ImageView mImageView;
    protected ImageView mHeartImageView;
    protected WaterDropView mWaterDropView;
    Animation mAnimation = null ;
    Context context;
    private String TAG = "WaterDropHeader";
    //</editor-fold>

    //<editor-fold desc="ViewGroup">
    public WaterDropHeader(Context context) {
        this(context, null);
    }

    public WaterDropHeader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaterDropHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        final ViewGroup thisGroup = this;
        final DensityUtil density = new DensityUtil();

        mSpinnerStyle = SpinnerStyle.Scale;
        mWaterDropView = new WaterDropView(context);
        mWaterDropView.updateCompleteState(0);
        thisGroup.addView(mWaterDropView, MATCH_PARENT, MATCH_PARENT);

        mImageView = new ImageView(context);
        mImageView.setImageDrawable(getResources().getDrawable(R.drawable.refresh_loading_bg));
        thisGroup.addView(mImageView, density.dip2px(0), density.dip2px(0));


        mHeartImageView = new ImageView(context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(density.dip2px(30), density.dip2px(30));
        mHeartImageView.setLayoutParams(params);
        mHeartImageView.setImageDrawable(getResources().getDrawable(R.drawable.commonui_refreshing_image_frame_1));
        mAnimation = AnimationUtils.loadAnimation(context,R.anim.heart_scale_anim);
        thisGroup.addView(mHeartImageView);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final View imageView = mImageView;
        final View dropView = mWaterDropView;
        LayoutParams lpImage = (LayoutParams) imageView.getLayoutParams();
        imageView.measure(
                makeMeasureSpec(lpImage.width, EXACTLY),
                makeMeasureSpec(lpImage.height, EXACTLY)
        );
        dropView.measure(
                makeMeasureSpec(getSize(widthMeasureSpec), AT_MOST),
                heightMeasureSpec
        );
        int maxWidth = Math.max(imageView.getMeasuredWidth(), dropView.getMeasuredWidth());
        int maxHeight = Math.max(imageView.getMeasuredHeight(), dropView.getMeasuredHeight());
        super.setMeasuredDimension(View.resolveSize(maxWidth, widthMeasureSpec), View.resolveSize(maxHeight, heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final View thisView = this;
        final View imageView = mImageView;
        final View dropView = mWaterDropView;
        final ImageView heartImageView = mHeartImageView;
        final int measuredWidth = thisView.getMeasuredWidth();

        final int widthWaterDrop = dropView.getMeasuredWidth();
        final int heightWaterDrop = dropView.getMeasuredHeight();
        final int leftWaterDrop = measuredWidth / 2 - widthWaterDrop / 2;
        final int topWaterDrop = 0;
        dropView.layout(leftWaterDrop, topWaterDrop, leftWaterDrop + widthWaterDrop, topWaterDrop + heightWaterDrop);

        final int widthImage = imageView.getMeasuredWidth();
        final int heightImage = imageView.getMeasuredHeight();
        final int leftImage = measuredWidth / 2 - widthImage / 2;
        int topImage = widthWaterDrop / 2 - widthImage / 2;
        if (topImage + heightImage > dropView.getBottom() - (widthWaterDrop - widthImage) / 2) {
            topImage = dropView.getBottom() - (widthWaterDrop - widthImage) / 2 - heightImage;
        }
        imageView.layout(leftImage, topImage + 5, leftImage + widthImage, topImage + heightImage);

        final int widthHeartImage = heartImageView.getMeasuredWidth();
        final int heightHeartImage = heartImageView.getMeasuredHeight();
        final int leftHeartImage = measuredWidth / 2 - widthHeartImage / 2;
        int topHeartImage = widthWaterDrop / 2 - widthImage / 2;
        if (topHeartImage + heightHeartImage > dropView.getBottom() - (widthWaterDrop - widthHeartImage) / 2) {
            topHeartImage = dropView.getBottom() - (widthWaterDrop - widthHeartImage) / 2 - heightHeartImage;
        }
        heartImageView.layout(leftHeartImage, topHeartImage, leftHeartImage + widthHeartImage, topHeartImage + heightHeartImage);
        heartImageView.setVisibility(View.INVISIBLE);


    }
    //</editor-fold>

    //<editor-fold desc="Draw">
    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        final View thisView = this;
        final View dropView = mWaterDropView;
        final ImageView heartImageView = mHeartImageView;
        if (mState == RefreshState.Refreshing) {
            heartImageView.setVisibility(View.VISIBLE);

//            canvas.save();
//            canvas.translate(
//                    thisView.getWidth() / 2 - heartImageView.getWidth() / 2,
//                    mWaterDropView.getMaxCircleRadius()
//                            + dropView.getPaddingTop()
//                            - heartImageView.getHeight() / 2
//            );
//            heartImageView.draw(canvas);
//            canvas.restore();
        }
    }

    @Override
    public void invalidateDrawable(@NonNull Drawable drawable) {
        final View thisView = this;
        thisView.invalidate();
//        if (drawable == mProgressDrawable) {
//            super.invalidate();
//        } else {
//            super.invalidateDrawable(drawable);
//        }
    }


    @Override
    public void onMoving(boolean isDragging, float percent, int offset, int height, int maxDragHeight) {
        if (isDragging || (mState != RefreshState.Refreshing && mState != RefreshState.RefreshReleased)) {
            final View dropView = mWaterDropView;
            mWaterDropView.updateCompleteState(Math.max(offset, 0), height + maxDragHeight);
            dropView.postInvalidate();
        }
    }

//    @Override
//    public void onPulling(float percent, int offset, int height, int maxDragHeight) {
//        mWaterDropView.updateCompleteState((offset), height + maxDragHeight);
//        mWaterDropView.postInvalidate();
//
//        float originalDragPercent = 1f * offset / height;
//
//        float dragPercent = Math.min(1f, Math.abs(originalDragPercent));
//        float adjustedPercent = (float) Math.max(dragPercent - .4, 0) * 5 / 3;
//        float extraOS = Math.abs(offset) - height;
//        float tensionSlingshotPercent = Math.max(0, Math.min(extraOS, (float) height * 2)
//                / (float) height);
//        float tensionPercent = (float) ((tensionSlingshotPercent / 4) - Math.pow(
//                (tensionSlingshotPercent / 4), 2)) * 2f;
//        float strokeStart = adjustedPercent * .8f;
//        float rotation = (-0.25f + .4f * adjustedPercent + tensionPercent * 2) * .5f;
//        mProgress.showArrow(true);
//        mProgress.setStartEndTrim(0f, Math.min(MAX_PROGRESS_ANGLE, strokeStart));
//        mProgress.setArrowScale(Math.min(1f, adjustedPercent));
//        mProgress.setProgressRotation(rotation);
//    }
//
//    @Override
//    public void onReleasing(float percent, int offset, int height, int maxDragHeight) {
//        if (mState != RefreshState.Refreshing && mState != RefreshState.RefreshReleased) {
//            mWaterDropView.updateCompleteState(Math.max(offset, 0), height + maxDragHeight);
//            mWaterDropView.postInvalidate();
//        }
//    }

    @Override
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {
        final View dropView = mWaterDropView;
        final View imageView = mImageView;
        mState = newState;
        switch (newState) {
            case None:
                dropView.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.VISIBLE);
                break;
            case PullDownToRefresh:
                dropView.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.VISIBLE);
                break;
            case PullDownCanceled:
                break;
            case ReleaseToRefresh:
                dropView.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.VISIBLE);
                break;
            case Refreshing:
                break;
            case RefreshFinish:
                dropView.setVisibility(View.GONE);
                imageView.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onReleased(@NonNull final RefreshLayout layout, int height, int maxDragHeight) {
        final View imageView = mImageView;
        final View dropView = mWaterDropView;
        imageView.animate().setDuration(300).alpha(0).setListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                imageView.setVisibility(GONE);
                imageView.setAlpha(1);
            }
        });
        mWaterDropView.createAnimator().start();//开始回弹
        dropView.animate().setDuration(300).alpha(0).setListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                dropView.setVisibility(GONE);
                dropView.setAlpha(1);
                mHeartImageView.setAnimation(mAnimation);
                mAnimation.start();

            }
        });
    }

    @Override
    public int onFinish(@NonNull RefreshLayout layout, boolean success) {
        mHeartImageView.setVisibility(GONE);
        mHeartImageView.clearAnimation();
        return 0;
    }

    /**
     * @param colors 对应Xml中配置的 srlPrimaryColor srlAccentColor
     * @deprecated 请使用 {@link RefreshLayout#setPrimaryColorsId(int...)}
     */
    @Override
    @Deprecated
    public void setPrimaryColors(@ColorInt int... colors) {
        if (colors.length > 0) {
            mWaterDropView.setIndicatorColor(colors[0]);
        }
    }
//
//    @NonNull
//    @Override
//    public SpinnerStyle getSpinnerStyle() {
//        return SpinnerStyle.Scale;
//    }
    //</editor-fold>
}