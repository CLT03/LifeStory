package com.vivwe.video.api;

import com.mbs.sdk.net.msg.WebMsg;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface VideoApi {
    //获取视频分类
    @POST("api/video-type/searchVideoTypeList")
    Observable<WebMsg> getVideoTypeList();

    //根据视频分类获取视频
    @FormUrlEncoded
    @POST("api/video/searchVideoListByType")
    Observable<WebMsg> getVideoByType(@Field("pageNum") int pageNum,
                                      @Field("pageSize") int pageSize,
                                      @Field("typeId") int typeId);

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


    //获取视频评论列表
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

    //新的视频评论
    @FormUrlEncoded
    @POST("api/video-discuss/addVideoDiscuss")
    Observable<WebMsg> newComment(@Field("content") String content,
                                  @Field("userId") int userId,
                                  @Field("videoId") int videoId);

    //新的回复视频评论
    @FormUrlEncoded
    @POST("api/video-discuss-reply/addVideoDiscussReply")
    Observable<WebMsg> newReplyComment(@Field("content") String content,
                                       @Field("fromUserId") int fromUserId,
                                       @Field("toUserId") int toUserId,
                                       @Field("videoDiscussId") int videoDiscussId);


    /**
     * 获取更多视频评论的回复
     *
     * @param userId
     * @param vdrId          视频评论回复id：用于排除最新一条
     * @param videoDiscussId 视频评论id
     * @return
     */
    @FormUrlEncoded
    @POST("api/video-discuss-reply/searchVDRListItem")
    Observable<WebMsg> getMoreReply(@Field("userId") int userId,
                                    @Field("vdrId") int vdrId,
                                    @Field("videoDiscussId") int videoDiscussId);


    /**
     * 新的回复视频评论
     * @param videoId 视频id
     * @param videoDiscussId 视频评论id
     * @param type 	类型 1-视频举报 2-评论举报 3-用户举报
     * @param reportUserId 用户id
     * @param reason 举报原因 *1-违法反动 2-低俗色情 3-赌博诈骗 4-血腥暴力 5-侵权行为 6-虚假谣言 7-其他
     * @param description  描述
     * @param beReportUserId 被举报者用户id
     * @return
     */
    @FormUrlEncoded
    @POST("api/report/addReport")
    Observable<WebMsg> addReport(@Field("videoId") Integer videoId,
                                 @Field("videoDiscussId") Integer videoDiscussId,
                                 @Field("type") int type,
                                 @Field("reportUserId") int reportUserId,
                                 @Field("reason") int reason,
                                 @Field("description") String description,
                                 @Field("beReportUserId") Integer beReportUserId);


}
