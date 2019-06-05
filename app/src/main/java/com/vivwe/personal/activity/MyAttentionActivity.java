package com.vivwe.personal.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mbs.sdk.net.HttpRequest;
import com.mbs.sdk.net.listener.OnResultListener;
import com.mbs.sdk.net.msg.WebMsg;
import com.vivwe.base.activity.BaseActivity;
import com.vivwe.base.ui.alert.Toast;
import com.vivwe.main.R;
import com.vivwe.main.adapter.MessageAdapter;
import com.vivwe.personal.adapter.MyAttentionAdapter;
import com.vivwe.personal.api.PersonalApi;
import com.vivwe.personal.entity.AttentionEntity;
import com.vivwe.personal.entity.TemplateEntity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ahtor: super_link
 * date: 2019/4/26 17:59
 * remark: 我的关注
 */
public class MyAttentionActivity extends BaseActivity {

    @BindView(R.id.recycler_view_attention)
    RecyclerView recyclerViewAttention;
    private MyAttentionAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_myattention);
        ButterKnife.bind(this);
        init();
    }

    private void init(){
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewAttention.setLayoutManager(linearLayoutManager);
        adapter=new MyAttentionAdapter(this);
        recyclerViewAttention.setAdapter(adapter);
        getAttention();
    }

    private void getAttention(){
        HttpRequest.getInstance().excute(HttpRequest.create(PersonalApi.class).getAttentionList(1,Integer.MAX_VALUE), new OnResultListener() {
            @Override
            public void onWebUiResult(WebMsg webMsg) {
                if (webMsg.dataIsSuccessed()) {
                    AttentionEntity attentionEntity = webMsg.getData(AttentionEntity.class);
                    adapter.setAttentions(attentionEntity.getRecords(),1);
                } else if (webMsg.netIsSuccessed()) {
                    Toast.show(MyAttentionActivity.this, webMsg.getDesc(), 2000);
                }
            }
        });
    }

    @OnClick(R.id.iv_back)
    public void onClick() {
        finish();
    }
}
