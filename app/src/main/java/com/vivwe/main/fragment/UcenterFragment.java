package com.vivwe.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mbs.sdk.net.HttpRequest;
import com.mbs.sdk.net.listener.OnResultListener;
import com.mbs.sdk.net.msg.WebMsg;
import com.mbs.sdk.utils.ScreenUtils;
import com.vivwe.author.activity.CenterActivity;
import com.vivwe.base.activity.BaseFragment;
import com.vivwe.base.ui.alert.Toast;
import com.vivwe.base.ui.cardstack.CardStack;
import com.vivwe.main.R;
import com.vivwe.main.activity.MessageActivity;
import com.vivwe.main.activity.SettingActivity;
import com.vivwe.main.adapter.UcenterAdvAdapter;
import com.vivwe.main.adapter.UcenterHistoryAdapter;
import com.vivwe.main.api.WebUcenterApi;
import com.vivwe.main.entity.UcenterInfoEntity;
import com.vivwe.main.entity.UserInfoEntity;
import com.vivwe.main.entity.VideoHistoryEntity;
import com.vivwe.personal.activity.MyAssetsActivity;
import com.vivwe.personal.activity.MyAttentionActivity;
import com.vivwe.personal.activity.MyBrowsingHistoryActivity;
import com.vivwe.personal.activity.MyCollectedActivity;
import com.vivwe.personal.activity.MyDraftActivity;
import com.vivwe.personal.activity.MyFansActivity;
import com.vivwe.personal.activity.MyPurchasedActivity;
import com.vivwe.personal.activity.MyVideoActivity;
import com.vivwe.personal.activity.RecommendForYouActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * ahtor: super_link
 * date: 2019/4/25 10:58
 * remark: 个人中心
 */
