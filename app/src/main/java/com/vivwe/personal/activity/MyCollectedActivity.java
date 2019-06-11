package com.vivwe.personal.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mbs.sdk.net.HttpRequest;
import com.mbs.sdk.net.listener.OnResultListener;
import com.mbs.sdk.net.msg.WebMsg;
import com.vivwe.base.activity.BaseActivity;
import com.vivwe.main.R;
import com.vivwe.personal.adapter.MycollectedPagerAdapter;
import com.vivwe.personal.api.PersonalApi;
import com.vivwe.personal.entity.TemplateEntity;
import com.vivwe.personal.fragment.MyCollectedFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.vivwe.base.app.MyApplication.getContext;

/**
 * ahtor: super_link
 * date: 2019/4/26 17:59
 * remark: 我的收藏
 */
public class MyCollectedActivity extends BaseActivity {

    @BindView(R.id.group_edit)
    Group groupEdit;
    @BindView(R.id.tv_demo)
    TextView tvDemo;
    @BindView(R.id.view_demo)
    View viewDemo;
    @BindView(R.id.tv_video)
    TextView tvVideo;
    @BindView(R.id.view_video)
    View viewVideo;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tv_edit)
    TextView tvEdit;
    private boolean ifAllChoose;//是否全选
    ArrayList<MyCollectedFragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_mycollected);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        for (int i = 0; i < 2; i++) {
            MyCollectedFragment mycollectedFragment = new MyCollectedFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("tag", i);
            mycollectedFragment.setArguments(bundle);
            fragments.add(mycollectedFragment);
        }
        viewPager.setAdapter(new MycollectedPagerAdapter(getSupportFragmentManager(), fragments));
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i) {
                    case 0:
                        tvDemo.performClick();
                        groupEdit.setVisibility(View.GONE);
                        fragments.get(1).templateEdit(false);
                        tvEdit.setText("编辑");
                        break;
                    case 1:
                        tvVideo.performClick();
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
    }


    @OnClick({R.id.iv_back, R.id.tv_edit, R.id.tv_demo, R.id.view_demo, R.id.tv_video, R.id.view_video, R.id.tv_all, R.id.tv_delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_edit:
                if (groupEdit.getVisibility() == View.VISIBLE) {
                    groupEdit.setVisibility(View.GONE);
                    fragments.get(viewPager.getCurrentItem()).templateEdit(false);
                    tvEdit.setText("编辑");
                } else {
                    groupEdit.setVisibility(View.VISIBLE);
                    fragments.get(viewPager.getCurrentItem()).templateEdit(true);
                    tvEdit.setText("取消");
                }
                break;
            case R.id.tv_demo:
            case R.id.view_demo:
                viewPager.setCurrentItem(0);
                tvDemo.setTextColor(Color.parseColor("#FF5F22"));
                tvVideo.setTextColor(Color.parseColor("#262626"));
                viewDemo.setVisibility(View.VISIBLE);
                viewVideo.setVisibility(View.GONE);
                break;
            case R.id.tv_video:
            case R.id.view_video:
                viewPager.setCurrentItem(1);
                tvVideo.setTextColor(Color.parseColor("#FF5F22"));
                tvDemo.setTextColor(Color.parseColor("#262626"));
                viewDemo.setVisibility(View.GONE);
                viewVideo.setVisibility(View.VISIBLE);
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

    private void delete(){
        if(fragments.get(viewPager.getCurrentItem()).getChooseIdList().size()>0) {
            HttpRequest.getInstance().excute(HttpRequest.create(PersonalApi.class).deleteCollected(fragments.get(viewPager.getCurrentItem()).getChooseIdList()), new OnResultListener() {
                @Override
                public void onWebUiResult(WebMsg webMsg) {
                    if (webMsg.dataIsSuccessed()) {
                        com.vivwe.base.ui.alert.Toast.show(MyCollectedActivity.this, webMsg.getDesc(), 2000);
                        fragments.get(viewPager.getCurrentItem()).deleteSuccess();
                    } else if (webMsg.netIsSuccessed()) {
                        com.vivwe.base.ui.alert.Toast.show(MyCollectedActivity.this, webMsg.getDesc(), 2000);
                    }
                }
            });
        }else Toast.makeText(this, "选择不能为空！", Toast.LENGTH_SHORT).show();
    }


}
