package com.vivwe.personal.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.mbs.sdk.net.HttpRequest;
import com.mbs.sdk.net.listener.OnProgressListener;
import com.mbs.sdk.net.listener.OnResultListener;
import com.mbs.sdk.net.msg.WebMsg;
import com.vivwe.base.activity.BaseActivity;
import com.vivwe.base.constant.Globals;
import com.vivwe.base.ui.alert.Loading;
import com.vivwe.base.util.MiscUtil;
import com.vivwe.main.R;
import com.vivwe.personal.adapter.MyAssetsAdapter;
import com.vivwe.personal.adapter.MyAssetsPagerAdapter;
import com.vivwe.personal.adapter.MycollectedPagerAdapter;
import com.vivwe.personal.api.PersonalApi;
import com.vivwe.personal.fragment.MyAssetsFragment;
import com.vivwe.personal.fragment.MyCollectedFragment;
import com.vivwe.personal.ui.ProhibitScrollViewPager;
import com.vivwe.video.activity.MusicLibraryActivity;
import com.vivwe.video.activity.TemplateDetailActivity;
import com.vivwe.video.activity.VideoCreateByDynamicActivity;
import com.vivwe.video.activity.VideoCreateByStandardActivity;
import com.vivwe.video.ui.MusicPlayer;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ahtor: super_link
 * date: 2019/4/26 17:57
 * remark: 我的素材库
 */
public class MyAssetsActivity extends BaseActivity {

    @BindView(R.id.tv_image)
    TextView tvImage;
    @BindView(R.id.view_image)
    View viewImage;
    @BindView(R.id.tv_gif)
    TextView tvGif;
    @BindView(R.id.view_gif)
    View viewGif;
    @BindView(R.id.view_pager)
    ProhibitScrollViewPager viewPager;
    @BindView(R.id.group_edit)
    Group groupEdit;
    @BindView(R.id.tv_edit)
    TextView tvEdit;
    private boolean ifAllChoose;//是否全选
    ArrayList<MyAssetsFragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_myassets);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        for (int i = 0; i < 2; i++) {
            MyAssetsFragment myAssetsFragment = new MyAssetsFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("tag", i);
            myAssetsFragment.setArguments(bundle);
            fragments.add(myAssetsFragment);
        }
        viewPager.setAdapter(new MyAssetsPagerAdapter(getSupportFragmentManager(), fragments));
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i) {
                    case 0:
                        tvImage.performClick();
                        groupEdit.setVisibility(View.GONE);
                        fragments.get(1).templateEdit(false);
                        tvEdit.setText("编辑");
                        break;
                    case 1:
                        tvGif.performClick();
                        groupEdit.setVisibility(View.GONE);
                        fragments.get(0).templateEdit(false);
                        tvEdit.setText("编辑");
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        //如果是合成视频跳过来的不能滑动，只能选择图片
        if (getIntent().getIntExtra("tag", 0) != 0) {
            viewPager.setScanScroll(false);
            tvEdit.setText("完成");
            viewPager.post(new Runnable() {
                @Override
                public void run() {
                    fragments.get(viewPager.getCurrentItem()).templateEdit(true);
                }
            });
        }
    }

