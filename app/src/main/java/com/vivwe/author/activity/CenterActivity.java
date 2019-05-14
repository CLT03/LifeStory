package com.vivwe.author.activity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mbs.sdk.utils.ScreenUtils;
import com.vivwe.base.activity.BaseActivity;
import com.vivwe.main.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ahtor: super_link
 * date: 2019/4/26 17:57
 * remark: 创作者中心
 */
public class CenterActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_center);
        ButterKnife.bind(this);
        ViewGroup.LayoutParams layoutParams=tvTitle.getLayoutParams();
        layoutParams.height=ScreenUtils.getStatusHeight(CenterActivity.this)+getResources().getDimensionPixelOffset(R.dimen.x360);
        tvTitle.setLayoutParams(layoutParams);
        tvTitle.setPadding(0,ScreenUtils.getStatusHeight(CenterActivity.this),0,getResources().getDimensionPixelOffset(R.dimen.x272));
    }


    @OnClick({R.id.iv_back, R.id.tv_month_earning, R.id.tv_had_publish, R.id.tv_wait_review, R.id.tv_no_pass})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                Log.e("ououou",tvTitle.getHeight()+" "+tvTitle.getPaddingBottom()+" "+tvTitle.getPaddingTop());
                break;
            case R.id.tv_month_earning:
                startActivity(new Intent(this, IncomeActivity.class));
                break;
            case R.id.tv_had_publish:
                startActivity(new Intent(this, TemplateApprovedActivity.class));
                break;
            case R.id.tv_wait_review:
                startActivity(new Intent(this, TemplateApprovedActivity.class));
                break;
            case R.id.tv_no_pass:
                startActivity(new Intent(this, TemplateApprovedActivity.class));
                break;
        }
    }

}
