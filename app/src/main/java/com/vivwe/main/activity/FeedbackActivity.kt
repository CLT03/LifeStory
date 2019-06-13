package com.vivwe.main.activity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.mbs.sdk.net.HttpRequest
import com.mbs.sdk.utils.StringUtils
import com.vivwe.base.activity.BaseActivity
import com.vivwe.main.R
import com.vivwe.main.api.WebMainApi

/**
 * ahtor: super_link
 * date: 2019/6/13 14:18
 * remark: 用户反馈
 */
class FeedbackActivity : BaseActivity() {

    /** 联系人 */
    @BindView(R.id.edt_name)
    lateinit var nameEdt: EditText
    /** 联系电话 */
    @BindView(R.id.edt_phone)
    lateinit var phoneEdt: EditText
    /** 内容 */
    @BindView(R.id.edt_content)
    lateinit var contentEdt:EditText
    /** 提交 */
    @BindView(R.id.btn_submit)
    lateinit var submitBtn:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feeback)
        ButterKnife.bind(this)
    }

    var bindIsBusying = false
    /** 提交反馈 */
    @OnClick(R.id.btn_submit)
    fun submitFeedback(){

        if(bindIsBusying)return

        var name = nameEdt.text.toString()
        var phone = phoneEdt.text.toString()
        var content = contentEdt.text.toString()

        if(name.length < 2){
            Toast.makeText(this, "联系人至少2个字符！", Toast.LENGTH_LONG).show()
            return;
        }

        if(!StringUtils.checkMobileNumber(phone)){
            Toast.makeText(this, "请输入正确的手机号码！", Toast.LENGTH_LONG).show()
            return;
        }

        if(content.length < 10){
            Toast.makeText(this, "反馈内容至少为10个字符以上！", Toast.LENGTH_LONG).show()
            return;
        }

        bindIsBusying = true
        submitBtn.text = "反馈提交中"
        HttpRequest.getInstance().excute(HttpRequest.create(WebMainApi::class.java).addFeedback(name, phone, content)){
            webMsg ->
            submitBtn.text = "提交"
            bindIsBusying = false
            if (webMsg.dataIsSuccessed()) {
                Toast.makeText(this@FeedbackActivity, "我们已收到您的宝贵意见，感谢！", Toast.LENGTH_LONG).show()
                finish()
            } else if (webMsg.netIsSuccessed()) {
                Toast.makeText(this@FeedbackActivity, webMsg.desc, Toast.LENGTH_LONG).show()
            }
        }
    }

    /** 返回 */
    @OnClick(R.id.iv_back)
    fun onBack(){
        finish()
    }
}