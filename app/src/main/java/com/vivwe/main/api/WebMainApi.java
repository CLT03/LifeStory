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
public interface WebMainApi {

    /**
     * 用户注册
     * @param phoneNumber 手机号码
     * @param password 密码
     * @param confirmedPassword 确认密码
     * @param captcha 验证码
     * @return
     */
    @FormUrlEncoded
    @POST("api/authentication")
    public Observable<WebMsg> register(@Field("phoneNumber") String phoneNumber,
                                           @Field("password") String password,
                                           @Field("confirmedPassword") String confirmedPassword,
                                           @Field("captcha") String captcha);

    @FormUrlEncoded
    @POST("api/user/updateUserPassWord")
    public Observable<WebMsg> resetPwd(@Field("phoneNumber") String phoneNumber,
                                       @Field("password") String password,
                                       @Field("captcha") String captcha);

    /**
     * 通过手机获取验证码
     * @param phoneNumber 手机号
     * @return
     */
    @FormUrlEncoded
    @POST("api/captcha")
    public Observable<WebMsg> getCaptcha(@Field("phoneNumber") String phoneNumber);

    /**
     * 用户登录
     * @param phoneNumber 手机号
     * @param password 密码
     * @return
     */
    @GET("api/authentication")
    public Observable<WebMsg> login(@Query("phoneNumber") String phoneNumber, @Query("password") String password);
}
