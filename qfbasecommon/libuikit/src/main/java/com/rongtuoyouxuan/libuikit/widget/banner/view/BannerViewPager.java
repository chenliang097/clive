package com.rongtuoyouxuan.libuikit.widget.banner.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import com.rongtuoyouxuan.libuikit.layout.rtlviewpager.RtlViewPager;
import com.rongtuoyouxuan.libuikit.widget.banner.adapter.BannerAdapter;
import com.rongtuoyouxuan.libuikit.widget.banner.listener.OnItemClickListener;


/**
 * 说明：BannerViewPager
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2017/8/8 16:44
 * <p/>
 * 版本：verson 1.0
 */
public class BannerViewPager extends RtlViewPager {

    private static final float SENS = 5;
    private BannerAdapter mAdapter;
    private OnPageChangeListener mYourPageChangeListener;
    private OnItemClickListener onItemClickListener;
    private boolean canLoop = true;
    private boolean isCanScroll = true;
    private OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {

        private float mPreviousPosition = -1;

        @Override
        public void onPageSelected(int position) {
            int realPosition = mAdapter.toRealPosition(position);
            if (mPreviousPosition != realPosition) {
                mPreviousPosition = realPosition;
                if (mYourPageChangeListener != null) {
                    mYourPageChangeListener.onPageSelected(realPosition);
                }
            }
        }

        @Override
        public void onPageScrolled(int position, float positionOffset,
                                   int positionOffsetPixels) {
            int realPosition = position;
            if (mYourPageChangeListener != null) {
                if (realPosition != mAdapter.getRealCount() - 1) {
                    mYourPageChangeListener.onPageScrolled(realPosition,
                            positionOffset, positionOffsetPixels);
                } else {
                    if (positionOffset > .5) {
                        mYourPageChangeListener.onPageScrolled(0, 0, 0);
                    } else {
                        mYourPageChangeListener.onPageScrolled(realPosition,
                                0, 0);
                    }
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (mYourPageChangeListener != null) {
                mYourPageChangeListener.onPageScrollStateChanged(state);
            }
        }
    };
    private float oldX = 0.0f, newX = 0.0f;

    public BannerViewPager(Context context) {
        super(context);
        init();
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        super.addOnPageChangeListener(onPageChangeListener);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isCanScroll) {
            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                requestDisallowInterceptTouchEvent(true);
            }
            if (onItemClickListener != null) {
                switch (ev.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        oldX = ev.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        newX = ev.getX();
                        if (Math.abs(oldX - newX) < SENS) {
                            onItemClickListener.onItemClick(getRealItem());
                        }
                        oldX = 0.0f;
                        newX = 0.0f;
                        break;
                }
            }
            return super.onTouchEvent(ev);
        } else {
            return false;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isCanScroll) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isCanLoop() {
        return canLoop;
    }

    public void setCanLoop(boolean canLoop) {
        this.canLoop = canLoop;
//        if (canLoop == false){
//            setCurrentItem(getRealItem());
//        }
        if (mAdapter == null) return;
        mAdapter.setCanLoop(canLoop);
//        mAdapter.notifyDataSetChanged();
    }

    public boolean isCanScroll() {
        return isCanScroll;
    }

    public void setCanScroll(boolean scroll) {
        this.isCanScroll = scroll;
    }

    public int getFirstItem() {
        return canLoop ? mAdapter.getRealCount() : 0;
    }

    public int getLastItem() {
        return mAdapter.getRealCount() - 1;
    }

    public void setAdapter(BannerAdapter adapter, boolean canLoop) {
        mAdapter = adapter;
        mAdapter.setCanLoop(canLoop);
        mAdapter.setViewPager(this);
        setAdapter(mAdapter);
        if (canLoop) {
            setCurrentItem((adapter.getCount() / 2 + adapter.getRealCount()) - (adapter.getCount() / 2) % adapter.getRealCount());
        } else {
            setCurrentItem(getFirstItem(), false);
        }

    }

    public BannerAdapter getAdapter() {
        return mAdapter;
    }

    public int getRealItem() {
        return mAdapter != null ? mAdapter.toRealPosition(super.getCurrentItem()) : 0;
    }

    @Override
    public void addOnPageChangeListener(OnPageChangeListener listener) {
        this.mYourPageChangeListener = listener;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
}
