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
import com.vivwe.personal.adapter.MyAssetsAdapter;
import com.vivwe.personal.adapter.MyCollectedVideoAdapter;
import com.vivwe.personal.adapter.TemplateAdapter;
import com.vivwe.personal.api.PersonalApi;
import com.vivwe.personal.entity.AssetsEntity;
import com.vivwe.personal.entity.TemplateEntity;
import com.vivwe.personal.entity.VideoEntity;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MyAssetsFragment extends Fragment {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    Unbinder unbinder;
    private MyAssetsAdapter adapterImg;
    private MyAssetsAdapter adapterGif;
    private ArrayList<TemplateEntity.Template> templates;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_myassets, null);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }


    private void init() {
        if (getArguments() != null) {
            switch (getArguments().getInt("tag")) {
                case 0:
                    GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),3);
                    recyclerView.setLayoutManager(gridLayoutManager);
                    adapterImg=new MyAssetsAdapter(getActivity());
                    recyclerView.setAdapter(adapterImg);
                    getAssets(1);
                    break;
                case 1:
                    GridLayoutManager gridLayoutManager1=new GridLayoutManager(getContext(),3);
                    recyclerView.setLayoutManager(gridLayoutManager1);
                    adapterGif=new MyAssetsAdapter(getActivity());
                    recyclerView.setAdapter(adapterGif);
                    getAssets(2);
                    break;
            }
        }
    }

    private void getAssets(final int type){
        HttpRequest.getInstance().excute(HttpRequest.create(PersonalApi.class).getAssetsList(type,1,Integer.MAX_VALUE), new OnResultListener() {
            @Override
            public void onWebUiResult(WebMsg webMsg) {
                if (webMsg.dataIsSuccessed()) {
                    AssetsEntity assetsEntity = webMsg.getData(AssetsEntity.class);
                    if(type==1) adapterImg.setAssests(assetsEntity.getRecords());
                    else adapterGif.setAssests(assetsEntity.getRecords());
                } else if (webMsg.netIsSuccessed()) {
                    Toast.show(getContext(), webMsg.getDesc(), 2000);
                }
            }
        });
    }


    public void templateEdit(boolean ifEdit){
        switch (getArguments().getInt("tag")) {
            case 0:
                if(adapterImg!=null){
                    adapterImg.setIfEdit(ifEdit);
                }
                break;
            case 1:
                if(adapterGif!=null){
                    adapterGif.setIfEdit(ifEdit);
                }
                break;
        }
    }

    public ArrayList<Long> getChooseIdList(){
        switch (getArguments().getInt("tag")) {
            case 0:
                if(adapterImg!=null){
                    return adapterImg.getChooseIdList();
                }
                break;
            case 1:
                if(adapterGif!=null){
                    return adapterGif.getChooseIdList();
                }
                break;
        }
        return new ArrayList<>();
    }

    public void allChoose(boolean ifAllChoose){
        switch (getArguments().getInt("tag")) {
            case 0:
                if(adapterImg!=null){
                    adapterImg.allChoose(ifAllChoose);
                }
                break;
            case 1:
                if(adapterGif!=null){
                    adapterGif.allChoose(ifAllChoose);
                }
                break;
        }
    }

    public void deleteSuccess(){
        switch (getArguments().getInt("tag")) {
            case 0:
                if(adapterImg!=null){
                    adapterImg.deleteSuccess();
                }
                break;
            case 1:
                if(adapterGif!=null){
                    adapterGif.deleteSuccess();
                }
                break;
        }
    }

    public ArrayList<String> getChooseUrlList(){
        ArrayList<String> arrayList=new ArrayList<>();
        arrayList.add("http://www.youi-tech.cn/supervisor/public/uploads/images/20190228/f743db257d732392eae731592a557a07.jpeg");
        arrayList.add("http://www.youi-tech.cn/supervisor/public/uploads/images/20190228/0747dd1190a57a037d4e48d6b88acb3b.jpeg");
        arrayList.add("http://www.youi-tech.cn/supervisor/public/uploads/images/20190228/0747dd1190a57a037d4e48d6b88acb3b.jpeg");
        arrayList.add("http://www.youi-tech.cn/supervisor/public/uploads/images/20190228/0747dd1190a57a037d4e48d6b88acb3b.jpeg");
        arrayList.add("http://www.youi-tech.cn/supervisor/public/uploads/images/20190228/0747dd1190a57a037d4e48d6b88acb3b.jpeg");
        arrayList.add("http://www.youi-tech.cn/supervisor/public/uploads/images/20190228/0747dd1190a57a037d4e48d6b88acb3b.jpeg");
        arrayList.add("http://www.youi-tech.cn/supervisor/public/uploads/images/20190228/0747dd1190a57a037d4e48d6b88acb3b.jpeg");
        arrayList.add("http://www.youi-tech.cn/supervisor/public/uploads/images/20190228/0747dd1190a57a037d4e48d6b88acb3b.jpeg");
        arrayList.add("http://www.youi-tech.cn/supervisor/public/uploads/images/20190228/0747dd1190a57a037d4e48d6b88acb3b.jpeg");
        arrayList.add("http://www.youi-tech.cn/supervisor/public/uploads/images/20190228/0747dd1190a57a037d4e48d6b88acb3b.jpeg");
        return arrayList;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
