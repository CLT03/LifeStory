package com.vivwe.personal.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mbs.sdk.net.HttpRequest;
import com.mbs.sdk.net.listener.OnResultListener;
import com.mbs.sdk.net.msg.WebMsg;
import com.vivwe.base.activity.BaseActivity;
import com.vivwe.main.R;
import com.vivwe.personal.adapter.MyAssetsAdapter;
import com.vivwe.personal.api.WebAssetsApi;
import com.vivwe.personal.constant.AssetTypeEnum;
import com.vivwe.personal.entity.AssetEntity;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ahtor: super_link
 * date: 2019/4/26 17:57
 * remark: 我的素材库
 */
public class MyAssetsActivity extends BaseActivity {

    /**
     * 内容展示区
     */
    @BindView(R.id.rcv_content)
    RecyclerView contentRcv;

    /**
     * 1:图片展示， 2：git展示
     */
    AssetTypeEnum assetType = AssetTypeEnum.IMAGE;

    private List<AssetsPager> datas = new ArrayList<>();

    @BindView(R.id.tv_image)
    TextView tvImage;
    @BindView(R.id.view_image)
    View viewImage;
    @BindView(R.id.tv_gif)
    TextView tvGif;
    @BindView(R.id.view_gif)
    View viewGif;
    @BindView(R.id.group_edit)
    Group groupEdit;
    private MyAssetsAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_myassets);
        ButterKnife.bind(this);
        init();
    }

    private void init() {

        datas.add(new AssetsPager());
        datas.add(new AssetsPager());

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        contentRcv.setLayoutManager(gridLayoutManager);
        adapter = new MyAssetsAdapter(this);
        contentRcv.setAdapter(adapter);

        loadData(AssetTypeEnum.IMAGE, true);
    }

    /**
     * 展示图片
     */
    @OnClick(R.id.tv_image)
    public void toImageUI() {
        tvImage.setTextColor(Color.parseColor("#FF5F22"));
        tvGif.setTextColor(Color.parseColor("#262626"));
        viewImage.setVisibility(View.VISIBLE);
        viewGif.setVisibility(View.GONE);
    }

    /**
     * 展示GIF
     */
    @OnClick(R.id.tv_gif)
    public void toGifUI() {
        tvImage.setTextColor(Color.parseColor("#262626"));
        tvGif.setTextColor(Color.parseColor("#FF5F22"));
        viewImage.setVisibility(View.GONE);
        viewGif.setVisibility(View.VISIBLE);
    }

    /**
     * 加载数据
     */
    private void loadData(AssetTypeEnum assetType, boolean hasReload) {
        final AssetsPager pager = datas.get(assetType.getIndex());
        HttpRequest.getInstance().excute(HttpRequest.create(WebAssetsApi.class).listAssets(pager.nextPage(), 20, assetType), new OnResultListener() {
            @Override
            public void onWebUiResult(WebMsg webMsg) {
                if(webMsg.dataIsSuccessed()){
                    pager.toNextPage();
                    AssetsPager data = webMsg.getData(AssetsPager.class);
                    if(data.records!=null&&data.records.size()>0){
                        pager.records.addAll(data.records);
                        adapter.setDatas(pager.records);
                    }
                } else if(webMsg.netIsSuccessed()){
                    Toast.makeText(MyAssetsActivity.this, webMsg.getDesc(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @OnClick(R.id.tv_edit)
    public void toEditUI() {
        if (groupEdit.getVisibility() == View.VISIBLE) {
            groupEdit.setVisibility(View.GONE);
        } else groupEdit.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.iv_back)
    public void back() {
        finish();
    }

    @OnClick({R.id.tv_all, R.id.tv_delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_all:

                break;
            case R.id.tv_delete:

                break;
        }
    }

    /**
     * 资源分页
     */
    public class AssetsPager {
        /** 当前页 */
        private int current = 0;
        /** 返回资源列表 */
        private List<AssetEntity> records = new ArrayList<>();

        public int nextPage(){
            return current + 1;
        }

        public int toNextPage(){
            current ++;
            return current;
        }

        public int getCurrent() {
            return current;
        }

        public void setCurrent(int current) {
            this.current = current;
        }

        public List<AssetEntity> getRecords() {
            return records;
        }

        public void setRecords(List<AssetEntity> records) {
            this.records = records;
        }
    }
}
