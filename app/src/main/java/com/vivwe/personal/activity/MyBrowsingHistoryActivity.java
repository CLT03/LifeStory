package com.vivwe.personal.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mbs.sdk.net.HttpRequest;
import com.mbs.sdk.net.listener.OnResultListener;
import com.mbs.sdk.net.msg.WebMsg;
import com.vivwe.base.activity.BaseActivity;
import com.vivwe.main.R;
import com.vivwe.main.api.WebUcenterApi;
import com.vivwe.main.entity.VideoHistoryEntity;
import com.vivwe.personal.adapter.MyBrowsingHistoryAdapter;
import com.vivwe.personal.api.PersonalApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ahtor: super_link
 * date: 2019/5/7 10:16
 * remark: 我的浏览记录
 */
public class MyBrowsingHistoryActivity extends BaseActivity {

    @BindView(R.id.recycler_view_today)
    RecyclerView recyclerViewToday;
    @BindView(R.id.recycler_view_yesterday)
    RecyclerView recyclerViewYesterday;
    @BindView(R.id.recycler_view_earlier)
    RecyclerView recyclerViewEarlier;
    @BindView(R.id.group_today)
    Group groupToday;
    @BindView(R.id.group_yesterday)
    Group groupYesterday;
    @BindView(R.id.group_earlier)
    Group groupEarlier;
    @BindView(R.id.group_edit)
    Group groupEdit;
    @BindView(R.id.tv_edit)
    TextView tvEdit;
    private boolean ifAllChoose;//是否全选
    private MyBrowsingHistoryAdapter todayAdapter,yesterdayAdapter,earlierAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_mybrowsing_history);
        ButterKnife.bind(this);
        init();
    }

    private void init(){
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewToday.setLayoutManager(linearLayoutManager);
        todayAdapter=new MyBrowsingHistoryAdapter(this);
        recyclerViewToday.setAdapter(todayAdapter);
        LinearLayoutManager linearLayoutManager1=new LinearLayoutManager(this);
        linearLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewYesterday.setLayoutManager(linearLayoutManager1);
        yesterdayAdapter=new MyBrowsingHistoryAdapter(this);
        recyclerViewYesterday.setAdapter(yesterdayAdapter);
        LinearLayoutManager linearLayoutManager2=new LinearLayoutManager(this);
        linearLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewEarlier.setLayoutManager(linearLayoutManager2);
        earlierAdapter=new MyBrowsingHistoryAdapter(this);
        recyclerViewEarlier.setAdapter(earlierAdapter);
        getData();
        //Log.e("ououou"," "+dateDiff("2019-05-28","yyyy-MM-dd")+" "+videoHistoryEntity);
    }

    private void getData(){
        HttpRequest.getInstance().excute(HttpRequest.create(WebUcenterApi.class).getVideoHistory(1,Integer.MAX_VALUE), new OnResultListener() {
            @Override
            public void onWebUiResult(WebMsg webMsg) {
                if (webMsg.dataIsSuccessed()) {
                    VideoHistoryEntity videoHistoryEntity = webMsg.getData(VideoHistoryEntity.class);
                    if(videoHistoryEntity!=null &&videoHistoryEntity.getMyVideoList().size()>0){
                        ArrayList<VideoHistoryEntity.MyVideo> videos=videoHistoryEntity.getMyVideoList();
                        ArrayList<VideoHistoryEntity.MyVideo> todayHistory=new ArrayList<>();
                        ArrayList<VideoHistoryEntity.MyVideo> yesterdayHistory=new ArrayList<>();
                        ArrayList<VideoHistoryEntity.MyVideo> earlierHistory=new ArrayList<>();
                        for(int i=0; i<videos.size();i++){
                            int j=(int)dateDiff(videos.get(i).getGmtModified().substring(0,10),"yyyy-MM-dd");
                            if(j==0)
                                todayHistory.add(videos.get(i));
                            else if(j==1)
                                yesterdayHistory.add(videos.get(i));
                            else  {
                                earlierHistory.addAll(videos.subList(i,videos.size()));
                                break;
                            }
                        }
                        if(todayHistory.size()>0){
                            todayAdapter.setHistoryEntities(todayHistory);
                            groupToday.setVisibility(View.VISIBLE);
                        }
                        if(yesterdayHistory.size()>0){
                            yesterdayAdapter.setHistoryEntities(yesterdayHistory);
                            groupYesterday.setVisibility(View.VISIBLE);
                        }
                        if(earlierHistory.size()>0){
                            earlierAdapter.setHistoryEntities(earlierHistory);
                            groupEarlier.setVisibility(View.VISIBLE);
                        }
                    }
                } else if (webMsg.netIsSuccessed()) {
                    com.vivwe.base.ui.alert.Toast.show(MyBrowsingHistoryActivity.this, webMsg.getDesc(), 2000);
                }
            }
        });
    }

    //计算视频最后修改时候与现在时间的差的天数
    public long dateDiff( String endTime, String format) {
        // 按照传入的格式生成一个simpledateformate对象
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sd = new SimpleDateFormat(format);
        long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
        long diff;
        long day;
        try {
            // 获得两个时间的毫秒时间差异
            diff = System.currentTimeMillis()-sd.parse(endTime).getTime();
            day = diff / nd;// 计算差多少天
            return day;
            //System.out.println("时间相差：" + day );
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @OnClick({R.id.iv_back, R.id.tv_edit, R.id.tv_all, R.id.tv_delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_edit:
                if (groupEdit.getVisibility() == View.VISIBLE) {
                    groupEdit.setVisibility(View.GONE);
                    todayAdapter.setIfEdit(false);
                    yesterdayAdapter.setIfEdit(false);
                    earlierAdapter.setIfEdit(false);
                    tvEdit.setText("编辑");
                } else {
                    groupEdit.setVisibility(View.VISIBLE);
                    todayAdapter.setIfEdit(true);
                    yesterdayAdapter.setIfEdit(true);
                    earlierAdapter.setIfEdit(true);
                    tvEdit.setText("取消");
                }
                break;
            case R.id.tv_all:
                if (ifAllChoose) {
                    todayAdapter.allChoose(false);
                    yesterdayAdapter.allChoose(false);
                    earlierAdapter.allChoose(false);
                    ifAllChoose = false;
                } else {
                    todayAdapter.allChoose(true);
                    yesterdayAdapter.allChoose(true);
                    earlierAdapter.allChoose(true);
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
            todayAdapter.setIfEdit(false);
            yesterdayAdapter.setIfEdit(false);
            earlierAdapter.setIfEdit(false);
            tvEdit.setText("编辑");
        } else super.onBackPressed();
    }

    private void delete() {
        ArrayList<Integer> chooseIdList = new ArrayList<>(todayAdapter.getChooseIdList());
        chooseIdList.addAll(yesterdayAdapter.getChooseIdList());
        chooseIdList.addAll(earlierAdapter.getChooseIdList());
        if (chooseIdList.size() > 0) {
            HttpRequest.getInstance().excute(HttpRequest.create(PersonalApi.class).deleteVideoHistory(chooseIdList), new OnResultListener() {
                @Override
                public void onWebUiResult(WebMsg webMsg) {
                    if (webMsg.dataIsSuccessed()) {
                        com.vivwe.base.ui.alert.Toast.show(MyBrowsingHistoryActivity.this, webMsg.getDesc(), 2000);
                        todayAdapter.deleteSuccess();
                        yesterdayAdapter.deleteSuccess();
                        earlierAdapter.deleteSuccess();
                        if(todayAdapter.getHistoryEntities()==null){
                            groupToday.setVisibility(View.GONE);
                        }
                        if(yesterdayAdapter.getHistoryEntities()==null){
                            groupYesterday.setVisibility(View.GONE);
                        }
                        if(earlierAdapter.getHistoryEntities()==null){
                            groupEarlier.setVisibility(View.GONE);
                        }
                    } else if (webMsg.netIsSuccessed()) {
                        com.vivwe.base.ui.alert.Toast.show(MyBrowsingHistoryActivity.this, webMsg.getDesc(), 2000);
                    }
                }
            });
        } else Toast.makeText(this, "选择不能为空！", Toast.LENGTH_SHORT).show();
    }
}