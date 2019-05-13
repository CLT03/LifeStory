package com.vivwe.author.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.vivwe.author.adapter.TemplateNoPassAdapter;
import com.vivwe.author.adapter.TemplatePublishAdapter;
import com.vivwe.author.adapter.TemplateWaitReviewAdapter;
import com.vivwe.base.activity.BaseActivity;
import com.vivwe.base.ui.textview.LinearGradientTextView;
import com.vivwe.main.R;
import com.vivwe.personal.adapter.MyCollectedDemoAdapter;
import com.vivwe.personal.adapter.MyCollectedVideoAdapter;

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
    @BindView(R.id.recycler_view_publish)
    RecyclerView recyclerViewPublish;
    @BindView(R.id.recycler_view_wait_review)
    RecyclerView recyclerViewWaitReview;
    @BindView(R.id.recycler_view_no_pass)
    RecyclerView recyclerViewNoPass;
    @BindView(R.id.group_edit)
    Group groupEdit;
    private TemplatePublishAdapter publishAdapter;
    private TemplateWaitReviewAdapter waitReviewAdapter;
    private TemplateNoPassAdapter noPassAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_template_approved);
        ButterKnife.bind(this);
        init();
    }


    private void init() {
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        linearLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewPublish.setLayoutManager(linearLayoutManager1);
        publishAdapter = new TemplatePublishAdapter(this);
        recyclerViewPublish.setAdapter(publishAdapter);

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        linearLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewWaitReview.setLayoutManager(linearLayoutManager2);
        waitReviewAdapter = new TemplateWaitReviewAdapter(this);
        recyclerViewWaitReview.setAdapter(waitReviewAdapter);

        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(this);
        linearLayoutManager3.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewNoPass.setLayoutManager(linearLayoutManager3);
        noPassAdapter = new TemplateNoPassAdapter(this);
        recyclerViewNoPass.setAdapter(noPassAdapter);

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
                recyclerViewPublish.setVisibility(View.VISIBLE);
                recyclerViewNoPass.setVisibility(View.GONE);
                recyclerViewWaitReview.setVisibility(View.GONE);
                viewPublish.setVisibility(View.VISIBLE);
                viewNoPass.setVisibility(View.GONE);
                viewWaitReview.setVisibility(View.GONE);
                tvPublish.setTextColor(Color.parseColor("#52D3FF"),Color.parseColor("#B35CFF"));
                tvWaitReview.setTextColor(Color.parseColor("#262626"));
                tvNoPass.setTextColor(Color.parseColor("#262626"));
                break;
            case R.id.tv_wait_review:
            case R.id.view_wait_review:
                recyclerViewPublish.setVisibility(View.GONE);
                recyclerViewNoPass.setVisibility(View.GONE);
                recyclerViewWaitReview.setVisibility(View.VISIBLE);
                viewPublish.setVisibility(View.GONE);
                viewNoPass.setVisibility(View.GONE);
                viewWaitReview.setVisibility(View.VISIBLE);
                tvWaitReview.setTextColor(Color.parseColor("#52D3FF"),Color.parseColor("#B35CFF"));
                tvPublish.setTextColor(Color.parseColor("#262626"));
                tvNoPass.setTextColor(Color.parseColor("#262626"));
                break;
            case R.id.tv_no_pass:
            case R.id.view_no_pass:
                recyclerViewPublish.setVisibility(View.GONE);
                recyclerViewNoPass.setVisibility(View.VISIBLE);
                recyclerViewWaitReview.setVisibility(View.GONE);
                viewPublish.setVisibility(View.GONE);
                viewNoPass.setVisibility(View.VISIBLE);
                viewWaitReview.setVisibility(View.GONE);
                tvNoPass.setTextColor(Color.parseColor("#52D3FF"),Color.parseColor("#B35CFF"));
                tvWaitReview.setTextColor(Color.parseColor("#262626"));
                tvPublish.setTextColor(Color.parseColor("#262626"));
                break;
            case R.id.tv_all:

                break;
            case R.id.tv_delete:

                break;
        }
    }
}
