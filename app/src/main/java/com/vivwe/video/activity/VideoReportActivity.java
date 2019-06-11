package com.vivwe.video.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mbs.sdk.net.HttpRequest;
import com.mbs.sdk.net.listener.OnResultListener;
import com.mbs.sdk.net.msg.WebMsg;
import com.vivwe.base.activity.BaseActivity;
import com.vivwe.base.cache.UserCache;
import com.vivwe.base.ui.alert.Toast;
import com.vivwe.main.R;
import com.vivwe.video.api.VideoApi;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ahtor: super_link
 * date: 2019/4/26 15:38
 * remark: 举报与反馈
 */
public class VideoReportActivity extends BaseActivity {

    @BindView(R.id.editText)
    EditText editText;
    @BindViews({R.id.iv_illegal, R.id.iv_yellow, R.id.iv_gambling, R.id.iv_violence, R.id.iv_tort, R.id.iv_rumor, R.id.iv_other})
    ImageView[] ivReportTypes;
    @BindView(R.id.tv_number)
    TextView tvNumber;

    private int reportType = 1;
    private ImageView preChooseIv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_report);
        ButterKnife.bind(this);
        init();
    }

    private void init(){
        preChooseIv = ivReportTypes[0];
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvNumber.setText(editText.getText().toString().length() + "/200");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick({R.id.iv_back, R.id.tv_submit, R.id.iv_illegal, R.id.iv_yellow, R.id.iv_gambling, R.id.iv_violence, R.id.iv_tort, R.id.iv_rumor, R.id.iv_other})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_submit:
                addReport();
                break;
            case R.id.iv_illegal:
                changeType(1);
                break;
            case R.id.iv_yellow:
                changeType(2);
                break;
            case R.id.iv_gambling:
                changeType(3);
                break;
            case R.id.iv_violence:
                changeType(4);
                break;
            case R.id.iv_tort:
                changeType(5);
                break;
            case R.id.iv_rumor:
                changeType(6);
                break;
            case R.id.iv_other:
                changeType(7);
                break;
        }
    }

    private void changeType(int index) {
        if (preChooseIv != ivReportTypes[index - 1]) {
            preChooseIv.setImageDrawable(getResources().getDrawable(R.mipmap.icon_report_check));
            ivReportTypes[index - 1].setImageDrawable(getResources().getDrawable(R.mipmap.icon_report_checked));
            preChooseIv=ivReportTypes[index-1];
        }
        reportType = index;
    }

    private void addReport() {
        if(editText.getText().toString().length()<4){
            android.widget.Toast.makeText(this, "描述字数不能少于四个", Toast.LENGTH_SHORT).show();
            return;
        }
        HttpRequest.getInstance().excute(HttpRequest.create(VideoApi.class).addReport(null, null,
                3, UserCache.Companion.getUserInfo().getId(), reportType, editText.getText().toString(), getIntent().getIntExtra("userId", 0)
        ), new OnResultListener() {
            @Override
            public void onWebUiResult(WebMsg webMsg) {
                if (webMsg.dataIsSuccessed()) {
                    Toast.show(VideoReportActivity.this, webMsg.getDesc(), 2000);
                    finish();
                } else if (webMsg.netIsSuccessed()) {
                    Toast.show(VideoReportActivity.this, webMsg.getDesc(), 2000);
                }
            }
        });
    }
}
