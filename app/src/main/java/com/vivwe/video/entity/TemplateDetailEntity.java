package com.vivwe.video.entity;

public class TemplateDetailEntity {
    private int id;
    private int userId;
    private String title;
    private String description;
    private String imageUrl;//视频封面
    private String path;//视频路径
    private float price;//模板价格
    private String nickname;//作者昵称
    private String avatar;//作者头像
    private String templatePath;//模板下载路径
    private int isPaid;//是否已支付0否1是
    private int isSub;//是否关注0否1是
    private int isPublishing;//是否上架0否1是
    private int isStared; //是否收藏 0-否 *1-是
    private int starId; //收藏id

    public int getIsStared() {
        return isStared;
    }

    public void setIsStared(int isStared) {
        this.isStared = isStared;
    }

    public int getStarId() {
        return starId;
    }

    public void setStarId(int starId) {
        this.starId = starId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
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

    public String getTemplatePath() {
        return templatePath;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    public int getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(int isPaid) {
        this.isPaid = isPaid;
    }

    public int getIsSub() {
        return isSub;
    }

    public void setIsSub(int isSub) {
        this.isSub = isSub;
    }

    public int getIsPublishing() {
        return isPublishing;
    }

    public void setIsPublishing(int isPublishing) {
        this.isPublishing = isPublishing;
    }
}
