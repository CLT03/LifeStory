package com.vivwe.author.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mbs.sdk.net.HttpRequest;
import com.mbs.sdk.net.listener.OnResultListener;
import com.mbs.sdk.net.msg.WebMsg;
import com.vivwe.author.adapter.TemplateApprovedPagerAdapter;
import com.vivwe.author.api.AuthorApi;
import com.vivwe.author.fragment.TemplateApprovedFragment;
import com.vivwe.base.activity.BaseActivity;
import com.vivwe.base.ui.textview.LinearGradientTextView;
import com.vivwe.main.R;
import com.vivwe.personal.activity.MyAssetsActivity;
import com.vivwe.personal.api.PersonalApi;

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
    @BindView(R.id.tv_edit)
    TextView tvEdit;
    ArrayList<TemplateApprovedFragment> fragments = new ArrayList<>();
    LinearGradientTextView textViewTag;
    private boolean ifAllChoose;//是否全选


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
                        tvEdit.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        tvWaitReview.performClick();
                        groupEdit.setVisibility(View.GONE);
                        fragments.get(2).templateEdit(false);
                        fragments.get(0).templateEdit(false);
                        tvEdit.setText("编辑");
                        tvEdit.setVisibility(View.GONE);
                        break;
                    case 2:
                        tvNoPass.performClick();
                        tvEdit.setVisibility(View.VISIBLE);
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
                    fragments.get(viewPager.getCurrentItem()).templateEdit(false);
                    tvEdit.setText("编辑");
                } else {
                    groupEdit.setVisibility(View.VISIBLE);
                    fragments.get(viewPager.getCurrentItem()).templateEdit(true);
                    tvEdit.setText("取消");
                }
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
                if (ifAllChoose) {
                    fragments.get(viewPager.getCurrentItem()).allChoose(false);
                    ifAllChoose = false;
                } else {
                    fragments.get(viewPager.getCurrentItem()).allChoose(true);
                    ifAllChoose = true;
                }
                break;
            case R.id.tv_delete:
                delete();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (groupEdit.getVisibility() == View.VISIBLE) {
            groupEdit.setVisibility(View.GONE);
            fragments.get(viewPager.getCurrentItem()).templateEdit(false);
            tvEdit.setText("编辑");
        } else super.onBackPressed();
    }

    private void delete() {
        if (fragments.get(viewPager.getCurrentItem()).getChooseIdList().size() > 0) {
            HttpRequest.getInstance().excute(HttpRequest.create(AuthorApi.class).soldOutTemplates(fragments.get(viewPager.getCurrentItem()).getChooseIdList()), new OnResultListener() {
                @Override
                public void onWebUiResult(WebMsg webMsg) {
                    if (webMsg.dataIsSuccessed()) {
                        com.vivwe.base.ui.alert.Toast.show(TemplateApprovedActivity.this, webMsg.getDesc(), 2000);
                        fragments.get(viewPager.getCurrentItem()).deleteSuccess();
                    } else if (webMsg.netIsSuccessed()) {
                        com.vivwe.base.ui.alert.Toast.show(TemplateApprovedActivity.this, webMsg.getDesc(), 2000);
                    }
                }
            });
        } else Toast.makeText(this, "选择不能为空！", Toast.LENGTH_SHORT).show();
    }

}
