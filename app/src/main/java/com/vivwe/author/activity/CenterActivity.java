package com.vivwe.author.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mbs.sdk.net.HttpRequest;
import com.mbs.sdk.net.listener.OnResultListener;
import com.mbs.sdk.net.msg.WebMsg;
import com.mbs.sdk.utils.ScreenUtils;
import com.vivwe.author.api.AuthorApi;
import com.vivwe.author.entity.DesignrCenterEntity;
import com.vivwe.base.activity.BaseActivity;
import com.vivwe.base.constant.Globals;
import com.vivwe.base.ui.alert.Toast;
import com.vivwe.main.R;
import com.vivwe.main.entity.UcenterInfoEntity;
import com.vivwe.personal.activity.UpdateUserInfoActivity;
import com.vivwe.personal.api.PersonalApi;

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
    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_theme)
    TextView tvTheme;
    @BindView(R.id.tv_make)
    TextView tvMake;
    @BindView(R.id.tv_had_publish)
    TextView tvHadPublish;
    @BindView(R.id.tv_wait_review)
    TextView tvWaitReview;
    @BindView(R.id.tv_no_pass)
    TextView tvNoPass;
    @BindView(R.id.tv_month_earning)
    TextView tvMonthEarning;
    @BindView(R.id.tv_month_sales_volume)
    TextView tvMonthSalesVolume;
    @BindView(R.id.tv_collection)
    TextView tvCollection;
    @BindView(R.id.tv_annual_income)
    TextView tvAnnualIncome;
    @BindView(R.id.tv_year_sales_volume)
    TextView tvYearSalesVolume;
    @BindView(R.id.tv_total_income)
    TextView tvTotalIncome;
    @BindView(R.id.tv_month_browsing_volume)
    TextView tvMonthBrowsingVolume;
    @BindView(R.id.tv_year_browsing_volume)
    TextView tvYearBrowsingVolume;
    @BindView(R.id.tv_total_browsing_volume)
    TextView tvTotalBrowsingVolume;
    private RequestOptions requestOptions;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_center);
        ButterKnife.bind(this);
        ViewGroup.LayoutParams layoutParams = tvTitle.getLayoutParams();
        layoutParams.height = ScreenUtils.getStatusHeight(CenterActivity.this) + getResources().getDimensionPixelOffset(R.dimen.x360);
        tvTitle.setLayoutParams(layoutParams);
        tvTitle.setPadding(0, ScreenUtils.getStatusHeight(CenterActivity.this), 0, getResources().getDimensionPixelOffset(R.dimen.x272));
        requestOptions = new RequestOptions().circleCrop()
                .placeholder(getResources().getDrawable(R.drawable.ic_launcher_background));
        getData();
    }

    private void getData(){
        HttpRequest.getInstance().excute(HttpRequest.create(AuthorApi.class).getUserOriderInfo(), new OnResultListener() {
            @Override
            public void onWebUiResult(WebMsg webMsg) {
                if (webMsg.dataIsSuccessed()) {
                    DesignrCenterEntity designrCenterEntity = webMsg.getData(DesignrCenterEntity.class);
                    Glide.with(CenterActivity.this).load(Globals.URL_QINIU+designrCenterEntity.getAvatar()).apply(requestOptions).into(ivHead);
                    tvName.setText(designrCenterEntity.getNickname());
                    tvTheme.setText("主题 "+designrCenterEntity.getTopical());
                    tvMake.setText("制作 "+designrCenterEntity.getProductionNumber());
                    tvHadPublish.setText(String.valueOf(designrCenterEntity.getPublishNumber()));
                    tvWaitReview.setText(String.valueOf(designrCenterEntity.getAuditNumber()));
                    tvNoPass.setText(String.valueOf(designrCenterEntity.getNotPassNumber()));
                    tvMonthEarning.setText(String.valueOf(designrCenterEntity.getMouthInCome()/100));
                    tvMonthSalesVolume.setText(String.valueOf(designrCenterEntity.getMouthSales()));
                    tvCollection.setText(String.valueOf(designrCenterEntity.getSartNumber()));
                    tvAnnualIncome.setText(String.valueOf(designrCenterEntity.getYearInCome()/100));
                    tvYearSalesVolume.setText(String.valueOf(designrCenterEntity.getYearSales()));
                    tvTotalIncome.setText(String.valueOf(designrCenterEntity.getTotalInCome()/100
                    ));
                    tvMonthBrowsingVolume.setText(String.valueOf(designrCenterEntity.getMouthClickCount()));
                    tvYearBrowsingVolume.setText(String.valueOf(designrCenterEntity.getYearClickCount()));
                    tvTotalBrowsingVolume.setText(String.valueOf(designrCenterEntity.getTotalClickCount()));
                } else if (webMsg.netIsSuccessed()) {
                    Toast.show(CenterActivity.this, webMsg.getDesc(), 2000);
                }
            }
        });
    }


    @OnClick({R.id.iv_back, R.id.tv_month_earning, R.id.tv_had_publish, R.id.tv_wait_review, R.id.tv_no_pass})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_month_earning:
                startActivity(new Intent(this, IncomeActivity.class));
                break;
            case R.id.tv_had_publish:
                startActivity(new Intent(this, TemplateApprovedActivity.class).putExtra("tag", 0));
                break;
            case R.id.tv_wait_review:
                startActivity(new Intent(this, TemplateApprovedActivity.class).putExtra("tag", 1));
                break;
            case R.id.tv_no_pass:
                startActivity(new Intent(this, TemplateApprovedActivity.class).putExtra("tag", 2));
                break;
        }
    }
}
