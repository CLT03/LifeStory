package com.vivwe.author.api;

import com.mbs.sdk.net.msg.WebMsg;
import com.vivwe.author.entity.ApplyEntity;
import com.vivwe.personal.entity.UserEntity;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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

    //获取未通过模板
    @GET("api/user/getOtherUserInfo")
    Observable<WebMsg> getOtherUserInfo(@Query("otherUserId") int otherUserId);

    //获取他人用户模板
    @GET("api/template/getOtherUserTemplates")
    Observable<WebMsg> getOtherTemplate(@Query("otherUserId") int otherUserId,
                                        @Query("pageNum") int pageNum,
                                        @Query("pageSize") int pageSize);

    //获取创作者中心信息
    @GET("api/user/getUserOrderInfo")
    Observable<WebMsg> getUserOriderInfo();


    @FormUrlEncoded
    @PUT("api/template/soldOutTemplates")
    Observable<WebMsg> soldOutTemplates(@Field("templateId") ArrayList<Integer> templateId);


    //申请成为创作者
    @POST("api/user/applyCreator")
    Observable<WebMsg> applyCreator(@Body ApplyEntity applyEntity);



}
