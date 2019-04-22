package com.mbs.sdk.net;

import android.util.Log;
import com.google.gson.Gson;
import com.mbs.sdk.core.Globals;
import com.mbs.sdk.core.SdkContext;
import com.mbs.sdk.net.listener.OnProgressListener;
import com.mbs.sdk.net.listener.OnResultListener;
import com.mbs.sdk.net.msg.WebMsg;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * @author:zlcai
 * @createrDate:2017/7/27 14:57
 * @lastTime:2017/7/27 14:57
 * @detail: 该接口用于规定居于android，并遵守webmsg实体对象协议的接口说明。
 **/

public class HttpRequest {

    private static HttpRequest httpRequest;
    private static OkHttpClient httpClient;
    private Retrofit retrofit;
    private String webApi = "";

    public HttpRequest() {
        if (retrofit == null) {
            createRetrofix();
        }
    }

    private void createRetrofix(){
        webApi = SdkContext.getSdkContext().getHttpRequestConfig().getBaseUrl();
        retrofit = new Retrofit.Builder()
                .baseUrl(webApi)
                //增加返回值为String的支持
                .addConverterFactory(ScalarsConverterFactory.create())
                //增加返回值为Gson的支持(以实体类返回)
                .addConverterFactory(GsonConverterFactory.create())
                //增加返回值为Oservable<T>的支持
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                // 拦截器，可以在里面重新构造请求
                .client(new OkHttpClient.Builder().addInterceptor(new CommonInterceptor()).build())
                .build();
    }

    public static HttpRequest getInstance() {

        if (httpRequest == null) {
            httpRequest = new HttpRequest();
        }

        // 当url有变化或者对象为空时, 重新构造Retrofit
        if(!httpRequest.webApi.equals(SdkContext.getSdkContext().getHttpRequestConfig().getBaseUrl())){
            httpRequest.createRetrofix();
        }

        Log.v(">>>",httpRequest.webApi );
        return httpRequest;
    }

    /**
     * 创建
     *
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> T create(Class<T> cls) {
        return getInstance().retrofit.create(cls);
    }

    /**
     * 对方执行网络请求
     *
     * @param observable
     * @param listener
     */
    public void excute(Observable<WebMsg> observable, final OnResultListener listener) {
        new Excute().excute(observable, listener);
    }

    /**
     * 网络执行
     */
    class Excute {

        public void upload(Observable<WebMsg> observable, OnProgressListener onProgressListener) {


        }

        /**
         * 执行请求
         *
         * @param observable 观察者类型
         * @param listener   结果监听
         */
        public void excute(Observable<WebMsg> observable, final OnResultListener listener) {

            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<WebMsg>() {

                        @Override
                        public void onSubscribe(Disposable d) { }

                        @Override
                        public void onNext(WebMsg webMsg) {

                            if(Globals.isDebug){
                                Log.v(">>>request", new Gson().toJson(webMsg));
                            }

                            listener.onWebUiResult(webMsg);
                        }

                        @Override
                        public void onError(Throwable e) {
                            WebMsg webMsg = WebMsg.getFailed(e);
                            listener.onWebUiResult(webMsg);
                            SdkContext.getSdkContext().getHttpRequestConfig().onWebExceptionListener().onNetError(webMsg);
                        }

                        @Override
                        public void onComplete() {}


                    });
        }
    }
}
