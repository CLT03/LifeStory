package com.vivwe.video.entity;

import java.util.ArrayList;

public class VideoComment {
    private int vdId;//	评论id
    private int userId;
    private String avatar;
    private String nickName;
    private String gmtCreate;//创建时间
    private String content;
    private int lrCount; //	统计点赞数量
    private int lrId; //点赞id
    private int vdrCount;//	除了第一条评论回复，所剩下的评论回复数量
    private int isLiked; //是否点赞 0-否 *1-是
    private ArrayList<CommentCommentEntity> vdrList;
    private boolean isOpen;//是否展开
    private int newAddReplyNumber;//新加的回复数量 在展开的时候需要去掉 因为展开获取回来的还有他们
    private ArrayList<CommentCommentEntity> tempVdrList;//收起时用来保存展开时拿到的回复，收起后下次展开不用在请求

    public ArrayList<CommentCommentEntity> getTempVdrList() {
        return tempVdrList;
    }

    public void setTempVdrList() {
        tempVdrList=new ArrayList<>();
        tempVdrList.addAll(vdrList);
        tempVdrList.remove(0);//去掉第一个
        vdrList.removeAll(tempVdrList);
    }

    public void addReplyNumber(){
        newAddReplyNumber++;
    }

    public int getNewAddReplyNumber() {
        return newAddReplyNumber;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public int getVdrCount() {
        return vdrCount;
    }

    public void setVdrCount(int vdrCount) {
        this.vdrCount = vdrCount;
    }

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
        if(vdrList==null){
            vdrList=new ArrayList<CommentCommentEntity>();
        }
        return vdrList;
    }

    public void setVdrList(ArrayList<CommentCommentEntity> vdrList) {
        this.vdrList = vdrList;
    }
}
