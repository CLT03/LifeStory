package com.mbs.sdk.net;

import com.mbs.sdk.net.listener.OnWebExceptionListener;

import java.util.Map;

public interface HttpRequestConfig {

    /**
     * 获取基础地址url
     * @return url
     */
    public String getBaseUrl();

    /**
     * 获取公共头部
     * @return
     */
    public Map<String, String> getCommonHeaders();

    /**
     * 获取公共参数
     * @return
     */
    public Map<String, String> getCommonParams();

    /**
     * 获取回调异常监听方法
     * @return
     */
    public OnWebExceptionListener onWebExceptionListener();
}
