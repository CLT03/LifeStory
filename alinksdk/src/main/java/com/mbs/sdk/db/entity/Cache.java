package com.mbs.sdk.db.entity;

/**
 * ahtor: super_link
 * date: 2019/4/22 14:44
 * remark: 缓存表
 */
public class Cache {
    /** id */
    private int id;
    /** 存储标签 */
    private String label;
    /** 缓存key */
    private String key;
    /** 缓存呢绒 */
    private String value;
    /** 缓存时间（单位：毫秒） */
    private long updateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }
}
