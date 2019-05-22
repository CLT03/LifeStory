package com.vivwe.author.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.vivwe.base.activity.BaseActivity;
import com.vivwe.main.R;
import com.vivwe.personal.adapter.MyCollectedDemoAdapter;

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
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private MyCollectedDemoAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_designer_home);
        ButterKnife.bind(this);
        init();
    }

    private void init(){
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter=new MyCollectedDemoAdapter(this);
        recyclerView.setAdapter(adapter);
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

                break;
            case R.id.tv_template:

                break;
        }
    }
}
