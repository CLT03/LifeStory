package com.vivwe.personal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.vivwe.base.activity.BaseActivity;
import com.vivwe.main.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ahtor: super_link
 * date: 2019/4/26 18:04
 * remark: 修改用户信息
 */
public class UpdateUserInfoActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_update_userinfo);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.iv_back, R.id.iv_camera, R.id.tv_name, R.id.tv_gender, R.id.tv_birthday, R.id.tv_city, R.id.tv_sign})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_camera:
                break;
            case R.id.tv_name:
                startActivity(new Intent(this,UpdateNicknameActivity.class));
                break;
            case R.id.tv_gender:
                break;
            case R.id.tv_birthday:
                break;
            case R.id.tv_city:
                startActivity(new Intent(this,UpdateCityActivity.class));
                break;
            case R.id.tv_sign:
                startActivity(new Intent(this,UpdateRemarkActivity.class));
                break;
        }
    }
}
