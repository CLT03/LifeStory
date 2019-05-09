package com.vivwe.personal.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.vivwe.base.activity.BaseActivity;
import com.vivwe.main.R;
import com.vivwe.personal.adapter.MyFansAdapter;
import com.vivwe.personal.adapter.MyVideoAdapter;

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
    @BindView(R.id.group_edit)
    Group groupEdit;
    private MyVideoAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_myvideo);
        ButterKnife.bind(this);
        init();
    }

    private void init(){
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewVideo.setLayoutManager(linearLayoutManager);
        adapter=new MyVideoAdapter(this);
        recyclerViewVideo.setAdapter(adapter);
    }


    @OnClick({R.id.iv_back, R.id.tv_edit, R.id.tv_all, R.id.tv_delete})
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
            case R.id.tv_all:
                break;
            case R.id.tv_delete:
                break;
        }
    }
}