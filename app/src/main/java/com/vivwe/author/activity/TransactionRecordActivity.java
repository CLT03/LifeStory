package com.vivwe.author.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mbs.sdk.net.HttpRequest;
import com.mbs.sdk.net.listener.OnResultListener;
import com.mbs.sdk.net.msg.WebMsg;
import com.vivwe.author.adapter.TransactionRecordAdapter;
import com.vivwe.base.activity.BaseActivity;
import com.vivwe.base.ui.alert.Toast;
import com.vivwe.main.R;
import com.vivwe.personal.api.PersonalApi;
import com.vivwe.personal.entity.TransactionRecordEntity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ahtor: super_link
 * date: 2019/4/26 17:57
 * remark: 创作者交易记录
 */
public class TransactionRecordActivity extends BaseActivity {

    @BindView(R.id.recycler_view_purchased)
    RecyclerView recyclerViewPurchased;
    private TransactionRecordAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_transaction_record);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewPurchased.setLayoutManager(linearLayoutManager);
        adapter = new TransactionRecordAdapter(this);
        recyclerViewPurchased.setAdapter(adapter);
        getData();
    }

    private void getData(){
        HttpRequest.getInstance().excute(HttpRequest.create(PersonalApi.class).getTransactionRecord(1,Integer.MAX_VALUE), new OnResultListener() {
            @Override
            public void onWebUiResult(WebMsg webMsg) {
                if (webMsg.dataIsSuccessed()) {
                    TransactionRecordEntity transactionRecordEntity = webMsg.getData(TransactionRecordEntity.class);
                    adapter.setRecords(transactionRecordEntity.getRecords());
                } else if (webMsg.netIsSuccessed()) {
                    Toast.show(TransactionRecordActivity.this, webMsg.getDesc(), 2000);
                }
            }
        });
    }

    @OnClick(R.id.iv_back)
    public void onClick() {
        finish();
    }
}
