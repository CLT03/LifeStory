package com.vivwe.personal.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mbs.sdk.net.HttpRequest;
import com.mbs.sdk.net.listener.OnResultListener;
import com.mbs.sdk.net.msg.WebMsg;
import com.vivwe.base.activity.BaseActivity;
import com.vivwe.base.ui.alert.Toast;
import com.vivwe.main.R;
import com.vivwe.personal.adapter.MyDraftAdapter;
import com.vivwe.personal.api.PersonalApi;
import com.vivwe.personal.entity.DraftEntity;
import com.vivwe.personal.entity.TemplateEntity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ahtor: super_link
 * date: 2019/4/26 17:59
 * remark: 我的草稿
 */
public class MyDraftActivity extends BaseActivity {

    @BindView(R.id.tv_edit)
    TextView tvEdit;
    @BindView(R.id.recycler_view_draft)
    RecyclerView recyclerViewDraft;
    @BindView(R.id.group_edit)
    Group groupEdit;
    private MyDraftAdapter adapter;
    private boolean ifAllChoose;//是否全选


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_mydraft);
        ButterKnife.bind(this);
        init();
    }

    private void init(){
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,3);
        recyclerViewDraft.setLayoutManager(gridLayoutManager);
        adapter=new MyDraftAdapter(this);
        recyclerViewDraft.setAdapter(adapter);
        getData();
    }

    private void getData(){
        HttpRequest.getInstance().excute(HttpRequest.create(PersonalApi.class).getDraftsList(), new OnResultListener() {
            @Override
            public void onWebUiResult(WebMsg webMsg) {
                if (webMsg.dataIsSuccessed()) {
                    ArrayList<DraftEntity> draftEntities = new GsonBuilder().create().fromJson(webMsg.getData(),new TypeToken<ArrayList<DraftEntity>>(){}.getType());
                    adapter.setDraftEntities(draftEntities);
                } else if (webMsg.netIsSuccessed()) {
                    Toast.show(MyDraftActivity.this, webMsg.getDesc(), 2000);
                }
            }
        });
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
                    adapter.setIfEdit(false);
                    tvEdit.setText("编辑");
                } else {
                    groupEdit.setVisibility(View.VISIBLE);
                    adapter.setIfEdit(true);
                    tvEdit.setText("取消");
                }
                break;
            case R.id.tv_all:
                if (ifAllChoose) {
                    adapter.allChoose(false);
                    ifAllChoose = false;
                } else {
                    adapter.allChoose(true);
                    ifAllChoose = true;
                }
                break;
            case R.id.tv_delete:
                delete();
                break;
        }
    }

    private void delete(){
        if(adapter.getChooseIdList().size()>0) {
            HttpRequest.getInstance().excute(HttpRequest.create(PersonalApi.class).deleteDraft(adapter.getChooseIdList()), new OnResultListener() {
                @Override
                public void onWebUiResult(WebMsg webMsg) {
                    if (webMsg.dataIsSuccessed()) {
                        com.vivwe.base.ui.alert.Toast.show(MyDraftActivity.this, webMsg.getDesc(), 2000);
                        adapter.deleteSuccess();
                    } else if (webMsg.netIsSuccessed()) {
                        com.vivwe.base.ui.alert.Toast.show(MyDraftActivity.this, webMsg.getDesc(), 2000);
                    }
                }
            });
        }else android.widget.Toast.makeText(this, "选择不能为空！", android.widget.Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        if (groupEdit.getVisibility() == View.VISIBLE) {
            groupEdit.setVisibility(View.GONE);
            adapter.setIfEdit(false);
            tvEdit.setText("编辑");
        } else super.onBackPressed();
    }
}
