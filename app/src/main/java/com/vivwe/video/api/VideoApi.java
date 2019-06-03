package com.vivwe.video.api;

import com.mbs.sdk.net.msg.WebMsg;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface VideoApi {
    @FormUrlEncoded
    @POST("api/video-type/searchVideoTypeList")
    Observable<WebMsg> getVideoTypeList();

    //搜索视频
    @FormUrlEncoded
    @POST("api/video/getVideoList")
    Observable<WebMsg> searchVideo(@Field("pageNum") int pageNum,
                                   @Field("pageSize") int pageSize,
                                   @Field("searchName") String searchName,
                                   @Field("type") int type,
                                   @Field("userId") int userId);

    //搜索用户
    @GET("api/user/getUserList")
    Observable<WebMsg> searchUser(@Query("pageNum") int pageNum,
                                  @Query("pageSize") int pageSize,
                                  @Query("searchName") String searchName,
                                  @Query("userId") int userId);

    //获取视频详情
    @FormUrlEncoded
    @POST("api/video/searchVideoDetails")
    Observable<WebMsg> getVideoDetail(@Field("userId") int userId,
                                      @Field("videoId") int videoId);


    //获取视频详情
    @FormUrlEncoded
    @POST("api/video-discuss/getVideoDiscussList")
    Observable<WebMsg> getVideoCommentList(@Field("pageNum") int pageNum,
                                           @Field("pageSize") int pageSize,
                                           @Field("userId") int userId,
                                           @Field("videoId") int videoId);

    /**
     * @param type                分类：1-视频 2-评论 3
     * @param userId              用户id
     * @param videoDiscussId      视频评论id
     * @param videoDiscussReplyId 视频评论回复id
     * @param videoId             视频id
     * @return
     */
    //新增或取消点赞（公用：视频、视频评论、视频评论回复）
    @FormUrlEncoded
    @POST("api/like-record/addLikeRecord")
    Observable<WebMsg> newLike(@Field("type") int type,
                               @Field("userId") int userId,
                               @Field("videoDiscussId") Integer videoDiscussId,
                               @Field("videoDiscussReplyId") Integer videoDiscussReplyId,
                               @Field("videoId") Integer videoId);
}
