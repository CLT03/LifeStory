package com.vivwe.author.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.vivwe.base.activity.BaseActivity;
import com.vivwe.main.R;
import com.vivwe.personal.adapter.MyCollectedDemoAdapter;
import com.vivwe.personal.adapter.MyVideoAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ahtor: super_link
 * date: 2019/4/26 17:57
 * remark: 设计师个人主页
 */
public class DesignerHomeActivity extends BaseActivity {


    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_theme)
    TextView tvTheme;
    @BindView(R.id.tv_make)
    TextView tvMake;
    @BindView(R.id.tv_sign)
    TextView tvSign;
    @BindView(R.id.recycler_view_template)
    RecyclerView recyclerViewTemplate;
    @BindView(R.id.recycler_view_video)
    RecyclerView recyclerViewVideo;
    @BindView(R.id.tv_template)
    TextView tvTemplate;
    private MyCollectedDemoAdapter adapterTemplate;
    private MyVideoAdapter adapterVideo;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_designer_home);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerViewTemplate.setLayoutManager(gridLayoutManager);
        adapterTemplate = new MyCollectedDemoAdapter(this);
        recyclerViewTemplate.setAdapter(adapterTemplate);

        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(this, 3);
        recyclerViewVideo.setLayoutManager(gridLayoutManager1);
        adapterVideo = new MyVideoAdapter(this);
        recyclerViewVideo.setAdapter(adapterVideo);
    }

    @OnClick({R.id.iv_back, R.id.btn_attention, R.id.iv_change, R.id.tv_template})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_attention:

                break;
            case R.id.iv_change:
            case R.id.tv_template:
                if (recyclerViewTemplate.getVisibility() == View.VISIBLE) {
                    recyclerViewTemplate.setVisibility(View.GONE);
                    recyclerViewVideo.setVisibility(View.VISIBLE);
                    tvTemplate.setText("视频");
                } else {
                    recyclerViewVideo.setVisibility(View.GONE);
                    recyclerViewTemplate.setVisibility(View.VISIBLE);
                    tvTemplate.setText("模板");
                }
                break;
        }
    }
}
