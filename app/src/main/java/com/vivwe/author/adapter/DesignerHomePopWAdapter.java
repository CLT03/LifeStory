package com.vivwe.author.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vivwe.base.ui.alert.adapter.BasePopWindowAdapter;
import com.vivwe.main.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class DesignerHomePopWAdapter extends BasePopWindowAdapter {

    private View.OnClickListener listener;
    private ViewGroup viewRoot;


    public BasePopWindowAdapter setOnclickListener(View.OnClickListener listener) {
        this.listener = listener;
        return this;
    }

    @Override
    public View getView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_author_designer_home_pw, null);
        ButterKnife.bind(this, view);
        return view;
    }


    @OnClick({R.id.tv_report, R.id.btn_cancel})
    public void onClick(View view) {
        listener.onClick(view);
    }
}