public class UcenterFragment extends BaseFragment {
    Unbinder unbind;
    @BindView(R.id.recycler_view_history)
    RecyclerView recyclerViewHistory;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_notice_number)
    TextView tvNoticeNumber;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.tv_id)
    TextView tvId;
    @BindView(R.id.tv_attention)
    TextView tvAttention;
    @BindView(R.id.tv_fans)
    TextView tvFans;
    @BindView(R.id.tv_like)
    TextView tvLike;
    @BindView(R.id.tv_purchased)
    TextView tvPurchased;
    @BindView(R.id.tv_collected)
    TextView tvCollected;
    @BindView(R.id.tv_draft)
    TextView tvDraft;
    @BindView(R.id.tv_source)
    TextView tvSource;
    private UcenterHistoryAdapter adapter;
    private VideoHistoryEntity videoHistoryEntity;
    @BindView(R.id.cs_adv)
    CardStack advCs;

    // 广告Adatper
    private UcenterAdvAdapter advAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ucenter, null);
        unbind = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {

        // 初始化广告
        advAdapter = new UcenterAdvAdapter(this.getContext());
        advAdapter.add("test1");
        advAdapter.add("test2");
        advAdapter.add("test3");
        advCs.setContentResource(R.layout.fragment_ucenter_adv);
        advCs.setAdapter(advAdapter);

        if (advCs.getAdapter() != null) {
            Log.i("MyActivity", "Card Stack size: " + advCs.getAdapter().getCount());
        }

        advCs.reset(true);
        if(advAdapter.getCount() > 1) {
            advCs.startTimer();
        }


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewHistory.setLayoutManager(linearLayoutManager);
        adapter = new UcenterHistoryAdapter(getActivity());
        recyclerViewHistory.setAdapter(adapter);
        ViewGroup.LayoutParams layoutParams = tvTitle.getLayoutParams();
        layoutParams.height = ScreenUtils.getStatusHeight(getContext()) + getResources().getDimensionPixelOffset(R.dimen.x88);
        tvTitle.setLayoutParams(layoutParams);
        tvTitle.setPadding(0, ScreenUtils.getStatusHeight(getContext()), 0, 0);
        getData();
    }

    private void getData() {
        HttpRequest.getInstance().excute(HttpRequest.create(WebUcenterApi.class).getUserInfo(), new OnResultListener() {
            @Override
            public void onWebUiResult(WebMsg webMsg) {
                if (webMsg.dataIsSuccessed()) {
                    UcenterInfoEntity userInfoEntity = webMsg.getData(UcenterInfoEntity.class);
                    tvName.setText(userInfoEntity.getNickname());
                    tvId.setText(String.valueOf(userInfoEntity.getId()));
                    tvAttention.setText(String.valueOf(userInfoEntity.getSubNum()));
                    tvFans.setText(String.valueOf(userInfoEntity.getFans()));
                    tvLike.setText(String.valueOf(userInfoEntity.getLikeRecord()));
                } else if (webMsg.netIsSuccessed()) {
                    Toast.show(getContext(), webMsg.getDesc(), 2000);
                }
            }
        });

        HttpRequest.getInstance().excute(HttpRequest.create(WebUcenterApi.class).getUserRecordInfo(), new OnResultListener() {
            @Override
            public void onWebUiResult(WebMsg webMsg) {
                if (webMsg.dataIsSuccessed()) {
                    UcenterInfoEntity userInfoEntity = webMsg.getData(UcenterInfoEntity.class);
                    tvPurchased.setText(String.valueOf(userInfoEntity.getOrderCount()));
                    tvCollected.setText(String.valueOf(userInfoEntity.getStarCount()));
                    tvDraft.setText(String.valueOf(userInfoEntity.getDraftCount()));
                    tvSource.setText(String.valueOf(userInfoEntity.getFodderCount()));
                } else if (webMsg.netIsSuccessed()) {
                    Toast.show(getContext(), webMsg.getDesc(), 2000);
                }
            }
        });

        HttpRequest.getInstance().excute(HttpRequest.create(WebUcenterApi.class).getVideoHistory(1,Integer.MAX_VALUE), new OnResultListener() {
            @Override
            public void onWebUiResult(WebMsg webMsg) {
                if (webMsg.dataIsSuccessed()) {
                    videoHistoryEntity = webMsg.getData(VideoHistoryEntity.class);
                    if(videoHistoryEntity.getMyVideoList().size()>0)
                       adapter.setHistoryEntities(videoHistoryEntity.getMyVideoList());
                    else recyclerViewHistory.setVisibility(View.GONE);
                } else if (webMsg.netIsSuccessed()) {
                    recyclerViewHistory.setVisibility(View.GONE);
                    Toast.show(getContext(), webMsg.getDesc(), 2000);
                }
            }
        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        advCs.stopTimer();
        unbind.unbind();
    }

    @OnClick({R.id.iv_setting, R.id.iv_notice, R.id.iv_attention, R.id.tv_attention, R.id.iv_fans, R.id.tv_fans, R.id.tv_purchased,
            R.id.tv_collected, R.id.tv_draft, R.id.tv_source, R.id.iv_more, R.id.tv_more, R.id.tv_video, R.id.tv_recommend,
            R.id.tv_author_ucenter})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_setting:
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
            case R.id.iv_notice:
                startActivity(new Intent(getActivity(), MessageActivity.class));
                break;
            case R.id.iv_attention:
            case R.id.tv_attention:
                startActivity(new Intent(getActivity(), MyAttentionActivity.class));
                break;
            case R.id.iv_fans:
            case R.id.tv_fans:
                startActivity(new Intent(getActivity(), MyFansActivity.class));
                break;
            case R.id.tv_purchased:
                startActivity(new Intent(getActivity(), MyPurchasedActivity.class));
                break;
            case R.id.tv_collected:
                startActivity(new Intent(getActivity(), MyCollectedActivity.class));
                break;
            case R.id.tv_draft:
                startActivity(new Intent(getActivity(), MyDraftActivity.class));
                break;
            case R.id.tv_source:
                startActivity(new Intent(getActivity(), MyAssetsActivity.class).putExtra("tag",1));
                break;
            case R.id.iv_more:
            case R.id.tv_more:
                Bundle bundle =new Bundle();
                bundle.putSerializable("history",videoHistoryEntity);
                startActivity(new Intent(getActivity(), MyBrowsingHistoryActivity.class).putExtras(bundle));
                break;
            case R.id.tv_video:
                startActivity(new Intent(getActivity(), MyVideoActivity.class));
                break;
            case R.id.tv_recommend:
                startActivity(new Intent(getActivity(), RecommendForYouActivity.class));
                break;
            case R.id.tv_author_ucenter:
                startActivity(new Intent(getActivity(), CenterActivity.class));
                break;
        }
    }
}
