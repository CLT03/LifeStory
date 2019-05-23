package com.vivwe.video.ui;

public interface IMusicPlayView {
    void setPlayProgress(int percentage);
    void setStartText(String text);
    void setEndText(String text);
    void setMusicName(String name);
    void setImgPlay(boolean playing);
}
