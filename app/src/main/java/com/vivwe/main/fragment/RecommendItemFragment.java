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
    private VideoEntity videoEntity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recommend_item, null);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init(){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new RecommendItemAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        getData();
    }

    private void getData(){
        if(videoEntity!=null){
            adapter.setVideos(videoEntity.getMyVideoList());
        }else if (getArguments() != null) {
            HttpRequest.getInstance().excute(HttpRequest.create(VideoApi.class).getVideoByType(1,Integer.MAX_VALUE,getArguments().getInt("tag")), new OnResultListener() {
                @Override
                public void onWebUiResult(WebMsg webMsg) {
                    if (webMsg.dataIsSuccessed()) {
                        videoEntity = webMsg.getData(VideoEntity.class);
                        adapter.setVideos(videoEntity.getMyVideoList());
                    } else if (webMsg.netIsSuccessed()) {
                        Toast.show(getContext(), webMsg.getDesc(), 2000);
                    }
                }
            });
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
