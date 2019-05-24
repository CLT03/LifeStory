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

import com.vivwe.main.R;
import com.vivwe.main.adapter.RecommendItemAdapter;
import com.vivwe.personal.adapter.MyCollectedDemoAdapter;
import com.vivwe.personal.adapter.MyFansAdapter;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_search, null);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init(){
        if (getArguments() != null) {
            switch (getArguments().getInt("tag")){
                case 0:
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
                    recyclerView.setLayoutManager(gridLayoutManager);
                    adapterVideo=new RecommendItemAdapter(getActivity());
                    recyclerView.setAdapter(adapterVideo);
                    break;
                case 1:
                    GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getContext(), 2);
                    recyclerView.setLayoutManager(gridLayoutManager1);
                    adapterDemo = new MyCollectedDemoAdapter(getActivity());
                    recyclerView.setAdapter(adapterDemo);
                    recyclerView.setPadding(getResources().getDimensionPixelOffset(R.dimen.x32),
                            getResources().getDimensionPixelOffset(R.dimen.x24),0,0);
                    break;
                case 2:
                    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    adapterUser=new MyFansAdapter(getActivity());
                    recyclerView.setAdapter(adapterUser);
                    break;
            }
        }
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
