package com.vivwe.video.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.vivwe.base.activity.BaseActivity;
import com.vivwe.main.R;
import com.vivwe.video.adapter.MusicLibraryFragmentPagerAdapter;
import com.vivwe.video.fragment.MusicLibraryFragment;
import com.vivwe.video.ui.IMusicPlayView;
import com.vivwe.video.ui.MusicPlayer;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ahtor: super_link
 * date: 2019/4/26 15:38
 * remark: 音乐库
 */
public class MusicLibraryActivity extends BaseActivity implements IMusicPlayView {

    @BindView(R.id.tv_title1)
    TextView tvTitle1;
    @BindView(R.id.view_title1)
    View viewTitle1;
    @BindView(R.id.tv_title2)
    TextView tvTitle2;
    @BindView(R.id.view_title2)
    View viewTitle2;
    @BindView(R.id.tv_title3)
    TextView tvTitle3;
    @BindView(R.id.view_title3)
    View viewTitle3;
    @BindView(R.id.tv_title4)
    TextView tvTitle4;
    @BindView(R.id.view_title4)
    View viewTitle4;
    @BindView(R.id.tv_title5)
    TextView tvTitle5;
    @BindView(R.id.view_title5)
    View viewTitle5;
    @BindView(R.id.tv_title6)
    TextView tvTitle6;
    @BindView(R.id.view_title6)
    View viewTitle6;
    @BindView(R.id.tv_title7)
    TextView tvTitle7;
    @BindView(R.id.view_title7)
    View viewTitle7;
    @BindView(R.id.tv_title8)
    TextView tvTitle8;
    @BindView(R.id.view_title8)
    View viewTitle8;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.horizontalScrollView)
    HorizontalScrollView horizontalScrollView;
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
    private ArrayList<TextView> tvTitles;
    private View[] viewTitles;
    private ArrayList<MusicLibraryFragment> fragments;
    private int tag = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_music_library);
        ButterKnife.bind(this);
        getWindow().setStatusBarColor(Color.parseColor("#181818"));
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        init();
    }

    private void init() {
        tvTitles = new ArrayList<>();
        tvTitles.add(tvTitle1);
        tvTitles.add(tvTitle2);
        tvTitles.add(tvTitle3);
        tvTitles.add(tvTitle4);
        tvTitles.add(tvTitle5);
        tvTitles.add(tvTitle6);
        tvTitles.add(tvTitle7);
        tvTitles.add(tvTitle8);
        viewTitles = new View[]{viewTitle1, viewTitle2, viewTitle3, viewTitle4, viewTitle5, viewTitle6, viewTitle7, viewTitle8};
        fragments = new ArrayList<>();
        for (int i = 0; i < tvTitles.size(); i++) {
            MusicLibraryFragment musicLibraryFragment = new MusicLibraryFragment();
            //Bundle bundle = new Bundle();
            //bundle.putString("tag", title);
            // recommendItemFragment.setArguments(bundle);
            fragments.add(musicLibraryFragment);
        }
        viewPager.setAdapter(new MusicLibraryFragmentPagerAdapter(getSupportFragmentManager(), fragments));
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                tvTitles.get(i).performClick();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        for (int i = 0; i < tvTitles.size(); i++) {
            tag = i;
            tvTitles.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (tag != tvTitles.indexOf(v)) {
                        TextView textView = (TextView) v;
                        textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                        viewTitles[tvTitles.indexOf(v)].setVisibility(View.VISIBLE);
                        tvTitles.get(tag).setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                        viewTitles[tag].setVisibility(View.GONE);
                        tag = tvTitles.indexOf(v);
                        horizontalScrollView.scrollTo(0, 0);
                        int w = horizontalScrollView.getWidth() / 2;
                        int gw = v.getWidth() / 2;
                        if ((v.getLeft() + gw) > w) {
                            horizontalScrollView.scrollTo(v.getLeft() + gw - w, 0);
                        }
                        viewPager.setCurrentItem(tvTitles.indexOf(v));
                    }

                }
            });
            tvTitles.get(0).performClick();
        }
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

}
