package com.vivwe.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.vivwe.base.activity.BaseActivity;
import com.vivwe.main.R;
import com.vivwe.personal.activity.UpdateUserInfoActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ahtor: super_link
 * date: 2019/4/26 18:12
 * remark: 设置
 */
public class SettingActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_back, R.id.tv_user_info, R.id.tv_change_psw, R.id.tv_help, R.id.tv_update, R.id.tv_about})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_user_info:
                startActivity(new Intent(this,UpdateUserInfoActivity.class));
                break;
            case R.id.tv_change_psw:
                startActivity(new Intent(this,ResetPasswordActivity.class));
                break;
            case R.id.tv_help:
                startActivity(new Intent(this,FeebackActivity.class));
                break;
            case R.id.tv_update:
                startActivity(new Intent(this,VersionUpdateActivity.class));
                break;
            case R.id.tv_about:
                startActivity(new Intent(this,AboutActivity.class));
                break;
        }
    }
}
