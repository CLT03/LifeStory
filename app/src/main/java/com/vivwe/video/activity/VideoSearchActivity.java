package com.vivwe.video.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.vivwe.base.activity.BaseActivity;
import com.vivwe.base.ui.textview.LinearGradientTextView;
import com.vivwe.main.R;
import com.vivwe.main.adapter.RecommendFragmentPagerAdapter;
import com.vivwe.main.fragment.RecommendItemFragment;
import com.vivwe.video.adapter.VideoSearchPagerAdapter;
import com.vivwe.video.fragment.MusicLibraryFragment;
import com.vivwe.video.fragment.VideoSearchFragment;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ahtor: super_link
 * date: 2019/5/10 09:38
 * remark: 视频搜索页
 */
public class VideoSearchActivity extends BaseActivity {
    @BindView(R.id.edt_search)
    EditText edtSearch;
    @BindView(R.id.iv_clear)
    ImageView ivClear;
    @BindView(R.id.group_popular)
    Group groupPopular;
    @BindView(R.id.tv_video)
    LinearGradientTextView tvVideo;
    @BindView(R.id.tv_template)
    LinearGradientTextView tvTemplate;
    @BindView(R.id.tv_user)
    LinearGradientTextView tvUser;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.group_result)
    Group groupResult;
    ArrayList<VideoSearchFragment> fragments = new ArrayList<>();
    LinearGradientTextView textViewTag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_search);
        ButterKnife.bind(this);
        init();
    }

    private void init(){
        textViewTag=tvVideo;
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0) ivClear.setVisibility(View.VISIBLE);
                else ivClear.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        for (int i = 0; i < 3; i++) {
            VideoSearchFragment videoSearchFragment = new VideoSearchFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("tag", i);
            videoSearchFragment.setArguments(bundle);
            fragments.add(videoSearchFragment);
        }
        viewPager.setAdapter(new VideoSearchPagerAdapter(getSupportFragmentManager(), fragments));
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i){
                    case 0:
                        tvVideo.performClick();
                        break;
                    case 1:
                        tvTemplate.performClick();
                        break;
                    case 2:
                        tvUser.performClick();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @OnClick({R.id.tv_cancel, R.id.iv_clear, R.id.tv_video, R.id.tv_template, R.id.tv_user})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                finish();
                break;
            case R.id.iv_clear:
                edtSearch.setText(null);
                break;
            case R.id.tv_video:
                if(textViewTag!=tvVideo) {
                    textViewTag.setTextColor(Color.parseColor("#8E8E8E"));
                    tvVideo.setTextColor(0xFF52D3FF, 0xFFB35CFF);
                    viewPager.setCurrentItem(0);
                    textViewTag=tvVideo;
                }
                break;
            case R.id.tv_template:
                if(textViewTag!=tvTemplate) {
                    textViewTag.setTextColor(Color.parseColor("#8E8E8E"));
                    tvTemplate.setTextColor(0xFF52D3FF, 0xFFB35CFF);
                    viewPager.setCurrentItem(1);
                    textViewTag=tvTemplate;
                }
                break;
            case R.id.tv_user:
                if(textViewTag!=tvUser) {
                    textViewTag.setTextColor(Color.parseColor("#8E8E8E"));
                    tvUser.setTextColor(0xFF52D3FF, 0xFFB35CFF);
                    viewPager.setCurrentItem(2);
                    textViewTag=tvUser;
                }
                break;
        }
    }
}
