package com.vivwe.author.entity;

public class DesignerInfoEntity {
    private int id;
    private String nickname;
    private String avatar;
    private int role;//角色 1-普通用户 2-设计师
    private int isSub;//是否关注 1-是 0-否
    private int topical;//主题
    private int productNum;//制作
    private String signature;

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

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getIsSub() {
        return isSub;
    }

    public void setIsSub(int isSub) {
        this.isSub = isSub;
    }

    public int getTopical() {
        return topical;
    }

    public void setTopical(int topical) {
        this.topical = topical;
    }

    public int getProductNum() {
        return productNum;
    }

    public void setProductNum(int productNum) {
        this.productNum = productNum;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
