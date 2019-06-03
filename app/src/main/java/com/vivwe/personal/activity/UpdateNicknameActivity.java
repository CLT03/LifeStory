package com.vivwe.personal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.vivwe.base.activity.BaseActivity;
import com.vivwe.main.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ahtor: super_link
 * date: 2019/4/26 18:04
 * remark: 修改用户昵称
 */
public class UpdateNicknameActivity extends BaseActivity {

    @BindView(R.id.edt_nickname)
    EditText edtNickname;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_update_nickname);
        ButterKnife.bind(this);
        edtNickname.setText(getIntent().getStringExtra("name"));
    }

    @OnClick({R.id.iv_back, R.id.btn_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_submit:
                Intent intent=new Intent().putExtra("name",edtNickname.getText().toString());
                setResult(2,intent);
                finish();
                break;
        }
    }

}
