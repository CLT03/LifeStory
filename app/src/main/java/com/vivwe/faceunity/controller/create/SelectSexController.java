package com.vivwe.faceunity.controller.create;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import com.vivwe.faceunity.constant.SexEnum;
import com.vivwe.faceunity.controller.listener.OnCreateAvatarListener;
import com.vivwe.main.R;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 选择性别
 */
public class SelectSexController {

    private Context context;
    private OnCreateAvatarListener listener;


    public SelectSexController(Context context, OnCreateAvatarListener listener) {
        this.context = context;
        this.listener = listener;
    }

    /***
     * 展示界面
     * @param view
     */
    public void show(RelativeLayout view){
        view.removeAllViews();
        View v = LayoutInflater.from(context).inflate(R.layout.item_faceunity_newmodel_sex, view);
        ButterKnife.bind(this, v);
    }

    @OnClick(R.id.iv_cancel)
    public void onBack(){
        listener.onFinished();
    }

    @OnClick({R.id.iv_cancel, R.id.btn_male, R.id.btn_female})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_male: // 男
                listener.onSexResult(SexEnum.MALE.getIndex());
                break;
            case R.id.btn_female: // 女
                listener.onSexResult(SexEnum.FEMALE.getIndex());
                break;
            case R.id.iv_cancel:
                listener.onFinished();
                break;
        }
    }

}
