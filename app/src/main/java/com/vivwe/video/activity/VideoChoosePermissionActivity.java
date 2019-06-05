package com.vivwe.video.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.vivwe.base.activity.BaseActivity;
import com.vivwe.main.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ahtor: super_link
 * date: 2019/5/10 09:38
 * remark: 选择视频权限
 */
public class VideoChoosePermissionActivity extends BaseActivity {


    @BindView(R.id.iv_open)
    ImageView ivOpen;
    @BindView(R.id.iv_friend)
    ImageView ivFriend;
    @BindView(R.id.iv_private)
    ImageView ivPrivate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_permission);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.iv_back, R.id.tv_sure, R.id.tv_open, R.id.tv_friend, R.id.tv_private})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_sure:

                break;
            case R.id.tv_open:
                ivFriend.setVisibility(View.GONE);
                ivPrivate.setVisibility(View.GONE);
                ivOpen.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_friend:
                ivOpen.setVisibility(View.GONE);
                ivPrivate.setVisibility(View.GONE);
                ivFriend.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_private:
                ivFriend.setVisibility(View.GONE);
                ivOpen.setVisibility(View.GONE);
                ivPrivate.setVisibility(View.VISIBLE);
                break;
        }
    }
}
