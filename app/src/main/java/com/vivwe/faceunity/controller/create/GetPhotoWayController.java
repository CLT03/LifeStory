package com.vivwe.faceunity.controller.create;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import com.vivwe.faceunity.fragment.CreateAvatarFragment;
import com.vivwe.faceunity.constant.CreateAvatarTypeEnum;
import com.vivwe.faceunity.controller.listener.OnCreateAvatarListener;
import com.vivwe.main.R;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GetPhotoWayController {

    private Context context;
    private OnCreateAvatarListener listener;

    public GetPhotoWayController(Context context, OnCreateAvatarListener listener) {
        this.context = context;
        this.listener = listener;
    }

    /***
     * 展示准备拍照页面
     * @param view
     */
    public void show(RelativeLayout view) {
        view.removeAllViews();
        View v = LayoutInflater.from(context).inflate(R.layout.item_faceunity_newmodel_way, view);
        ButterKnife.bind(this, v);
    }

    @OnClick({R.id.iv_cancel, R.id.btn_takephoto, R.id.btn_take_photo})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_cancel:
                listener.onTakePhotoReadyListener(null);
                break;
            case R.id.btn_takephoto:
                listener.onTakePhotoReadyListener(CreateAvatarTypeEnum.CAMARA);
                break;
            case R.id.btn_take_photo:
                listener.onTakePhotoReadyListener(CreateAvatarTypeEnum.FILE);
                break;
        }
    }
}

