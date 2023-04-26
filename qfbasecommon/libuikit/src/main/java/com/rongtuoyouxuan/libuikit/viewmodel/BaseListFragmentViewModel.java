package com.rongtuoyouxuan.libuikit.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.annotation.NonNull;

import com.rongtuoyouxuan.chatlive.arch.LiveEvent;
import com.rongtuoyouxuan.chatlive.biz2.model.list.BaseListModel;
import com.rongtuoyouxuan.chatlive.biz2.model.list.LoadEvent;
import com.rongtuoyouxuan.chatlive.util.Pair;

public abstract class BaseListFragmentViewModel<Model extends BaseListModel> extends AndroidViewModel {

    public LiveEvent<Integer> updateItemEvent = new LiveEvent<>();
    public LiveEvent<Integer> deleteItemEvent = new LiveEvent<>();
    public LiveEvent<Integer> insertItemEvent = new LiveEvent<>();
    public LiveEvent<Void> addItemEvent = new LiveEvent<>();
    public LiveEvent<Integer> addItemsEvent = new LiveEvent<>();
    public LiveEvent<Pair<Integer, Integer>> insertItemsEvent = new LiveEvent<>();
    protected LiveData<Model> data = new MutableLiveData<Model>();
    private int mPage;

    public BaseListFragmentViewModel(@NonNull Application application) {
        super(application);
    }

    public void updateItem(int position) {
        updateItemEvent.setValue(position);
    }

    public void deleteItem(int position) {
        deleteItemEvent.setValue(position);
    }

    public void insertItem(int position) {
        insertItemEvent.setValue(position);
    }

    public void addItem() {
        addItemEvent.call();
    }

    public void addItems(int size) {
        addItemsEvent.setValue(size);
    }

    public void insertItems(int position, int size) {
        insertItemsEvent.setValue(new Pair<Integer, Integer>(position, size));
    }

    public void loadData(LoadEvent loadEvent) {
        switch (loadEvent) {
            case EVENT_LOAD_INIT_OR_RETRY:
                mPage = 1;
                doLoadData(mPage,loadEvent);
                break;
            case EVENT_PULL_TO_REFRESH:
                mPage = 1;
                doLoadData(mPage,loadEvent);
                break;
            case EVENT_LOAD_MORE:
                mPage++;
                doLoadData(mPage,loadEvent);
                break;
            default:
                break;
        }
    }

    protected void loadError() {
        if (mPage > 1) {
            mPage--;
        }
    }

    /**
     * 处理加载数据
     *
     * @param mPage
     * @param event
     */
    public abstract void doLoadData(int mPage,LoadEvent event);


    public LiveData<Model> getData() {
        return data;
    }

    protected MutableLiveData<Model> _getData() {
        return (MutableLiveData<Model>)data;
    }
}
