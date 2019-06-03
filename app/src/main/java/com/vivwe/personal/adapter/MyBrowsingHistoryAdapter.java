package com.vivwe.personal.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.vivwe.main.R;
import com.vivwe.main.entity.VideoHistoryEntity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyBrowsingHistoryAdapter extends RecyclerView.Adapter<MyBrowsingHistoryAdapter.ViewHolder> {


    private Activity activity;
    private ArrayList<VideoHistoryEntity.MyVideo> historyEntities;
    private RequestOptions requestOptions;


    public MyBrowsingHistoryAdapter(Activity activity) {
        this.activity = activity;
        requestOptions=new RequestOptions().centerCrop()
                .placeholder(activity.getResources().getDrawable(R.drawable.ic_launcher_background));
    }

    public void setHistoryEntities(ArrayList<VideoHistoryEntity.MyVideo> historyEntities) {
        this.historyEntities = historyEntities;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_choose)
        ImageView ivChoose;
        @BindView(R.id.iv_cover)
        ImageView ivCover;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_progress)
        TextView tvProgress;
        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(activity).inflate(R.layout.item_personal_mybrowsing_history, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Glide.with(activity).load(historyEntities.get(i).getImageUrl()).apply(requestOptions).into(viewHolder.ivCover);
        viewHolder.tvTitle.setText(historyEntities.get(i).getVideoTitle());
    }

    @Override
    public int getItemCount() {
        return historyEntities==null?0:historyEntities.size();
    }


}
