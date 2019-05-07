package com.vivwe.personal.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.vivwe.base.activity.BaseActivity;
import com.vivwe.main.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ahtor: super_link
 * date: 2019/4/26 17:59
 * remark: 我的草稿
 */
public class MyDraftActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_mydraft);
        ButterKnife.bind(this);
    }



    @OnClick({R.id.iv_back, R.id.tv_edit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_edit:
                break;
        }
    }
}
