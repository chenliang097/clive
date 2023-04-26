package com.rongtuoyouxuan.chatlive.live.view.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rongtuoyouxuan.chatlive.biz2.model.stream.im.RoomTempActionInfo;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.rongtuoyouxuan.chatlive.base.utils.ViewModelUtils;
import com.rongtuoyouxuan.chatlive.base.viewmodel.IMLiveViewModel;
import com.rongtuoyouxuan.chatlive.live.view.adapter.LiveGreetAdapter;
import com.rongtuoyouxuan.chatlive.stream.R;
import com.rongtuoyouxuan.chatlive.stream.view.activity.StreamActivity;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.List;

public class LiveGreetLayout extends LinearLayout {

    private RecyclerView rvGreetList;
    private IMLiveViewModel imViewModel;
    private ImageView ivClose;
    private LiveGreetAdapter liveGreetAdapter;

    public LiveGreetLayout(Context context) {
        this(context, null);
    }

    public LiveGreetLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LiveGreetLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.qf_stream_live_layout_live_greet, this);
        if((FragmentActivity) context instanceof StreamActivity) {
            imViewModel = ViewModelUtils.get((FragmentActivity) context, IMLiveViewModel.class);
        }else{
            imViewModel = ViewModelUtils.getLive(IMLiveViewModel.class);
        }
        initView(context);
        initRecyclerView(context);
        initData(context);
    }

    private void initData(Context context) {
    }

    private void initView(Context context) {
        if (context instanceof StreamActivity) { //只有观看端显示打招呼
            LiveGreetLayout.this.setVisibility(GONE);
        }
        rvGreetList = findViewById(R.id.rv_greet_list);
        ivClose = findViewById(R.id.iv_close);
        ivClose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                LiveGreetLayout.this.setVisibility(GONE);
            }
        });
    }

    private void initRecyclerView(Context context) {
        rvGreetList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        liveGreetAdapter = new LiveGreetAdapter();
        rvGreetList.setAdapter(liveGreetAdapter);
        liveGreetAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<RoomTempActionInfo.HelloMsg.ItemsBean> data = adapter.getData();
                LiveGreetLayout.this.setVisibility(GONE);
            }
        });

        rvGreetList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //无操作隐藏
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
}
