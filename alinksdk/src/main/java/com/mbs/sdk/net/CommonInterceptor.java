package com.mbs.sdk.net;

import android.util.Log;

import com.mbs.sdk.core.SdkContext;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * ahtor: super_link
 * date: 2019/4/11 09:07
 * remark:
 */
public class CommonInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request oldRequest = chain.request();
        Log.v(">>>Request url", oldRequest.url().uri().getPath());
        HttpUrl.Builder authorizedUrlBuilder = oldRequest.url().newBuilder().scheme(oldRequest.url()
                .scheme())
                .host(oldRequest.url().host());

        // 添加公共参数
        Map<String, String> params = SdkContext.getSdkContext().getHttpRequestConfig().getCommonParams();
        if(params != null){
            Set<String> keys = params.keySet();
            for (String key : keys) {
                authorizedUrlBuilder.addQueryParameter(key, params.get(key));
            }
        }

        Request.Builder builder = oldRequest.newBuilder()
                .method(oldRequest.method(), oldRequest.body())
                .url(authorizedUrlBuilder.build());

        // 添加公共头部
        // 从sdkContext中获取用户设置>>>Authorization的全局头部信息
        Map<String, String> headers = SdkContext.getSdkContext().getHttpRequestConfig().getCommonHeaders();
        if (headers != null) {
            Set<String> keys = headers.keySet();
            for (String key : keys) {
                Log.v(">>>addHeader", key +" : "+ headers.get(key) );
                builder.addHeader(key, headers.get(key));
            }
        }
        Log.v(">>>addHeader", "Accept-Encoding"+" : "+"identity");
        builder.addHeader("Accept-Encoding", "identity");
        return chain.proceed(builder.build());
    }
}
