package com.vivwe.main.entity;

import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class VideoHistoryEntity implements Serializable {
    private ArrayList<MyVideo> records;

    public ArrayList<MyVideo> getMyVideoList() {
        return records;
    }

    public class MyVideo implements Serializable  {
        private String imageUrl;
        private String videoTitle;
        private String gmtModified;

        public String getGmtModified() {
            return gmtModified;
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
        
    }

}
