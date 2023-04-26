package com.rongtuoyouxuan.chatlive.stream.view.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.rongtuoyouxuan.chatlive.biz2.model.im.msg.BaseMsg;
import com.rongtuoyouxuan.chatlive.biz2.model.im.msg.MessageContent;
import com.rongtuoyouxuan.chatlive.biz2.model.im.msg.textmsg.TxtMsg;
import com.rongtuoyouxuan.chatlive.stream.view.msgspan.MessageCallBack;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.rongtuoyouxuan.chatlive.biz2.model.stream.im.BaseRoomMessage;
import com.rongtuoyouxuan.chatlive.stream.R;
import com.rongtuoyouxuan.chatlive.stream.view.msgspan.IMessageSpanMatcher;
import com.rongtuoyouxuan.chatlive.stream.view.msgspan.MessageSpanMatcher;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class LivePublicChatAreaListAdapter extends BaseMultiItemQuickAdapter<BaseMsg, BaseViewHolder> {

    private final IMessageSpanMatcher mMessageSpanMatcher;

    public LivePublicChatAreaListAdapter(Context context) {
        super(new LinkedList<BaseMsg>());
        setHasStableIds(true);
        mMessageSpanMatcher = new MessageSpanMatcher(context);
        addItemType(BaseRoomMessage.TYPE_UNKNOWN, R.layout.qf_stream_adapter_item_empty);
        addItemType(MessageContent.MSG_TEXT.type(), R.layout.qf_stream_adapter_item_common);
        addItemType(MessageContent.MSG_LIKE_ANCHOR.type(), R.layout.qf_stream_adapter_item_common);
        addItemType(MessageContent.MSG_LIVE_JOIN.type(), R.layout.qf_stream_adapter_item_common);
        addItemType(MessageContent.MSG_GIFT.type(), R.layout.qf_stream_adapter_item_common);
        addItemType(MessageContent.MSG_BANNED.type(), R.layout.qf_stream_adapter_item_common);
        addItemType(MessageContent.MSG_LIVE_KICK.type(), R.layout.qf_stream_adapter_item_common);
        addItemType(MessageContent.MSG_GIF.type(), R.layout.qf_stream_adapter_item_gif);
        addItemType(MessageContent.MSG_FANS_LIGHT.type(), R.layout.qf_stream_adapter_item_common);

    }

    @Override
    public long getItemId(int position) {
        List<BaseMsg> dataList = getData();
        if (dataList != null && dataList.size() > position) {
            return dataList.get(position).hashCode();
        }
        return super.getItemId(position);
    }

    @Override
    protected void convert(BaseViewHolder helper, final BaseMsg item) {
        switch (MessageContent.getByValue(item.messageType)) {
            case MSG_TEXT:
            case MSG_LIKE_ANCHOR:
            case MSG_LIVE_JOIN:
            case MSG_GIFT:
            case MSG_BANNED:
            case MSG_LIVE_KICK:
            case MSG_FANS_LIGHT:
                TextView textView = helper.getView(R.id.textView);
                textView.setBackgroundResource(R.drawable.transparent);
                mMessageSpanMatcher.loadSpan(textView, item);
                break;
            case MSG_GIF:
                TextView textView1 = helper.getView(R.id.textView);
                textView1.setBackgroundResource(R.drawable.transparent);
                ImageView img = helper.getView(R.id.gifImg);
                mMessageSpanMatcher.loadSpan(textView1, img, item);
                break;
            default:
                break;
        }
    }

    @Override
    public void setNewData(@Nullable List<BaseMsg> data) {
        super.setNewData(data);
    }

    //过滤重复的数据，目前过滤txt
    public void addItem(BaseMsg message) {
        if (breakConnectingMsg(message)) {
            return;
        }
        addData(message);
    }

    private boolean breakConnectingMsg(BaseMsg message) {
        if (getData().isEmpty()) {
            return false;
        }
        if (message.messageType == MessageContent.MSG_TEXT.type) {
            TxtMsg msg = (TxtMsg) message.body;
            if (msg.type == 1) {
                BaseMsg lastMsg = getData().get(getData().size() - 1);
                if (lastMsg.body instanceof TxtMsg) {
                    return Objects.equals(((TxtMsg) lastMsg.body).content, msg.content);
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        return false;
    }

    public void setItemClickCallBack(MessageCallBack callback) {
        if(mMessageSpanMatcher!=null){
            mMessageSpanMatcher.setItemClickCallBack(callback);
        }
    }
}