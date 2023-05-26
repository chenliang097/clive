package com.rongtuoyouxuan.chatlive.crtuikit.widget.banner.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

import com.rongtuoyouxuan.chatlive.crtuikit.widget.banner.holder.BannerHolderCreator;
import com.rongtuoyouxuan.chatlive.crtuikit.widget.banner.holder.Holder;
import com.rongtuoyouxuan.chatlive.crtuikit.widget.banner.view.BannerViewPager;
import com.rongtuoyouxuan.chatlive.stream.R;

import java.util.List;

/**
 * 说明：BannerAdapter
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2017/8/8 16:47
 * <p/>
 * 版本：verson 1.0
 */
public class BannerAdapter<T> extends PagerAdapter {

    private final int MULTIPLE_COUNT = 10_000;
    protected List<T> mDatas;
    private BannerHolderCreator mHolderCreator;
    private boolean canLoop;
    private BannerViewPager mViewPager;

    public BannerAdapter(BannerHolderCreator holderCreator, List<T> data) {
        this.mDatas = data;
        this.mHolderCreator = holderCreator;
    }

    @Override
    public int getCount() {

//        return canLoop ? getRealCount() * MULTIPLE_COUNT : getRealCount()+6;
//        return getRealCount();
        return canLoop ? getRealCount() * MULTIPLE_COUNT : getRealCount();
    }

    public int toRealPosition(int position) {
        int realCount = getRealCount();
        if (realCount == 0) {
            return 0;
        }
        return position % realCount;
    }

    public int getRealCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = getView(toRealPosition(position), null, container);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        int position = mViewPager.getCurrentItem();
        if (position == 0) {
            position = mViewPager.getFirstItem();
        } else if (position == getCount() - 1) {
            position = mViewPager.getLastItem();
        }
        try {
            mViewPager.setCurrentItem(position, false);
        } catch (Exception e) {
        }
    }

    public View getView(int position, View view, ViewGroup container) {
        Holder holder = null;
        if (view == null) {
            holder = (Holder) mHolderCreator.createHolder();
            view = holder.createView(container.getContext());
            view.setTag(R.id.pl_libgift_banner_item_tag, holder);
        } else {
            holder = (Holder) view.getTag(R.id.pl_libgift_banner_item_tag);
        }
        if (mDatas != null && !mDatas.isEmpty()) {
            holder.convert(container.getContext(), position, mDatas.get(position));
        }
        return view;
    }

    /**
     * 说明：设置是否可以轮播
     *
     * @param canLoop
     */
    public void setCanLoop(boolean canLoop) {
        this.canLoop = canLoop;
    }

    public void setViewPager(BannerViewPager viewPager) {
        this.mViewPager = viewPager;
    }
}
