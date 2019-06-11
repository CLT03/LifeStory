package com.vivwe.personal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.vivwe.base.activity.BaseActivity;
import com.vivwe.main.R;
import com.vivwe.personal.adapter.MyDraftAdapter;
import com.vivwe.personal.adapter.MyVideoAdapter;
import com.vivwe.personal.adapter.RecommendActivityAdapter;
import com.vivwe.personal.adapter.RecommendDemoAdapter;
import com.vivwe.personal.adapter.RecommendUserAdapter;
import com.vivwe.personal.adapter.RecommendVideoAdapter;

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
    private RecommendDemoAdapter demoAdapter;
    private RecommendUserAdapter designerAdapter,userAdapter;
    private RecommendVideoAdapter videoAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_recommend_foryou);
        ButterKnife.bind(this);
        init();
    }

    private void init(){
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewActivity.setLayoutManager(linearLayoutManager);
        activityAdapter=new RecommendActivityAdapter(this);
        recyclerViewActivity.setAdapter(activityAdapter);

        LinearLayoutManager linearLayoutManager3=new LinearLayoutManager(this);
        linearLayoutManager3.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewDemo.setLayoutManager(linearLayoutManager3);
        demoAdapter=new RecommendDemoAdapter(this);
        recyclerViewDemo.setAdapter(demoAdapter);

        LinearLayoutManager linearLayoutManager1=new LinearLayoutManager(this);
        linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewDesigner.setLayoutManager(linearLayoutManager1);
        designerAdapter=new RecommendUserAdapter(this);
        recyclerViewDesigner.setAdapter(designerAdapter);

        GridLayoutManager gridLayoutManager1=new GridLayoutManager(this,2);
        recyclerViewVideo.setLayoutManager(gridLayoutManager1);
        videoAdapter=new RecommendVideoAdapter(this);
        recyclerViewVideo.setAdapter(videoAdapter);

        LinearLayoutManager linearLayoutManager2=new LinearLayoutManager(this);
        linearLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewUser.setLayoutManager(linearLayoutManager2);
        userAdapter=new RecommendUserAdapter(this);
        recyclerViewUser.setAdapter(userAdapter);

    }

    @OnClick({R.id.iv_back, R.id.tv_demo_more, R.id.iv_demo_more, R.id.tv_designer_more, R.id.iv_designer_more, R.id.tv_video_more, R.id.iv_video_more, R.id.tv_user_more, R.id.iv_user_more})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_demo_more:
            case R.id.iv_demo_more:

                break;
            case R.id.tv_designer_more:
            case R.id.iv_designer_more:
                startActivity(new Intent(this,TheDesignerRecommendForYouActivity.class));
                break;
            case R.id.tv_video_more:
            case R.id.iv_video_more:

                break;
            case R.id.tv_user_more:
            case R.id.iv_user_more:
                startActivity(new Intent(this,TheDesignerRecommendForYouActivity.class));
                break;
        }
    }

    private void getRecommendVideo(){

    }
    private void getRecommendTemplate(){

    }
    private void getRecommendUser(){

    }
    private void getRecommendDesigner(){

    }
}
