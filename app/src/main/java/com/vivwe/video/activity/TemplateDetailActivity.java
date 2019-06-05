package com.vivwe.video.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mbs.sdk.net.HttpRequest;
import com.mbs.sdk.net.listener.OnProgressListener;
import com.mbs.sdk.net.listener.OnResultListener;
import com.mbs.sdk.net.msg.WebMsg;
import com.vivwe.author.activity.DesignerHomeActivity;
import com.vivwe.base.activity.BaseActivity;
import com.vivwe.base.cache.UserCache;
import com.vivwe.base.ui.alert.Toast;
import com.vivwe.base.util.MiscUtil;
import com.vivwe.main.R;
import com.vivwe.video.entity.TemplateDetailEntity;
import com.vivwe.video.api.TemplateApi;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ahtor: super_link
 * date: 2019/5/10 09:38
 * remark: 模板详情（3.1）
 */
public class TemplateDetailActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_download)
    TextView tvDownload;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.btn_buy)
    Button btnBuy;
    @BindView(R.id.iv_star)
    ImageView ivStar;
    private RequestOptions requestOptions;
    private int isStarted;
    private TemplateDetailEntity templateDetailEntity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_template_detail);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
            requestOptions = new RequestOptions().circleCrop()
                    .placeholder(getResources().getDrawable(R.drawable.ic_launcher_background));
        HttpRequest.getInstance().excute(HttpRequest.create(TemplateApi.class).getTemplateDetail(getIntent().getIntExtra("templateId", 0)), new OnResultListener() {
            @Override
            public void onWebUiResult(WebMsg webMsg) {
                if (webMsg.dataIsSuccessed()) {
                    templateDetailEntity = webMsg.getData(TemplateDetailEntity.class);
                    tvTitle.setText(templateDetailEntity.getTitle());
                    tvContent.setText(templateDetailEntity.getDescription());
                    tvName.setText(templateDetailEntity.getNickname());
                    Log.e("ou",templateDetailEntity.getPrice()+" ");
                    btnBuy.setText("¥" + templateDetailEntity.getPrice() + "/次");
                    if (templateDetailEntity.getIsStared() == 1) {
                        isStarted=templateDetailEntity.getIsStared();
                        ivStar.setImageDrawable(getResources().getDrawable(R.mipmap.icon_collect_template));
                    }
                    Glide.with(TemplateDetailActivity.this).load(templateDetailEntity.getAvatar()).apply(requestOptions).into(ivHead);
                } else if (webMsg.netIsSuccessed()) {
                    Toast.show(TemplateDetailActivity.this, webMsg.getDesc(), 2000);
                }
            }
        });
    }

    @OnClick({R.id.iv_back, R.id.iv_star, R.id.iv_share, R.id.iv_head, R.id.btn_buy})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_star:
                collectedTemplate();
                break;
            case R.id.iv_share:

                break;
            case R.id.iv_head:
                startActivity(new Intent(this, DesignerHomeActivity.class));
                break;
            case R.id.btn_buy:
                downLoadTemplate();
                break;
        }
    }

    private void downLoadTemplate(){
        final File fileDest=new File(templateDetailEntity.getStyle()==1?getExternalFilesDir("standard"):getExternalFilesDir("dynamic"),
                templateDetailEntity.getTitle());
        Log.e("ouo",fileDest.getPath()+" "+templateDetailEntity.getTemplatePath());
        if(!fileDest.exists()){
            final File file=new File(templateDetailEntity.getStyle()==1?getExternalFilesDir("standard"):getExternalFilesDir("dynamic"),
                    templateDetailEntity.getTitle()+".zip");
            HttpRequest.getInstance().downloadToExcute("http://192.168.0.253:8083/api/template/downloadTemplate",templateDetailEntity.getTemplatePath(),
                    file.getPath(), new OnProgressListener() {
                @Override
                public void onProgress(long currentBytes, long contentLength) {
                    btnBuy.setText(currentBytes/1024/1024+"MB");
                }

                @Override
                public void onFinished(WebMsg webMsg) {
                    try {
                        MiscUtil.UnZipFolder(file.getPath(),fileDest.getPath());//解压
                        file.delete();
                        if(templateDetailEntity.getStyle()==1){
                            startActivity(new Intent(TemplateDetailActivity.this,VideoCreateByStandardActivity.class)
                                    .putExtra("path",fileDest.getPath()+"/"+fileDest.list()[0]));
                        }else{
                            startActivity(new Intent(TemplateDetailActivity.this,VideoCreateByDynamicActivity.class)
                                    .putExtra("path",fileDest.getPath()+"/"+fileDest.list()[0]));
                        }
                        btnBuy.setText("¥" + templateDetailEntity.getPrice() + "/次");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }else {
            if(templateDetailEntity.getStyle()==1){
                startActivity(new Intent(TemplateDetailActivity.this,VideoCreateByStandardActivity.class)
                        .putExtra("path",fileDest.getPath()+"/"+fileDest.list()[0]));
            }else{
                startActivity(new Intent(TemplateDetailActivity.this,VideoCreateByDynamicActivity.class)
                        .putExtra("path",fileDest.getPath()+"/"+fileDest.list()[0]));
            }
        }
    }


    //收藏与取消收藏
    private void collectedTemplate(){
        HttpRequest.getInstance().excute(HttpRequest.create(TemplateApi.class).collectedTemplate(getIntent().getIntExtra("templateId", 0),
                2,UserCache.Companion.getUserInfo().getId(),null), new OnResultListener() {
            @Override
            public void onWebUiResult(WebMsg webMsg) {
                if (webMsg.dataIsSuccessed()) {
                    Toast.show(TemplateDetailActivity.this, webMsg.getDesc(), 2000);
                    if(isStarted==0) {
                        isStarted=1;
                        ivStar.setImageDrawable(getResources().getDrawable(R.mipmap.icon_collect_template));
                    } else {
                        isStarted=0;
                        ivStar.setImageDrawable(getResources().getDrawable(R.mipmap.icon_collect_template_add));
                    }
                }else {
                    Toast.show(TemplateDetailActivity.this, webMsg.getDesc(), 2000);
                }
            }
        });
    }

}
