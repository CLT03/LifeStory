package com.vivwe.video.api;

import com.mbs.sdk.net.msg.WebMsg;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface TemplateApi {
    //获取推荐模板
    @GET("api/template/listTemplate")
    Observable<WebMsg> getRecommendTemplate(@Query("pageNum") int pageNum,
                                            @Query("pageSize") int pageSize,
                                            @Query("queryId") int queryId);

    //获取模板详情
    @GET("api/template/getAndroidTemplate")
    Observable<WebMsg> getTemplateDetail(@Query("templateId") int templateId);

    /**
     * 收藏或取消收藏视频模板
     * @param templateId 	模板id：模板模块就传值，否则不传
     * @param type 	类型：1-视频 2-模板
     * @param userId 	用户id
     * @param videoId 	视频id：视频模块就传值，否则不传
     * @return
     */
    @FormUrlEncoded
    @POST("api/star/addStar")
    Observable<WebMsg> collectedTemplate(@Field("templateId") Integer templateId,
                                      @Field("type") int type,
                                      @Field("userId") int userId,
                                      @Field("videoId") Integer videoId);


    //根据标题查询模板
    @GET("api/template/listTemplate")
    Observable<WebMsg> searchTemplate(@Query("pageNum") int pageNum,
                                      @Query("pageSize") int pageSize,
                                      @Query("searchName") String searchName);


    //获取模板类型（或者叫合集吧）
    @GET("api/template-type/listTemplateType")
    Observable<WebMsg> getTemplateType();

   //购买模板其实就是全部模板了
   @GET("api/template/listTemplate")
   Observable<WebMsg> getTemplateByType(@Query("pageNum") int pageNum,
                                      @Query("pageSize") int pageSize,
                                      @Query("id") int id);

}
