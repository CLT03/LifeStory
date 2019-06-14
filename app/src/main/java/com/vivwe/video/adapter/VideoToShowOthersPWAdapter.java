package com.vivwe.video.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vivwe.base.ui.alert.adapter.BasePopWindowAdapter;
import com.vivwe.main.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VideoToShowOthersPWAdapter extends BasePopWindowAdapter {

    @BindView(R.id.iv_tread)
    ImageView ivTread;
    @BindView(R.id.iv_collect)
    ImageView ivCollect;
    @BindView(R.id.tv_tread)
    TextView tvTread;
    private View.OnClickListener listener;
    private ViewGroup viewRoot;


    public VideoToShowOthersPWAdapter setOnclickListener(View.OnClickListener listener) {
        this.listener = listener;
        return this;
    }

    @Override
    public View getView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_video_to_show_others_pw, null);
        ButterKnife.bind(this, view);
        return view;
    }

    public ImageView getIvTread() {
        return ivTread;
    }

    public ImageView getIvCollect() {
        return ivCollect;
    }

    public TextView getTvTread() {
        return tvTread;
    }

    @OnClick({R.id.tv_send, R.id.tv_wechat, R.id.tv_qq, R.id.tv_sina, R.id.tv_tread, R.id.tv_collect, R.id.tv_report, R.id.btn_cancel})
    public void onClick(View view) {
        listener.onClick(view);
    }
}
