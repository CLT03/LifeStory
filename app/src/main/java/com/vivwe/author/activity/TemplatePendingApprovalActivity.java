package com.vivwe.author.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.vivwe.base.activity.BaseActivity;
import com.vivwe.main.R;

/**
 * ahtor: super_link
 * date: 2019/4/26 17:57
 * remark: 待审核模板
 */
public class TemplatePendingApprovalActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_template_pending_approval);
    }
}