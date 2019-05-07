package com.vivwe.author.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.vivwe.author.activity.IncomeActivity;
import com.vivwe.base.activity.BaseActivity;
import com.vivwe.main.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ahtor: super_link
 * date: 2019/4/26 17:57
 * remark: 创作者中心
 */
public class CenterActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_center);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.iv_back, R.id.tv_today_earning})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_today_earning:
                startActivity(new Intent(this,IncomeActivity.class));
                break;
        }
    }
}
