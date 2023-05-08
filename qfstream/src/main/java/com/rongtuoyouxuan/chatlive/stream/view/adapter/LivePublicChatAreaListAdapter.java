package com.rongtuoyouxuan.chatlive.stream.view.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.rongtuoyouxuan.chatlive.biz2.model.im.BaseRoomMessage;
import com.rongtuoyouxuan.chatlive.stream.view.msgspan.MessageCallBack;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.rongtuoyouxuan.chatlive.stream.R;
import com.rongtuoyouxuan.chatlive.stream.view.msgspan.IMessageSpanMatcher;
import com.rongtuoyouxuan.chatlive.stream.view.msgspan.MessageSpanMatcher;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class LivePublicChatAreaListAdapter extends BaseMultiItemQuickAdapter<BaseRoomMessage, BaseViewHolder> {

    private final IMessageSpanMatcher mMessageSpanMatcher;

    public LivePublicChatAreaListAdapter(Context context) {
        super(new LinkedList<BaseRoomMessage>());
        setHasStableIds(true);
        mMessageSpanMatcher = new MessageSpanMatcher(context);
        addItemType(BaseRoomMessage.TYPE_UNKNOWN, R.layout.qf_stream_adapter_item_empty);
        addItemType(BaseRoomMessage.TYPE_MESSAGE, R.layout.qf_stream_adapter_item_common);
        addItemType(BaseRoomMessage.TYPE_ANNOUNCE, R.layout.qf_stream_adapter_item_common);
        addItemType(BaseRoomMessage.TYPE_ENTER_ROOM, R.layout.qf_stream_adapter_item_common);
        addItemType(BaseRoomMessage.TYPE_FOLLOW, R.layout.qf_stream_adapter_item_common);
        addItemType(BaseRoomMessage.TYPE_OUT_ROOM, R.layout.qf_stream_adapter_item_common);
    }

    @Override
    public long getItemId(int position) {
        List<BaseRoomMessage> dataList = getData();
        if (dataList != null && dataList.size() > position) {
            return dataList.get(position).hashCode();
        }
        return super.getItemId(position);
    }

    @Override
    protected void convert(BaseViewHolder helper, final BaseRoomMessage item) {
        switch (item.getItemType()) {
            case BaseRoomMessage.TYPE_OUT_ROOM:
            case BaseRoomMessage.TYPE_FOLLOW:
            case BaseRoomMessage.TYPE_ANNOUNCE:
            case BaseRoomMessage.TYPE_MESSAGE:
            case BaseRoomMessage.TYPE_ENTER_ROOM:
                TextView textView = helper.getView(R.id.textView);
                textView.setBackgroundResource(R.drawable.transparent);
                mMessageSpanMatcher.loadSpan(textView, item);
                break;
            default:
                break;
        }
    }

    @Override
    public void setNewData(@Nullable List<BaseRoomMessage> data) {
        super.setNewData(data);
    }

    //过滤重复的数据，目前过滤txt
    public void addItem(BaseRoomMessage message) {
        addData(message);
    }

    public void setItemClickCallBack(MessageCallBack callback) {
        if(mMessageSpanMatcher!=null){
            mMessageSpanMatcher.setItemClickCallBack(callback);
        }
    }
}