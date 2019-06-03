package com.vivwe.main.entity;

public class UcenterInfoEntity {
    private long id;
    private String nickname;
    private String avatar;
    private int fans;
    private int subNum;//关注数
    private int likeRecord;//like数
    private int draftCount;//草稿
    private int fodderCount;//素材
    private int orderCount;//已购买
    private int starCount;//收藏
    private History data;

    private String gender;
    private String birthday;
    private String address;
    private String signature;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public int getDraftCount() {
        return draftCount;
    }

    public void setDraftCount(int draftCount) {
        this.draftCount = draftCount;
    }

    public int getFodderCount() {
        return fodderCount;
    }

    public void setFodderCount(int fodderCount) {
        this.fodderCount = fodderCount;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public int getStarCount() {
        return starCount;
    }

    public void setStarCount(int starCount) {
        this.starCount = starCount;
    }

    public History getData() {
        return data;
    }

    public void setData(History data) {
        this.data = data;
    }

    public class History{
        private String imageUrl;
        private String videoUrl;
        private String videoTitle;

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getVideoUrl() {
            return videoUrl;
        }

        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
        }

        public String getVideoTitle() {
            return videoTitle;
        }

        public void setVideoTitle(String videoTitle) {
            this.videoTitle = videoTitle;
        }
    }

}
