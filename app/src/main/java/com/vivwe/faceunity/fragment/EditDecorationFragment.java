package com.vivwe.faceunity.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.faceunity.p2a_art.constant.AvatarConstant;
import com.faceunity.p2a_art.entity.AvatarP2A;
import com.faceunity.p2a_art.entity.BundleRes;
import com.mbs.sdk.utils.ScreenUtils;
import com.vivwe.base.activity.BaseFragment;
import com.vivwe.base.util.MarginUtils;
import com.vivwe.faceunity.adapter.AvatarPartsAdapter;
import com.vivwe.main.R;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.faceunity.p2a_art.entity.AvatarP2A.gender_boy;

/**
 * ahtor: super_link
 * date: 2019/5/9 10:50
 * remark: 编辑装扮
 */
public class EditDecorationFragment extends BaseFragment {

    /** 部件布局 */
    @BindView(R.id.rcv_parts)
    RecyclerView partsRcv;

    /** 形象部件适配器 */
    private AvatarPartsAdapter avatarPartsAdapter;

    /** 状态栏 */
    @BindView(R.id.v_status)
    View statusV;

    @BindViews({R.id.ibtn_cloth, R.id.ibtn_hair, R.id.ibtn_glass, R.id.ibtn_hat})
    ImageButton[] menuIbtns;

    /** 当前选中的装扮类型下标 */
    private int currentIndex = -1;
    private AvatarP2A avatarP2A;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_faceunity_decoration_edit, null);
        ButterKnife.bind(this, v);
        return v;
//        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init();
    }

    private void init(){

        // 设置状态栏高度
        setViewToStatusHeight(statusV);
        avatarP2A = super.avatarP2A.clone();

        // 设置状态栏字体颜色
//        setStatusBarColor(Color.BLACK);

        partsRcv.setLayoutManager(new LinearLayoutManager(this.getActivity(),LinearLayoutManager.VERTICAL,false));
        avatarPartsAdapter = new AvatarPartsAdapter();
        partsRcv.setAdapter(avatarPartsAdapter);

        mainActivity.setGLSurfaceViewSize(getResources().getDimensionPixelSize(R.dimen.x120),
                getResources().getDimensionPixelSize(R.dimen.x98) + ScreenUtils.getStatusHeight(this.getContext()), 0, 0);

        // listener
        avatarPartsAdapter.setBundleResSelectListener(new AvatarPartsAdapter.BundleResSelectListener() {
            @Override
            public void onScenesSelectListener(BundleRes scenes, int position) {
                switch (currentIndex){
                    case 0:
                        avatarP2A.setClothesIndex(position);
                        break;
                    case 1:
                        avatarP2A.setHairIndex(position);
                        break;
                    case 2:
                        avatarP2A.setGlassesIndex(position);
                        break;
                    case 3:
                        avatarP2A.setHatIndex(position);
                        break;
                }
                mAvatarHandle.setAvatar(avatarP2A);

                // 保存avatar
                // updateSaveBtn();
            }
        });

    }

    @OnClick(R.id.tv_face)
    public void toEditFaceFragment(){
        // 展示编辑容貌
        mainActivity.showFragment(EditFaceFragment.class);
    }

    /**
     * 显示衣服组件
     */
    @OnClick(R.id.ibtn_cloth)
    public void showClothParts(){
        avatarPartsAdapter.setDatas(AvatarConstant.clothesBundleRes(gender_boy));
        refreshMenuUI(true, 0);
    }

    /**
     * 显示衣服组件
     */
    @OnClick(R.id.ibtn_hair)
    public void showHairParts(){
        avatarPartsAdapter.setDatas(AvatarConstant.hairBundleRes(gender_boy));
        refreshMenuUI(true, 1);
    }

    /**
     * 显示衣服组件
     */
    @OnClick(R.id.ibtn_glass)
    public void showGlassParts(){
        avatarPartsAdapter.setDatas(AvatarConstant.glassesBundleRes(gender_boy));
        refreshMenuUI(true, 2);
    }

    @OnClick(R.id.ibtn_hat)
    public void showHatParts(){
        avatarPartsAdapter.setDatas(AvatarConstant.hatBundleRes(gender_boy));
        refreshMenuUI(true, 3);
    }

    /**
     * 刷新菜单按钮状态
     * @param isChecked 是否选中
     * @param index 选中下标
     */
    private void refreshMenuUI(boolean isChecked, int index){

        if(index == currentIndex && isChecked && partsRcv.getVisibility() == View.VISIBLE){
            // 重复点击隐藏
            partsRcv.setVisibility(View.INVISIBLE);
            return;
        } else if(index == currentIndex && isChecked){
            partsRcv.setVisibility(View.VISIBLE);
            return;
        }

        Drawable src = null;
        switch (index){
            case 0:
                src = isChecked ? getResources().getDrawable(R.mipmap.icon_faceunity_decoration_clothes_foc, null):
                        getResources().getDrawable(R.mipmap.icon_faceunity_decoration_clothes_foc, null);
                break;
            case 1:
                src = isChecked ? getResources().getDrawable(R.mipmap.icon_faceunity_hair_foc, null):
                        getResources().getDrawable(R.mipmap.icon_faceunity_hair_nor, null);
                break;
            case 2:
                src = isChecked ? getResources().getDrawable(R.mipmap.icon_faceunity_glass_foc, null):
                        getResources().getDrawable(R.mipmap.icon_faceunity_glass_nor, null);
                break;
            case 3:
                src = isChecked ? getResources().getDrawable(R.mipmap.icon_faceunity_hat_foc, null):
                        getResources().getDrawable(R.mipmap.icon_faceunity_hat_nor, null);
                break;
        }

        if(src != null){
            partsRcv.setVisibility(View.VISIBLE);
            menuIbtns[index].setImageDrawable(src);
            if(isChecked){
                refreshMenuUI(false, currentIndex); // 当切换了选中项时，将上一次的状态切换为普通未选中状态
                currentIndex = index;
            }
        }
    }

    @OnClick(R.id.btn_back)
    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        this.getActivity().setTheme(R.style.AppImmersiveTheme);

        mainActivity.setGLSurfaceViewSize(0, 0, 0, getResources().getDimensionPixelSize(R.dimen.x98));
    }

    @Override
    public boolean touchIsCanToParent() {
        return true;
    }
}
