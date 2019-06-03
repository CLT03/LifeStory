package com.vivwe.video.entity;

public class VideoDetailEntity {
    private int vid; //视频id
    private int templateId;  //模板id
    private int userId;  //用户id
    private String nickName;  //用户名
    private String avatar; //头像链接
    private int shareCount; //分享数量
    private String videoTitle; //视频标题
    private String videoUrl; //视频链接
    private int isStared; //是否收藏 0否1是
    private int scount; //收藏数量
    private int vdcount; //评论总数量
    private int sid; //收藏id
    private int isLiked;//	是否点赞 0-否 *1-是
    private int lrcount;//	赞数量
    private int hrId;//踩id
    private int hrcount;//踩数量
    private int isHated;//	是否点踩 0-否 *1-是
    private int isSub; //是否关注了作者 	是否关注 0-否 *1-是

    public int getIsSub() {
        return isSub;
    }

    public void setIsSub(int isSub) {
        this.isSub = isSub;
    }

    public int getLrcount() {
        return lrcount;
    }

    public void setLrcount(int lrcount) {
        this.lrcount = lrcount;
    }

    public int getHrId() {
        return hrId;
    }

    public void setHrId(int hrId) {
        this.hrId = hrId;
    }

    public int getHrcount() {
        return hrcount;
    }

    public void setHrcount(int hrcount) {
        this.hrcount = hrcount;
    }

    public int getIsHated() {
        return isHated;
    }

    public void setIsHated(int isHated) {
        this.isHated = isHated;
    }

    public int getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(int isLiked) {
        this.isLiked = isLiked;
    }

    public int getVid() {
        return vid;
    }

    public void setVid(int vid) {
        this.vid = vid;
    }

    public int getTemplateId() {
        return templateId;
    }

    public void setTemplateId(int templateId) {
        this.templateId = templateId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getShareCount() {
        return shareCount;
    }

    public void setShareCount(int shareCount) {
        this.shareCount = shareCount;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public int getIsStared() {
        return isStared;
    }

    public void setIsStared(int isStared) {
        this.isStared = isStared;
    }

    public int getScount() {
        return scount;
    }

    public void setScount(int scount) {
        this.scount = scount;
    }

    public int getVdcount() {
        return vdcount;
    }

    public void setVdcount(int vdcount) {
        this.vdcount = vdcount;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }
}
