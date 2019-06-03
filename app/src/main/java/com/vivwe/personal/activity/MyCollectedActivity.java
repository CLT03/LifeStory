package com.vivwe.personal.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.vivwe.base.activity.BaseActivity;
import com.vivwe.base.ui.textview.LinearGradientTextView;
import com.vivwe.main.R;
import com.vivwe.personal.adapter.MyCollectedDemoAdapter;
import com.vivwe.personal.adapter.MyCollectedVideoAdapter;
import com.vivwe.personal.adapter.MycollectedPagerAdapter;
import com.vivwe.personal.fragment.MycollectedFragment;
import com.vivwe.video.adapter.VideoSearchPagerAdapter;
import com.vivwe.video.fragment.VideoSearchFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ahtor: super_link
 * date: 2019/4/26 17:59
 * remark: 我的收藏
 */
public class MyCollectedActivity extends BaseActivity {

    @BindView(R.id.group_edit)
    Group groupEdit;
    @BindView(R.id.tv_demo)
    TextView tvDemo;
    @BindView(R.id.view_demo)
    View viewDemo;
    @BindView(R.id.tv_video)
    TextView tvVideo;
    @BindView(R.id.view_video)
    View viewVideo;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    ArrayList<MycollectedFragment> fragments = new ArrayList<>();
    TextView textViewTag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_mycollected);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        textViewTag=tvDemo;
        for (int i = 0; i < 2; i++) {
            MycollectedFragment mycollectedFragment = new MycollectedFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("tag", i);
            mycollectedFragment.setArguments(bundle);
            fragments.add(mycollectedFragment);
        }
        viewPager.setAdapter(new MycollectedPagerAdapter(getSupportFragmentManager(), fragments));
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i){
                    case 0:
                        tvDemo.performClick();
                        break;
                    case 1:
                        tvVideo.performClick();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }


    @OnClick({R.id.iv_back, R.id.tv_edit, R.id.tv_demo, R.id.view_demo, R.id.tv_video, R.id.view_video, R.id.tv_all, R.id.tv_delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_edit:
                if (groupEdit.getVisibility() == View.VISIBLE) {
                    groupEdit.setVisibility(View.GONE);
                } else groupEdit.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_demo:
            case R.id.view_demo:
                viewPager.setCurrentItem(0);
                tvDemo.setTextColor(Color.parseColor("#FF5F22"));
                tvVideo.setTextColor(Color.parseColor("#262626"));
                viewDemo.setVisibility(View.VISIBLE);
                viewVideo.setVisibility(View.GONE);
                break;
            case R.id.tv_video:
            case R.id.view_video:
                viewPager.setCurrentItem(1);
                tvVideo.setTextColor(Color.parseColor("#FF5F22"));
                tvDemo.setTextColor(Color.parseColor("#262626"));
                viewDemo.setVisibility(View.GONE);
                viewVideo.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_all:

                break;
            case R.id.tv_delete:

                break;
        }
    }
}
