package com.vivwe.video.fragment;

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
import com.vivwe.personal.entity.TemplateEntity;
import com.vivwe.video.api.TemplateApi;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TemplateItemFragment extends Fragment {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    Unbinder unbinder;
    TemplateAdapter adapter;
    private TemplateEntity templateEntity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_template_item, null);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init(){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new TemplateAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        getData();
    }


    private void getData(){
        if(templateEntity!=null){
            adapter.setTemplates(templateEntity.getRecords());
        }else if (getArguments() != null) {
            HttpRequest.getInstance().excute(HttpRequest.create(TemplateApi.class).getTemplateByType(1,Integer.MAX_VALUE,getArguments().getInt("tag")), new OnResultListener() {
                @Override
                public void onWebUiResult(WebMsg webMsg) {
                    if (webMsg.dataIsSuccessed()) {
                        templateEntity = webMsg.getData(TemplateEntity.class);
                        adapter.setTemplates(templateEntity.getRecords());
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
