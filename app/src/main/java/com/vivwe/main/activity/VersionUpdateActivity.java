package com.vivwe.main.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.vivwe.base.activity.BaseActivity;
import com.vivwe.main.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ahtor: super_link
 * date: 2019/4/26 18:12
 * remark: 软件更新
 */
public class VersionUpdateActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_version_update);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.iv_back)
    public void onClick() {
        finish();
    }
}
