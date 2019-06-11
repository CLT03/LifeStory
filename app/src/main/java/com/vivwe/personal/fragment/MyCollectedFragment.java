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
import com.vivwe.personal.adapter.TemplateAdapter;
import com.vivwe.personal.adapter.MyCollectedVideoAdapter;
import com.vivwe.personal.api.PersonalApi;
import com.vivwe.personal.entity.TemplateEntity;
import com.vivwe.personal.entity.VideoEntity;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MyCollectedFragment extends Fragment {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    Unbinder unbinder;
    private TemplateAdapter templateAdapter;
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
                    templateAdapter = new TemplateAdapter(Objects.requireNonNull(getActivity()));
                    recyclerView.setAdapter(templateAdapter);
                    recyclerView.setPadding(getResources().getDimensionPixelOffset(R.dimen.x32), 0, getResources().getDimensionPixelOffset(R.dimen.x6), 0);
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
                    templateAdapter.setTemplates(templateEntity.getRecords());
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


    public void templateEdit(boolean ifEdit){
        switch (getArguments().getInt("tag")) {
            case 0:
                if(templateAdapter!=null){
                    templateAdapter.setIfEdit(ifEdit);
                }
                break;
            case 1:
                if(videoAdapter!=null){
                    videoAdapter.setIfEdit(ifEdit);
                }
                break;
        }
    }

    public ArrayList<Integer> getChooseIdList(){
        switch (getArguments().getInt("tag")) {
            case 0:
                if(templateAdapter!=null){
                    return templateAdapter.getChooseIdList();
                }
                break;
            case 1:
                if(videoAdapter!=null){
                    return videoAdapter.getChooseIdList();
                }
                break;
        }
        return new ArrayList<>();
    }

    public void allChoose(boolean ifAllChoose){
        switch (getArguments().getInt("tag")) {
            case 0:
                if(templateAdapter!=null){
                    templateAdapter.allChoose(ifAllChoose);
                }
                break;
            case 1:
                if(videoAdapter!=null){
                     videoAdapter.allChoose(ifAllChoose);
                }
                break;
        }
    }

    public void deleteSuccess(){
        switch (getArguments().getInt("tag")) {
            case 0:
                if(templateAdapter!=null){
                    templateAdapter.deleteSuccess();
                }
                break;
            case 1:
                if(videoAdapter!=null){
                    videoAdapter.deleteSuccess();
                }
                break;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
