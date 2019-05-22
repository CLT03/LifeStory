package com.vivwe.video.activity

import android.os.Bundle
import butterknife.ButterKnife
import butterknife.OnClick
import com.vivwe.base.activity.BaseActivity
import com.vivwe.main.R

/**
 * ahtor: super_link
 * date: 2019/5/21 11:59
 * remark: 制作动态视频
 */
class VideoCreateActivity:BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_create)

        ButterKnife.bind(this)
    }

    @OnClick(R.id.tv_back)
    fun onBack() {
        finish()
    }
}