package com.vivwe.personal.entity;

public class DraftEntity {
    private int id;
    private String gmtEndTime;
    private String imageUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGmtEndTime() {
        return gmtEndTime;
    }

    public void setGmtEndTime(String gmtEndTime) {
        this.gmtEndTime = gmtEndTime;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
