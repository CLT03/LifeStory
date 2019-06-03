package com.vivwe.personal.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.vivwe.base.activity.BaseActivity;
import com.vivwe.main.R;
import com.vivwe.main.entity.VideoHistoryEntity;
import com.vivwe.personal.adapter.MyBrowsingHistoryAdapter;
import com.vivwe.personal.adapter.MyFansAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
        VideoHistoryEntity videoHistoryEntity=(VideoHistoryEntity)Objects.requireNonNull(getIntent().getExtras()).getSerializable("history");
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
                yesterdayAdapter.setHistoryEntities(todayHistory);
                groupYesterday.setVisibility(View.VISIBLE);
            }
            if(earlierHistory.size()>0){
                earlierAdapter.setHistoryEntities(todayHistory);
                groupEarlier.setVisibility(View.VISIBLE);
            }
        }

        //Log.e("ououou"," "+dateDiff("2019-05-28","yyyy-MM-dd")+" "+videoHistoryEntity);
    }


    public long dateDiff( String endTime, String format) {
        // 按照传入的格式生成一个simpledateformate对象
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sd = new SimpleDateFormat(format);
        long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
        long diff;
        long day;
        try {
            // 获得两个时间的毫秒时间差异
            diff = sd.parse(endTime).getTime()
                    - System.currentTimeMillis();
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
                } else groupEdit.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_all:

                break;
            case R.id.tv_delete:

                break;
        }
    }
}