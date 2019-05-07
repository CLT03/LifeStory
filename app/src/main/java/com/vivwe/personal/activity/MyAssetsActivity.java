package com.vivwe.personal.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.vivwe.base.activity.BaseActivity;
import com.vivwe.main.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ahtor: super_link
 * date: 2019/4/26 17:57
 * remark: 我的素材库
 */
public class MyAssetsActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_myassets);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.iv_back)
    public void onClick() {
        finish();
    }
}
