package com.vivwe.personal.entity;

import java.io.Serializable;
import java.nio.file.SecureDirectoryStream;
import java.util.ArrayList;

public class TemplateEntity {
    private ArrayList<Template> records;

    public ArrayList<Template> getRecords() {
        return records;
    }

    public void setRecords(ArrayList<Template> records) {
        this.records = records;
    }

    public class Template {
        private int starId;//收藏id
        private int id;//模板id
        private String title;
        private String imageUrl;
        private String price;//价格
        private int isStarted;//是否收藏
        private int clickCount;//点击量
        private String gmtCreate;//发布时间
        private String max_material_count; //最大素材数量
        private int collectNumber; //收藏数
        private String max_duration; //最大时长 单位秒
        private int numberOfUsers; //使用人数
        private String reason; //未通过原因

        public int getStarId() {
            return starId;
        }

        public void setStarId(int starId) {
            this.starId = starId;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public int getClickCount() {
            return clickCount;
        }

        public void setClickCount(int clickCount) {
            this.clickCount = clickCount;
        }

        public String getGmtCreate() {
            return gmtCreate;
        }

        public void setGmtCreate(String gmtCreate) {
            this.gmtCreate = gmtCreate;
        }

        public String getMax_material_count() {
            return max_material_count;
        }

        public void setMax_material_count(String max_material_count) {
            this.max_material_count = max_material_count;
        }

        public int getCollectNumber() {
            return collectNumber;
        }

        public void setCollectNumber(int collectNumber) {
            this.collectNumber = collectNumber;
        }

        public String getMax_duration() {
            return max_duration;
        }

        public void setMax_duration(String max_duration) {
            this.max_duration = max_duration;
        }

        public int getNumberOfUsers() {
            return numberOfUsers;
        }

        public void setNumberOfUsers(int numberOfUsers) {
            this.numberOfUsers = numberOfUsers;
        }

        public int getIsStarted() {
            return isStarted;
        }

        public void setIsStarted(int isStarted) {
            this.isStarted = isStarted;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }
    }
}
