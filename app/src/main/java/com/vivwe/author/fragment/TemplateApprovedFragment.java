package com.vivwe.author.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mbs.sdk.net.HttpRequest;
import com.mbs.sdk.net.listener.OnResultListener;
import com.mbs.sdk.net.msg.WebMsg;
import com.vivwe.author.adapter.TemplateNoPassAdapter;
import com.vivwe.author.adapter.TemplatePublishAdapter;
import com.vivwe.author.adapter.TemplateWaitReviewAdapter;
import com.vivwe.author.api.AuthorApi;
import com.vivwe.base.ui.alert.Toast;
import com.vivwe.main.R;

import com.vivwe.personal.entity.TemplateEntity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TemplateApprovedFragment extends Fragment {
    
    Unbinder unbinder;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private TemplatePublishAdapter publishAdapter;
    private TemplateWaitReviewAdapter waitReviewAdapter;
    private TemplateNoPassAdapter noPassAdapter;
    private TemplateEntity templatePublishEntity,templateWaitReviewEntity,templateNoPassEntity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_author_template_approved, null);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }


    private void init() {
        if (getArguments() != null) {
            switch (getArguments().getInt("tag")) {
                case 0:
                    LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext());
                    linearLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(linearLayoutManager1);
                    if(publishAdapter==null)
                        publishAdapter = new TemplatePublishAdapter(getActivity());
                    recyclerView.setAdapter(publishAdapter);
                    if(templatePublishEntity!=null){
                        publishAdapter.setTemplates(templatePublishEntity.getRecords());
                    }else getPublished();
                    break;
                case 1:
                    LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext());
                    linearLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(linearLayoutManager2);
                    if(waitReviewAdapter==null)
                        waitReviewAdapter = new TemplateWaitReviewAdapter(getActivity());
                    recyclerView.setAdapter(waitReviewAdapter);
                    if(templateWaitReviewEntity!=null){
                        waitReviewAdapter.setTemplates(templateWaitReviewEntity.getRecords());
                    }else getWaitReview();
                    break;
                case 2:
                    LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(getContext());
                    linearLayoutManager3.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(linearLayoutManager3);
                    if(noPassAdapter==null)
                        noPassAdapter = new TemplateNoPassAdapter(getActivity());
                    recyclerView.setAdapter(noPassAdapter);
                    if(templateNoPassEntity!=null){
                        noPassAdapter.setTemplates(templateNoPassEntity.getRecords());
                    }else getNoPass();
                    break;
            }
        }
    }

    private void getPublished() {
        HttpRequest.getInstance().excute(HttpRequest.create(AuthorApi.class).getPublished(1, Integer.MAX_VALUE), new OnResultListener() {
            @Override
            public void onWebUiResult(WebMsg webMsg) {
                if (webMsg.dataIsSuccessed()) {
                    templatePublishEntity = webMsg.getData(TemplateEntity.class);
                    publishAdapter.setTemplates(templatePublishEntity.getRecords());
                } else if (webMsg.netIsSuccessed()) {
                    Toast.show(getContext(), webMsg.getDesc(), 2000);
                }
            }
        });
    }

    private void getWaitReview() {
        HttpRequest.getInstance().excute(HttpRequest.create(AuthorApi.class).getWaitReview(1, Integer.MAX_VALUE), new OnResultListener() {
            @Override
            public void onWebUiResult(WebMsg webMsg) {
                if (webMsg.dataIsSuccessed()) {
                    templateWaitReviewEntity = webMsg.getData(TemplateEntity.class);
                    waitReviewAdapter.setTemplates(templateWaitReviewEntity.getRecords());
                } else if (webMsg.netIsSuccessed()) {
                    Toast.show(getContext(), webMsg.getDesc(), 2000);
                }
            }
        });
    }

    private void getNoPass() {
        HttpRequest.getInstance().excute(HttpRequest.create(AuthorApi.class).getNoPass(1, Integer.MAX_VALUE), new OnResultListener() {
            @Override
            public void onWebUiResult(WebMsg webMsg) {
                if (webMsg.dataIsSuccessed()) {
                    templateNoPassEntity = webMsg.getData(TemplateEntity.class);
                    noPassAdapter.setTemplates(templateNoPassEntity.getRecords());
                } else if (webMsg.netIsSuccessed()) {
                    Toast.show(getContext(), webMsg.getDesc(), 2000);
                }
            }
        });
    }

    public void templateEdit(boolean ifEdit){
        switch (getArguments().getInt("tag")) {
            case 0:
                if(publishAdapter!=null){
                    publishAdapter.setIfEdit(ifEdit);
                }
                break;
            case 2:
                if(noPassAdapter!=null){
                    noPassAdapter.setIfEdit(ifEdit);
                }
                break;
        }
    }

    public ArrayList<Integer> getChooseIdList(){
        switch (getArguments().getInt("tag")) {
            case 0:
                if(publishAdapter!=null){
                    return publishAdapter.getChooseIdList();
                }
                break;
            case 2:
                if(noPassAdapter!=null){
                    return noPassAdapter.getChooseIdList();
                }
                break;
        }
        return new ArrayList<>();
    }

    public void allChoose(boolean ifAllChoose){
        switch (getArguments().getInt("tag")) {
            case 0:
                if(publishAdapter!=null){
                    publishAdapter.allChoose(ifAllChoose);
                }
                break;
            case 2:
                if(noPassAdapter!=null){
                    noPassAdapter.allChoose(ifAllChoose);
                }
                break;
        }
    }

    public void deleteSuccess(){
        switch (getArguments().getInt("tag")) {
            case 0:
                if(publishAdapter!=null){
                    publishAdapter.deleteSuccess();
                }
                break;
            case 2:
                if(noPassAdapter!=null){
                    noPassAdapter.deleteSuccess();
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
