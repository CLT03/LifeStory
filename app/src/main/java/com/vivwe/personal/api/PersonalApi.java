package com.vivwe.personal.api;

import com.mbs.sdk.net.msg.WebMsg;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.DELETE;
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

    //批量取消收藏
    @FormUrlEncoded
    @POST("api/star/updateStar")
    Observable<WebMsg> deleteCollected(@Field("sIdList") ArrayList<Integer> sIdList);

    //获取素材
    @GET("api/asset")
    Observable<WebMsg> getAssetsList(@Query("type") int type,//	类型 1-图片 2-Gif 3-其他
                                   @Query("pageNum") int pageNum,
                                   @Query("pageSize") int pageSize);

    //获取草稿
    @GET("api/drafts/listDrafts")
    Observable<WebMsg> getDraftsList();

    //删除草稿
    @DELETE("api/drafts/deleteDrafts")
    Observable<WebMsg> deleteDraft(@Query("id") ArrayList<Integer> id);

    //删除素材
    @DELETE("api/asset")
    Observable<WebMsg> deleteAssets(@Query("assetIds") ArrayList<Long> assetIds);

    //删除历史记录
    @DELETE("api/play-history/deletePlayHistorys")
    Observable<WebMsg> deleteVideoHistory(@Query("playHistoryIds") ArrayList<Integer> playHistoryIds);

    //获取推荐模板
    @GET("api/template/getPushTemplate")
    Observable<WebMsg> getRecommendTemplate(@Query("pageNum") int pageNum,
                                           @Query("pageSize") int pageSize,
                                            @Query("userId") int userId);
    //获取推荐视频
    @GET("api/video/getPushVideo")
    Observable<WebMsg> getRecommendVideo(@Query("pageNum") int pageNum,
                                            @Query("pageSize") int pageSize,
                                            @Query("userId") int userId);
    //获取推荐设计师
    @GET("api/user/getPushDesigner")
    Observable<WebMsg> getRecommendDesigner(@Query("pageNum") int pageNum,
                                            @Query("pageSize") int pageSize,
                                            @Query("userId") int userId);
    //获取推荐普通用户
    @GET("api/user/getPushUser")
    Observable<WebMsg> getRecommendUser(@Query("pageNum") int pageNum,
                                            @Query("pageSize") int pageSize,
                                            @Query("userId") int userId);

    //获取广告列表
    @FormUrlEncoded
    @POST("api/ad-image/getAdImageList")
    Observable<WebMsg> getAdImageList(@Field("pageNum") int pageNum,
                                    @Field("pageSize") int pageSize,
                                    @Field("type") int type);
}
