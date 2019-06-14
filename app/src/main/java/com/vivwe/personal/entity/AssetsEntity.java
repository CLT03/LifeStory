package com.vivwe.personal.entity;

import java.util.ArrayList;

public class AssetsEntity {
    private ArrayList<Assests> records;

    public ArrayList<Assests> getRecords() {
        return records;
    }

    public void setRecords(ArrayList<Assests> records) {
        this.records = records;
    }

    public class Assests {
        private int id;
        private int userId;
        private String url;
        private int type;//	类型 1-图片 2-gif
        private String gmtExpireTime;

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

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getGmtExpireTime() {
            return gmtExpireTime;
        }

        public void setGmtExpireTime(String gmtExpireTime) {
            this.gmtExpireTime = gmtExpireTime;
        }
    }
}
