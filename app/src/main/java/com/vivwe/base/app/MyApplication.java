package com.vivwe.base.app;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.mbs.sdk.core.SdkContext;
import com.mbs.sdk.net.HttpRequestConfig;
import com.mbs.sdk.net.listener.OnWebExceptionListener;
import com.mbs.sdk.net.msg.WebMsg;
import com.shixing.sxvideoengine.SXVideo;
import com.vivwe.base.cache.UserCache;
import com.vivwe.base.entity.UserToken;
import com.vivwe.base.exception.CrashCollectHandler;
import com.vivwe.base.ui.alert.AlertDialog;
import com.vivwe.base.ui.alert.Toast;
import com.vivwe.base.ui.alert.constant.AlertDialogEnum;
import com.vivwe.main.activity.LoginActivity;

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

        //设置该CrashHandler为程序的默认处理器
        CrashCollectHandler catchExcep = new CrashCollectHandler();
//        catchExcep.init(this);

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
                return "http://112.74.164.53:8083/";
            }

            @Override
            public Map<String, String> getCommonHeaders() {

                UserToken userToken = UserCache.Companion.getUserToken();
                if(userToken != null){
                    // 当用户登录后，将token作为header方式传参
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
            case 401:
                Toast.show(this, "您的登录已经失效!", 3000);
                // 清空缓存
                UserCache.Companion.loginOut();
                Intent intent = new Intent();
                intent.setClass(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
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
