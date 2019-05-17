package com.vivwe.main.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.TextView;
import com.mbs.sdk.net.HttpRequest;
import com.mbs.sdk.net.listener.OnResultListener;
import com.mbs.sdk.net.msg.WebMsg;
import com.mbs.sdk.utils.StringUtils;
import com.vivwe.base.activity.BaseActivity;
import com.vivwe.base.ui.alert.Toast;
import com.vivwe.main.R;
import com.vivwe.main.api.WebMainApi;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * ahtor: super_link
 * date: 2019/4/23 13:48
 * remark: 用户注册
 */
public class RegisterActivity extends BaseActivity {

    /** 账号 */
    @BindView(R.id.id_account)
    EditText accountEdt;
    /** 验证码 */
    @BindView(R.id.edt_code)
    EditText codeEdt;
    /** 密码 */
    @BindView(R.id.edt_password)
    EditText passwordEdt;
    /** 重复密码 */
    @BindView(R.id.edt_password_repeat)
    EditText passwordRepeatEdt;
    /** 获取验证码按钮 */
    @BindView(R.id.tv_code_obtain)
    TextView codeObtainTv;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_login, R.id.tv_exit})
    public void toLogin(){
        finish();
    }

    /**
     * 获取验证码
     */
    @OnClick(R.id.tv_code_obtain)
    public void obtainCode(){
        if (count != 0 || sendCodeIsBusying) return;

        String phoneNumber = accountEdt.getText().toString().trim();

        if(!StringUtils.checkMobileNumber(phoneNumber)){
            Toast.show(this, "请输入正确的手机号码", 2000);
            return;
        }

        sendCodeIsBusying = true;
        HttpRequest.getInstance().excute(HttpRequest.create(WebMainApi.class).getCaptcha(phoneNumber), new OnResultListener() {
            @Override
            public void onWebUiResult(WebMsg webMsg) {

                sendCodeIsBusying = false;

                if(webMsg.isSuccessed()){
                    countDown();
                }
            }
        });
    }

    /**
     * 发送验证码计时
     */
    private boolean sendCodeIsBusying = false;
    int count = 0;
    public void countDown() {

        count = 60;
        codeObtainTv.setBackgroundResource(R.drawable.r8_gray_noc);
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                while (count-- > 0) {
                    Thread.sleep(1000);
                    emitter.onNext(count);
                }
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(Integer count) {
                codeObtainTv.setText(String.valueOf(count + "s"));
            }

            @Override
            public void onError(Throwable e) {
            }

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void onComplete() {
                sendCodeIsBusying = false;
                codeObtainTv.setText("获取验证码");
                codeObtainTv.setBackgroundResource(R.drawable.r8_black_btn);
            }
        });
    }

    /**
     * 注册账号
     */
    @OnClick(R.id.tv_register)
    public void register(){

        String phone = accountEdt.getText().toString();

        if(StringUtils.checkMobileNumber(phone)){
            Toast.show(this, "请输入正确的手机号码！", 2000);
            return;
        }

        String captcha = codeEdt.getText().toString().trim();
        if(captcha.length() != 6){
            Toast.show(this, "请输入6位数字验证码！", 2000);
            return;
        }

        String password = passwordEdt.getText().toString().trim();
        if(password.length() < 6){
            Toast.show(this, "密码至少为大于等于6个字符以上！", 2000);
            return;
        }

        String passwordRepeat = passwordRepeatEdt.getText().toString().trim();

        if(!password.equals(passwordRepeat)){
            Toast.show(this, "两次密码输入不一致！", 2000);
            return;
        }

        HttpRequest.getInstance().excute(HttpRequest.create(WebMainApi.class).register(phone,
                password, passwordRepeat, captcha), new OnResultListener() {
            @Override
            public void onWebUiResult(WebMsg webMsg) {
                if(webMsg.isSuccessed()){
//                    Toast.show(this, "");
                }
            }
        });

    }
}
