package com.vivwe.video.activity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
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

    @BindView(R.id.tv_play)
    ImageView playIv;

    private boolean isPlay = false;
    private boolean hasInit = false;

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

        MediaController mediaController = new MediaController(this);
        mediaController.setVisibility(View.GONE); //隐藏进度条
        videoView.setMediaController(mediaController);

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                videoView.setVideoPath(path);
                videoView.requestFocus();
                videoView.start();
            }
        });

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                videoView.start();
            }
        });
    }

    @OnClick(R.id.tv_back)
    public void onBack(){
        finish();
    }

    /**
     * 播放视频
     */
    @OnClick({R.id.tv_play, R.id.vv_video})
    public void play(){
        if(!isPlay){
            if(!hasInit){
                videoView.setVisibility(View.VISIBLE);
                videoView.setVideoPath(path);
                MediaController mediaController = new MediaController(this);
                videoView.setMediaController(mediaController);
                videoView.requestFocus();
                hasInit = true;
            } else {
                videoView.start();
            }
            playIv.setVisibility(View.GONE);
        } else {
            videoView.pause();
            playIv.setVisibility(View.VISIBLE);
        }

        isPlay = !isPlay;

    }

    /**
     * 去发布视频
     */
    @OnClick(R.id.tv_next)
    public void publish(){


    }

}
