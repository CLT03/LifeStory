package com.vivwe.main.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
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
import com.vivwe.base.activity.BaseFragment;
import com.vivwe.base.ui.alert.Toast;
import com.vivwe.main.R;
import com.vivwe.main.adapter.RecommendItemAdapter;
import com.vivwe.personal.entity.VideoEntity;
import com.vivwe.video.api.VideoApi;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RecommendItemFragment extends BaseFragment {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    Unbinder unbinder;
    RecommendItemAdapter adapter;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private VideoEntity videoEntity;
    private int pageNum=1;
    private int pageSize=20;
    private int mRefreshOrLoadMore;//1是刷新 2是加载更多

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recommend_item, null);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new RecommendItemAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNum=1;
                mRefreshOrLoadMore=1;
                getData();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if(videoEntity.getMyVideoList().size()==pageSize) {
                    pageNum++;
                    mRefreshOrLoadMore=2;
                    getData();
                }else {
                    android.widget.Toast.makeText(getContext(), "没有更多数据~", Toast.LENGTH_SHORT).show();
                    refreshLayout.finishLoadMore();
                }
            }
        });
        if (videoEntity != null) {
            adapter.setVideos(videoEntity.getMyVideoList());
        }else getData();
    }


    private void getData(){
        HttpRequest.getInstance().excute(HttpRequest.create(VideoApi.class).getVideoByType(pageNum,pageSize, getArguments().getInt("tag")), new OnResultListener() {
            @Override
            public void onWebUiResult(WebMsg webMsg) {
                if (webMsg.dataIsSuccessed()) {
                    videoEntity = webMsg.getData(VideoEntity.class);
                    adapter.setVideos(videoEntity.getMyVideoList());
                    if(mRefreshOrLoadMore==1){
                        refreshLayout.finishRefresh();
                        android.widget.Toast.makeText(getContext(), "刷新成功！", Toast.LENGTH_SHORT).show();
                    }else if(mRefreshOrLoadMore==2)
                        refreshLayout.finishLoadMore();

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
