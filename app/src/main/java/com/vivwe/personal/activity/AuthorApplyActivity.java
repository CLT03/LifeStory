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
 * date: 2019/4/26 17:57
 * remark: 申请为创作者
 */
public class AuthorApplyActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_author_apply);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.iv_back, R.id.btn_get_verification, R.id.iv_upload_id_card, R.id.iv_upload_id_card_back, R.id.btn_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_get_verification:
                break;
            case R.id.iv_upload_id_card:
                break;
            case R.id.iv_upload_id_card_back:
                break;
            case R.id.btn_submit:
                break;
        }
    }
}
