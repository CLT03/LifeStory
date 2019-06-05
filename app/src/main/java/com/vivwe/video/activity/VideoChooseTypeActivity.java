package com.vivwe.video.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mbs.sdk.net.HttpRequest;
import com.mbs.sdk.net.listener.OnResultListener;
import com.mbs.sdk.net.msg.WebMsg;
import com.vivwe.base.activity.BaseActivity;
import com.vivwe.base.ui.alert.Toast;
import com.vivwe.main.R;
import com.vivwe.video.adapter.VideoChooseTypeAdapter;
import com.vivwe.video.api.VideoApi;
import com.vivwe.video.entity.VideoTypeEntity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ahtor: super_link
 * date: 2019/5/10 09:38
 * remark: 选择视频分类
 */
public class VideoChooseTypeActivity extends BaseActivity {


    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private VideoChooseTypeAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_type);
        ButterKnife.bind(this);
        init();
    }

    private void init(){
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter=new VideoChooseTypeAdapter(this);
        recyclerView.setAdapter(adapter);
        getData();
    }

    private void getData() {
        HttpRequest.getInstance().excute(HttpRequest.create(VideoApi.class).getVideoTypeList(), new OnResultListener() {
            @Override
            public void onWebUiResult(WebMsg webMsg) {
                if (webMsg.dataIsSuccessed()) {
                    ArrayList<VideoTypeEntity> arrayVideoType = new GsonBuilder().create().fromJson(webMsg.getData(), new TypeToken<ArrayList<VideoTypeEntity>>() {
                    }.getType());
                    adapter.setArrayVideoType(arrayVideoType);
                } else if (webMsg.netIsSuccessed()) {
                    Toast.show(VideoChooseTypeActivity.this, webMsg.getDesc(), 2000);
                }
            }
        });
    }

    @OnClick({R.id.iv_back, R.id.tv_sure})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_sure:

                break;
        }
    }
}
