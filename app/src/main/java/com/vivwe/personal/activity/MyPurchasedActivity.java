package com.vivwe.personal.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.vivwe.base.activity.BaseActivity;
import com.vivwe.main.R;

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
    }
}