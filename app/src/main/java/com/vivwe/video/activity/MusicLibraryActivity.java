package com.vivwe.video.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mbs.sdk.net.HttpRequest;
import com.mbs.sdk.net.listener.OnResultListener;
import com.mbs.sdk.net.msg.WebMsg;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vivwe.base.activity.BaseActivity;
import com.vivwe.base.ui.alert.Loading;
import com.vivwe.main.R;
import com.vivwe.video.adapter.MusicLibraryFragmentPagerAdapter;
import com.vivwe.video.adapter.MusicLibraryTypeAdapter;
import com.vivwe.video.api.WebMusicApi;
import com.vivwe.video.entity.MusicEntity;
import com.vivwe.video.entity.MusicTypeEntity;
import com.vivwe.video.fragment.MusicLibraryFragment;
import com.vivwe.video.ui.IMusicPlayView;
import com.vivwe.video.ui.MusicPlayer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ahtor: super_link
 * date: 2019/4/26 15:38
 * remark: 音乐库
 */
public class MusicLibraryActivity extends BaseActivity implements IMusicPlayView, ViewPager.OnPageChangeListener {

    /** 音乐分类 */
    @BindView(R.id.rcv_type)
    RecyclerView typeRcv;
    /** 音乐分类适配器 */
    MusicLibraryTypeAdapter typeAdapter;
    /** 当前对应音乐分类显示页面下标 */
    private int currentIndex = 0;

    /** 上拉刷新 */
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout refreshSrl;

