package com.vivwe.main.api;

import com.mbs.sdk.net.msg.WebMsg;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * ahtor: super_link
 * date: 2019/5/9 16:57
 * remark:
 */
public interface WebUserInfoApi {

  /*  *//**
     * 通过手机获取验证码
     * @param phoneNumber 手机号
     * @return
     *//*
    @FormUrlEncoded
    @POST("api/captcha")
    public Observable<WebMsg> getCaptcha(@Field("phoneNumber") String phoneNumber);
*/

    /**
     * 获取用户信息
     * @param userId 用户ID
     * @return
     */
    @GET("api/user/getUserInfo")
    public Observable<WebMsg> getUserInfo(@Query("userId") Integer userId);
}
