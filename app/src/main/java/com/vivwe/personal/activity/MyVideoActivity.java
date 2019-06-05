package com.vivwe.personal.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v7.widget.GridLayoutManager;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mbs.sdk.net.HttpRequest;
import com.mbs.sdk.net.listener.OnResultListener;
import com.mbs.sdk.net.msg.WebMsg;
import com.vivwe.base.activity.BaseActivity;
import com.vivwe.base.cache.UserCache;
import com.vivwe.base.ui.alert.Toast;
import com.vivwe.main.R;


import com.vivwe.personal.adapter.MyVideoAdapter;
import com.vivwe.personal.api.PersonalApi;
import com.vivwe.personal.entity.VideoEntity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ahtor: super_link
 * date: 2019/5/7 10:16
 * remark: 我的视频
 */
public class MyVideoActivity extends BaseActivity {

    @BindView(R.id.recycler_view_video)
    RecyclerView recyclerViewVideo;
    private MyVideoAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_myvideo);
        ButterKnife.bind(this);
        init();
    }

    private void init(){
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this ,3);
        recyclerViewVideo.setLayoutManager(gridLayoutManager);
        adapter=new MyVideoAdapter(this);
        recyclerViewVideo.setAdapter(adapter);
        getData();
    }

    private void getData(){
        HttpRequest.getInstance().excute(HttpRequest.create(PersonalApi.class).getVideoList(1,Integer.MAX_VALUE,
                UserCache.Companion.getUserInfo().getId()), new OnResultListener() {
            @Override
            public void onWebUiResult(WebMsg webMsg) {
                if (webMsg.dataIsSuccessed()) {
                    VideoEntity myVideoEntity= webMsg.getData(VideoEntity.class);
                    adapter.setData(myVideoEntity.getMyVideoList());
                } else if (webMsg.netIsSuccessed()) {
                    Toast.show(MyVideoActivity.this, webMsg.getDesc(), 2000);
                }
            }
        });
    }


    @OnClick({R.id.iv_back, R.id.tv_notice})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_notice:

                break;
        }
    }
}