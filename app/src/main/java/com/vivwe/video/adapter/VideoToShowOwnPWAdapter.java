package com.vivwe.video.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.vivwe.base.ui.alert.adapter.BasePopWindowAdapter;
import com.vivwe.main.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VideoToShowOwnPWAdapter extends BasePopWindowAdapter {

    @BindView(R.id.iv_collect)
    ImageView ivCollect;
    private View.OnClickListener listener;
    private ViewGroup viewRoot;


    public VideoToShowOwnPWAdapter setOnclickListener(View.OnClickListener listener) {
        this.listener = listener;
        return this;
    }

    @Override
    public View getView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_video_to_show_own_pw, null);
        ButterKnife.bind(this, view);
        return view;
    }

    public ImageView getIvCollect() {
        return ivCollect;
    }

    @OnClick({R.id.tv_send, R.id.tv_wechat, R.id.tv_qq, R.id.tv_sina, R.id.tv_save, R.id.tv_collect, R.id.iv_collect, R.id.tv_del, R.id.btn_cancel})
    public void onClick(View view) {
        listener.onClick(view);
    }
}