    private ArrayList<MusicLibraryFragment> fragments;

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_start)
    TextView tvStart;
    @BindView(R.id.tv_end)
    TextView tvEnd;
    @BindView(R.id.iv_play)
    ImageView ivPlay;
    @BindView(R.id.seekBar)
    SeekBar seekBar;

    private int tag = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_music_library);
        ButterKnife.bind(this);
        getWindow().setStatusBarColor(Color.parseColor("#181818"));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        init();
    }

    private void init() {
        // 初始化音乐分类
        typeRcv.setLayoutManager(new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false));
        typeAdapter = new MusicLibraryTypeAdapter(this, this);
        typeRcv.setAdapter(typeAdapter);

        loadMusicType();
    }

    /**
     * 加载音乐分类
     */
    private void loadMusicType(){

        Loading.start(this, false);

        HttpRequest.getInstance().excute(HttpRequest.create(WebMusicApi.class).listMusicType(), new OnResultListener() {
            @Override
            public void onWebUiResult(WebMsg webMsg) {

                Loading.stop();

                if(webMsg.dataIsSuccessed()){
                    List<MusicTypeEntity> datas = new GsonBuilder().create().fromJson(webMsg.getData(), new TypeToken<List<MusicTypeEntity>>(){}.getType());
                    typeAdapter.setDatas(datas);

                    loadMusicModuleUI();
                } else if(webMsg.netIsSuccessed()){
                    Toast.makeText(MusicLibraryActivity.this, webMsg.getDesc(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /***
     * 加载音乐模块UI
     */
    private void loadMusicModuleUI(){
        fragments = new ArrayList<>();
        for (int i = 0; i < typeAdapter.getItemCount(); i ++){
            fragments.add(new MusicLibraryFragment());

        }

        viewPager.setAdapter(new MusicLibraryFragmentPagerAdapter(getSupportFragmentManager(), fragments));

        viewPager.addOnPageChangeListener(this);

        int i = getResources().getDimensionPixelOffset(R.dimen.x12);
        seekBar.setPadding(i, 0, i, 0);//铺不满问题
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                MusicPlayer.getInstance().setProgress(seekBar.getProgress());
            }
        });

        MusicPlayer.getInstance().initView(this);

        refreshSrl.setEnableLoadMore(true);
        refreshSrl.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadMusic(true);
                Toast.makeText(MusicLibraryActivity.this, "加载更多...", Toast.LENGTH_LONG).show();
            }
        });

        loadMusic(false);
    }

    /***
     * 加载音乐
     */
    private void loadMusic(boolean loadMore){

        int pageNum = fragments.get(currentIndex).getNextPageNum();

        if(!loadMore && pageNum != 1){
            return;
        }



    }


    private void temp(){
//        fragments = new ArrayList<>();
//        for (int i = 0; i < tvTitles.size(); i++) {
//            MusicLibraryFragment musicLibraryFragment = new MusicLibraryFragment();
//            //Bundle bundle = new Bundle();
//            //bundle.putString("tag", title);
//            // recommendItemFragment.setArguments(bundle);
//            fragments.add(musicLibraryFragment);
//        }
//        viewPager.setAdapter(new MusicLibraryFragmentPagerAdapter(getSupportFragmentManager(), fragments));
//        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int i, float v, int i1) {
//
//            }
//
//            @Override
//            public void onPageSelected(int i) {
//                tvTitles.get(i).performClick();
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int i) {
//
//            }
//        });
//
//        for (int i = 0; i < tvTitles.size(); i++) {
//            tag = i;
//            tvTitles.get(i).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (tag != tvTitles.indexOf(v)) {
//                        TextView textView = (TextView) v;
//                        textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
//                        viewTitles[tvTitles.indexOf(v)].setVisibility(View.VISIBLE);
//                        tvTitles.get(tag).setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
//                        viewTitles[tag].setVisibility(View.GONE);
//                        tag = tvTitles.indexOf(v);
//                        horizontalScrollView.scrollTo(0, 0);
//                        int w = horizontalScrollView.getWidth() / 2;
//                        int gw = v.getWidth() / 2;
//                        if ((v.getLeft() + gw) > w) {
//                            horizontalScrollView.scrollTo(v.getLeft() + gw - w, 0);
//                        }
//                        viewPager.setCurrentItem(tvTitles.indexOf(v));
//                    }
//
//                }
//            });
//            tvTitles.get(0).performClick();
//        }
//        int i = getResources().getDimensionPixelOffset(R.dimen.x12);
//        seekBar.setPadding(i, 0, i, 0);//铺不满问题
//        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//                MusicPlayer.getInstance().setProgress(seekBar.getProgress());
//            }
//        });
//        MusicPlayer.getInstance().initView(this);
    }


    @OnClick({R.id.iv_back, R.id.iv_play, R.id.tv_local, R.id.btn_use,R.id.tv_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_play:
                MusicPlayer.getInstance().pauseOrPlay();
                break;
            case R.id.tv_local:

                break;
            case R.id.btn_use:
                if (MusicPlayer.getInstance().getUrl() != null)
                    setResult(2, new Intent().putExtra("result", MusicPlayer.getInstance().getUrl()));
                finish();
                break;
            case R.id.tv_search:

                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MusicPlayer.getInstance().release();
    }

    @Override
    public void setPlayProgress(int percentage) {
        seekBar.setProgress(percentage);
    }

    @Override
    public void setStartText(String text) {
        tvStart.setText(text);
    }

    @Override
    public void setEndText(String text) {
        tvEnd.setText(text);
    }

    @Override
    public void setMusicName(String name) {
        tvName.setText(name);
    }

    @Override
    public void setImgPlay(boolean playing) {
        if (playing) {
            ivPlay.setImageDrawable(getResources().getDrawable(R.mipmap.icon_music_pause));
        } else ivPlay.setImageDrawable(getResources().getDrawable(R.mipmap.icon_music_play));
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int position) {
        if(currentIndex != position){
            currentIndex = position;
            loadMusic(false);
            typeAdapter.setCurrentIndex(position);
            viewPager.setCurrentItem(position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    class LoadMusic {
        private List<MusicEntity> records;
        private int total;
        private int size;
        private int current;
        private boolean searchCount;
        private int pages;

        public List<MusicEntity> getRecords() {
            return records;
        }

        public void setRecords(List<MusicEntity> records) {
            this.records = records;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getCurrent() {
            return current;
        }

        public void setCurrent(int current) {
            this.current = current;
        }

        public boolean isSearchCount() {
            return searchCount;
        }

        public void setSearchCount(boolean searchCount) {
            this.searchCount = searchCount;
        }

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }
    }
}
