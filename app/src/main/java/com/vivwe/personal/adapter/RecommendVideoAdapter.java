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
import com.vivwe.base.constant.Globals;
import com.vivwe.main.R;
import com.vivwe.personal.entity.VideoEntity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecommendVideoAdapter extends RecyclerView.Adapter<RecommendVideoAdapter.ViewHolder> {


    private Activity activity;
    private ArrayList<VideoEntity.Video> videos;
    private RequestOptions requestOptions;


    public void setVideos(ArrayList<VideoEntity.Video> videos) {
        this.videos = videos;
        notifyDataSetChanged();
    }

    public RecommendVideoAdapter(Activity activity) {
        this.activity = activity;
        requestOptions = new RequestOptions().centerCrop()
                .placeholder(activity.getResources().getDrawable(R.drawable.ic_launcher_background));
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_cover)
        ImageView ivCover;
        @BindView(R.id.iv_play)
        ImageView ivPlay;
        @BindView(R.id.iv_number)
        TextView ivNumber;
        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(activity).inflate(R.layout.item_personal_recommend_video, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        Glide.with(activity).load(Globals.URL_QINIU+videos.get(i).getImageUrl()).apply(requestOptions).into(holder.ivCover);
        holder.ivNumber.setText(String.valueOf(videos.get(i).getScount()));
    }

    @Override
    public int getItemCount() {
        return videos == null ? 0 : videos.size();
    }


}


