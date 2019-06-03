package com.vivwe.author.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.vivwe.author.adapter.TemplateApprovedPagerAdapter;
import com.vivwe.author.adapter.TemplateNoPassAdapter;
import com.vivwe.author.adapter.TemplatePublishAdapter;
import com.vivwe.author.adapter.TemplateWaitReviewAdapter;
import com.vivwe.author.fragment.TemplateApprovedFragment;
import com.vivwe.base.activity.BaseActivity;
import com.vivwe.base.ui.textview.LinearGradientTextView;
import com.vivwe.main.R;
import com.vivwe.personal.adapter.MycollectedPagerAdapter;
import com.vivwe.personal.fragment.MycollectedFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ahtor: super_link
 * date: 2019/4/26 17:57
 * remark: 未批准模板
 */
public class TemplateApprovedActivity extends BaseActivity {

    @BindView(R.id.tv_publish)
    LinearGradientTextView tvPublish;
    @BindView(R.id.view_publish)
    View viewPublish;
    @BindView(R.id.tv_wait_review)
    LinearGradientTextView tvWaitReview;
    @BindView(R.id.view_wait_review)
    View viewWaitReview;
    @BindView(R.id.tv_no_pass)
    LinearGradientTextView tvNoPass;
    @BindView(R.id.view_no_pass)
    View viewNoPass;
    @BindView(R.id.group_edit)
    Group groupEdit;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    ArrayList<TemplateApprovedFragment> fragments = new ArrayList<>();
    LinearGradientTextView textViewTag;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_template_approved);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        for (int i = 0; i < 3; i++) {
            TemplateApprovedFragment templateApprovedFragment = new TemplateApprovedFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("tag", i);
            templateApprovedFragment.setArguments(bundle);
            fragments.add(templateApprovedFragment);
        }
        viewPager.setAdapter(new TemplateApprovedPagerAdapter(getSupportFragmentManager(), fragments));
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i){
                    case 0:
                        tvPublish.performClick();
                        break;
                    case 1:
                        tvWaitReview.performClick();
                        break;
                    case 2:
                        tvNoPass.performClick();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        switch (getIntent().getIntExtra("tag",-1)){
            case 0:
                textViewTag=tvPublish;
                tvPublish.performClick();
                break;
            case 1:
                textViewTag=tvWaitReview;
                tvWaitReview.performClick();
                break;
            case 2:
                textViewTag=tvNoPass;
                tvNoPass.performClick();
                break;
        }
    }



    @OnClick({R.id.iv_back, R.id.tv_edit, R.id.tv_publish, R.id.view_publish, R.id.tv_wait_review, R.id.view_wait_review, R.id.tv_no_pass, R.id.view_no_pass, R.id.tv_all, R.id.tv_delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_edit:
                if (groupEdit.getVisibility() == View.VISIBLE) {
                    groupEdit.setVisibility(View.GONE);
                } else groupEdit.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_publish:
            case R.id.view_publish:
                viewPublish.setVisibility(View.VISIBLE);
                viewNoPass.setVisibility(View.GONE);
                viewWaitReview.setVisibility(View.GONE);
                tvPublish.setTextColor(Color.parseColor("#52D3FF"), Color.parseColor("#B35CFF"));
                tvWaitReview.setTextColor(Color.parseColor("#262626"));
                tvNoPass.setTextColor(Color.parseColor("#262626"));
                viewPager.setCurrentItem(0);
                break;
            case R.id.tv_wait_review:
            case R.id.view_wait_review:
                viewPublish.setVisibility(View.GONE);
                viewNoPass.setVisibility(View.GONE);
                viewWaitReview.setVisibility(View.VISIBLE);
                tvWaitReview.setTextColor(Color.parseColor("#52D3FF"), Color.parseColor("#B35CFF"));
                tvPublish.setTextColor(Color.parseColor("#262626"));
                tvNoPass.setTextColor(Color.parseColor("#262626"));
                viewPager.setCurrentItem(1);
                break;
            case R.id.tv_no_pass:
            case R.id.view_no_pass:
                viewPublish.setVisibility(View.GONE);
                viewNoPass.setVisibility(View.VISIBLE);
                viewWaitReview.setVisibility(View.GONE);
                tvNoPass.setTextColor(Color.parseColor("#52D3FF"), Color.parseColor("#B35CFF"));
                tvWaitReview.setTextColor(Color.parseColor("#262626"));
                tvPublish.setTextColor(Color.parseColor("#262626"));
                viewPager.setCurrentItem(2);
                break;
            case R.id.tv_all:

                break;
            case R.id.tv_delete:

                break;
        }
    }
}
