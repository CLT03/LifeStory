package com.vivwe.personal.entity;

import java.util.ArrayList;

public class AttentionEntity {
    private ArrayList<Attention> records;

    public ArrayList<Attention> getRecords() {
        return records;
    }

    public void setRecords(ArrayList<Attention> records) {
        this.records = records;
    }

    public class Attention {

        private int id;
        private String nickname;
        private String avatar;
        private String signature;
        private int isSub;//1 是已关注 0 是未关注

        public int getId() {
            return id;
        }

        public int getIsSub() {
            return isSub;
        }

        public void setIsSub(int isSub) {
            this.isSub = isSub;
        }

        public void setId(int id) {
            this.id = id;
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

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }


    }
}
