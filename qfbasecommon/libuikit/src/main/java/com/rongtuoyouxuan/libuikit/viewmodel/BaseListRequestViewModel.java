package com.rongtuoyouxuan.libuikit.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.rongtuoyouxuan.chatlive.biz2.model.list.BaseListModel;
import com.rongtuoyouxuan.chatlive.biz2.model.list.LoadEvent;
import com.rongtuoyouxuan.chatlive.net2.NetWorks;
import com.rongtuoyouxuan.chatlive.net2.RequestListener;
import com.rongtuoyouxuan.libuikit.UiKitConfig;

import retrofit2.Call;
import retrofit2.Retrofit;

public abstract class BaseListRequestViewModel<Model extends BaseListModel> extends BaseListFragmentViewModel<Model> {

//    protected String mBaseUrl = UrlConstanst.BASE_URL_U_API_BOBOO_COM;
    protected String mBaseUrl = UiKitConfig.getBaseUrl();

    public BaseListRequestViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void doLoadData(final int mPage, final LoadEvent event) {
        new NetWorks<Model>(null, new RequestListener<Model>() {
            @Override
            public void onSuccess(String reqId, Model result) {
                result.event = event;
                _getData().setValue(result);
            }

            @Override
            public void onFailure(String reqId, String errCode, String msg) {
                Model erron = createModel();
                erron.event = event;
                erron.errCode = Integer.valueOf(errCode);
                erron.errMsg = msg;
                _getData().setValue(erron);
                loadError();
            }
        }) {

            @Override
            public String getBaseUrl() {
                return mBaseUrl;
            }

            @Override
            public Call<Model> createCall(Retrofit retrofit) {
                return onCreateCall(retrofit, mPage);
            }

            @Override
            protected String getReqId() {
                return initReqId();
            }
        }.start();
    }



    public abstract String initReqId();

    /**
     * 创建Call
     *
     * @param retrofit
     * @return
     */
    public abstract Call<Model> onCreateCall(Retrofit retrofit, int page);

    public abstract Model createModel();

}
