package com.rongtuoyouxuan.chatlive.qfcommon.widget;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2019/5/16.
 */
//该viewpager  不会销毁fragment  但是还是会一次加载 当前 下一个 上一个 三个fragment!
public class CommonViewPagerTabAdapter extends FragmentStatePagerAdapter {
    private FragmentManager mManager;
    private ArrayList<String> mTitles;
    public ArrayList<Fragment> fragments;


    public CommonViewPagerTabAdapter(FragmentManager fm, ArrayList<String> titles, ArrayList<Fragment> fragments) {
        super(fm);
        mManager = fm;
        this.mTitles = titles;
        this.fragments = fragments;
    }


    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles != null ? mTitles.get(position) : null;
    }

    //控制销毁
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        mManager.beginTransaction().show(fragment).commitAllowingStateLoss(); //这个commit允许该fragment被操作
        return fragment;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        //super.destroyItem(container, position, object); 注释掉这样就不会销毁前边的fragment
        Fragment fragment = fragments.get(position);
        mManager.beginTransaction().hide(fragment).commitAllowingStateLoss();
    }
}
