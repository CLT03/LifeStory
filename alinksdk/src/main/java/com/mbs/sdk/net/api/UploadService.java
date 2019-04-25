package com.mbs.sdk.net.api;

import com.mbs.sdk.net.ProgressRequestBody;
import com.mbs.sdk.net.msg.WebMsg;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

/**
 * ahtor: super_link
 * date: 2018/9/25 17:20
 * remark:
 */
public interface UploadService {

    @Multipart
    @POST
    Call<WebMsg> uploadFiles(@Url String url, @Part List<MultipartBody.Part> file);

    @Multipart
    @POST
    Call<WebMsg> uploadFile(@Url String url, @Part MultipartBody.Part file);
}
