package com.rongtuoyouxuan.chatlive.stream.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.rongtuoyouxuan.chatlive.stream.view.layout.StreamPreivewLiveFragment

class StreamPreviewViewPagerAdapter: FragmentPagerAdapter {

    private var lists:MutableList<String>? = null

    constructor(fm: FragmentManager, lists:MutableList<String>?):super(fm){
        this.lists = lists
    }

//    override fun getItemCount(): Int {
//        return lists?.size!!
//    }
//
//    override fun createFragment(position: Int): Fragment {
//        return when(position){
//            0-> StreamPreivewLiveFragment()
//            1-> StreamPreviewGameFragment()
//            else->{
//                StreamPreivewLiveFragment()
//            }
//        }
//    }

    override fun getCount(): Int {
        return lists?.size!!
    }

    override fun getItem(position: Int): Fragment {
        return when(position){
            0-> StreamPreivewLiveFragment()
            else->{
                StreamPreivewLiveFragment()
            }
        }
//        return StreamPreivewLiveFragment()
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return lists?.get(position)
    }
}