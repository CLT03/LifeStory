package com.vivwe.main.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import com.mbs.sdk.net.HttpRequest
import com.mbs.sdk.utils.StringUtils
import com.vivwe.base.activity.BaseActivity
import com.vivwe.base.ui.alert.Toast
import com.vivwe.main.R
import com.vivwe.main.api.WebMainApi
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * ahtor: super_link
 * date: 2019/4/23 13:48
 * remark: 用户注册
 */
class RegisterActivity : BaseActivity() {

    /** 账号  */
    @BindView(R.id.id_account)
    internal var accountEdt: EditText? = null
    /** 验证码  */
    @BindView(R.id.edt_code)
    internal var codeEdt: EditText? = null
    /** 密码  */
    @BindView(R.id.edt_password)
    internal var passwordEdt: EditText? = null
    /** 重复密码  */
    @BindView(R.id.edt_password_repeat)
    internal var passwordRepeatEdt: EditText? = null
    /** 获取验证码按钮  */
    @BindView(R.id.tv_code_obtain)
    internal var codeObtainTv: TextView? = null

    /**
     * 发送验证码计时
     */
    private var sendCodeIsBusying = false
    internal var count = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        ButterKnife.bind(this)
    }

    @OnClick(R.id.tv_login, R.id.tv_exit)
    fun toLogin() {
        finish()
    }

    /**
     * 获取验证码
     */
    @OnClick(R.id.tv_code_obtain)
    fun obtainCode() {
        if (count != 0 || sendCodeIsBusying) return

        val phoneNumber = accountEdt!!.text.toString().trim { it <= ' ' }

        if (!StringUtils.checkMobileNumber(phoneNumber)) {
            Toast.show(this, "请输入正确的手机号码", 2000)
            return
        }

        sendCodeIsBusying = true
        HttpRequest.getInstance().excute(HttpRequest.create(WebMainApi::class.java).getCaptcha(phoneNumber)) { webMsg ->
            sendCodeIsBusying = false

            if (webMsg.dataIsSuccessed()) {
                countDown()
            } else if (webMsg.netIsSuccessed()) {
                Toast.show(this@RegisterActivity, webMsg.desc, 2000)
            }
        }
    }

    fun countDown() {

        count = 60
        codeObtainTv!!.setBackgroundResource(R.drawable.r8_gray_noc)
        Observable.create(ObservableOnSubscribe<Int> { emitter ->
            while (count-- > 0) {
                Thread.sleep(1000)
                emitter.onNext(count)
            }
            emitter.onComplete()
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(object : Observer<Int> {
            override fun onNext(count: Int) {
                codeObtainTv!!.text = count.toString() + "s"
            }

            override fun onSubscribe(d: Disposable) {}

//            override fun onNext(count: Int?) {
//                codeObtainTv!!.text = count!!.toString() + "s"
//            }

            override fun onError(e: Throwable) {}

            @SuppressLint("ClickableViewAccessibility")
            override fun onComplete() {
                count = 0
                sendCodeIsBusying = false
                codeObtainTv!!.text = "获取验证码"
                codeObtainTv!!.setBackgroundResource(R.drawable.r8_black_btn)
            }
        })
    }

    /**
     * 注册账号
     */
    @OnClick(R.id.tv_register)
    fun register() {

        val phone = accountEdt!!.text.toString()

        if (!StringUtils.checkMobileNumber(phone)) {
            Toast.show(this, "请输入正确的手机号码！", 2000)
            return
        }

        val captcha = codeEdt!!.text.toString().trim { it <= ' ' }
        if (captcha.length != 6) {
            Toast.show(this, "请输入6位数字验证码！", 2000)
            return
        }

        val password = passwordEdt!!.text.toString().trim { it <= ' ' }
        if (password.length < 6) {
            Toast.show(this, "密码至少为大于等于6个字符以上！", 2000)
            return
        }

        val passwordRepeat = passwordRepeatEdt!!.text.toString().trim { it <= ' ' }

        if (password != passwordRepeat) {
            Toast.show(this, "两次密码输入不一致！", 2000)
            return
        }

        HttpRequest.getInstance().excute(HttpRequest.create(WebMainApi::class.java).register(phone,
                password, passwordRepeat, captcha)) { webMsg ->
            if (webMsg.dataIsSuccessed()) {
                Toast.show(this@RegisterActivity, "恭喜注册成功！", 3000)

                val intent = Intent()
                intent.putExtra("account", phone)
                this@RegisterActivity.setResult(1, intent)
                this@RegisterActivity.finish()
            } else if (webMsg.netIsSuccessed()) {
                Toast.show(this@RegisterActivity, webMsg.desc, 3000)
            }
        }

    }
}
