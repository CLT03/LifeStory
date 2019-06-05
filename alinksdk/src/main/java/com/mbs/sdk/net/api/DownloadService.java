package com.mbs.sdk.net.api;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/***
 * 上传下载服务
 */
public interface DownloadService {

    @Streaming //大文件时要加不然会OOM
    @FormUrlEncoded
    @POST
    Call<ResponseBody> downloadFile(@Url String fileUrl, @Field("path") String path);

    @Streaming //大文件时要加不然会OOM
    @GET
    Call<ResponseBody> downloadFile(@Url String fileUrl);
}
