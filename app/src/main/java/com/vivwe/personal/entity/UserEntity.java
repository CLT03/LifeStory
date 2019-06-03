package com.vivwe.personal.entity;

import java.util.ArrayList;

public class UserEntity {
    private ArrayList<User> records;

    public ArrayList<User> getRecords() {
        return records;
    }

    public void setRecords(ArrayList<User> records) {
        this.records = records;
    }

    public class User{
        private String nickName;
        private String avatar;
        private String signature;
        private int isSub;//是否关注 0-否 *1-是
        private int uid;//该用户id
        private int sid;//关注表id

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
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

        public int getIsSub() {
            return isSub;
        }

        public void setIsSub(int isSub) {
            this.isSub = isSub;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public int getSid() {
            return sid;
        }

        public void setSid(int sid) {
            this.sid = sid;
        }
    }

}
