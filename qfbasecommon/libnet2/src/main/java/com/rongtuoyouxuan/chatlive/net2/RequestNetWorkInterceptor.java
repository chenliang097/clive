package com.rongtuoyouxuan.chatlive.net2;

import android.content.Context;

import com.blankj.utilcode.util.StringUtils;
import com.rongtuoyouxuan.chatlive.util.EnvUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by jinqinglin on 2018/5/10.
 */

public class RequestNetWorkInterceptor implements Interceptor {

    private final Map<String, String> headerParams = new ConcurrentHashMap<>();
    private String userAgent;
    private String token;

    public RequestNetWorkInterceptor(Context mContext, Map<String, String> params) {
        userAgent = EnvUtils.getAppVersion(mContext);
        addHeaderParam(params);
    }

    public void addToken(String token) {
        this.token = token;
    }

    public synchronized void addHeaderParam(Map<String, String> params) {
        this.headerParams.clear();
        if (params != null) {
            Set<String> keys = params.keySet();
            for (String key : keys) {
                if (key != null) {
                    String value = params.get(key);
                    this.headerParams.put(key, value == null ? "" : value);
                }
            }
        }
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        if (request.url().url().toString().toLowerCase(Locale.ROOT).contains(".mp4.zip")
                || request.url().url().toString().toLowerCase(Locale.ROOT).contains(".pag.zip")) {
            return chain.proceed(request);
        }

        HttpUrl.Builder urlBuilder = request.url().newBuilder();
        urlBuilder.scheme(request.url().scheme())
                .host(request.url().host());
        Request.Builder newRequest = request.newBuilder()
                .addHeader("User-Agent", userAgent)
                .addHeader("Connection", "keep-alive")
                .addHeader("atom", getHeaderData())
                .method(request.method(), request.body())
                .url(urlBuilder.build());
        if (!StringUtils.isTrimEmpty(token)) {
            newRequest.addHeader("Authorization", "Bearer " + token);
        }
        return chain.proceed(newRequest.build());
    }

    public String getHeaderData() {
        if (headerParams != null && !headerParams.isEmpty()) {
            Iterator<Map.Entry<String, String>> iterator = headerParams.entrySet().iterator();
            try {
                JSONObject jsonObject = new JSONObject();
                while (iterator.hasNext()) {
                    Map.Entry<String, String> entry = iterator.next();
                    jsonObject.put(entry.getKey(), entry.getValue());
                }
                return jsonObject.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return "";

    }
}
