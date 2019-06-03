package com.vivwe.main.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.mbs.sdk.net.HttpRequest;
import com.mbs.sdk.net.listener.OnResultListener;
import com.mbs.sdk.net.msg.WebMsg;
import com.mbs.sdk.utils.ScreenUtils;
import com.vivwe.base.activity.BaseFragment;
import com.vivwe.base.ui.alert.Toast;
import com.vivwe.base.ui.textview.LinearGradientTextView;
import com.vivwe.main.R;
import com.vivwe.main.adapter.RecommendFragmentPagerAdapter;
import com.vivwe.video.activity.VideoSearchActivity;
import com.vivwe.video.api.VideoApi;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * ahtor: super_link
 * date: 2019/4/25 11:00
 * remark: 主页推荐
 */
public class RecommendFragment extends BaseFragment {
    @BindView(R.id.linearlayout)
    LinearLayout linearlayout;
    @BindView(R.id.horizontalScrollView)
    HorizontalScrollView horizontalScrollView;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.view)
    View view;
    String[] titles = new String[]{"推荐", "搞笑", "舞蹈", "游戏", "音乐", "旅行", "宠物", "搞笑", "舞蹈", "游戏", "音乐", "旅行", "宠物"};
    ArrayList<LinearGradientTextView> linearGradientTextViews = new ArrayList<>();
    ArrayList<RecommendItemFragment> fragments = new ArrayList<>();
    int tag = 0;
    Unbinder unbinder;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recommend, null);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        ViewGroup.LayoutParams layoutParams1 = view.getLayoutParams();
        layoutParams1.height = ScreenUtils.getStatusHeight(getContext());
        view.setLayoutParams(layoutParams1);
        for (String title : titles) {
            RecommendItemFragment recommendItemFragment = new RecommendItemFragment();
            Bundle bundle = new Bundle();
            bundle.putString("tag", title);
            recommendItemFragment.setArguments(bundle);
            fragments.add(recommendItemFragment);
        }
        viewPager.setAdapter(new RecommendFragmentPagerAdapter(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), fragments));
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
            final LinearGradientTextView linearGradientTextView = new LinearGradientTextView(getContext());
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

    private void getVideoTypeList(){
        HttpRequest.getInstance().excute(HttpRequest.create(VideoApi.class).getVideoTypeList(), new OnResultListener() {
            @Override
            public void onWebUiResult(WebMsg webMsg) {
                if (webMsg.dataIsSuccessed()) {
                    Toast.show(getContext(), webMsg.getDesc(), 2000);
                } else if (webMsg.netIsSuccessed()) {
                    Toast.show(getContext(), webMsg.getDesc(), 2000);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.iv_search)
    public void onClick() {
        startActivity(new Intent(getActivity(),VideoSearchActivity.class));
    }
}
