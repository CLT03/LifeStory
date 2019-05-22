package com.vivwe.faceunity.entity;

/**
 * ahtor: super_link
 * date: 2019/5/20 18:48
 * remark: 上传token
 */
public class UploadToken {
    private String linkAddress;
    private String token;

    public String getLinkAddress() {
        return linkAddress;
    }

    public void setLinkAddress(String linkAddress) {
        this.linkAddress = linkAddress;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
