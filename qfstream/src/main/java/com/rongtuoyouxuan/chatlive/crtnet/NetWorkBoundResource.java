package com.rongtuoyouxuan.chatlive.crtnet;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by jinqinglin on 2018/2/8.
 */

public abstract class NetWorkBoundResource<Model extends BaseModel> {

    private Retrofit retrofit;
    private LifecycleObserver observer;
    private HashMap<String, String> params;
    private LifecycleOwner lifecycleOwner;

    private Call<Model> mCall = null;

    private List<String> baseUrls;
    private int currentBaseUrlIndex = 0;

    @SuppressWarnings("unchecked")
    public NetWorkBoundResource(final LifecycleOwner lifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner;

        observer = new LifecycleObserver() {
            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            public void onDestroy() {
                cancelListener();
                if (mCall != null)
                    mCall.cancel();
            }
        };
        if (lifecycleOwner != null) {
            lifecycleOwner.getLifecycle().addObserver(observer);
        }

    }

    /**
     * 同步请求
     */
    public void startExcute() {
        retrofit = BaseNetImpl.getInstance().getRetrofit(getBaseUrl(), takeDefaultParams(), params);
        mCall = createCall(retrofit);
        try {
            Response<Model> response = mCall.execute();
            if (lifecycleOwner != null)
                lifecycleOwner.getLifecycle().removeObserver(observer);
            endRequest(response);
        } catch (Exception e) {
            _fail(new BaseNetImpl.NetInfo(), Constants.CODE_NETWORK_ERROR, Constants.MSG_NETWORK_ERROR);
        }
    }

    /**
     * 开始异步请求
     */
    public void start() {
        _getBaseUrl();
        if (baseUrls.isEmpty()) {
            return;
        }

        _start(getCurrentBaseUrl());

    }

    public String getCurrentBaseUrl() {
        if(null == baseUrls){
            _getBaseUrl();
        }
        if (currentBaseUrlIndex < baseUrls.size()) {
            return baseUrls.get(currentBaseUrlIndex++);
        }
        return "";
    }

    private void _getBaseUrl() {
        baseUrls = getBaseUrls();
        if (baseUrls.isEmpty()) {
            String url = getBaseUrl();
            if (!TextUtils.isEmpty(url)) {
                baseUrls.add(url);
            }
        }
    }

    private void enqueueRetry(Call<Model> call, final Callback<Model> callback) {
        call.enqueue(new RetryCallback() {
            @Override
            public void onFinalResponse(Call<Model> call, Response<Model> response) {
                callback.onResponse(call, response);
            }

            @Override
            public void onFinalFailure(Call<Model> call, Throwable t) {
                callback.onFailure(call, t);
            }
        });
    }

    private void _start(String baseUrl) {
        retrofit = BaseNetImpl.getInstance().getRetrofit(baseUrl, takeDefaultParams(), params);
        mCall = createCall(retrofit);
        enqueueRetry(mCall, new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, final Response<Model> response) {
                if (lifecycleOwner != null) {
                    if (lifecycleOwner.getLifecycle().getCurrentState() == Lifecycle.State.DESTROYED) {
                        return;
                    }
                }

                endRequest(response);
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                if (lifecycleOwner != null) {
                    if (lifecycleOwner.getLifecycle().getCurrentState() == Lifecycle.State.DESTROYED) {
                        return;
                    }
                }

                if (call.isCanceled())
                    return;

                BaseNetImpl.NetInfo netInfo = new BaseNetImpl.NetInfo();
                netInfo.url = call.request().url().toString();
                netInfo.throwMsg = t.getMessage();
                _fail(netInfo, Constants.CODE_NETWORK_ERROR, Constants.MSG_NETWORK_ERROR);
            }
        });
    }

    private void endRequest(final Response<Model> response) {
        if (response == null) {
            _fail(new BaseNetImpl.NetInfo(), Constants.CODE_DATA_ERROR, Constants.MSG_DATA_ERROR);
            return;
        }

        BaseNetImpl.NetInfo netInfo = new BaseNetImpl.NetInfo();
        netInfo.url = response.raw().request().url().toString();
        if (!response.isSuccessful()) {
            _fail(netInfo, Constants.CODE_NETWORK_ERROR, Constants.MSG_NETWORK_ERROR);
            return;
        }

        final Model result = response.body();
        if (result == null) {
            _fail(netInfo, Constants.CODE_DATA_ERROR, Constants.MSG_DATA_ERROR);
            return;
        }
        _success(netInfo, result);
    }

    private void _success(final BaseNetImpl.NetInfo netInfo, final Model result) {
        BaseNetImpl.getInstance().getHandler().post(new Runnable() {
            @Override
            public void run() {
                success(netInfo, result);
            }
        });
    }

    private void _fail(final BaseNetImpl.NetInfo netInfo, final String code, final String msg) {
        BaseNetImpl.getInstance().getHandler().post(new Runnable() {
            @Override
            public void run() {
                fail(netInfo, code, msg);
            }
        });
    }

    public abstract void success(BaseNetImpl.NetInfo netInfo, Model result);

    public abstract void fail(BaseNetImpl.NetInfo netInfo, String code, String msg);

    /**
     * 创建Call
     *
     * @param retrofit
     * @return
     */
    public abstract Call<Model> createCall(Retrofit retrofit);

    /**
     * 复写方法设置baseUrl
     *
     * @return
     */
    public String getBaseUrl() {
        return "";
    }

    /**
     * 复写方法设置baseUrls
     *
     * @return
     */
    public List<String> getBaseUrls() {
        return new ArrayList<>();
    }

    /**
     * 是否携带默认参数
     *
     * @return
     */
    public boolean takeDefaultParams() {
        return true;
    }

    public void cancel() {
        if (mCall != null)
            mCall.cancel();
    }

    protected void cancelListener() {
    }

    /**
     * 动态添加默认参数
     *
     * @param key
     * @param value
     * @return
     */
    public NetWorkBoundResource addParams(String key, String value) {
        if (params == null)
            params = new HashMap<>();
        return this;
    }

    abstract class RetryCallback implements Callback<Model> {

        public RetryCallback() {

        }
        @Override
        public void onResponse(Call<Model> call, Response<Model> response) {
            if (lifecycleOwner != null) {
                if (lifecycleOwner.getLifecycle().getCurrentState() == Lifecycle.State.DESTROYED) {
                    return;
                }
            }
            if (!response.isSuccessful()) {
                String baseUrl = getCurrentBaseUrl();
                if (!TextUtils.isEmpty(baseUrl)) {
                    retry(baseUrl);
                } else {
                    onFinalResponse(call, response);
                }
            } else {
                onFinalResponse(call, response);
            }
        }

        @Override
        public void onFailure(Call<Model> call, Throwable t) {
            if (lifecycleOwner != null) {
                if (lifecycleOwner.getLifecycle().getCurrentState() == Lifecycle.State.DESTROYED) {
                    return;
                }
            }
            if (call.isCanceled())
                return;

            String baseUrl = getCurrentBaseUrl();
            if (!TextUtils.isEmpty(baseUrl)) {
                retry(baseUrl);
            } else {
                onFinalFailure(call, t);
            }
        }

        void retry(String baseUrl) {
            retrofit = BaseNetImpl.getInstance().getRetrofit(baseUrl, takeDefaultParams(), params);
            mCall = createCall(retrofit);
            mCall.enqueue(this);
        }

        abstract public void onFinalResponse(Call<Model> call, Response<Model> response);

        abstract public void onFinalFailure(Call<Model> call, Throwable t);
    }
}
