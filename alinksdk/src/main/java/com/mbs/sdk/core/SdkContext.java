package com.mbs.sdk.core;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.mbs.sdk.net.HttpRequestConfig;
import com.mbs.sdk.net.listener.OnWebExceptionListener;
import java.util.Map;

/**
 * ahtor: super_link
 * date: 2019/4/9 14:43
 * remark:
 */
public class SdkContext {

    // self
    private static SdkContext sdkContext;

    // application 上下文
    private Context context;

    // 网络异常监听
//    private OnWebExceptionListener onWebExceptionListener;

    // 本地路径
    private String baseDir;

    private HttpRequestConfig httpRequestConfig;

    private SdkContext(Application context, boolean isDebug) {
        this.context = context;
        Globals.isDebug = isDebug;
        baseDir = Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    /**
     * 获取SdkContext
     * @return
     */
    public static SdkContext getSdkContext() {
        if(sdkContext == null && Globals.isDebug){
            Log.e(">>>SDK", "SdkContext未初始化，请初始化...");
        }
        return sdkContext;
    }

    /**
     * 全局类初始化
     * @param context 上下文
     * @param isDebug 是否为调试模式
     */
    public static void init(Application context, boolean isDebug){
        if(sdkContext == null){
            sdkContext = new SdkContext(context, isDebug);
        }
    }

    public HttpRequestConfig getHttpRequestConfig() {
        return httpRequestConfig;
    }

    public void setHttpRequestConfig(HttpRequestConfig httpRequestConfig) {
        this.httpRequestConfig = httpRequestConfig;
    }

    public String getBaseDir() {
        return baseDir;
    }

//    public OnWebExceptionListener getOnWebExceptionListener() {
//        return onWebExceptionListener;
//    }
    public Context getContext() {
        return context;
    }
}
