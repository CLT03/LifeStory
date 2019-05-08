package com.vivwe.personal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.vivwe.author.activity.TransactionRecordActivity;
import com.vivwe.base.activity.BaseActivity;
import com.vivwe.main.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ahtor: super_link
 * date: 2019/4/26 17:59
 * remark: 已购买模板
 */
public class MyPurchasedActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_mypurchased);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_back, R.id.tv_record})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_record:
                //
                startActivity(new Intent(this,TransactionRecordActivity.class));
                break;
        }
    }
}
