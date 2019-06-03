package com.vivwe.author.api;

import com.mbs.sdk.net.msg.WebMsg;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AuthorApi {

    //获取已发布模板
    @GET("api/template/listAuditedTemplate")
    Observable<WebMsg> getPublished(@Query("pageNum") int pageNum,
                                            @Query("pageSize") int pageSize);

    //获取待审核模板
    @GET("api/template/listTemplateToBeApproved")
    Observable<WebMsg> getWaitReview(@Query("pageNum") int pageNum,
                                    @Query("pageSize") int pageSize);

    //获取未通过模板
    @GET("api/template/listIsAuditedTemplate")
    Observable<WebMsg> getNoPass(@Query("pageNum") int pageNum,
                                     @Query("pageSize") int pageSize);
}
