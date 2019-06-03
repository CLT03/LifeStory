package com.vivwe.main.api;

import com.mbs.sdk.net.msg.WebMsg;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TemplateApi {
    //获取推荐模板
    @GET("api/template/listTemplate")
    Observable<WebMsg> getRecommendTemplate(@Query("pageNum") int pageNum,
                                       @Query("pageSize") int pageSize,
                                            @Query("queryId") int queryId);
}
