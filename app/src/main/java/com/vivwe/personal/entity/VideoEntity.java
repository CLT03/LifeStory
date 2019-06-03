package com.vivwe.personal.entity;

import java.util.ArrayList;

public class VideoEntity {
    private ArrayList<Video> records;

    public ArrayList<Video> getMyVideoList() {
        return records;
    }

    public class Video {
        private int vid;
        private String avatar;
        private String nickname;
        private String imageUrl;
        private int playCount;
        private String videoTitle;

        public int getVid() {
            return vid;
        }

        public void setVid(int vid) {
            this.vid = vid;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getVideoTitle() {
            return videoTitle;
        }

        public void setVideoTitle(String videoTitle) {
            this.videoTitle = videoTitle;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public int getPlayCount() {
            return playCount;
        }

        public void setPlayCount(int playCount) {
            this.playCount = playCount;
        }
    }

}
