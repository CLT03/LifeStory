package com.vivwe.video.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vivwe.base.activity.BaseActivity;
import com.vivwe.base.ui.textview.LinearGradientTextView;
import com.vivwe.main.R;
import com.vivwe.video.adapter.VideoSearchPagerAdapter;
import com.vivwe.video.fragment.VideoSearchFragment;
import com.vivwe.video.ui.flowlayout.FlowLayout;
import com.vivwe.video.ui.flowlayout.TagAdapter;
import com.vivwe.video.ui.flowlayout.TagFlowLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ahtor: super_link
 * date: 2019/5/10 09:38
 * remark: 视频搜索页
 */
public class VideoSearchActivity extends BaseActivity {
    @BindView(R.id.edt_search)
    EditText edtSearch;
    @BindView(R.id.iv_clear)
    ImageView ivClear;
    @BindView(R.id.group_popular)
    Group groupPopular;
    @BindView(R.id.tv_video)
    LinearGradientTextView tvVideo;
    @BindView(R.id.tv_template)
    LinearGradientTextView tvTemplate;
    @BindView(R.id.tv_user)
    LinearGradientTextView tvUser;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.group_result)
    Group groupResult;
    ArrayList<VideoSearchFragment> fragments = new ArrayList<>();
    LinearGradientTextView textViewTag;
    @BindView(R.id.flow_layout)
    TagFlowLayout flowLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_search);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        textViewTag = tvVideo;
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edtSearch.getText().toString().length() > 0)
                    ivClear.setVisibility(View.VISIBLE);
                else {
                    ivClear.setVisibility(View.GONE);
                    groupPopular.setVisibility(View.VISIBLE);
                    groupResult.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //判断是否是“搜索”键
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    fragments.get(0).setKeyWord(edtSearch.getText().toString());
                    fragments.get(1).setKeyWord(edtSearch.getText().toString());
                    fragments.get(2).setKeyWord(edtSearch.getText().toString());
                    fragments.get(0).getData();
                    fragments.get(1).getData();
                    fragments.get(2).getData();
                    groupPopular.setVisibility(View.GONE);
                    groupResult.setVisibility(View.VISIBLE);
                    return true;
                }
                return false;
            }
        });


        for (int i = 0; i < 3; i++) {
            VideoSearchFragment videoSearchFragment = new VideoSearchFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("tag", i);
            videoSearchFragment.setArguments(bundle);
            fragments.add(videoSearchFragment);
        }
        viewPager.setAdapter(new VideoSearchPagerAdapter(getSupportFragmentManager(), fragments));
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i) {
                    case 0:
                        tvVideo.performClick();
                        break;
                    case 1:
                        tvTemplate.performClick();
                        break;
                    case 2:
                        tvUser.performClick();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        edtSearch.requestFocus();
        ArrayList<String> arrayList=new ArrayList<>();
        arrayList.add("sdjfjsdkjfskd");
        arrayList.add("sdjfjsdkjfskd");
        arrayList.add("真的吗等你回家俯拾地芥");
        arrayList.add("sdjfjsdkjfskd");
        arrayList.add("真的吗等你回家俯拾地芥");
        TagAdapter<String> tagAdapter = new TagAdapter<String>(arrayList) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) LayoutInflater.from(VideoSearchActivity.this).inflate(R.layout.item_video_flowlayout,
                        parent, false);
                tv.setText(s);
                return tv;
            }
        };
        flowLayout.setAdapter(tagAdapter);
        flowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                Toast.makeText(VideoSearchActivity.this, "点击了"+position, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }


    @OnClick({R.id.tv_cancel, R.id.iv_clear, R.id.tv_video, R.id.tv_template, R.id.tv_user})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                finish();
                break;
            case R.id.iv_clear:
                edtSearch.setText(null);
                break;
            case R.id.tv_video:
                if (textViewTag != tvVideo) {
                    textViewTag.setTextColor(Color.parseColor("#8E8E8E"));
                    tvVideo.setTextColor(0xFF52D3FF, 0xFFB35CFF);
                    viewPager.setCurrentItem(0);
                    textViewTag = tvVideo;
                }
                break;
            case R.id.tv_template:
                if (textViewTag != tvTemplate) {
                    textViewTag.setTextColor(Color.parseColor("#8E8E8E"));
                    tvTemplate.setTextColor(0xFF52D3FF, 0xFFB35CFF);
                    viewPager.setCurrentItem(1);
                    textViewTag = tvTemplate;
                }
                break;
            case R.id.tv_user:
                if (textViewTag != tvUser) {
                    textViewTag.setTextColor(Color.parseColor("#8E8E8E"));
                    tvUser.setTextColor(0xFF52D3FF, 0xFFB35CFF);
                    viewPager.setCurrentItem(2);
                    textViewTag = tvUser;
                }
                break;
        }
    }
}
