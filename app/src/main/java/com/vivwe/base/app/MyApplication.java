package com.vivwe.base.app;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import com.mbs.sdk.core.SdkContext;
import com.mbs.sdk.net.HttpRequestConfig;
import com.mbs.sdk.net.listener.OnWebExceptionListener;
import com.mbs.sdk.net.msg.WebMsg;
import com.vivwe.base.cache.UserCache;
import com.vivwe.base.entity.UserToken;
import com.vivwe.base.ui.alert.Toast;

import java.util.HashMap;
import java.util.Map;

/**
 * ahtor: super_link
 * date: 2019/4/22 14:12
 * remark:
 */
public class MyApplication extends Application implements OnWebExceptionListener {

    private static MyApplication myApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;

        initSdk();
        //更新
        //再试一次
    }

    /**
     * 获取上下文
     * @return
     */
    public static Context getContext() {
        return myApplication.getApplicationContext();
    }

    // init sdk
    private void initSdk(){

        Fup2aController.init(this);

        // 初始化SDK
        SdkContext.init(this, true);

        // 初始化网络访问
        SdkContext.getSdkContext().setHttpRequestConfig(new HttpRequestConfig() {
            @Override
            public String getBaseUrl() {
                return "http://192.168.0.253:8083/";
            }

            @Override
            public Map<String, String> getCommonHeaders() {

                UserToken userToken = UserCache.Companion.getUserToken();
                if(userToken != null){
                    // 当用户登录后，将token作为header方式传参

                    Log.v(">>>Authorization", "token " + userToken.getToken());
                    Map<String,String> params = new HashMap<>();
                    params.put("Authorization", "token " + userToken.getToken());
                    return params;
                }
                return null;
            }

            @Override
            public Map<String, String> getCommonParams() {
                return null;
            }

            @Override
            public OnWebExceptionListener onWebExceptionListener() {
                return MyApplication.this;
            }
        });
    }


    @Override
    public void onNetError(WebMsg webMsg) {
        Log.v(">>>onNetError", String.valueOf(webMsg.getWebCode()));
        switch (webMsg.getWebCode()){
            case -1:
                Toast.show(this, "服务器连接失败！", 3000);
                break;
            case 500:
                Toast.show(this, "网络开了小差，请稍后重试！", 3000);
                break;

        }
    }

    @Override
    public void onServiceError(WebMsg webMsg) {
        Log.v(">>>", String.valueOf(webMsg.getWebCode()));
        switch (webMsg.getWebCode()){
//            case
        }
    }
}
