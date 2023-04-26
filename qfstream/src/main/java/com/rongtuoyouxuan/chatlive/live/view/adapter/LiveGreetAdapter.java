package com.rongtuoyouxuan.chatlive.live.view.adapter;


import com.rongtuoyouxuan.chatlive.biz2.model.stream.im.RoomTempActionInfo;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.rongtuoyouxuan.chatlive.stream.R;

/**
 * Created by Ning on 2021/4/14
 * Describe:
 */
public class LiveGreetAdapter extends BaseQuickAdapter<RoomTempActionInfo.HelloMsg.ItemsBean,BaseViewHolder> {

    public LiveGreetAdapter() {
        super(R.layout.qf_stream_live_layout_greet_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, RoomTempActionInfo.HelloMsg.ItemsBean item) {
        helper.setText(R.id.tv_greet,item.en);
    }
}
