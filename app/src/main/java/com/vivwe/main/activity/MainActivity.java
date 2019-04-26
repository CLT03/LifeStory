package com.vivwe.main.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.vivwe.base.activity.BaseFragmentActivity;
import com.vivwe.main.R;
import com.vivwe.main.fragment.RecommendFragment;
import com.vivwe.main.fragment.TemplateFramgent;
import com.vivwe.main.fragment.HomeFragment;
import com.vivwe.main.fragment.UcenterFragment;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ahtor: super_link
 * date: 2019/4/23 13:49
 * remark: 主Activity
 */
public class MainActivity extends BaseFragmentActivity {

    private List<Fragment> fragments = new ArrayList<>();

    @BindViews({R.id.iv_home_icon, R.id.iv_video_icon, R.id.iv_friend_icon, R.id.iv_ucenter_icon})
    List<ImageView> menuIcons;
    @BindViews({R.id.tv_home_label, R.id.tv_video_label, R.id.tv_friend_label, R.id.tv_ucenter_label})
    List<TextView> menuLabels;
    @BindView(R.id.id_content)
    FrameLayout contentFl; // 内容

    private Integer showIndex;
    private Fragment showFragment;

    private int menuNormalIcons[] = {R.mipmap.icon_home_n, R.mipmap.icon_video_n, R.mipmap.icon_interactive_n, R.mipmap.icon_my_n};
    private int menuFocusIcons[] = {R.mipmap.icon_home_f, R.mipmap.icon_video_f, R.mipmap.icon_interactive_f, R.mipmap.icon_my_f};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        init();
    }

    private void init(){
        fragments.add(new HomeFragment());
        fragments.add(new RecommendFragment());
        fragments.add(new TemplateFramgent());
        fragments.add(new UcenterFragment());
    }

    /**
     * 显示fragment
     * @param index 下标
     */
    public void showFragment(int index){
        if(showIndex == index){
            return;
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment tempFramgnet = fragments.get(index);

        if(!tempFramgnet.isAdded()){
            transaction.add(contentFl.getId(), tempFramgnet);
        }

        if(showFragment != null){
            transaction.hide(showFragment);
        }

        menuLabels.get(index).setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorBlack, null));
        menuIcons.get(index).setImageResource(menuFocusIcons[index]);

        menuLabels.get(showIndex).setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimaryBlack, null));
        menuIcons.get(showIndex).setImageResource(menuNormalIcons[showIndex]);

        showFragment = tempFramgnet;
        showIndex = index;
        // 展示当前选中页面并提交
        transaction.show(tempFramgnet).commitAllowingStateLoss();
    }

    @OnClick({R.id.ll_home, R.id.ll_video, R.id.ll_friend, R.id.ll_ucenter})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_home:
                showFragment(0);
                break;
            case R.id.ll_video:
                showFragment(1);
                break;
            case R.id.ll_friend:
                showFragment(2);
                break;
            case R.id.ll_ucenter:
                showFragment(3);
                break;
        }
    }
}
