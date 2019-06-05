package com.vivwe.video.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mbs.sdk.net.HttpRequest;
import com.mbs.sdk.net.listener.OnResultListener;
import com.mbs.sdk.net.msg.WebMsg;
import com.vivwe.base.activity.BaseActivity;
import com.vivwe.base.ui.alert.Toast;
import com.vivwe.main.R;
import com.vivwe.personal.adapter.TemplateAdapter;
import com.vivwe.personal.entity.TemplateEntity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ahtor: super_link
 * date: 2019/5/10 09:38
 * remark: 模板搜索页面
 */
public class TemplateSearchActivity extends BaseActivity {
    @BindView(R.id.edt_search)
    EditText edtSearch;
    @BindView(R.id.group_popular)
    Group groupPopular;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.iv_clear)
    ImageView ivClear;
    private TemplateAdapter demoAdapter;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_template_search);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edtSearch.getText().toString().length() > 0)
                    ivClear.setVisibility(View.VISIBLE);
                else {
                    ivClear.setVisibility(View.GONE);
                    groupPopular.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //判断是否是“done”键
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    getData();
                    return true;
                }
                return false;
            }
        });


        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);
        demoAdapter=new TemplateAdapter(this);
        recyclerView.setAdapter(demoAdapter);
        edtSearch.requestFocus();
    }

    private void getData(){
        HttpRequest.getInstance().excute(HttpRequest.create(com.vivwe.video.api.TemplateApi.class).searchTemplate(1,Integer.MAX_VALUE,edtSearch.getText().toString()), new OnResultListener() {
            @Override
            public void onWebUiResult(WebMsg webMsg) {
                if (webMsg.dataIsSuccessed()) {
                    TemplateEntity templateEntity = webMsg.getData(TemplateEntity.class);
                    demoAdapter.setTemplates(templateEntity.getRecords());
                    groupPopular.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                } else if (webMsg.netIsSuccessed()) {
                    Toast.show(TemplateSearchActivity.this, webMsg.getDesc(), 2000);
                }
            }
        });

    }

    @OnClick({R.id.tv_cancel, R.id.iv_clear})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                finish();
                break;
            case R.id.iv_clear:
                edtSearch.setText(null);
                break;
        }
    }
}
