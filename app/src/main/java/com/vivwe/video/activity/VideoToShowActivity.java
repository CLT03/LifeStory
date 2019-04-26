package com.vivwe.video.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.vivwe.base.activity.BaseActivity;
import com.vivwe.main.R;

/**
 * ahtor: super_link
 * date: 2019/4/26 15:38
 * remark: 视频展示页
 */
public class VideoToShowActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_show);
    }
}
