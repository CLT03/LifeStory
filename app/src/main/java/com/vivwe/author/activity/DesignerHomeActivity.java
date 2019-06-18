package com.vivwe.author.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mbs.sdk.net.HttpRequest;
import com.mbs.sdk.net.listener.OnResultListener;
import com.mbs.sdk.net.msg.WebMsg;
import com.vivwe.author.adapter.DesignerHomePopWAdapter;
import com.vivwe.author.api.AuthorApi;
import com.vivwe.author.entity.DesignerInfoEntity;
import com.vivwe.base.activity.BaseActivity;
import com.vivwe.base.constant.Globals;
import com.vivwe.base.ui.alert.PopWindow;
import com.vivwe.base.ui.alert.Toast;
import com.vivwe.main.R;
import com.vivwe.personal.adapter.MyVideoAdapter;
import com.vivwe.personal.adapter.TemplateAdapter;
import com.vivwe.personal.api.PersonalApi;
import com.vivwe.personal.entity.TemplateEntity;
import com.vivwe.personal.entity.VideoEntity;
import com.vivwe.video.activity.VideoReportActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ahtor: super_link
 * date: 2019/4/26 17:57
 * remark: 设计师个人主页
 */
public class DesignerHomeActivity extends BaseActivity {


    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_theme)
    TextView tvTheme;
    @BindView(R.id.tv_make)
    TextView tvMake;
    @BindView(R.id.tv_sign)
    TextView tvSign;
    @BindView(R.id.recycler_view_template)
    RecyclerView recyclerViewTemplate;
    @BindView(R.id.recycler_view_video)
    RecyclerView recyclerViewVideo;
    @BindView(R.id.tv_template)
    TextView tvTemplate;
    @BindView(R.id.btn_attention)
    TextView btnAttention;
    @BindView(R.id.cl)
    ConstraintLayout cl;
    @BindView(R.id.iv_new_message)
    ImageView ivNewMessage;
    private TemplateAdapter adapterTemplate;
    private MyVideoAdapter adapterVideo;
    private RequestOptions requestOptions;
    private boolean isLiked;//是否关注
    private int mRole;//角色 1-普通用户 2-设计师
    private PopWindow pw;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_designer_home);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerViewTemplate.setLayoutManager(gridLayoutManager);
        adapterTemplate = new TemplateAdapter(this);
        recyclerViewTemplate.setAdapter(adapterTemplate);

        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(this, 3);
        recyclerViewVideo.setLayoutManager(gridLayoutManager1);
        adapterVideo = new MyVideoAdapter(this);
        recyclerViewVideo.setAdapter(adapterVideo);

        requestOptions = new RequestOptions().circleCrop()
                .placeholder(getResources().getDrawable(R.drawable.ic_launcher_background));
        getData();
        getVideo();
    }


    private void getData() {
        HttpRequest.getInstance().excute(HttpRequest.create(AuthorApi.class).getOtherUserInfo(getIntent().getIntExtra("userId", 0)), new OnResultListener() {
            @Override
            public void onWebUiResult(WebMsg webMsg) {
                if (webMsg.dataIsSuccessed()) {
                    DesignerInfoEntity designerInfoEntity = webMsg.getData(DesignerInfoEntity.class);
                    Glide.with(DesignerHomeActivity.this).load(Globals.URL_QINIU+designerInfoEntity.getAvatar()).apply(requestOptions).into(ivHead);
                    tvName.setText(designerInfoEntity.getNickname());
                    tvTheme.setText(String.valueOf(designerInfoEntity.getTopical()));
                    tvMake.setText(String.valueOf(designerInfoEntity.getProductNum()));
                    tvSign.setText(designerInfoEntity.getSignature());
                    if (designerInfoEntity.getIsSub() == 1) {
                        isLiked = true;
                        btnAttention.setBackground(getResources().getDrawable(R.drawable.r13_gray_btn));
                        btnAttention.setText("已关注");
                        ivNewMessage.setVisibility(View.VISIBLE);
                    }
                    mRole = designerInfoEntity.getRole();
                    if (designerInfoEntity.getRole() == 1) {//普通用户
                        recyclerViewTemplate.setVisibility(View.GONE);
                        recyclerViewVideo.setVisibility(View.VISIBLE);
                        tvTemplate.setText("视频");
                    } else {
                        getTemplate();
                    }
                } else if (webMsg.netIsSuccessed()) {
                    Toast.show(DesignerHomeActivity.this, webMsg.getDesc(), 2000);
                }
            }
        });
    }


    @OnClick({R.id.iv_back, R.id.iv_menu, R.id.btn_attention, R.id.iv_change, R.id.tv_template,R.id.iv_new_message})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_menu:
                showPw();
                break;
            case R.id.btn_attention:
                attention();
                break;
            case R.id.iv_change:
            case R.id.tv_template:
                if (mRole == 2) {
                    if (recyclerViewTemplate.getVisibility() == View.VISIBLE) {
                        recyclerViewTemplate.setVisibility(View.GONE);
                        recyclerViewVideo.setVisibility(View.VISIBLE);
                        tvTemplate.setText("视频");
                    } else {
                        recyclerViewVideo.setVisibility(View.GONE);
                        recyclerViewTemplate.setVisibility(View.VISIBLE);
                        tvTemplate.setText("模板");
                    }
                }
                break;
            case R.id.iv_new_message:
              //  startActivity(new Intent(this,));
                break;
        }
    }


    private void showPw() {
        if (pw == null) {
            pw = new PopWindow(this);
            pw.addView(new DesignerHomePopWAdapter().setOnclickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (view.getId()) {
                        case R.id.tv_report:
                            startActivity(new Intent(DesignerHomeActivity.this, VideoReportActivity.class)
                                    .putExtra("userId", getIntent().getIntExtra("userId", 0)).putExtra("type", 3));
                            break;
                        case R.id.btn_cancel:
                            pw.dismiss();
                            break;
                    }
                }
            }));
            pw.setAnimationStyle(R.style.popwin_anim_style);
            pw.showAtLocation(cl, Gravity.BOTTOM, 0, 0);
        } else pw.showAtLocation(cl, Gravity.BOTTOM, 0, 0);
    }

    //关注
    private void attention() {
        HttpRequest.getInstance().excute(HttpRequest.create(PersonalApi.class).attentionOrCancel(getIntent().getIntExtra("userId", 0)), new OnResultListener() {
            @Override
            public void onWebUiResult(WebMsg webMsg) {
                if (webMsg.dataIsSuccessed()) {
                    if (isLiked) {
                        isLiked = false;
                        btnAttention.setBackground(getResources().getDrawable(R.drawable.r13_orange_btn));
                        btnAttention.setText("+关注");
                        ivNewMessage.setVisibility(View.GONE);
                    } else {
                        isLiked = true;
                        btnAttention.setBackground(getResources().getDrawable(R.drawable.r13_gray_btn));
                        btnAttention.setText("已关注");
                        ivNewMessage.setVisibility(View.VISIBLE);
                    }
                    Toast.show(DesignerHomeActivity.this, webMsg.getDesc(), 2000);
                } else if (webMsg.netIsSuccessed()) {
                    Toast.show(DesignerHomeActivity.this, webMsg.getDesc(), 2000);
                }
            }
        });
    }


    private void getVideo() {
        HttpRequest.getInstance().excute(HttpRequest.create(PersonalApi.class).getVideoList(1, Integer.MAX_VALUE,
                getIntent().getIntExtra("userId", 0)), new OnResultListener() {
            @Override
            public void onWebUiResult(WebMsg webMsg) {
                if (webMsg.dataIsSuccessed()) {
                    VideoEntity myVideoEntity = webMsg.getData(VideoEntity.class);
                    adapterVideo.setData(myVideoEntity.getMyVideoList());
                } else if (webMsg.netIsSuccessed()) {
                    Toast.show(DesignerHomeActivity.this, webMsg.getDesc(), 2000);
                }
            }
        });
    }

    private void getTemplate() {
        HttpRequest.getInstance().excute(HttpRequest.create(AuthorApi.class).getOtherTemplate(getIntent().getIntExtra("userId", 0),
                1, Integer.MAX_VALUE
        ), new OnResultListener() {
            @Override
            public void onWebUiResult(WebMsg webMsg) {
                if (webMsg.dataIsSuccessed()) {
                    TemplateEntity templateEntity = webMsg.getData(TemplateEntity.class);
                    adapterTemplate.setTemplates(templateEntity.getRecords());
                } else if (webMsg.netIsSuccessed()) {
                    Toast.show(DesignerHomeActivity.this, webMsg.getDesc(), 2000);
                }
            }
        });
    }
}
