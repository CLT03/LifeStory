package com.vivwe.video.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mbs.sdk.net.HttpRequest;
import com.mbs.sdk.net.listener.OnResultListener;
import com.mbs.sdk.net.msg.WebMsg;
import com.vivwe.base.ui.alert.Toast;
import com.vivwe.main.R;
import com.vivwe.main.adapter.RecommendItemAdapter;
import com.vivwe.main.api.TemplateApi;
import com.vivwe.personal.adapter.MyCollectedDemoAdapter;
import com.vivwe.personal.adapter.MyFansAdapter;
import com.vivwe.personal.entity.TemplateEntity;
import com.vivwe.personal.entity.UserEntity;
import com.vivwe.personal.entity.VideoEntity;
import com.vivwe.video.activity.VideoSearchActivity;
import com.vivwe.video.api.VideoApi;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class VideoSearchFragment extends Fragment {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    Unbinder unbinder;
    private RecommendItemAdapter adapterVideo;
    private MyCollectedDemoAdapter adapterDemo;
    private MyFansAdapter adapterUser;
    private VideoEntity videoEntity;
    private UserEntity userEntity;
    private TemplateEntity templateEntity;
    private String keyWord;
    private boolean first = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_search, null);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        if (getArguments() != null) {
            switch (getArguments().getInt("tag")) {
                case 0:
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
                    recyclerView.setLayoutManager(gridLayoutManager);
                    if (adapterVideo == null)
                        adapterVideo = new RecommendItemAdapter(getActivity());
                    recyclerView.setAdapter(adapterVideo);
                    break;
                case 1:
                    GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getContext(), 2);
                    recyclerView.setLayoutManager(gridLayoutManager1);
                    if (adapterDemo == null)
                        adapterDemo = new MyCollectedDemoAdapter(getActivity());
                    recyclerView.setAdapter(adapterDemo);
                    recyclerView.setPadding(getResources().getDimensionPixelOffset(R.dimen.x32),
                            getResources().getDimensionPixelOffset(R.dimen.x24), 0, 0);
                    break;
                case 2:
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    if (adapterUser == null)
                        adapterUser = new MyFansAdapter(getActivity());
                    recyclerView.setAdapter(adapterUser);
                    break;
            }
        }
        Log.e("ououou", "sdf" + getArguments().getInt("tag"));
      /*  if (first) {//第一次进来先不搜索
            first = false;
        } else {
            getData();
        }*/
        getData();
    }

    public void getData() {
        switch (getArguments().getInt("tag")) {
            case 0:
                if (videoEntity != null) {
                    adapterVideo.setVideos(videoEntity.getMyVideoList());
                } else if (keyWord != null) {
                    searchVideo(keyWord);
                }
                break;
            case 1:
                if (templateEntity != null) {
                    adapterDemo.setTemplates(templateEntity.getRecords());
                } else if (keyWord != null) {
                    searchTemplate(keyWord);
                }
                break;
            case 2:
                if (userEntity != null) {
                    adapterUser.setUsers(userEntity.getRecords());
                } else if (keyWord != null) {
                    searchUser(keyWord);
                }
                break;
        }
    }

    public void setKeyWord(String keyWord) {
        if (this.keyWord == null || !this.keyWord.equals(keyWord)) {
            this.keyWord = keyWord;
            videoEntity = null;
            templateEntity=null;
            userEntity = null;
        }
    }

    private void searchVideo(String keyWord) {
        HttpRequest.getInstance().excute(HttpRequest.create(VideoApi.class).searchVideo(1, Integer.MAX_VALUE, keyWord,
                2, 16), new OnResultListener() {
            @Override
            public void onWebUiResult(WebMsg webMsg) {
                if (webMsg.dataIsSuccessed()) {
                    videoEntity = webMsg.getData(VideoEntity.class);
                    adapterVideo.setVideos(videoEntity.getMyVideoList());
                } else if (webMsg.netIsSuccessed()) {
                    Toast.show(getContext(), webMsg.getDesc(), 2000);
                }
            }
        });
    }

    private void searchUser(String keyWord) {
        HttpRequest.getInstance().excute(HttpRequest.create(VideoApi.class).searchUser(1, Integer.MAX_VALUE, keyWord,
                16), new OnResultListener() {
            @Override
            public void onWebUiResult(WebMsg webMsg) {
                if (webMsg.dataIsSuccessed()) {
                    userEntity = webMsg.getData(UserEntity.class);
                    if(adapterUser!=null)//狠关键
                       adapterUser.setUsers(userEntity.getRecords());
                } else if (webMsg.netIsSuccessed()) {
                    Toast.show(getContext(), webMsg.getDesc(), 2000);
                }
            }
        });
    }

    private void searchTemplate(String keyWord) {
        HttpRequest.getInstance().excute(HttpRequest.create(com.vivwe.video.api.TemplateApi.class).searchTemplate(1, Integer.MAX_VALUE, keyWord), new OnResultListener() {
            @Override
            public void onWebUiResult(WebMsg webMsg) {
                if (webMsg.dataIsSuccessed()) {
                    templateEntity = webMsg.getData(TemplateEntity.class);
                    adapterDemo.setTemplates(templateEntity.getRecords());
                } else if (webMsg.netIsSuccessed()) {
                    Toast.show(getContext(), webMsg.getDesc(), 2000);
                }
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
