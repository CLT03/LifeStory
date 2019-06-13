package com.vivwe.main.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.mbs.sdk.net.HttpRequest
import com.mbs.sdk.utils.StringUtils
import com.vivwe.base.activity.BaseActivity
import com.vivwe.base.cache.UserCache
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
 * date: 2019/6/13 11:22
 * remark:
 */
class ResetPasswordActivity : BaseActivity() {

    /** 手机号码显示 */
    @BindView(R.id.tv_phone)
    lateinit var phoneTv: TextView
    /** 验证码输入 */
    @BindView(R.id.edt_verification)
    lateinit var verificationCodeEdt: EditText
    /** 新密码输入 */
    @BindView(R.id.edt_password)
    lateinit var passwordEdt: EditText
    /** 获取验证码按钮 */
    @BindView(R.id.btn_get_verification)
    lateinit var verificationBtn: Button
    /** 提交按钮 */
    @BindView(R.id.btn_submit)
    lateinit var submitBtn: Button

    var phone:String = ""
    /** 发送验证码计时 */
    private var sendCodeIsBusying = false
    internal var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_reset)
        ButterKnife.bind(this)

        init()
    }

    fun init(){
        var phoneStart = UserCache.getUserToken()!!.account.substring(0, 3)
        var phoneEnd = UserCache.getUserToken()!!.account.substring(7, 11)

        phone = UserCache.getUserToken()!!.account
        phoneTv.text = phoneStart + "****" + phoneEnd
    }

    /** 发送短信验证码 */
    @OnClick(R.id.btn_get_verification)
    fun sendVerificationCode(){
        if (count != 0 || sendCodeIsBusying) return

        if (!StringUtils.checkMobileNumber(phone)) {
            Toast.makeText(this@ResetPasswordActivity, "请输入正确的手机号码", Toast.LENGTH_LONG).show()
            return
        }

        sendCodeIsBusying = true
        HttpRequest.getInstance().excute(HttpRequest.create(WebMainApi::class.java).getCaptcha(phone)) { webMsg ->
            sendCodeIsBusying = false

            if (webMsg.dataIsSuccessed()) {
                countDown()
            } else if (webMsg.netIsSuccessed()) {
                Toast.makeText(this@ResetPasswordActivity, webMsg.desc, Toast.LENGTH_LONG).show()
            }
        }
    }

    var bindIsBusying = false
    /**
     * 修改密码
     */
    @OnClick(R.id.btn_submit)
    fun resetPassword(){
        if(bindIsBusying) return

        val newPassword = passwordEdt!!.text.toString().trim()

        if (newPassword.length < 6) {
            com.vivwe.base.ui.alert.Toast.show(this, "密码至少为6个以上有效字符！", 2000)
            return
        }

        val captcha = verificationCodeEdt!!.text.toString().trim { it <= ' ' }
        if (captcha.length != 6) {
            com.vivwe.base.ui.alert.Toast.show(this, "请输入6位数字验证码！", 2000)
            return
        }

        bindIsBusying = true
        submitBtn.text = "正在提交修改..."
        HttpRequest.getInstance().excute(HttpRequest.create(WebMainApi::class.java).resetPwd(phone,
                newPassword, captcha)) { webMsg ->
            submitBtn.text = "提交"
            bindIsBusying = false
            if (webMsg.dataIsSuccessed()) {
                Toast.makeText(this@ResetPasswordActivity, "恭喜，密码修改成功！", Toast.LENGTH_LONG).show()
                UserCache.loginOut(this)

            } else if (webMsg.netIsSuccessed()) {
                Toast.makeText(this@ResetPasswordActivity, webMsg.desc, Toast.LENGTH_LONG).show()
            }
        }
    }

    /** 退出界面 */
    @OnClick(R.id.iv_back)
    fun onBack(){
        finish()
    }

    fun countDown() {

        count = 60
        verificationBtn!!.setBackgroundResource(R.drawable.r8_gray_noc)
        Observable.create(ObservableOnSubscribe<Int> { emitter ->
            while (count-- > 0) {
                Thread.sleep(1000)
                emitter.onNext(count)
            }
            emitter.onComplete()
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(object : Observer<Int> {
            override fun onNext(count: Int) {
                verificationBtn!!.text = count.toString() + "s"
            }

            override fun onSubscribe(d: Disposable) {}

            override fun onError(e: Throwable) {}

            @SuppressLint("ClickableViewAccessibility")
            override fun onComplete() {
                count = 0
                sendCodeIsBusying = false
                verificationBtn!!.text = "获取验证码"
                verificationBtn!!.setBackgroundResource(R.drawable.r8_black_btn)
            }
        })
    }
}