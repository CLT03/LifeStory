package com.vivwe.faceunity.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.faceunity.p2a_art.constant.AvatarConstant;
import com.faceunity.p2a_art.constant.ColorConstant;
import com.mbs.sdk.utils.ScreenUtils;
import com.vivwe.base.activity.BaseFragment;
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
public class EditFaceFragment extends BaseFragment {

    /** 部件布局 */
    @BindView(R.id.rcv_parts)
    RecyclerView partsRcv;

    /** 形象部件适配器 */
    private AvatarPartsAdapter avatarPartsAdapter;

    /** 状态栏 */
    @BindView(R.id.v_status)
    View statusV;

    @BindViews({R.id.ibtn_reset, R.id.ibtn_skin, R.id.ibtn_hollow, R.id.ibtn_lips, R.id.ibtn_beard, R.id.ibtn_eyebrow, R.id.ibtn_pinchingface})
    ImageButton[] menuIbtns;

    /** 当前选中的装扮类型下标 */
    private int currentIndex = -1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_faceunity_face_edit, null);
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

        // 设置状态栏字体颜色
//        setStatusBarColor(Color.BLACK);

        partsRcv.setLayoutManager(new LinearLayoutManager(this.getActivity(),LinearLayoutManager.VERTICAL,false));
        avatarPartsAdapter = new AvatarPartsAdapter();
        partsRcv.setAdapter(avatarPartsAdapter);

        mainActivity.setGLSurfaceViewSize(getResources().getDimensionPixelSize(R.dimen.x120),
                getResources().getDimensionPixelSize(R.dimen.x98) + ScreenUtils.getStatusHeight(this.getContext()), 0, 0);
    }

    @OnClick(R.id.tv_decoration)
    public void toEditDecorationFragment(){
        // 展示编辑容貌
        mainActivity.showFragment(EditDecorationFragment.class);
    }

    /** 重置 */
    @OnClick(R.id.ibtn_reset)
    public void showResetParts(){

    }

    /** 肤色 */
    @OnClick(R.id.ibtn_skin)
    public void showSkinParts(){
//        avatarPartsAdapter.setDatas(AvatarConstant.s);
        avatarPartsAdapter.setDatas(ColorConstant.skin_color);
        refreshMenuUI(true, 1);
    }

    /** 瞳色 */
    @OnClick(R.id.ibtn_hollow)
    public void showHollowParts(){
        avatarPartsAdapter.setDatas(ColorConstant.iris_color);
        refreshMenuUI(true, 2);
    }

    /** 唇色 */
    @OnClick(R.id.ibtn_lips)
    public void showLipsParts(){
        avatarPartsAdapter.setDatas(ColorConstant.lip_color);
        refreshMenuUI(true, 3);
    }

    /** 胡子 */
    @OnClick(R.id.ibtn_beard)
    public void showBeardParts(){
        avatarPartsAdapter.setDatas(avatarP2A.getGender() == gender_boy ? AvatarConstant.beardBundleRes() : AvatarConstant.eyelashBundleRes());
        refreshMenuUI(true, 4);
    }

    /** 眉毛 */
    @OnClick(R.id.ibtn_eyebrow)
    public void showEyebrowParts(){
        avatarPartsAdapter.setDatas(AvatarConstant.eyebrowBundleRes(gender_boy));
        refreshMenuUI(true, 5);
    }

    /** 捏脸 */
    @OnClick(R.id.ibtn_pinchingface)
    public void showPinchngfaceParts(){
//        avatarPartsAdapter.setDatas(AvatarConstant.hatBundleRes(gender_boy));
        refreshMenuUI(true, 6);
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
//                src = isChecked ? getResources().getDrawable(R.mipmap.icon_faceunit_face_reset_foc, null):
//                        getResources().getDrawable(R.mipmap.icon_faceunit_face_reset_noc, null);
                break;
            case 1:
                src = isChecked ? getResources().getDrawable(R.mipmap.icon_faceunit_face_skin_foc, null):
                        getResources().getDrawable(R.mipmap.icon_faceunit_face_skin_noc, null);
                break;
            case 2:
                src = isChecked ? getResources().getDrawable(R.mipmap.icon_faceunit_face_hollow_foc, null):
                        getResources().getDrawable(R.mipmap.icon_faceunit_face_hollow_noc, null);
                break;
            case 3:
                src = isChecked ? getResources().getDrawable(R.mipmap.icon_faceunit_face_lips_foc, null):
                        getResources().getDrawable(R.mipmap.icon_faceunit_face_lips_noc, null);
                break;
            case 4:
                src = isChecked ? getResources().getDrawable(R.mipmap.icon_faceunit_face_beard_foc, null):
                        getResources().getDrawable(R.mipmap.icon_faceunit_face_beard_noc, null);
                break;
            case 5:
                if(avatarP2A.getGender() == gender_boy){ // 胡子
                    src = isChecked ? getResources().getDrawable(R.mipmap.icon_faceunit_face_eyebrow_foc, null):
                            getResources().getDrawable(R.mipmap.icon_faceunit_face_eyebrow_noc, null);
                } else { // 睫毛
                    src = isChecked ? getResources().getDrawable(R.mipmap.icon_faceunit_face_eyelash_foc, null):
                            getResources().getDrawable(R.mipmap.icon_faceunit_face_eyelash_noc, null);
                }

                break;
            case 6:
                src = isChecked ? getResources().getDrawable(R.mipmap.icon_faceunit_face_pinchingface_foc, null):
                        getResources().getDrawable(R.mipmap.icon_faceunit_face_pinchingface_noc, null);
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
