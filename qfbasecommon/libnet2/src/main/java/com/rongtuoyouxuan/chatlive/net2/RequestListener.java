package com.rongtuoyouxuan.chatlive.net2;

/**
 * Created by jinqinglin on 2018/5/9.
 */

public interface RequestListener<Model extends BaseModel> {
    void onSuccess(String reqId, Model result);
    void onFailure(String reqId, String errCode, String msg);
}
