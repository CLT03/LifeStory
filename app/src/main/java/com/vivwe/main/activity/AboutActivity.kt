package com.vivwe.main.activity

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.mbs.sdk.net.HttpRequest
import com.vivwe.base.activity.BaseActivity
import com.vivwe.main.R
import com.vivwe.main.api.WebMainApi
import com.vivwe.main.entity.AboutEntity

/**
 * ahtor: super_link
 * date: 2019/6/13 14:42
 * remark: 关于
 */
class AboutActivity : BaseActivity() {

    /** 标题 */
    @BindView(R.id.tv_title)
    lateinit var titleTv: TextView
    /** 内容 */
    @BindView(R.id.tv_content)
    lateinit var contentTv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        ButterKnife.bind(this)

        loadData()
    }

    /** 加载数据 */
    fun loadData(){
        HttpRequest.getInstance().excute(HttpRequest.create(WebMainApi::class.java).getAbout()){
            webMsg ->
            if(webMsg.dataIsSuccessed()){
                var about = webMsg.getData(AboutEntity::class.java)
                titleTv.text = about.summaryTitle
                contentTv.text = about.summaryContent
                titleTv.visibility = View.VISIBLE
                contentTv.visibility = View.VISIBLE
            } else if(webMsg.netIsSuccessed()){
                Toast.makeText(this@AboutActivity, webMsg.desc, Toast.LENGTH_LONG).show()
            }
        }

    }

    /** 返回 */
    @OnClick(R.id.iv_back)
    fun onClick() {
        finish()
    }
}