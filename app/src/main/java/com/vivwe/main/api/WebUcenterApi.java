package com.vivwe.main.api;

import com.mbs.sdk.net.msg.WebMsg;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WebUcenterApi {
    //获取用户信息
    @GET("api/user/getUserInfo")
    Observable<WebMsg> getUserInfo();

    //获取用户信息
    @GET("api/user/getUserRecordInfo")
    Observable<WebMsg> getUserRecordInfo();

    //获取历史记录
    @GET("api/user/listPlayHistory")
    Observable<WebMsg> getVideoHistory(@Query("pageNum") int pageNum,
                                       @Query("pageSize") int pageSize);
}
