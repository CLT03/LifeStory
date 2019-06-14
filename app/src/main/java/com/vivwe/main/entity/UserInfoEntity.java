package com.vivwe.main.entity;

import com.faceunity.p2a_art.constant.AvatarConstant;
import com.faceunity.p2a_art.entity.AvatarP2A;
import com.google.gson.GsonBuilder;
import com.vivwe.main.R;

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
     * 头像
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

    private int role;//角色 1普通 2设计师
    private String phoneNumber; //手机号码

    public int getRole() {
        return role;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setRole(int role) {
        this.role = role;
    }

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
//        return avatar == null ? new AvatarP2A(AvatarP2A.style_art, R.drawable.head_1_male, AvatarP2A.gender_boy,
//                "head_1/head.bundle", AvatarConstant.hairBundle("head_1", AvatarP2A.gender_boy),
//                2, 0) : new GsonBuilder().create().fromJson(avatar, AvatarP2A.class);
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
