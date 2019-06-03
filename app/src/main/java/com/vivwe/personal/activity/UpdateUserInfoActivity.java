package com.vivwe.personal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mbs.sdk.net.HttpRequest;
import com.mbs.sdk.net.listener.OnResultListener;
import com.mbs.sdk.net.msg.WebMsg;
import com.vivwe.base.activity.BaseActivity;
import com.vivwe.base.ui.alert.Toast;
import com.vivwe.main.R;
import com.vivwe.main.entity.UcenterInfoEntity;
import com.vivwe.personal.api.PersonalApi;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ahtor: super_link
 * date: 2019/4/26 18:04
 * remark: 个人信息设置
 */
public class UpdateUserInfoActivity extends BaseActivity {

    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_gender)
    TextView tvGender;
    @BindView(R.id.tv_birthday)
    TextView tvBirthday;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.tv_sign)
    TextView tvSign;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_update_userinfo);
        ButterKnife.bind(this);
        getData();
    }

    @OnClick({R.id.iv_back, R.id.iv_camera, R.id.tv_name, R.id.tv_gender, R.id.tv_birthday, R.id.tv_city, R.id.tv_sign,R.id.tv_save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_camera:
                break;
            case R.id.tv_name:
                startActivityForResult(new Intent(this, UpdateNicknameActivity.class)
                        .putExtra("name",tvName.getText().toString()), 1);
                break;
            case R.id.tv_gender:
                break;
            case R.id.tv_birthday:
                break;
            case R.id.tv_city:
                startActivity(new Intent(this, UpdateCityActivity.class));
                break;
            case R.id.tv_sign:
                startActivityForResult(new Intent(this, UpdateRemarkActivity.class)
                        .putExtra("sign",tvSign.getText().toString()), 1);
                break;
            case R.id.tv_save:
                update();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode){
            case 2:
                tvName.setText(data.getStringExtra("name"));
                break;
            case 3:

                break;
            case 4:

                break;
            case 5:

                break;
            case 6:
                tvSign.setText(data.getStringExtra("sign"));
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private UcenterInfoEntity userInfoEntity;

    private void getData(){
        HttpRequest.getInstance().excute(HttpRequest.create(PersonalApi.class).getUserInfo(), new OnResultListener() {
            @Override
            public void onWebUiResult(WebMsg webMsg) {
                if (webMsg.dataIsSuccessed()) {
                    userInfoEntity = webMsg.getData(UcenterInfoEntity.class);
                    tvName.setText(userInfoEntity.getNickname());
                    tvGender.setText(userInfoEntity.getGender().equals("1.0")?"男":"女");
                    tvBirthday.setText(userInfoEntity.getBirthday());
                    tvCity.setText(userInfoEntity.getAddress());
                    tvSign.setText(userInfoEntity.getSignature());
                } else if (webMsg.netIsSuccessed()) {
                    Toast.show(UpdateUserInfoActivity.this, webMsg.getDesc(), 2000);
                }
            }
        });
    }

    private void update(){
        HttpRequest.getInstance().excute(HttpRequest.create(PersonalApi.class).updateUserInfo(String.valueOf(userInfoEntity.getId()),
                userInfoEntity.getAvatar(),
                tvName.getText().toString(),
                tvGender.getText().toString().equals("男")?"1":"2",
                tvBirthday.getText().toString(),
                tvCity.getText().toString(),
                tvSign.getText().toString()), new OnResultListener() {
            @Override
            public void onWebUiResult(WebMsg webMsg) {
                Toast.show(UpdateUserInfoActivity.this, webMsg.getDesc(), 2000);
            }
        });
    }


}
