package com.vivwe.video.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.mbs.sdk.utils.ScreenUtils;
import com.vivwe.base.activity.BaseActivity;
import com.vivwe.base.ui.textview.LinearGradientTextView;
import com.vivwe.main.R;
import com.vivwe.video.adapter.TempalteFragmentPagerAdapter;
import com.vivwe.video.fragment.TemplateItemFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ahtor: super_link
 * date: 2019/5/10 09:38
 * remark: 模板详情（3.1）
 */
public class AllTemplateActivity extends BaseActivity {
    @BindView(R.id.linearlayout)
    LinearLayout linearlayout;
    @BindView(R.id.horizontalScrollView)
    HorizontalScrollView horizontalScrollView;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    String[] titles = new String[]{"推荐", "搞笑", "舞蹈", "游戏", "音乐", "旅行", "宠物", "搞笑", "舞蹈", "游戏", "音乐", "旅行", "宠物"};
    ArrayList<LinearGradientTextView> linearGradientTextViews = new ArrayList<>();
    ArrayList<TemplateItemFragment> fragments = new ArrayList<>();
    int tag = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_all_template);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        for (String title : titles) {
            TemplateItemFragment templateItemFragment = new TemplateItemFragment();
            Bundle bundle = new Bundle();
            bundle.putString("tag", title);
            templateItemFragment.setArguments(bundle);
            fragments.add(templateItemFragment);
        }
        viewPager.setAdapter(new TempalteFragmentPagerAdapter(getSupportFragmentManager(), fragments));
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                linearGradientTextViews.get(i).performClick();

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        for (int i = 0; i < titles.length; i++) {
            final LinearGradientTextView linearGradientTextView = new LinearGradientTextView(this);
            ViewGroup.LayoutParams layoutParams;
            layoutParams = new LinearLayout.LayoutParams(getResources().getDimensionPixelOffset(R.dimen.x94),
                    ViewGroup.LayoutParams.MATCH_PARENT);
            linearGradientTextView.setText(titles[i]);
            linearGradientTextView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            linearGradientTextView.setTextColor(Color.parseColor("#ffffff"));

            // linearGradientTextView.setGravity(Gravity.CENTER);
            if (i == 0) {
                linearGradientTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.x40));
                linearGradientTextView.setTextColor(0xFF52D3FF, 0xFFB35CFF);
            } else {
                linearGradientTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.x32));
                linearGradientTextView.setTextColor(Color.parseColor("#8E8E8E"));
            }
            linearGradientTextView.setLayoutParams(layoutParams);
            linearlayout.addView(linearGradientTextView);
            linearGradientTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (tag != linearGradientTextViews.indexOf(linearGradientTextView)) {
                        linearGradientTextView.setTextColor(0xFF52D3FF, 0xFFB35CFF);
                        linearGradientTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.x40));
                        linearGradientTextViews.get(tag).setTextColor(Color.parseColor("#8E8E8E"));
                        linearGradientTextViews.get(tag).setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.x32));
                        tag = linearGradientTextViews.indexOf(linearGradientTextView);
                        horizontalScrollView.scrollTo(0, 0);
                        int w = horizontalScrollView.getWidth() / 2;
                        int gw = linearGradientTextView.getWidth() / 2;
                        if ((linearGradientTextView.getLeft() + gw) > w) {
                            horizontalScrollView.scrollTo(linearGradientTextView.getLeft() + gw - w, 0);
                        }
                        viewPager.setCurrentItem(linearGradientTextViews.indexOf(linearGradientTextView));
                    }
                }
            });
            linearGradientTextViews.add(linearGradientTextView);
        }
        //  getVideoTypeList();
    }


    @OnClick({R.id.iv_back, R.id.iv_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_search:
                startActivity(new Intent(this,TemplateSearchActivity.class));
                break;
        }
    }
}
