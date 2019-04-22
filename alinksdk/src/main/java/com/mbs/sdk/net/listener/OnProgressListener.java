package com.mbs.sdk.net.listener;

import com.mbs.sdk.net.msg.WebMsg;

public interface OnProgressListener {
    public void onProgress(long currentBytes, long contentLength);
    public void onFinished(WebMsg webMsg);
}
