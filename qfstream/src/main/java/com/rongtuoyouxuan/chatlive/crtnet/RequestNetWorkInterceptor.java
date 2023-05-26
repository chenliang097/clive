package com.rongtuoyouxuan.chatlive.crtnet;

import android.content.Context;
import android.net.Uri;

import com.rongtuoyouxuan.chatlive.crtlog.upload.ULog;
import com.rongtuoyouxuan.chatlive.crtutil.util.EnvUtils;
import com.rongtuoyouxuan.chatlive.crtutil.util.Md5Utils;
import com.rongtuoyouxuan.chatlive.crtutil.util.UUIDUtil;

import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * Created by jinqinglin on 2018/5/10.
 */

public class RequestNetWorkInterceptor implements Interceptor {

    private final Map<String, String> headerParams = new ConcurrentHashMap<>();
    private String userAgent;
    private String token;

    public RequestNetWorkInterceptor(Context mContext, Map<String, String> params) {
//        userAgent = EnvUtils.getAppVersion(mContext);
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

        Request.Builder newRequest = request.newBuilder();
        addHeaderData(request, newRequest);
//        newRequest.addHeader("User-Agent", userAgent);
        newRequest.addHeader("Connection", "keep-alive");
        getHeaderData(newRequest);
        newRequest.method(request.method(), request.body());
        newRequest.url(urlBuilder.build());
        return chain.proceed(newRequest.build());
    }

    private void addHeaderData(Request request, Request.Builder newRequest){
        String uuid = UUIDUtil.getUUID();
        String timestamp = String.valueOf(System.currentTimeMillis());
        TreeMap<String, String> map = new TreeMap<>();
        Uri url = Uri.parse(request.url().url().toString());
        ULog.e("clll", "requestString:" + request.url().url().toString());
        Set<String> param = url.getQueryParameterNames();
        for (String key: param) {
            String value = url.getQueryParameter(key);
            newRequest.addHeader(key, value);
            map.put(key, value);
            ULog.e("clll", "requestString:" + key + "--" + value);
        }

        RequestBody requestBody = request.body();
        if(requestBody != null) {
            Set<String> paramNames = url.getQueryParameterNames();
            for (String key : paramNames) {
                String value = url.getQueryParameter(key);
            }
            Buffer buffer = new Buffer();
            try {
                requestBody.writeTo(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //编码设为UTF-8
            Charset charset = Charset.forName("UTF-8");
            MediaType contentType = requestBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(Charset.forName("UTF-8"));
            }
            String requestString = buffer.readString(charset);
            try {
                JSONObject jsonObject = new JSONObject(requestString);
                Iterator iterator = jsonObject.keys();
                while (iterator.hasNext()) {
                    String key = (String) iterator.next();
                    String value = jsonObject.optString(key);
                    map.put(key, value);
                    ULog.e("clll", "requestString:" + key);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            map.put("nonce", uuid);
            map.put("timestamp", timestamp);
            newRequest.addHeader("sign", BaseNetImpl.getInstance().treeMap(map));
            newRequest.addHeader("scbuid", BaseNetImpl.USER_ID);
            newRequest.addHeader("nonce", uuid);
            newRequest.addHeader("timestamp", timestamp);
            newRequest.addHeader("token", Md5Utils.getMD5("appid=" + BaseNetImpl.APPID + "&user_id="+ BaseNetImpl.USER_ID + "&shijian=" + timestamp));
            newRequest.addHeader("client", EnvUtils.getAppPlat());//软件平台
            newRequest.addHeader("appid", BaseNetImpl.APPID);//device token
            newRequest.addHeader("version", "1.0.0");
        }


    }

    public void getHeaderData(Request.Builder newRequest) {
        if (headerParams != null && !headerParams.isEmpty()) {
            Iterator<Map.Entry<String, String>> iterator = headerParams.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                newRequest.addHeader(entry.getKey(), entry.getValue());
            }

        }

    }
}
