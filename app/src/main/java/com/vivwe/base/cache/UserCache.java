package com.vivwe.base.cache;

import com.faceunity.p2a_art.entity.AvatarP2A;
import com.google.gson.GsonBuilder;
import com.mbs.sdk.db.SharedPreferences;
import com.vivwe.main.entity.UserLoginInfoEntity;

/**
 * ahtor: super_link
 * date: 2019/5/14 13:38
 * remark:
 */
public class UserCache extends SharedPreferences {

    private static UserLoginInfoEntity userLoginInfo = null;

    /**
     * 获取当前登录用户信息
     * @return
     */
    public static UserLoginInfoEntity getUserLoginInfo(){

        if(userLoginInfo == null){
            userLoginInfo = new GsonBuilder().create().fromJson(getValueByString("LOGIN-INFO"), UserLoginInfoEntity.class);
        }

        return userLoginInfo;
    }

    /**
     * 设置当前登录用户信息
     * @param userLoginInfo
     */
    public static void setUserLoginInfo(UserLoginInfoEntity userLoginInfo){
        if(userLoginInfo != null){
            saveValueToSharePerference("LOGIN-INFO", new GsonBuilder().create().toJson(userLoginInfo));
        }
        UserCache.userLoginInfo = userLoginInfo;
    }

    /**
     * 清空当前登录用户信息
     */
    public static void clearUserLoginInfo(){
        if(userLoginInfo != null){
            clear();
            UserCache.userLoginInfo = null;
        }
    }

    /**
     * 获取化身数据
     * @return
     */
    public static AvatarP2A getAvatarP2A(){

        AvatarP2A avatarP2A = null;
        if(userLoginInfo != null){
            avatarP2A = new GsonBuilder().create().fromJson(userLoginInfo.getAvatar(), AvatarP2A.class);
        }

        return avatarP2A == null ? new AvatarP2A() : avatarP2A;

    }

    /**
     * 保存用户缓存数据
     * @param key
     * @param value
     */
    public static void save(String key, String value){
        if(UserCache.userLoginInfo != null){
            DataServiceCache.getInstance().save(userLoginInfo.getAccount(), key, value);
        }
    }

    /**
     * 根据key获取缓存数据
     * @param key
     * @return
     */
    public static String get(String key){
        if(UserCache.userLoginInfo != null){
            return DataServiceCache.getInstance().get(userLoginInfo.getAccount(), key);
        }
        return null;
    }
}
