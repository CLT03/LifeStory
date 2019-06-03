package com.vivwe.personal.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mbs.sdk.net.HttpRequest;
import com.mbs.sdk.net.listener.OnResultListener;
import com.mbs.sdk.net.msg.WebMsg;
import com.vivwe.base.ui.alert.Toast;
import com.vivwe.main.R;
import com.vivwe.personal.adapter.MyCollectedDemoAdapter;
import com.vivwe.personal.adapter.MyCollectedVideoAdapter;
import com.vivwe.personal.api.PersonalApi;
import com.vivwe.personal.entity.TemplateEntity;
import com.vivwe.personal.entity.VideoEntity;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MycollectedFragment extends Fragment {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    Unbinder unbinder;
    private MyCollectedDemoAdapter demoAdapter;
    private MyCollectedVideoAdapter videoAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_mycollected, null);
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
                    demoAdapter = new MyCollectedDemoAdapter(Objects.requireNonNull(getActivity()));
                    recyclerView.setAdapter(demoAdapter);
                    recyclerView.setPadding(getResources().getDimensionPixelOffset(R.dimen.x32), 0,
                            getResources().getDimensionPixelOffset(R.dimen.x6), 0);
                    getTemplateCollected();
                    break;
                case 1:
                    GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getContext(),3);
                    recyclerView.setLayoutManager(gridLayoutManager1);
                    videoAdapter = new MyCollectedVideoAdapter(Objects.requireNonNull(getActivity()));
                    recyclerView.setAdapter(videoAdapter);
                    getVideoCollected();
                    break;
            }
        }
    }

    private void getTemplateCollected(){
        HttpRequest.getInstance().excute(HttpRequest.create(PersonalApi.class).getTemplateCollected(1,Integer.MAX_VALUE), new OnResultListener() {
            @Override
            public void onWebUiResult(WebMsg webMsg) {
                if (webMsg.dataIsSuccessed()) {
                    TemplateEntity templateEntity = webMsg.getData(TemplateEntity.class);
                    demoAdapter.setTemplates(templateEntity.getRecords());
                } else if (webMsg.netIsSuccessed()) {
                    Toast.show(getContext(), webMsg.getDesc(), 2000);
                }
            }
        });
    }

    private void getVideoCollected(){
        HttpRequest.getInstance().excute(HttpRequest.create(PersonalApi.class).getVideoCollected(1,Integer.MAX_VALUE), new OnResultListener() {
            @Override
            public void onWebUiResult(WebMsg webMsg) {
                if (webMsg.dataIsSuccessed()) {
                    VideoEntity videoEntity = webMsg.getData(VideoEntity.class);
                    videoAdapter.setVideos(videoEntity.getMyVideoList());
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
