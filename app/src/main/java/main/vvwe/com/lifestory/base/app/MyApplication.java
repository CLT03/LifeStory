package main.vvwe.com.lifestory.base.app;

import android.app.Application;
import android.content.Context;
import com.mbs.sdk.core.SdkContext;
import com.mbs.sdk.net.HttpRequestConfig;
import com.mbs.sdk.net.listener.OnWebExceptionListener;
import java.util.Map;

/**
 * ahtor: super_link
 * date: 2019/4/22 14:12
 * remark:
 */
public class MyApplication extends Application {

    private static MyApplication myApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;

        initSdk();
        //更新
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
        // 初始化SDK
        SdkContext.init(this, true);

        // 初始化网络访问
        SdkContext.getSdkContext().setHttpRequestConfig(new HttpRequestConfig() {
            @Override
            public String getBaseUrl() {
                return null;
            }

            @Override
            public Map<String, String> getCommonHeaders() {
                return null;
            }

            @Override
            public Map<String, String> getCommonParams() {
                return null;
            }

            @Override
            public OnWebExceptionListener onWebExceptionListener() {
                return null;
            }
        });
    }



}
