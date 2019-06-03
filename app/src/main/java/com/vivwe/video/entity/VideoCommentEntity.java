package com.vivwe.video.entity;

import java.util.ArrayList;

public class VideoCommentEntity {
    private int vdId;//	评论id
    private int userId;
    private String avatar;
    private String nickName;
    private String gmtCreate;//创建时间
    private String content;
    private int lrCount; //	统计点赞数量
    private int lrId; //点赞id
    private int isLiked; //是否点赞 0-否 *1-是
    private ArrayList<CommentCommentEntity> vdrList;

    public int getVdId() {
        return vdId;
    }

    public void setVdId(int vdId) {
        this.vdId = vdId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(String gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLrCount() {
        return lrCount;
    }

    public void setLrCount(int lrCount) {
        this.lrCount = lrCount;
    }

    public int getLrId() {
        return lrId;
    }

    public void setLrId(int lrId) {
        this.lrId = lrId;
    }

    public int getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(int isLiked) {
        this.isLiked = isLiked;
    }

    public ArrayList<CommentCommentEntity> getVdrList() {
        return vdrList;
    }

    public void setVdrList(ArrayList<CommentCommentEntity> vdrList) {
        this.vdrList = vdrList;
    }
}
