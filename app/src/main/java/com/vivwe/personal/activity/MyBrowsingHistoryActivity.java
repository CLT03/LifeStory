package com.vivwe.personal.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.vivwe.base.activity.BaseActivity;
import com.vivwe.main.R;
import com.vivwe.personal.adapter.MyBrowsingHistoryAdapter;
import com.vivwe.personal.adapter.MyFansAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ahtor: super_link
 * date: 2019/5/7 10:16
 * remark: 我的浏览记录
 */
public class MyBrowsingHistoryActivity extends BaseActivity {

    @BindView(R.id.recycler_view_today)
    RecyclerView recyclerViewToday;
    @BindView(R.id.recycler_view_yesterday)
    RecyclerView recyclerViewYesterday;
    @BindView(R.id.recycler_view_earlier)
    RecyclerView recyclerViewEarlier;
    @BindView(R.id.group_today)
    Group groupToday;
    @BindView(R.id.group_yesterday)
    Group groupYesterday;
    @BindView(R.id.group_earlier)
    Group groupEarlier;
    @BindView(R.id.group_edit)
    Group groupEdit;
    private MyBrowsingHistoryAdapter todayAdapter,yesterdayAdapter,earlierAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_mybrowsing_history);
        ButterKnife.bind(this);
        init();
    }

    private void init(){
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewToday.setLayoutManager(linearLayoutManager);
        todayAdapter=new MyBrowsingHistoryAdapter(this);
        recyclerViewToday.setAdapter(todayAdapter);
        LinearLayoutManager linearLayoutManager1=new LinearLayoutManager(this);
        linearLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewYesterday.setLayoutManager(linearLayoutManager1);
        yesterdayAdapter=new MyBrowsingHistoryAdapter(this);
        recyclerViewYesterday.setAdapter(yesterdayAdapter);
        LinearLayoutManager linearLayoutManager2=new LinearLayoutManager(this);
        linearLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewEarlier.setLayoutManager(linearLayoutManager2);
        earlierAdapter=new MyBrowsingHistoryAdapter(this);
        recyclerViewEarlier.setAdapter(earlierAdapter);
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