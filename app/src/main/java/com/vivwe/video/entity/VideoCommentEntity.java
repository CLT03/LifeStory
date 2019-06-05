package com.vivwe.video.entity;

import java.security.PrivateKey;
import java.util.ArrayList;

public class VideoCommentEntity {

    private ArrayList<VideoComment> vdList;
    private Page pageItem;

    public ArrayList<VideoComment> getVdList() {
        return vdList;
    }

    public void setVdList(ArrayList<VideoComment> vdList) {
        this.vdList = vdList;
    }


    public Page getPageItem() {
        return pageItem;
    }

    public void setPageItem(Page pageItem) {
        this.pageItem = pageItem;
    }

    public class Page{
        private int total;//总的条数

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }
    }
}