/*
    private void init(){
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,3);
        recyclerViewAssets.setLayoutManager(gridLayoutManager);
        adapter=new MyAssetsAdapter(this);
        recyclerViewAssets.setAdapter(adapter);
    }
*/


    @OnClick({R.id.iv_back, R.id.tv_edit, R.id.tv_image, R.id.view_image, R.id.tv_gif, R.id.view_gif, R.id.tv_all, R.id.tv_delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_edit:
                if (getIntent().getIntExtra("tag", 0) == 0) {
                    if (groupEdit.getVisibility() == View.VISIBLE) {
                        groupEdit.setVisibility(View.GONE);
                        fragments.get(viewPager.getCurrentItem()).templateEdit(false);
                        tvEdit.setText("编辑");
                    } else {
                        groupEdit.setVisibility(View.VISIBLE);
                        fragments.get(viewPager.getCurrentItem()).templateEdit(true);
                        tvEdit.setText("取消");
                    }
                }else {//有值就是选择素材调回
                    downloadChooseImg();
                }
                break;
            case R.id.tv_image:
            case R.id.view_image:
                if (getIntent().getIntExtra("tag", 0) == 0) {
                    tvImage.setTextColor(Color.parseColor("#FF5F22"));
                    tvGif.setTextColor(Color.parseColor("#262626"));
                    viewImage.setVisibility(View.VISIBLE);
                    viewGif.setVisibility(View.GONE);
                }
                break;
            case R.id.tv_gif:
            case R.id.view_gif:
                if (getIntent().getIntExtra("tag", 0) == 0) {
                    tvGif.setTextColor(Color.parseColor("#FF5F22"));
                    tvImage.setTextColor(Color.parseColor("#262626"));
                    viewGif.setVisibility(View.VISIBLE);
                    viewImage.setVisibility(View.GONE);
                }
                break;
            case R.id.tv_all:
                if (ifAllChoose) {
                    fragments.get(viewPager.getCurrentItem()).allChoose(false);
                    ifAllChoose = false;
                } else {
                    fragments.get(viewPager.getCurrentItem()).allChoose(true);
                    ifAllChoose = true;
                }
                break;
            case R.id.tv_delete:
                delete();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (groupEdit.getVisibility() == View.VISIBLE) {
            groupEdit.setVisibility(View.GONE);
            fragments.get(viewPager.getCurrentItem()).templateEdit(false);
            tvEdit.setText("编辑");
        } else super.onBackPressed();
    }

    private void delete() {
        if (fragments.get(viewPager.getCurrentItem()).getChooseIdList().size() > 0) {
            HttpRequest.getInstance().excute(HttpRequest.create(PersonalApi.class).deleteAssets(fragments.get(viewPager.getCurrentItem()).getChooseIdList()), new OnResultListener() {
                @Override
                public void onWebUiResult(WebMsg webMsg) {
                    if (webMsg.dataIsSuccessed()) {
                        com.vivwe.base.ui.alert.Toast.show(MyAssetsActivity.this, webMsg.getDesc(), 2000);
                        fragments.get(viewPager.getCurrentItem()).deleteSuccess();
                    } else if (webMsg.netIsSuccessed()) {
                        com.vivwe.base.ui.alert.Toast.show(MyAssetsActivity.this, webMsg.getDesc(), 2000);
                    }
                }
            });
        } else Toast.makeText(this, "选择不能为空！", Toast.LENGTH_SHORT).show();
    }

    private int number;

    private void downloadChooseImg(){
        if (fragments.get(viewPager.getCurrentItem()).getChooseUrlList().size() > 0) {
            final ArrayList<String> filePathList=new ArrayList<>();
            for(int i=0;i<fragments.get(0).getChooseUrlList().size();i++) {
                final File fileDest = new File(getExternalFilesDir("temp"),fragments.get(0).getChooseUrlList().get(i));
                filePathList.add(fileDest.getPath());
                if(!fileDest.exists()) {
                    number++;
                    HttpRequest.getInstance().downloadToExcute(fragments.get(0).getChooseUrlList().get(i), getExternalFilesDir("temp") + "/" + System.currentTimeMillis()+".jpg", new OnProgressListener() {
                        @Override
                        public void onProgress(long currentBytes, long contentLength) {
                            Loading.start(MyAssetsActivity.this, "缓存中...", false);
                        }

                        @Override
                        public void onFinished(WebMsg webMsg) {
                            if (webMsg.dataIsSuccessed()) {
                                number--;
                                if(number==0){
                                    Loading.stop();
                                    Log.e("ououou","result:"+filePathList.toString());
                                    //成功了！
                                }
                            } else {
                                Toast.makeText(MyAssetsActivity.this, "缓冲失败，请重试...", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        } else Toast.makeText(this, "选择不能为空！", Toast.LENGTH_SHORT).show();
    }


}
