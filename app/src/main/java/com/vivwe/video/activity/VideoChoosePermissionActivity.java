package com.vivwe.video.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mbs.sdk.net.HttpRequest;
import com.mbs.sdk.net.listener.OnProgressListener;
import com.mbs.sdk.net.listener.OnResultListener;
import com.mbs.sdk.net.msg.WebMsg;
import com.vivwe.author.activity.DesignerHomeActivity;
import com.vivwe.base.activity.BaseActivity;
import com.vivwe.base.cache.UserCache;
import com.vivwe.base.ui.alert.Toast;
import com.vivwe.base.util.MiscUtil;
import com.vivwe.main.R;
import com.vivwe.video.api.TemplateApi;
import com.vivwe.video.entity.TemplateDetailEntity;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ahtor: super_link
 * date: 2019/5/10 09:38
 * remark: 选择视频权限
 */
public class VideoChoosePermissionActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_permission);
        ButterKnife.bind(this);
        init();
    }

    private void init() {

    }

}
