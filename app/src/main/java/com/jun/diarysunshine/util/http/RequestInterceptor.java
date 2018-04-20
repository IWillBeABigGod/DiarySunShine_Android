package com.jun.diarysunshine.util.http;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by jun on 2017/11/1.
 * <p>
 * 统一进行公共参数添加的网络请求
 */

public class RequestInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();

        // 添加新的参数
        //此处添加统一的请求参数
        HttpUrl.Builder authorizedUrlBuilder = oldRequest.url()
                .newBuilder()
                .scheme(oldRequest.url().scheme())
                .host(oldRequest.url().host());

        // 新的请求
        Request newRequest = oldRequest.newBuilder()
                .method(oldRequest.method(), oldRequest.body())
                .url(authorizedUrlBuilder.build())
                .build();
//        LogUtils.w("---------------------------------http request------------------\n\n"+newRequest.toString()+"\n\n---------------------------------http request------------------");
        return chain.proceed(newRequest);
    }
}
