package com.vivwe.main.activity

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.EditText
import com.vivwe.base.ui.alert.Toast
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.mbs.sdk.utils.StringUtils
import com.vivwe.base.activity.BaseActivity
import com.vivwe.main.R
import com.vivwe.base.ui.alert.Loading
import com.vivwe.base.cache.UserCache
import com.mbs.sdk.net.HttpRequest
import com.vivwe.base.entity.UserToken
import com.mbs.sdk.net.listener.OnResultListener
import com.vivwe.main.api.WebMainApi
import com.vivwe.main.entity.UserInfoEntity
import com.vivwe.main.api.WebUserInfoApi





/**
 * ahtor: super_link
 * date: 2019/5/20 09:56
 * remark:
 */
class LoginActivity:BaseActivity() {

    @BindView(R.id.edit_username)
    var accountEdt:EditText? = null

    @BindView(R.id.edt_password)
    var passwordEdt: EditText? = null



    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_login)
        ButterKnife.bind(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 1 && resultCode == 1){ // 注册返回结果
            var account = data!!.getStringExtra("account")
            accountEdt!!.setText(account)
            passwordEdt!!.setText("")
            passwordEdt!!.requestFocus()
        }
    }

    @OnClick(R.id.tv_register)
    fun toRegister(){
        var intent = Intent()
        intent.setClass(this, RegisterActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        this.startActivityForResult(intent, 1)
    }

    var loginIsBusying = false

    @OnClick(R.id.tv_login)
    fun login(){
        if(loginIsBusying ) return

        var account: String = accountEdt!!.text.toString().trim()
        val password: String = passwordEdt!!.text.toString().trim()

        if(!StringUtils.checkMobileNumber(account)) {
            Toast.show(this, "请输入正确的手机号码！", 3000);
            return;
        }

        if(password.length < 6){
            Toast.show(this, "请输入正确的密码！", 3000);
            return;
        }

        loginIsBusying = true
        Loading.start(this, "正在登录中...", false)

        HttpRequest.getInstance().excute(HttpRequest.create(WebMainApi::class.java).login(account, password), OnResultListener { webMsg ->
            loginIsBusying = false

            if (webMsg.dataIsSuccessed()) {
                val userToken = webMsg.getData(UserToken::class.java)
                userToken.password = password
                UserCache.setUserToken(userToken)
                getWebUserInfo()
                return@OnResultListener
            } else if (webMsg.netIsSuccessed()) {
                Toast.show(this@LoginActivity, webMsg.desc, 2000)
            }
            Loading.stop()
        })
    }

    /**
     * 获取用户信息
     */
    fun getWebUserInfo() {
        if (loginIsBusying) return

        loginIsBusying = true

        HttpRequest.getInstance().excute(HttpRequest.create(WebUserInfoApi::class.java).getUserInfo(UserCache.getUserToken().id)) { webMsg ->
            Loading.stop()
            loginIsBusying = false

            if (webMsg.dataIsSuccessed()) {
                UserCache.setUserInfo(webMsg.getData(UserInfoEntity::class.java))
                Toast.show(this@LoginActivity, "恭喜，登录成功！", 3000)
                val intent = Intent()
                intent.setClass(this@LoginActivity, MainActivity::class.java)
                this@LoginActivity.startActivity(intent)
                this@LoginActivity.finish()
            } else {
                Toast.show(this@LoginActivity, "登录失败，请重新尝试！", 3000)
            }
        }
    }

}