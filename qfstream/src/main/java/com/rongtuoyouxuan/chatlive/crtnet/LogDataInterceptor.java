package com.rongtuoyouxuan.chatlive.crtnet;


import com.blankj.utilcode.util.LogUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;


public class LogDataInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("Request====> %s \n%s \n", request.url(), request.headers()));
        String reqBody = "";
        if (null != request.body()) {
            Buffer buffer = new Buffer();
            request.body().writeTo(buffer);

            reqBody = buffer.readString(StandardCharsets.UTF_8);
            stringBuilder.append(String.format("body====> %s \n", reqBody));
        }
        ResponseBody responseBody = response.peekBody(1024 * 1024);
        String body = responseBody.string();
        stringBuilder.append(String.format("Response====> %s \n", body));
        LogUtils.d(stringBuilder);
        return response;
    }


}
