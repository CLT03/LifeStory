package com.vivwe.personal.api;

import com.mbs.sdk.net.msg.WebMsg;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PersonalApi {

    //获取用户信息
    @GET("api/user/getUserSettingInfo")
    Observable<WebMsg> getUserInfo();

    @FormUrlEncoded
    @POST("api/user/updateUserInfo")
    Observable<WebMsg> updateUserInfo(@Field("userId") String userId,
                                       @Field("avatar") String avatar,
                                       @Field("nickname") String nickname,
                                      @Field("gender") String gender,
                                      @Field("birthday") String birthday,
                                      @Field("address") String address,
                                       @Field("signature") String signature);

    //历史记录
    @FormUrlEncoded
    @POST("api/video/searchMyVideoList")
    Observable<WebMsg> getVideoList(@Field("pageNum") int pageNum,
                                      @Field("pageSize") int pageSize,
                                      @Field("userId") long userId);

    //获取已购买信息
    @GET("api/template-order/listPurchaseHistory")
    Observable<WebMsg> getMyPurchased(@Query("pageNum") int pageNum,
                                      @Query("pageSize") int pageSize);

    //获取交易记录
    @GET("api/template-order/listTransactionRecord")
    Observable<WebMsg> getTransactionRecord(@Query("pageNum") int pageNum,
                                      @Query("pageSize") int pageSize);

    //获取视频收藏
    @GET("api/star/listStarVideo")
    Observable<WebMsg> getVideoCollected(@Query("pageNum") int pageNum,
                                            @Query("pageSize") int pageSize);

    //获取模板收藏
    @GET("api/star/listStarTemplate")
    Observable<WebMsg> getTemplateCollected(@Query("pageNum") int pageNum,
                                            @Query("pageSize") int pageSize);

    //新增或取消关注
    @FormUrlEncoded
    @POST("api/subscription/insertSubscription")
    Observable<WebMsg> attentionOrCancel(@Field("toUserId") int toUserId);

    //获取关注列表
    @GET("api/subscription/listSubscriptionUserInfo")
    Observable<WebMsg> getAttentionList(@Query("pageNum") int pageNum,
                                            @Query("pageSize") int pageSize);

    //获取粉丝列表
    @GET("api/subscription/listFansInfo")
    Observable<WebMsg> getFansList(@Query("pageNum") int pageNum,
                                        @Query("pageSize") int pageSize);
}
