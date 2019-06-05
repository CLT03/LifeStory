package com.vivwe.video.ui;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class MusicPlayer {
    private MediaPlayer mediaPlayer;
    private static MusicPlayer musicPlayer=new MusicPlayer();
    private String name;
    private String url="";
    private IMusicPlayView iMusicPlayView;
    private long mDuration;
    private Disposable disposable;

    public static MusicPlayer getInstance(){
        return musicPlayer;
    }

    public void initView(IMusicPlayView iMusicPlayView){
        this.iMusicPlayView=iMusicPlayView;
    }

    public void playMusic(String url,String name){
        if (mediaPlayer == null) {
            this.url=url;
            this.name = name;
            iMusicPlayView.setMusicName(name);
            mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.setLooping(true);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            // 通过异步的方式装载媒体资源
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    // 装载完毕回调
                    mediaPlayer.start();
                    mDuration=mediaPlayer.getDuration();
                    iMusicPlayView.setEndText(changeTime(mDuration));
                    iMusicPlayView.setImgPlay(true);
                    updatePlayProgress();
                }
            });
        } else {
            release();
            playMusic(url,name);
        }
    }


    /**
     * 更新播放进度，一秒更新一次
     */
    private void updatePlayProgress() {
        disposable = Observable.interval(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).filter(new Predicate<Long>() {
                    @Override
                    public boolean test(Long aLong) {//过滤
                        return mediaPlayer != null && mediaPlayer.isPlaying();
                    }
                })
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong)  {
                        long position = mediaPlayer.getCurrentPosition();
                        iMusicPlayView.setStartText(changeTime(position));
                        iMusicPlayView.setPlayProgress(mDuration == 0 ? 0 : (int) (position * 100 / mDuration));//+1是解决seekTo不准确的
                       // Log.e("ououou","setPlayProgress"+position * 100 / mDuration + " "+position);
                    }
                });
    }

    public void setProgress(int percentage){
        if(mediaPlayer!=null)
            mediaPlayer.seekTo(percentage*(int)mDuration/100);
    }


    public void release() {
        if (disposable != null) disposable.dispose();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
            url="";
        }
    }

    public void pauseOrPlay(){
        if(mediaPlayer!=null){
            if(mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                iMusicPlayView.setImgPlay(false);
            } else {
                mediaPlayer.start();
                iMusicPlayView.setImgPlay(true);
            }
        }
    }

    private String changeTime(long time) {
        long hour = time / 1000 / 60;
        long minute = time / 1000 % 60;
        String minute1;
        String hour1;
        if (hour >= 10) {
            hour1 = String.valueOf(hour);
        } else hour1 = "0" + hour;
        if (minute >= 10) {
            minute1 = String.valueOf(minute);
        } else minute1 = "0" + minute;
        return hour1 + ":" + minute1;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }
}
