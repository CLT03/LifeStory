package com.vivwe.video.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.MediaController;
import android.widget.VideoView;

import com.vivwe.base.activity.BaseActivity;
import com.vivwe.main.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ahtor: super_link
 * date: 2019/4/26 15:38
 * remark: 视频预览
 */
public class VideoPreviewActivity extends BaseActivity {

    @BindView(R.id.vv_video)
    VideoView videoView;

    private boolean isPlay = false;
    private String path;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_preview);

        ButterKnife.bind(this);
        init();
    }

    private void init(){
        path = getIntent().getStringExtra("path");

    }

    @OnClick(R.id.tv_back)
    public void onBack(){
        finish();
    }

    /**
     * 播放视频
     */
    @OnClick(R.id.tv_play)
    public void play(){

        videoView.setVideoPath(path);
        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        videoView.requestFocus();


    }

    /**
     * 去发布视频
     */
    @OnClick(R.id.tv_to_publish)
    public void publish(){


    }

}
