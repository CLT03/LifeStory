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
 * remark: 修改个性签名
 */
public class UpdateRemarkActivity extends BaseActivity {

    @BindView(R.id.edt_sign)
    EditText edtSign;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_update_remark);
        ButterKnife.bind(this);
        edtSign.setText(getIntent().getStringExtra("sign"));
    }

    @OnClick({R.id.iv_back, R.id.btn_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_submit:
                Intent intent=new Intent().putExtra("sign",edtSign.getText().toString());
                setResult(6,intent);
                finish();
                break;
        }
    }
}
