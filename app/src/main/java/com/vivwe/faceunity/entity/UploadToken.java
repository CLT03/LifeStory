package com.vivwe.faceunity.entity;

/**
 * ahtor: super_link
 * date: 2019/5/20 18:48
 * remark: 上传token
 */
public class UploadToken {
    private String linkAddress;
    private String token;
    private String key;

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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
