package com.vivwe.personal.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.vivwe.base.activity.BaseActivity;
import com.vivwe.main.R;
import com.vivwe.personal.adapter.MyAttentionAdapter;
import com.vivwe.personal.adapter.TheDesignerRecommendAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ahtor: super_link
 * date: 2019/4/26 15:38
 * remark: 推荐用户/设计师
 */
public class TheDesignerRecommendForYouActivity extends BaseActivity {

    @BindView(R.id.recycler_view_user)
    RecyclerView recyclerViewUser;
    private TheDesignerRecommendAdapter designerRecommendAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_recommend_designer_foryou);
        ButterKnife.bind(this);
        init();
    }

    private void init(){
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewUser.setLayoutManager(linearLayoutManager);
        designerRecommendAdapter=new TheDesignerRecommendAdapter(this);
        recyclerViewUser.setAdapter(designerRecommendAdapter);
    }

    @OnClick(R.id.iv_back)
    public void onClick() {
        finish();
    }
}
