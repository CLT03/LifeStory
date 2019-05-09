package com.vivwe.personal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.vivwe.author.activity.TransactionRecordActivity;
import com.vivwe.base.activity.BaseActivity;
import com.vivwe.main.R;
import com.vivwe.personal.adapter.MyFansAdapter;
import com.vivwe.personal.adapter.MyPurchasedAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ahtor: super_link
 * date: 2019/4/26 17:59
 * remark: 已购买模板
 */
public class MyPurchasedActivity extends BaseActivity {

    @BindView(R.id.recycler_view_purchased)
    RecyclerView recyclerViewPurchased;
    private MyPurchasedAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_mypurchased);
        ButterKnife.bind(this);
        init();
    }

    private void init(){
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,2);
        recyclerViewPurchased.setLayoutManager(gridLayoutManager);
        adapter=new MyPurchasedAdapter(this);
        recyclerViewPurchased.setAdapter(adapter);
    }

    @OnClick({R.id.iv_back, R.id.tv_record})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_record:
                startActivity(new Intent(this, TransactionRecordActivity.class));
                break;
        }
    }
}
