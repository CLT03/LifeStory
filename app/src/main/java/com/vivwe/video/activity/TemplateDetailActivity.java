package com.vivwe.video.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.vivwe.author.activity.DesignerHomeActivity;
import com.vivwe.base.activity.BaseActivity;
import com.vivwe.main.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ahtor: super_link
 * date: 2019/5/10 09:38
 * remark: 模板详情（3.1）
 */
public class TemplateDetailActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_download)
    TextView tvDownload;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.btn_buy)
    Button btnBuy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_template_detail);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_back, R.id.iv_star, R.id.iv_share, R.id.iv_head, R.id.btn_buy})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_star:
                startActivity(new Intent(this,MusicLibraryActivity.class));
                break;
            case R.id.iv_share:

                break;
            case R.id.iv_head:
                startActivity(new Intent(this,DesignerHomeActivity.class));
                break;
            case R.id.btn_buy:

                break;
        }
    }
}
