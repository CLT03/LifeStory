package com.vivwe.personal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mbs.sdk.net.HttpRequest;
import com.mbs.sdk.net.listener.OnResultListener;
import com.mbs.sdk.net.msg.WebMsg;
import com.vivwe.author.activity.TransactionRecordActivity;
import com.vivwe.base.activity.BaseActivity;
import com.vivwe.base.ui.alert.Toast;
import com.vivwe.main.R;
import com.vivwe.personal.adapter.MyCollectedDemoAdapter;
import com.vivwe.personal.api.PersonalApi;
import com.vivwe.personal.entity.TemplateEntity;

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
    private MyCollectedDemoAdapter adapter;

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
        adapter=new MyCollectedDemoAdapter(this);
        recyclerViewPurchased.setAdapter(adapter);
        getData();
    }

    private void getData(){
        HttpRequest.getInstance().excute(HttpRequest.create(PersonalApi.class).getMyPurchased(1,Integer.MAX_VALUE), new OnResultListener() {
            @Override
            public void onWebUiResult(WebMsg webMsg) {
                if (webMsg.dataIsSuccessed()) {
                    TemplateEntity templateEntity = webMsg.getData(TemplateEntity.class);
                    adapter.setTemplates(templateEntity.getRecords());
                } else if (webMsg.netIsSuccessed()) {
                    Toast.show(MyPurchasedActivity.this, webMsg.getDesc(), 2000);
                }
            }
        });
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
