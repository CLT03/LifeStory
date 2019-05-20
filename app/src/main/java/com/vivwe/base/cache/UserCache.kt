package com.vivwe.base.cache

import com.faceunity.p2a_art.entity.AvatarP2A
import com.google.gson.GsonBuilder
import com.mbs.sdk.db.SharedPreferences
import com.vivwe.base.entity.UserToken
import com.vivwe.main.entity.UserInfoEntity

/**
 * ahtor: super_link
 * date: 2019/5/14 13:38
 * remark:
 */
open class UserCache : SharedPreferences() {
    companion object {


        private var userToken: UserToken? = null
        private var userInfoEntity: UserInfoEntity? = null

        /**
         * 获取当前登录用户信息
         * @return
         */
        fun getUserToken(): UserToken? {

            if (userToken == null) {
                userToken = GsonBuilder().create().fromJson(SharedPreferences.getValueByString("LOGIN-INFO"), UserToken::class.java)
            }

            return userToken
        }

        /**
         * 设置当前登录用户信息
         * @param userToken
         */
        fun setUserToken(userToken: UserToken?) {
            if (userToken != null) {
                SharedPreferences.saveValueToSharePerference("LOGIN-INFO", GsonBuilder().create().toJson(userToken))
            }
            UserCache.userToken = userToken
        }

        /**
         * 获取用户信息
         * @return
         */
        /**
         * 保存用户信息
         * @param userInfoEntity 用户实体
         */
        var userInfo: UserInfoEntity?
            get() {

                if (userInfoEntity == null) {
                    userInfoEntity = GsonBuilder().create().fromJson(SharedPreferences.getValueByString("USER_INFO"), UserInfoEntity::class.java)
                }

                return userInfoEntity
            }
            set(userInfoEntity) {

                if (userInfoEntity != null) {
                    UserCache.userInfoEntity = userInfoEntity
                    SharedPreferences.saveValueToSharePerference("USER_INFO", GsonBuilder().create().toJson(userInfoEntity))
                }
            }

        /**
         * 保存用户缓存数据
         * @param key
         * @param value
         */
        fun save(key: String, value: String) {
            if (UserCache.userToken != null) {
                DataServiceCache.getInstance().save(userToken!!.account, key, value)
            }
        }

        /**
         * 根据key获取缓存数据
         * @param key
         * @return
         */
        operator fun get(key: String): String? {
            return if (UserCache.userToken != null) {
                DataServiceCache.getInstance().get(userToken!!.account, key)
            } else null
        }

        /**
         * 用户退出登录并清空用户所有信息
         */
        fun loginOut() {
            if (userToken != null) {
                SharedPreferences.clear()
                DataServiceCache.getInstance().remove(userToken!!.account)
                UserCache.userToken = null
                UserCache.userInfoEntity = null
            }
        }
    }
}
