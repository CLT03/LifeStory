package com.vivwe.main.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.vivwe.faceunity.base.BaseFragment;
import com.vivwe.main.R;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ahtor: super_link
 * date: 2019/4/25 10:56
 * remark: 推荐模块
 */
public class MainFragment extends BaseFragment {

    @BindViews({R.id.iv_home_icon, R.id.iv_video_icon, R.id.iv_friend_icon, R.id.iv_ucenter_icon})
    List<ImageView> menuIcons;
    @BindViews({R.id.tv_home_label, R.id.tv_video_label, R.id.tv_friend_label, R.id.tv_ucenter_label})
    List<TextView> menuLabels;

    private int menuNormalIcons[] = {R.mipmap.icon_avatart_gay, R.mipmap.icon_recommend_gay,
            R.mipmap.icon_model_gay, R.mipmap.icon_ucenter_gay};
    private int menuFocusIcons[] = {R.mipmap.icon_avatart_black, R.mipmap.icon_recommend_black,
            R.mipmap.icon_model_black, R.mipmap.icon_ucenter_black};

    private List<BaseFragment> fragments = new ArrayList<>();

    @BindView(R.id.fl_content)
    FrameLayout contentFl; // 内容
    private int showIndex = -1;
    private Fragment showFragment;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, null);
        ButterKnife.bind(this, view);
        return view;
    }


    private void init(){
        fragments.add(new HomeFragment());
        fragments.add(new RecommendFragment());
        fragments.add(new TemplateFramgent());
        fragments.add(new UcenterFragment());

        for (BaseFragment fragment : fragments) {
            fragment.setOnFragmentListener(this.getOnFragmentListener());
        }

        showFragment(0);
    }

    /**
     * 显示fragment
     * @param index 下标
     */
    public void showFragment(int index){
        if(showIndex == index){
            return;
        }

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        Fragment tempFramgnet = fragments.get(index);

        if(!tempFramgnet.isAdded()){
            transaction.add(contentFl.getId(), tempFramgnet);
        }

        if(showFragment != null){
            transaction.hide(showFragment);
            menuLabels.get(showIndex).setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimaryBlack, null));
            menuIcons.get(showIndex).setImageResource(menuNormalIcons[showIndex]);
        }

        menuLabels.get(index).setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorBlack, null));
        menuIcons.get(index).setImageResource(menuFocusIcons[index]);

        showFragment = tempFramgnet;
        showIndex = index;
        // 展示当前选中页面并提交
        transaction.show(tempFramgnet).commitAllowingStateLoss();
    }

    @OnClick({R.id.ll_home, R.id.ll_video, R.id.ll_friend, R.id.ll_ucenter})
    public void onClick(View view){
        switch (view.getId()){
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
