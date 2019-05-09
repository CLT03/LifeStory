package com.vivwe.personal.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.vivwe.base.activity.BaseActivity;
import com.vivwe.main.R;
import com.vivwe.personal.adapter.MyCollectedDemoAdapter;
import com.vivwe.personal.adapter.MyCollectedVideoAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ahtor: super_link
 * date: 2019/4/26 17:59
 * remark: 我的收藏
 */
public class MyCollectedActivity extends BaseActivity {

    @BindView(R.id.recycler_view_purchased_demo)
    RecyclerView recyclerViewPurchasedDemo;
    @BindView(R.id.recycler_view_purchased_video)
    RecyclerView recyclerViewPurchasedVideo;
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
    private MyCollectedDemoAdapter demoAdapter;
    private MyCollectedVideoAdapter videoAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_mycollected);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerViewPurchasedDemo.setLayoutManager(gridLayoutManager);
        demoAdapter = new MyCollectedDemoAdapter(this);
        recyclerViewPurchasedDemo.setAdapter(demoAdapter);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        linearLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewPurchasedVideo.setLayoutManager(linearLayoutManager1);
        videoAdapter = new MyCollectedVideoAdapter(this);
        recyclerViewPurchasedVideo.setAdapter(videoAdapter);
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
                recyclerViewPurchasedDemo.setVisibility(View.VISIBLE);
                recyclerViewPurchasedVideo.setVisibility(View.GONE);
                tvDemo.setTextColor(Color.parseColor("#FF5F22"));
                tvVideo.setTextColor(Color.parseColor("#262626"));
                viewDemo.setVisibility(View.VISIBLE);
                viewVideo.setVisibility(View.GONE);
                break;
            case R.id.tv_video:
            case R.id.view_video:
                recyclerViewPurchasedVideo.setVisibility(View.VISIBLE);
                recyclerViewPurchasedDemo.setVisibility(View.GONE);
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
