package com.vivwe.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;

import com.mbs.sdk.net.HttpRequest;
import com.vivwe.base.activity.BaseActivity;
import com.vivwe.base.ui.alert.Toast;
import com.vivwe.main.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ahtor: super_link
 * date: 2019/4/23 13:48
 * remark: 用户登录
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.edit_username)
    EditText usernameEdt;

    @BindView(R.id.edt_password)
    EditText passwordEdt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.tv_register)
    public void toRegister(){
        Intent intent = new Intent();
        intent.setClass(this, RegisterActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        this.startActivity(intent);
    }

    /**
     * 登录
     */
    @OnClick(R.id.tv_login)
    public void login(){

        String username = usernameEdt.getText().toString().trim();
        String password = passwordEdt.getText().toString().trim();

        if(username.length() != 11){
            Toast.show(this, "请输入正确的手机号码！", 2000);
            return;
        }

        if(password.length() < 6){
            Toast.show(this, "请输入正确的密码！", 2000);
            return;
        }

//        Toast.show(this, "登录成功！", 2000);
//        HttpRequest.getInstance().excute(HttpRequest.create());
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        this.startActivity(intent);
        this.finish();
    }
}
