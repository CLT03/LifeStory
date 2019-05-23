package com.vivwe.main.entity;

import com.faceunity.p2a_art.entity.AvatarP2A;
import com.google.gson.GsonBuilder;

import java.math.BigDecimal;

/**
 * ahtor: super_link
 * date: 2019/5/14 10:07
 * remark: 用户登录信息
 */
public class UserInfoEntity {
    /**
     * id
     */
    private int id;
    /**
     * 用户账号
     */
    private String nickname;
    /**
     * 地址
     */
    private String avatar;
    /**
     * 生日
     */
    private int income;
    /**
     * 教育背景
     */
    private int productNum;
    /**
     * email地址
     */
    private int fans;
    /**
     * 名字
     */
    private int subNum;
    /**
     * 姓
     */
    private int likeRecord;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public int getProductNum() {
        return productNum;
    }

    public void setProductNum(int productNum) {
        this.productNum = productNum;
    }

    public int getFans() {
        return fans;
    }

    public void setFans(int fans) {
        this.fans = fans;
    }

    public int getSubNum() {
        return subNum;
    }

    public void setSubNum(int subNum) {
        this.subNum = subNum;
    }

    public int getLikeRecord() {
        return likeRecord;
    }

    public void setLikeRecord(int likeRecord) {
        this.likeRecord = likeRecord;
    }
}
