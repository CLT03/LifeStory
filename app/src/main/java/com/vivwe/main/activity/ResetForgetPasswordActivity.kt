package com.vivwe.main.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.mbs.sdk.net.HttpRequest
import com.mbs.sdk.net.listener.OnResultListener
import com.mbs.sdk.net.msg.WebMsg
import com.mbs.sdk.utils.StringUtils
import com.vivwe.base.activity.BaseActivity
import com.vivwe.main.R
import com.vivwe.main.api.WebMainApi
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * ahtor: super_link
 * date: 2019/6/6 17:18
 * remark:
 */
class ResetForgetPasswordActivity : BaseActivity() {

    // 账号
    @BindView(R.id.id_account)
    lateinit var accountEdt: EditText
    // 验证码
    @BindView(R.id.edt_code)
    lateinit var codeEdt: EditText
    // 验证码
    @BindView(R.id.tv_code_obtain)
    lateinit var codeTv: TextView
    // 密码
    @BindView(R.id.id_password)
    lateinit var passwordEdt: EditText
    // 密码重复
    @BindView(R.id.id_password_new)
    lateinit var passwordAgainEdt: EditText


    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgetpwd)
        ButterKnife.bind(this)
    }

    /**
     * 修改密码
     */
    @OnClick(R.id.id_ok)
    fun reset() {
        val phone = accountEdt!!.text.toString()

        if (!StringUtils.checkMobileNumber(phone)) {
            com.vivwe.base.ui.alert.Toast.show(this, "请输入正确的手机号码！", 2000)
            return
        }

        val captcha = codeEdt!!.text.toString().trim { it <= ' ' }
        if (captcha.length != 6) {
            com.vivwe.base.ui.alert.Toast.show(this, "请输入6位数字验证码！", 2000)
            return
        }

        val password = passwordEdt!!.text.toString().trim { it <= ' ' }
        if (password.length < 6) {
            com.vivwe.base.ui.alert.Toast.show(this, "密码至少为大于等于6个字符以上！", 2000)
            return
        }

        val passwordRepeat = passwordAgainEdt!!.text.toString().trim { it <= ' ' }

        if (password != passwordRepeat) {
            com.vivwe.base.ui.alert.Toast.show(this, "两次密码输入不一致！", 2000)
            return
        }

        HttpRequest.getInstance().excute(HttpRequest.create(WebMainApi::class.java).resetPwd(phone,
                password, captcha)) { webMsg ->
            if (webMsg.dataIsSuccessed()) {
                com.vivwe.base.ui.alert.Toast.show(this@ResetForgetPasswordActivity, "恭喜重置密码成功！", 3000)

                val intent = Intent()
                intent.putExtra("account", phone)
                this@ResetForgetPasswordActivity.setResult(1, intent)
                this@ResetForgetPasswordActivity.finish()
            } else if (webMsg.netIsSuccessed()) {
                com.vivwe.base.ui.alert.Toast.show(this@ResetForgetPasswordActivity, webMsg.desc, 3000)
            }
        }
    }

    /**
     * 发送验证码计时
     */
    private var sendCodeIsBusying = false
    internal var count = 0

    /**
     * 发送验证码
     */
    @OnClick(R.id.tv_code_obtain)
    fun sendCode() {
        if (count != 0 || sendCodeIsBusying) return

        val phoneNumber = accountEdt!!.text.toString().trim { it <= ' ' }

        if (!StringUtils.checkMobileNumber(phoneNumber)) {
            Toast.makeText(this, "请输入正确的手机号码", Toast.LENGTH_LONG).show()
            return
        }

        sendCodeIsBusying = true
        HttpRequest.getInstance().excute(HttpRequest.create(WebMainApi::class.java).getCaptcha(phoneNumber), object : OnResultListener {
            override fun onWebUiResult(webMsg: WebMsg) {
                sendCodeIsBusying = false

                if (webMsg.dataIsSuccessed()) {
                    countDown()
                } else if (webMsg.netIsSuccessed()) {
                    Toast.makeText(this@ResetForgetPasswordActivity, webMsg.desc, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    /**
     * 验证码获取计时
     */
    fun countDown() {

        count = 60
        codeTv!!.setBackgroundResource(R.drawable.r8_gray_noc)
        Observable.create(ObservableOnSubscribe<Int> { emitter ->
            while (count-- > 0) {
                Thread.sleep(1000)
                emitter.onNext(count)
            }
            emitter.onComplete()
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(object : Observer<Int> {
            override fun onNext(count: Int) {
                codeTv!!.text = count.toString() + "s"
            }

            override fun onSubscribe(d: Disposable) {}


            override fun onError(e: Throwable) {}

            @SuppressLint("ClickableViewAccessibility")
            override fun onComplete() {
                count = 0
                sendCodeIsBusying = false
                codeTv!!.text = "获取验证码"
                codeTv!!.setBackgroundResource(R.drawable.r8_black_btn)
            }
        })
    }

    /**
     * 返回
     */
    @OnClick(R.id.tv_exit)
    fun onBack() {
        finish()
    }
}