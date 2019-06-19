package com.vivwe.video.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mbs.sdk.net.HttpRequest;
import com.mbs.sdk.net.listener.OnResultListener;
import com.mbs.sdk.net.msg.WebMsg;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vivwe.base.cache.UserCache;
import com.vivwe.base.ui.alert.Toast;
import com.vivwe.main.R;
import com.vivwe.main.adapter.RecommendItemAdapter;
import com.vivwe.personal.adapter.TemplateAdapter;
import com.vivwe.personal.entity.TemplateEntity;
import com.vivwe.personal.entity.UserEntity;
import com.vivwe.personal.entity.VideoEntity;
import com.vivwe.video.adapter.VideoSearchUserAdapter;
import com.vivwe.video.api.TemplateApi;
import com.vivwe.video.api.VideoApi;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class VideoSearchFragment extends Fragment {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    Unbinder unbinder;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private RecommendItemAdapter adapterVideo;
    private TemplateAdapter adapterDemo;
    private VideoSearchUserAdapter adapterUser;
    private VideoEntity videoEntity;
    private UserEntity userEntity;
    private TemplateEntity templateEntity;
    private String keyWord;
    private int mRefreshOrLoadMore;//1是刷新 2是加载更多
    private int[] pageNums = new int[]{1, 1, 1};
    private int pageSize = 20;

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
                        adapterDemo = new TemplateAdapter(getActivity());
                    recyclerView.setAdapter(adapterDemo);
                    recyclerView.setPadding(getResources().getDimensionPixelOffset(R.dimen.x32),
                            getResources().getDimensionPixelOffset(R.dimen.x24), 0, 0);
                    break;
                case 2:
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    if (adapterUser == null)
                        adapterUser = new VideoSearchUserAdapter(getActivity());
                    recyclerView.setAdapter(adapterUser);
                    break;
            }
        }
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if (keyWord != null) {
                    mRefreshOrLoadMore=1;
                    pageNums[getArguments().getInt("tag")]=1;
                    switch (getArguments().getInt("tag")) {
                        case 0:
                            searchVideo(keyWord);
                            break;
                        case 1:
                            searchTemplate(keyWord);
                            break;
                        case 2:
                            searchUser(keyWord);
                            break;
                    }
                }
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (keyWord != null) {
                    switch (getArguments().getInt("tag")) {
                        case 0:
                            if(videoEntity.getMyVideoList().size()==pageSize) {
                                pageNums[0]++;
                                mRefreshOrLoadMore=2;
                                searchVideo(keyWord);
                            }else {
                                android.widget.Toast.makeText(getContext(), "没有更多数据~", Toast.LENGTH_SHORT).show();
                                refreshLayout.finishLoadMore();
                            }
                            break;
                        case 1:
                            if(templateEntity.getRecords().size()==pageSize) {
                                pageNums[1]++;
                                mRefreshOrLoadMore=2;
                                searchTemplate(keyWord);
                            }else {
                                android.widget.Toast.makeText(getContext(), "没有更多数据~", Toast.LENGTH_SHORT).show();
                                refreshLayout.finishLoadMore();
                            }
                            break;
                        case 2:
                            if(userEntity.getRecords().size()==pageSize) {
                                pageNums[2]++;
                                mRefreshOrLoadMore=2;
                                searchUser(keyWord);
                            }else {
                                android.widget.Toast.makeText(getContext(), "没有更多数据~", Toast.LENGTH_SHORT).show();
                                refreshLayout.finishLoadMore();
                            }
                            break;
                    }
                }
            }
        });
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
            templateEntity = null;
            userEntity = null;
        }
    }

    private void searchVideo(String keyWord) {
        HttpRequest.getInstance().excute(HttpRequest.create(VideoApi.class).searchVideo(pageNums[0], pageSize, keyWord,
                2, UserCache.Companion.getUserInfo().getId()), new OnResultListener() {
            @Override
            public void onWebUiResult(WebMsg webMsg) {
                if (webMsg.dataIsSuccessed()) {
                    videoEntity = webMsg.getData(VideoEntity.class);
                    adapterVideo.setVideos(videoEntity.getMyVideoList());
                    finishGetData(true);
                } else if (webMsg.netIsSuccessed()) {
                    finishGetData(false);
                    Toast.show(getContext(), webMsg.getDesc(), 2000);
                }
            }
        });
    }

    private void searchTemplate(String keyWord) {
        HttpRequest.getInstance().excute(HttpRequest.create(TemplateApi.class).searchTemplate(pageNums[1], pageSize, keyWord), new OnResultListener() {
            @Override
            public void onWebUiResult(WebMsg webMsg) {
                if (webMsg.dataIsSuccessed()) {
                    templateEntity = webMsg.getData(TemplateEntity.class);
                    adapterDemo.setTemplates(templateEntity.getRecords());
                    finishGetData(true);
                } else if (webMsg.netIsSuccessed()) {
                    finishGetData(false);
                    Toast.show(getContext(), webMsg.getDesc(), 2000);
                }
            }
        });
    }

    private void searchUser(String keyWord) {
        HttpRequest.getInstance().excute(HttpRequest.create(VideoApi.class).searchUser(pageNums[2], pageSize, keyWord,
                UserCache.Companion.getUserInfo().getId()), new OnResultListener() {
            @Override
            public void onWebUiResult(WebMsg webMsg) {
                if (webMsg.dataIsSuccessed()) {
                    userEntity = webMsg.getData(UserEntity.class);
                    if (adapterUser != null)//狠关键
                        adapterUser.setUsers(userEntity.getRecords());
                    finishGetData(true);
                } else if (webMsg.netIsSuccessed()) {
                    finishGetData(false);
                    Toast.show(getContext(), webMsg.getDesc(), 2000);
                }
            }
        });
    }

    private void finishGetData(boolean success) {
        if (mRefreshOrLoadMore == 1 && refreshLayout!=null) {
            if(success) android.widget.Toast.makeText(getContext(), "刷新成功！", Toast.LENGTH_SHORT).show();
            refreshLayout.finishRefresh();
        }
        if (mRefreshOrLoadMore == 2 && refreshLayout!=null)
            refreshLayout.finishLoadMore();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
