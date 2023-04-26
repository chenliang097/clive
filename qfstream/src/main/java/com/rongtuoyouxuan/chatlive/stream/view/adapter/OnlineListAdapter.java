package com.rongtuoyouxuan.chatlive.stream.view.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup;

import com.rongtuoyouxuan.chatlive.biz2.model.live.StreamOnlineModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rongtuoyouxuan.chatlive.stream.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class OnlineListAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    int Max = 10;
    public List<StreamOnlineModel.OnlineInfo> data = new ArrayList<>();
    private ItemsClickList mItemsClickList;
    private ConcurrentHashMap<String, StreamOnlineModel.OnlineInfo> onLineVistor = new ConcurrentHashMap<>();//在线观众

    public static List<StreamOnlineModel.OnlineInfo> toList(ConcurrentHashMap<String, StreamOnlineModel.OnlineInfo> map) {
        ArrayList<StreamOnlineModel.OnlineInfo> list = new ArrayList<>(map.values());
        Collections.sort(list);
        return list;
    }

    public static ConcurrentHashMap<String, StreamOnlineModel.OnlineInfo> toMap(List<StreamOnlineModel.OnlineInfo> list) {
        ConcurrentHashMap<String, StreamOnlineModel.OnlineInfo> concurrentHashMap = new ConcurrentHashMap();
        for (StreamOnlineModel.OnlineInfo onlineInfo : list) {
            concurrentHashMap.put(String.valueOf(onlineInfo.getUser_id()), onlineInfo);
        }
        return concurrentHashMap;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = View.inflate(viewGroup.getContext(), R.layout.qf_stream_adapter_item_online_person, null);
        return new BaseViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder helper, @SuppressLint("RecyclerView") final int position) {
        final StreamOnlineModel.OnlineInfo onlineInfo = data.get(position);
        if (onlineInfo == null) {
            return;
        }
        RoundedImageView ivOnlinePerson = helper.getView(R.id.iv_online_person_headimg);
        int drawable = onlineInfo.getGender() == 2 ? R.drawable.icon_default_live_common : R.drawable.icon_default_live_common;
        Glide.with(helper.getConvertView()).load(onlineInfo.getAvatar()).apply(new RequestOptions()
                        .error(drawable)
                        .placeholder(drawable)
                        .dontAnimate())
                .into(ivOnlinePerson);

        helper.getView(R.id.rl_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemsClickList != null) {
                    mItemsClickList.onClick(position, onlineInfo.getUser_id());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (data != null) {
            return data.size();
        }
        return 0;
    }

    public void addData(@NonNull StreamOnlineModel.OnlineInfo data) {
        if (this.data.size() >= Max && data.getScore() <= this.data.get(Max - 1).getScore()) {
            return;
        }
        StreamOnlineModel.OnlineInfo onlineinfo = onLineVistor.get(data.getUser_id());
        if (onlineinfo != null && onlineinfo.getScore() == data.getScore()) {
            return;
        } else {
            onLineVistor.put(String.valueOf(data.getUser_id()), data);
        }
        this.data = toList(onLineVistor);
        if (onLineVistor.size() > Max) {
            this.data = this.data.subList(0, Max);
            onLineVistor = toMap(this.data);
        }
        notifyDataSetChanged();
    }

    public void removeData(@NonNull StreamOnlineModel.OnlineInfo data) {
        if (this.data.size() >= Max && data.getScore() <= this.data.get(Max - 1).getScore()) {
            return;
        }
        StreamOnlineModel.OnlineInfo remove = onLineVistor.remove(data.getUser_id());
        if (remove != null) {
            this.data = toList(onLineVistor);
            if (onLineVistor.size() > Max) {
                this.data = this.data.subList(0, Max);
                onLineVistor = toMap(this.data);
            }
            notifyDataSetChanged();
        }

    }

    /**
     * setting up a new instance to data;
     *
     * @param data
     */
    public void setNewData(@Nullable List<StreamOnlineModel.OnlineInfo> data) {
        this.data = data == null ? new ArrayList<StreamOnlineModel.OnlineInfo>() : data;
        if (this.data.size() > 0) {
            onLineVistor = toMap(data);
            if (data.size() > Max) {
                for (int i = data.size() - 1; i >= Max; i--) {
                    StreamOnlineModel.OnlineInfo remove = data.remove(i);
                    onLineVistor.remove(remove.getUser_id());
                }
            }
            this.data = toList(onLineVistor);
        }
        notifyDataSetChanged();
    }

    public void setItemsClickList(ItemsClickList itemsClickList) {
        mItemsClickList = itemsClickList;
    }

    public interface ItemsClickList {
        void onClick(int position, long uid);
    }

}
