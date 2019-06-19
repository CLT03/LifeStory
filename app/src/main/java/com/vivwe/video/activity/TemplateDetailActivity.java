package com.vivwe.video.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mbs.sdk.net.HttpRequest;
import com.mbs.sdk.net.listener.OnProgressListener;
import com.mbs.sdk.net.listener.OnResultListener;
import com.mbs.sdk.net.msg.WebMsg;
import com.vivwe.author.activity.DesignerHomeActivity;
import com.vivwe.base.activity.BaseActivity;
import com.vivwe.base.cache.UserCache;
import com.vivwe.base.constant.Globals;
import com.vivwe.base.util.MiscUtil;
import com.vivwe.main.R;
import com.vivwe.video.api.TemplateApi;
import com.vivwe.video.entity.TemplateDetailEntity;
import java.io.File;
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
    @BindView(R.id.video_view)
    VideoView videoView;
    private RequestOptions requestOptions;
    private int isStarted;
    private TemplateDetailEntity templateDetailEntity;
    private boolean mFinished;//是否结束activity

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
                    videoView.setVideoPath(Globals.URL_QINIU+templateDetailEntity.getPath());
                    MediaController controller=new MediaController(TemplateDetailActivity.this);
                    videoView.setMediaController(controller);
                    videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                        @Override
                        public boolean onError(MediaPlayer mp, int what, int extra) {
                            videoView.stopPlayback();
                            return true;
                        }
                    });
                    videoView.start();
                    tvTitle.setText(templateDetailEntity.getTitle());
                    tvContent.setText(templateDetailEntity.getDescription());
                    tvName.setText(templateDetailEntity.getNickname());
                    Log.e("ou", templateDetailEntity.getPrice() + " ");
                    btnBuy.setText("¥" + templateDetailEntity.getPrice() + "/次");
                    if (templateDetailEntity.getIsStared() == 1) {
                        isStarted = templateDetailEntity.getIsStared();
                        ivStar.setImageDrawable(getResources().getDrawable(R.mipmap.icon_collect_template));
                    }
                    Glide.with(TemplateDetailActivity.this).load(Globals.URL_QINIU+templateDetailEntity.getAvatar()).apply(requestOptions).into(ivHead);
                } else if (webMsg.netIsSuccessed()) {
                    Toast.makeText(TemplateDetailActivity.this, webMsg.getDesc(), Toast.LENGTH_LONG).show();
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
                startActivity(new Intent(this, DesignerHomeActivity.class).putExtra("userId", templateDetailEntity.getUserId()));
                break;
            case R.id.btn_buy:
                downLoadTemplate();
                break;
        }
    }



    private void downLoadTemplate() {
        final File fileDest = new File(templateDetailEntity.getStyle() == 1 ? getExternalFilesDir("standard") : getExternalFilesDir("dynamic"),
                templateDetailEntity.getTitle());
        Log.e("ououou", fileDest.getPath() + " " + templateDetailEntity.getTemplatePath());
        if (!fileDest.exists()) {
            final File file = new File(templateDetailEntity.getStyle() == 1 ? getExternalFilesDir("standard") : getExternalFilesDir("dynamic"),
                    templateDetailEntity.getTitle() + ".zip");
            btnBuy.setClickable(false);
            HttpRequest.getInstance().downloadToExcute(Globals.URL_WEB + "api/template/downloadTemplate", String.valueOf(templateDetailEntity.getId()),
                    file.getPath(), new OnProgressListener() {
                        @Override
                        public void onProgress(long currentBytes, long contentLength) {
                          //  Log.e("ououou","progress "+currentBytes+" "+contentLength);
                            btnBuy.setText(String.valueOf(currentBytes*100 / contentLength)+"%");
                            //btnBuy.setText(currentBytes/1024/1024+"MB");
                        }

                        @Override
                        public void onFinished(WebMsg webMsg) {
                           // Log.e("ououou","onFinished "+webMsg.getData());
                            try {
                                if (webMsg.dataIsSuccessed()) {
                                    MiscUtil.UnZipFolder(file.getPath(), fileDest.getPath());//解压
                                    file.delete();
                                    if(fileDest.exists()) {
                                        if (!mFinished) {
                                            if (templateDetailEntity.getStyle() == 1) {
                                                startActivity(new Intent(TemplateDetailActivity.this, VideoCreateByStandardActivity.class)
                                                        .putExtra("path", fileDest.getPath() + "/" + fileDest.list()[0]));
                                            } else {
                                                startActivity(new Intent(TemplateDetailActivity.this, VideoCreateByDynamicActivity.class)
                                                        .putExtra("path", fileDest.getPath() + "/" + fileDest.list()[0]));
                                            }
                                        }
                                    }else Toast.makeText(TemplateDetailActivity.this, "服务器文件错误。", Toast.LENGTH_SHORT).show();
                            } else {
                                    btnBuy.setClickable(true);
                                    btnBuy.setText("¥" + templateDetailEntity.getPrice() + "/次");
                                    Toast.makeText(TemplateDetailActivity.this, "缓存失败，请请重试！", Toast.LENGTH_LONG).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                btnBuy.setText("¥" + templateDetailEntity.getPrice() + "/次");
                                btnBuy.setClickable(true);
                            }
                        }
                    });
        } else {
            if (templateDetailEntity.getStyle() == 1) {
                startActivity(new Intent(TemplateDetailActivity.this, VideoCreateByStandardActivity.class)
                        .putExtra("path", fileDest.getPath() + "/" + fileDest.list()[0]));
            } else {
                startActivity(new Intent(TemplateDetailActivity.this, VideoCreateByDynamicActivity.class)
                        .putExtra("path", fileDest.getPath() + "/" + fileDest.list()[0]));
            }
        }
    }


    //收藏与取消收藏
    private void collectedTemplate() {
        HttpRequest.getInstance().excute(HttpRequest.create(TemplateApi.class).collectedTemplate(getIntent().getIntExtra("templateId", 0),
                2, UserCache.Companion.getUserInfo().getId(), null), new OnResultListener() {
            @Override
            public void onWebUiResult(WebMsg webMsg) {
                if (webMsg.dataIsSuccessed()) {
                    Toast.makeText(TemplateDetailActivity.this, "操作成功！", Toast.LENGTH_LONG).show();
                    if (isStarted == 0) {
                        isStarted = 1;
                        ivStar.setImageDrawable(getResources().getDrawable(R.mipmap.icon_collect_template));
                    } else {
                        isStarted = 0;
                        ivStar.setImageDrawable(getResources().getDrawable(R.mipmap.icon_collect_template_add));
                    }
                } else if (webMsg.netIsSuccessed()) {
                    Toast.makeText(TemplateDetailActivity.this, webMsg.getDesc(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        if(videoView!=null && videoView.isPlaying())
            videoView.stopPlayback();
        mFinished=true;
        super.onDestroy();
    }
}