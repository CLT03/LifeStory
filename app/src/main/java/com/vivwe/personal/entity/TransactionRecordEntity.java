package com.vivwe.personal.entity;

import java.math.BigDecimal;
import java.util.ArrayList;

public class TransactionRecordEntity {

    private ArrayList<TransactionRecord> records;

    public void setRecords(ArrayList<TransactionRecord> records) {
        this.records = records;
    }

    public ArrayList<TransactionRecord> getRecords() {
        return records;
    }

    public class TransactionRecord{
        private String nickname;
        private String price;
        private String gmtOrderTime;

        public String getGmtOrderTime() {
            return gmtOrderTime;
        }

        public void setGmtOrderTime(String gmtOrderTime) {
            this.gmtOrderTime = gmtOrderTime;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }
}
