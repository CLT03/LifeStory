package com.vivwe.personal.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.vivwe.base.activity.BaseActivity;
import com.vivwe.main.R;
import com.vivwe.personal.adapter.MyAssetsAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ahtor: super_link
 * date: 2019/4/26 17:57
 * remark: 我的素材库
 */
public class MyAssetsActivity extends BaseActivity {

    @BindView(R.id.tv_image)
    TextView tvImage;
    @BindView(R.id.view_image)
    View viewImage;
    @BindView(R.id.tv_gif)
    TextView tvGif;
    @BindView(R.id.view_gif)
    View viewGif;
    @BindView(R.id.recycler_view_assets)
    RecyclerView recyclerViewAssets;
    @BindView(R.id.group_edit)
    Group groupEdit;
    private MyAssetsAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_myassets);
        ButterKnife.bind(this);
        init();
    }

    private void init(){
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,3);
        recyclerViewAssets.setLayoutManager(gridLayoutManager);
        adapter=new MyAssetsAdapter(this);
        recyclerViewAssets.setAdapter(adapter);
    }


    @OnClick({R.id.iv_back, R.id.tv_edit, R.id.tv_image, R.id.view_image, R.id.tv_gif, R.id.view_gif, R.id.tv_all, R.id.tv_delete})
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
            case R.id.tv_image:
            case R.id.view_image:
                tvImage.setTextColor(Color.parseColor("#FF5F22"));
                tvGif.setTextColor(Color.parseColor("#262626"));
                viewImage.setVisibility(View.VISIBLE);
                viewGif.setVisibility(View.GONE);
                break;
            case R.id.tv_gif:
            case R.id.view_gif:
                tvGif.setTextColor(Color.parseColor("#FF5F22"));
                tvImage.setTextColor(Color.parseColor("#262626"));
                viewGif.setVisibility(View.VISIBLE);
                viewImage.setVisibility(View.GONE);
                break;
            case R.id.tv_all:

                break;
            case R.id.tv_delete:

                break;
        }
    }
}
