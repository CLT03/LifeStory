package com.vivwe.personal.entity;

import java.util.ArrayList;

public class AdvertisementEntity {

    private ArrayList<Advertisement> records;

    public ArrayList<Advertisement> getRecords() {
        return records;
    }

    public void setRecords(ArrayList<Advertisement> records) {
        this.records = records;
    }

    public class Advertisement {
        private int id;
        private String imageUrl;
        private String targetUrl;
        private int type;//类型 *1-视频页面 2-我的推送 3-创作者联盟 4-故事页面 5-模板页面

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getTargetUrl() {
            return targetUrl;
        }

        public void setTargetUrl(String targetUrl) {
            this.targetUrl = targetUrl;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
