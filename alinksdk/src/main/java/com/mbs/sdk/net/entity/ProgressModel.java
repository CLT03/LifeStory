package com.mbs.sdk.net.entity;

/**
 * 进度模型
 */
public class ProgressModel {
    private long currentLength;
    private long totalLength;

    public ProgressModel(long currentLength, long totalLength){
        this.currentLength = currentLength;
        this.totalLength = totalLength;
    }
    public long getCurrentLength() {
        return currentLength;
    }

    public void setCurrentLength(long currentLength) {
        this.currentLength = currentLength;
    }

    public long getTotalLength() {
        return totalLength;
    }

    public void setTotalLength(long totalLength) {
        this.totalLength = totalLength;
    }
}
