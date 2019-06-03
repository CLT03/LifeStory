package com.vivwe.video.entity;

public class CommentCommentEntity {
    private int vdrId; //视频评论回复id
    private int videoDiscussId; //	视频评论id
    private int fromUId; //回复用户id
    private String fromNickname; //	回复用户昵称
    private String fromAvatar;//	回复用户头像地址
    private int toUId;//被回复用户id
    private String toNickname;//	被回复用户昵称
    private String toAvatar;//	被回复用户头像地址
    private String content;//回复内容
    private String gmtReplyTime;//	回复时间
    private int lrId;//	用户点赞id
    private int lrCount;//是否点赞 0-否 *1-是
    private int isLiked;//	是否点赞 0-否 *1-是

    public int getVdrId() {
        return vdrId;
    }

    public void setVdrId(int vdrId) {
        this.vdrId = vdrId;
    }

    public int getVideoDiscussId() {
        return videoDiscussId;
    }

    public void setVideoDiscussId(int videoDiscussId) {
        this.videoDiscussId = videoDiscussId;
    }

    public int getFromUId() {
        return fromUId;
    }

    public void setFromUId(int fromUId) {
        this.fromUId = fromUId;
    }

    public String getFromNickname() {
        return fromNickname;
    }

    public void setFromNickname(String fromNickname) {
        this.fromNickname = fromNickname;
    }

    public String getFromAvatar() {
        return fromAvatar;
    }

    public void setFromAvatar(String fromAvatar) {
        this.fromAvatar = fromAvatar;
    }

    public int getToUId() {
        return toUId;
    }

    public void setToUId(int toUId) {
        this.toUId = toUId;
    }

    public String getToNickname() {
        return toNickname;
    }

    public void setToNickname(String toNickname) {
        this.toNickname = toNickname;
    }

    public String getToAvatar() {
        return toAvatar;
    }

    public void setToAvatar(String toAvatar) {
        this.toAvatar = toAvatar;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getGmtReplyTime() {
        return gmtReplyTime;
    }

    public void setGmtReplyTime(String gmtReplyTime) {
        this.gmtReplyTime = gmtReplyTime;
    }

    public int getLrId() {
        return lrId;
    }

    public void setLrId(int lrId) {
        this.lrId = lrId;
    }

    public int getLrCount() {
        return lrCount;
    }

    public void setLrCount(int lrCount) {
        this.lrCount = lrCount;
    }

    public int getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(int isLiked) {
        this.isLiked = isLiked;
    }
}
