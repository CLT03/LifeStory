package com.vivwe.video.api;

import com.mbs.sdk.net.msg.WebMsg;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface WebMusicApi {
    //获取模板详情
    @GET("/api/music-type/listMusicType")
    Observable<WebMsg> listMusicType();

}
