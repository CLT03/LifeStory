package com.vivwe.video.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.vivwe.base.activity.BaseActivity;
import com.vivwe.main.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ahtor: super_link
 * date: 2019/4/26 15:38
 * remark: 发布成功
 */
public class VideoPulishedActivity extends BaseActivity {

    @BindView(R.id.tv_had_save)
    TextView tvHadSave;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_published);
        ButterKnife.bind(this);
        getWindow().setStatusBarColor(Color.parseColor("#040404"));
    }

    @OnClick({R.id.iv_back, R.id.tv_complete, R.id.tv_share})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_complete:

                break;
            case R.id.tv_share:

                break;
        }
    }
}
