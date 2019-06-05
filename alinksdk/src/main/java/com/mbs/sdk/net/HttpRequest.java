package com.mbs.sdk.net;

import android.util.Log;

import com.google.gson.Gson;
import com.mbs.sdk.core.Globals;
import com.mbs.sdk.core.SdkContext;
import com.mbs.sdk.net.api.DownloadService;
import com.mbs.sdk.net.api.UploadService;
import com.mbs.sdk.net.entity.ProgressModel;
import com.mbs.sdk.net.listener.OnProgressListener;
import com.mbs.sdk.net.listener.OnResultListener;
import com.mbs.sdk.net.msg.WebMsg;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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
//    private static OkHttpClient httpClient;
    private Retrofit retrofit;
    private String webApi = "";

    public HttpRequest() {
        if (retrofit == null) {
            createRetrofix();
        }
    }

    private void createRetrofix() {
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
        if (!httpRequest.webApi.equals(SdkContext.getSdkContext().getHttpRequestConfig().getBaseUrl())) {
            httpRequest.createRetrofix();
        }

//        Log.v(">>>",httpRequest.webApi );
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

    public static RequestBody create(Map<String, String> params, Map<String, File> files) {
        MultipartBody.Builder muBuilder = new MultipartBody.Builder();

        // 设置文件到请求体中
        for (String key : files.keySet()) {
            muBuilder.addFormDataPart(key, files.get(key).getName(), RequestBody.create(MediaType.parse("multipart/form-data"), files.get(key)));
        }

        // 设置参数到请求体中
        if (params != null) {
            for (String key : params.keySet()) {
                muBuilder.addFormDataPart(key, params.get(key));
            }
        }

        return muBuilder.build();
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
     * 多文件上传
     *
     * @param url      上传路径
     * @param params   参数
     * @param files    文件组
     * @param listener 监听
     */
    public void uploadToExcute(String url, Map<String, String> params, Map<String, File> files, final OnProgressListener listener) {

        MultipartBody.Builder muBuilder = new MultipartBody.Builder();
        for (String key : files.keySet()) {
            muBuilder.addFormDataPart(key, files.get(key).getName(), RequestBody.create(MediaType.parse("multipart/form-data"), files.get(key)));
        }
        for (String key : params.keySet()) {
            muBuilder.addFormDataPart(key, params.get(key));
        }
        RequestBody requestBody = muBuilder.build();


        // 将上面的requestBody重新包装成ProgressRequestBody
        ProgressRequestBody body = new ProgressRequestBody(requestBody, listener);

        List<MultipartBody.Part> parts = new ArrayList<>();

        // 添加参数
        if (params != null) {
            for (String key : params.keySet()) {
                parts.add(MultipartBody.Part.createFormData(key, params.get(key)));
            }
        }

        for (String key : params.keySet()) {
            parts.add(MultipartBody.Part.createFormData(key, files.get(key).getName(), body));
        }

        Call<WebMsg> call = retrofit.create(UploadService.class).uploadFiles(url, parts);

        new Excute().upload(call, listener);
    }

    /**
     * 单文件上传
     *
     * @param url      上传地址
     * @param fileName 文件名称
     * @param file     文件
     * @param listener 监听
     */
    public void uploadToExcute(String url, String fileName, File file, final OnProgressListener listener) {

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/otcet-stream"), file);

        // 将上面的requestBody重新包装成ProgressRequestBody
        ProgressRequestBody body = new ProgressRequestBody(requestBody, listener);
        MultipartBody.Part parts = MultipartBody.Part.createFormData(fileName, file.getName(), body);
        Call<WebMsg> call = retrofit.create(UploadService.class).uploadFile(url, parts);

        new Excute().upload(call, listener);
    }

    /**
     * 下载文件执行
     *
     * @param url      下载Url
     * @param path     下载路径
     * @param savePath 保存路径
     * @param listener 监听
     */
    public void downloadToExcute(String url, String path, String savePath, final OnProgressListener listener) {
        DownloadService downloadService = retrofit.create(DownloadService.class);
        Call<ResponseBody> responseBodyCall = downloadService.downloadFile(url, path);
        new Excute().download(responseBodyCall, savePath, listener);
    }

    /**
     * 下载文件执行
     *
     * @param url      下载路径
     * @param savePath 保存路径
     * @param listener 监听
     */
    public void downloadToExcute(String url, String savePath, final OnProgressListener listener) {
        DownloadService downloadService = retrofit.create(DownloadService.class);
        Call<ResponseBody> responseBodyCall = downloadService.downloadFile(url);
        new Excute().download(responseBodyCall, savePath, listener);
    }

    /**
     * 网络执行
     */
    class Excute {

        /**
         * 上传文件
         *
         * @param call
         * @param listener 监听
         */
        public void upload(Call<WebMsg> call, final OnProgressListener listener) {
            call.enqueue(new Callback<WebMsg>() {
                @Override
                public void onResponse(Call<WebMsg> call, Response<WebMsg> response) {
                    listener.onFinished(response.body());
                }

                @Override
                public void onFailure(Call<WebMsg> call, Throwable e) {
                    WebMsg webMsg = WebMsg.getFailed(e);
                    listener.onFinished(webMsg);
                    SdkContext.getSdkContext().getHttpRequestConfig().onWebExceptionListener().onNetError(webMsg);
                }
            });
        }

        /**
         * 下载文件
         *
         * @param call
         * @param path     保存路径
         * @param listener 监听
         */
        public void download(Call<ResponseBody> call, String path, final OnProgressListener listener) {

            call.enqueue(new Callback<ResponseBody>() {
                 @Override

                 public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                     Observable.create(new ObservableOnSubscribe<ProgressModel>() {
                         @Override
                         public void subscribe(ObservableEmitter<ProgressModel> emitter) throws Exception {
                             long currentLength = 0L;
                             OutputStream os = null;
                             InputStream is = response.body().byteStream();
                             long totalLength =response.body().contentLength();

                             //建立一个文件
                             final File file = new File(path);
                             try{
                                 if(!file.isFile()) {
                                     file.createNewFile();
                                 }

                                 os = new FileOutputStream(file);
                                 int len ;
                                 byte [] buff = new byte[1024];
                                 while((len=is.read(buff))!=-1){
                                     os.write(buff,0,len);
                                     currentLength+=len;
                                     if(Globals.isDebug){
                                         Log.v(">>>download", String.valueOf(currentLength) + ":" + String.valueOf(totalLength));
                                     }
                                     emitter.onNext(new ProgressModel(currentLength, totalLength));
                                 }
                                 emitter.onComplete();
                             } catch (IOException e){
                                 if(Globals.isDebug){
                                     Log.e(">>>download", e.toString());
                                 }
                                 emitter.onError(e);
                             }
                         }
                     })
                     .subscribeOn(Schedulers.io())
                     .observeOn(AndroidSchedulers.mainThread())
                     .subscribe(new Observer<ProgressModel>() {
                         @Override
                         public void onSubscribe(Disposable d) {
                         }

                         @Override
                         public void onNext(ProgressModel progressModel) {
                             listener.onProgress(progressModel.getCurrentLength(), progressModel.getTotalLength());
                         }

                         @Override
                         public void onError(Throwable e) {
                             if (Globals.isDebug) {
                                 Log.e(">>>download", e.toString());
                             }
                             listener.onFinished(WebMsg.getFailed(e));
                         }

                         @Override
                         public void onComplete() {
                             listener.onFinished(WebMsg.getSuccessed());
                         }
                     });
                 }

                 @Override
                 public void onFailure(Call<ResponseBody> call, Throwable e) {
//                     emitter.onError(t);
                     listener.onFinished(WebMsg.getFailed(e));
                 }
             });
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
                        public void onSubscribe(Disposable d) {
                        }

                        @Override
                        public void onNext(WebMsg webMsg) {

                            if (Globals.isDebug) {
                                Log.v(">>>request", new Gson().toJson(webMsg));
                            }

                            listener.onWebUiResult(webMsg);
                        }

                        @Override
                        public void onError(Throwable e) {

                            if (Globals.isDebug) {
                                Log.v(">>>request::error", e.toString());
                            }

                            WebMsg webMsg = WebMsg.getFailed(e);
                            listener.onWebUiResult(webMsg);
                            if (SdkContext.getSdkContext().getHttpRequestConfig().onWebExceptionListener() != null) {
                                SdkContext.getSdkContext().getHttpRequestConfig().onWebExceptionListener().onNetError(webMsg);
                            }

                        }

                        @Override
                        public void onComplete() {
                        }


                    });
        }
    }
}
