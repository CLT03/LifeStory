package com.vivwe.personal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mbs.sdk.net.HttpRequest;
import com.mbs.sdk.net.listener.OnResultListener;
import com.mbs.sdk.net.msg.WebMsg;
import com.vivwe.base.activity.BaseActivity;
import com.vivwe.base.cache.UserCache;
import com.vivwe.base.ui.alert.Toast;
import com.vivwe.main.R;
import com.vivwe.personal.adapter.RecommendActivityAdapter;
import com.vivwe.personal.adapter.RecommendUserAdapter;
import com.vivwe.personal.adapter.RecommendVideoAdapter;
import com.vivwe.personal.adapter.TemplateAdapter;
import com.vivwe.personal.api.PersonalApi;
import com.vivwe.personal.entity.AttentionEntity;
import com.vivwe.personal.entity.TemplateEntity;
import com.vivwe.personal.entity.UserEntity;
import com.vivwe.personal.entity.VideoEntity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ahtor: super_link
 * date: 2019/4/26 15:38
 * remark: 为你推荐
 */
public class RecommendForYouActivity extends BaseActivity {

    @BindView(R.id.recycler_view_activity)
    RecyclerView recyclerViewActivity;
    @BindView(R.id.recycler_view_demo)
    RecyclerView recyclerViewDemo;
    @BindView(R.id.recycler_view_designer)
    RecyclerView recyclerViewDesigner;
    @BindView(R.id.recycler_view_video)
    RecyclerView recyclerViewVideo;
    @BindView(R.id.recycler_view_user)
    RecyclerView recyclerViewUser;
    private RecommendActivityAdapter activityAdapter;
    private TemplateAdapter templateAdapter;
    private RecommendUserAdapter designerAdapter, userAdapter;
    private RecommendVideoAdapter videoAdapter;
    private int templatePageNum = 1, videoPageNum = 1, designerPageNum = 1, userPageNum = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_recommend_foryou);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewActivity.setLayoutManager(linearLayoutManager);
        activityAdapter = new RecommendActivityAdapter(this);
        recyclerViewActivity.setAdapter(activityAdapter);

        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(this);
        linearLayoutManager3.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewDemo.setLayoutManager(linearLayoutManager3);
        templateAdapter = new TemplateAdapter(this);
        recyclerViewDemo.setAdapter(templateAdapter);

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewDesigner.setLayoutManager(linearLayoutManager1);
        designerAdapter = new RecommendUserAdapter(this);
        recyclerViewDesigner.setAdapter(designerAdapter);

        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(this, 2);
        recyclerViewVideo.setLayoutManager(gridLayoutManager1);
        videoAdapter = new RecommendVideoAdapter(this);
        recyclerViewVideo.setAdapter(videoAdapter);

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        linearLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewUser.setLayoutManager(linearLayoutManager2);
        userAdapter = new RecommendUserAdapter(this);
        recyclerViewUser.setAdapter(userAdapter);
        getRecommendVideo();
        getRecommendDesigner();
        getRecommendTemplate();
        getRecommendUser();

    }

    @OnClick({R.id.iv_back, R.id.tv_demo_more, R.id.iv_demo_more, R.id.tv_designer_more, R.id.iv_designer_more, R.id.tv_video_more, R.id.iv_video_more, R.id.tv_user_more, R.id.iv_user_more})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_demo_more:
            case R.id.iv_demo_more:
                getRecommendTemplate();
                break;
            case R.id.tv_designer_more:
            case R.id.iv_designer_more:
                getRecommendDesigner();
                break;
            case R.id.tv_video_more:
            case R.id.iv_video_more:
                getRecommendVideo();
                break;
            case R.id.tv_user_more:
            case R.id.iv_user_more:
                getRecommendUser();
                break;
        }
    }


    private void getRecommendVideo() {
        HttpRequest.getInstance().excute(HttpRequest.create(PersonalApi.class).getRecommendVideo(videoPageNum, 4,
                UserCache.Companion.getUserInfo().getId()), new OnResultListener() {
            @Override
            public void onWebUiResult(WebMsg webMsg) {
                if (webMsg.dataIsSuccessed()) {
                    VideoEntity videoEntity = webMsg.getData(VideoEntity.class);
                    videoAdapter.setVideos(videoEntity.getMyVideoList());
                    if (videoEntity.getMyVideoList().size() == 4) {
                        videoPageNum++;
                    } else videoPageNum = 1;
                } else if (webMsg.netIsSuccessed()) {
                    Toast.show(RecommendForYouActivity.this, webMsg.getDesc(), 2000);
                }
            }
        });
    }

    private void getRecommendTemplate() {
        HttpRequest.getInstance().excute(HttpRequest.create(PersonalApi.class).getRecommendTemplate(templatePageNum, 2,
                UserCache.Companion.getUserInfo().getId()), new OnResultListener() {
            @Override
            public void onWebUiResult(WebMsg webMsg) {
                if (webMsg.dataIsSuccessed()) {
                    TemplateEntity templateEntity = webMsg.getData(TemplateEntity.class);
                    if(templateEntity!=null) {
                        templateAdapter.setTemplates(templateEntity.getRecords());
                        if (templateEntity.getRecords().size() == 2) {
                            templatePageNum++;
                        } else templatePageNum = 1;
                    }
                } else if (webMsg.netIsSuccessed()) {
                    Toast.show(RecommendForYouActivity.this, webMsg.getDesc(), 2000);
                }
            }
        });
    }

    private void getRecommendUser() {
        HttpRequest.getInstance().excute(HttpRequest.create(PersonalApi.class).getRecommendUser(userPageNum, 5,
                UserCache.Companion.getUserInfo().getId()), new OnResultListener() {
            @Override
            public void onWebUiResult(WebMsg webMsg) {
                if (webMsg.dataIsSuccessed()) {
                    UserEntity userEntity = webMsg.getData(UserEntity.class);
                    userAdapter.setUsers(userEntity.getRecords());
                    if (userEntity.getRecords().size() == 5) {
                        userPageNum++;
                    } else userPageNum = 1;
                } else if (webMsg.netIsSuccessed()) {
                    Toast.show(RecommendForYouActivity.this, webMsg.getDesc(), 2000);
                }
            }
        });
    }

    private void getRecommendDesigner() {
        HttpRequest.getInstance().excute(HttpRequest.create(PersonalApi.class).getRecommendDesigner(designerPageNum, 5,
                UserCache.Companion.getUserInfo().getId()), new OnResultListener() {
            @Override
            public void onWebUiResult(WebMsg webMsg) {
                if (webMsg.dataIsSuccessed()) {
                    UserEntity userEntity = webMsg.getData(UserEntity.class);
                    designerAdapter.setUsers(userEntity.getRecords());
                    if (userEntity.getRecords().size() == 5) {
                        designerPageNum++;
                    } else designerPageNum = 1;
                } else if (webMsg.netIsSuccessed()) {
                    Toast.show(RecommendForYouActivity.this, webMsg.getDesc(), 2000);
                }
            }
        });
    }
}
