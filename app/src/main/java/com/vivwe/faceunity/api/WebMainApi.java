package com.vivwe.faceunity.api;

import com.mbs.sdk.net.msg.WebMsg;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * ahtor: super_link
 * date: 2019/5/9 16:57
 * remark:
 */
public interface WebMainApi {

    /**
     * 七牛上传获取token
     * @return
     */
    @GET("api/qi-niu/token")
    public Observable<WebMsg> getToken();
}
