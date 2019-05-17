package com.vivwe.author.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.vivwe.author.adapter.IncomeAdapter;
import com.vivwe.base.activity.BaseActivity;
import com.vivwe.main.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ahtor: super_link
 * date: 2019/4/26 17:57
 * remark: 设计师个人主页
 */
public class DesignerHomeActivity extends BaseActivity {

    @BindView(R.id.recycler_view_income)
    RecyclerView recyclerViewIncome;
    private IncomeAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_designer_home);
    }

    @OnClick(R.id.iv_back)
    public void onClick() {
        finish();
    }
}
