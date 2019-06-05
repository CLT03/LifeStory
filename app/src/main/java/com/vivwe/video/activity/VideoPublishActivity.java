package com.vivwe.video.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.vivwe.base.activity.BaseActivity;
import com.vivwe.main.R;
import com.vivwe.video.adapter.VideoChooseTypeAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ahtor: super_link
 * date: 2019/4/26 15:38
 * remark: 发布视频
 */
public class VideoPublishActivity extends BaseActivity {

    @BindView(R.id.edt_describe)
    EditText edtDescribe;
    @BindView(R.id.iv_cover)
    ImageView ivCover;
    @BindView(R.id.tv_private)
    TextView tvPrivate;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.iv_save)
    ImageView ivSave;
    private boolean mSaveLocal;//是否保存到本地

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_publish);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_back, R.id.iv_play, R.id.tv_change_cover, R.id.tv_private, R.id.tv_type ,R.id.iv_save,R.id.btn_publish})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_play:
                
                break;
            case R.id.tv_change_cover:

                break;
            case R.id.tv_private:
                startActivityForResult(new Intent(this,VideoChoosePermissionActivity.class),1);
                break;
            case R.id.tv_type:
                startActivityForResult(new Intent(this,VideoChooseTypeActivity.class),2);
                break;
            case R.id.iv_save:
                if(mSaveLocal){
                    mSaveLocal=false;
                    ivSave.setImageDrawable(getResources().getDrawable(R.mipmap.icon_attention_del));
                }else{
                    mSaveLocal=true;
                    ivSave.setImageDrawable(getResources().getDrawable(R.mipmap.icon_checked));
                }
                break;
            case R.id.btn_publish:

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==1 && resultCode==1){

        }
        if(requestCode==2 && resultCode==2){

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
