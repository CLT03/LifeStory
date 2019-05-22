package com.vivwe.main.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mbs.sdk.utils.ScreenUtils;
import com.vivwe.base.activity.BaseFragment;
import com.vivwe.main.R;
import com.vivwe.main.adapter.TemplateCollectionAdapter;
import com.vivwe.personal.adapter.MyCollectedDemoAdapter;
import com.vivwe.personal.adapter.RecommendActivityAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * ahtor: super_link
 * date: 2019/4/25 11:00
 * remark: 主页模板
 */
public class TemplateFragment extends BaseFragment {

    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.tv_notice_number)
    TextView tvNoticeNumber;
    @BindView(R.id.recycler_view_collection)
    RecyclerView recyclerViewCollection;
    @BindView(R.id.recycler_view_template)
    RecyclerView recyclerViewTemplate;
    @BindView(R.id.view)
    View view;
    Unbinder unbinder;
    private TemplateCollectionAdapter adapterCollection;
    private MyCollectedDemoAdapter demoAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_template, null);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        ViewGroup.LayoutParams layoutParams1 = view.getLayoutParams();
        layoutParams1.height = ScreenUtils.getStatusHeight(getContext());
        view.setLayoutParams(layoutParams1);
        adapterCollection=new TemplateCollectionAdapter(getActivity());
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewCollection.setLayoutManager(linearLayoutManager);
        recyclerViewCollection.setAdapter(adapterCollection);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerViewTemplate.setLayoutManager(gridLayoutManager);
        demoAdapter = new MyCollectedDemoAdapter(getActivity());
        recyclerViewTemplate.setAdapter(demoAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_search, R.id.iv_search, R.id.iv_notice, R.id.tv_notice_number, R.id.tv_all, R.id.iv_all})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_search:
            case R.id.iv_search:

                break;
            case R.id.iv_notice:
            case R.id.tv_notice_number:

                break;
            case R.id.tv_all:
            case R.id.iv_all:

                break;
        }
    }
}
