package com.vivwe.video.api;

import com.mbs.sdk.net.msg.WebMsg;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface WebMusicApi {
    /**
     * 加载音乐分类
     * @return
     */
    @GET("api/music-type/listMusicType")
    Observable<WebMsg> listMusicType();

    /**
     * 加载音乐列表
     * @param pageNum 页码
     * @param pageSize 显示数量
     * @param typeId 类型id
     * @return
     */
    @GET("music/listMusic")
    Observable<WebMsg> listMusics(@Query("pageNum") int pageNum, @Query("pageSize") int pageSize, @Query("typeId") int typeId);
}
