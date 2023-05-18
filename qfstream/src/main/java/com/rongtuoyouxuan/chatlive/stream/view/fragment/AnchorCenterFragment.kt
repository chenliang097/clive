package com.rongtuoyouxuan.chatlive.stream.view.fragment
import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.rongtuoyouxuan.chatlive.biz2.model.stream.StreamEndBean
import com.rongtuoyouxuan.chatlive.databus.DataBus
import com.rongtuoyouxuan.chatlive.stream.R
import com.rongtuoyouxuan.chatlive.stream.viewmodel.AnchorCenterViewModel
import com.rongtuoyouxuan.libuikit.SimpleFragment
import kotlinx.android.synthetic.main.qf_stream_fragment_anchor_center.*

class AnchorCenterFragment:SimpleFragment() {

    companion object{
        fun newInstance(type:Int): AnchorCenterFragment {
            val args = Bundle()
            args.putInt("type", type)
            val fragment = AnchorCenterFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun getLayoutResId(): Int {
        return R.layout.qf_stream_fragment_anchor_center
    }

    override fun initData() {
        var type = arguments?.get("type") as Int?
        var anchorCenterViewModel = activity?.let { ViewModelProvider(it).get(AnchorCenterViewModel::class.java) }
        if (type != null) {
            anchorCenterViewModel?.getStreamStatiscData(DataBus.instance().USER_ID, type)
        }
        anchorCenterViewModel?.streamStaticsLiveData?.observe(activity as LifecycleOwner){
            updateData(it)
        }
    }

    fun updateData(data:StreamEndBean){
        anchorCenterHotTxt.text = "" + data.data?.hot_degree
        anchorCenterFansIncreaseNumTxt.text = "" + data.data?.fans_count
        anchorCenterSeePerNumTxt.text = "" + data.data?.view_count
        anchorCenterSeeFansNumTxt.text = "" + data.data?.fans_count
        anchorCenterZanNumTxt.text = "" + data.data?.like_count
        anchorCenterGiftNumTxt.text = "" + data.data?.income
        anchorCenterCoinTxt.text = "" + "0"
    }

    override fun initListener() {
    }


}