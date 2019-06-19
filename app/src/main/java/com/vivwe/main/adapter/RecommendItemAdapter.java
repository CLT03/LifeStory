package com.vivwe.main.adapter;

import android.app.Activity;
import android.content.Intent;
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
import com.vivwe.video.activity.VideoToShowActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecommendItemAdapter extends RecyclerView.Adapter<RecommendItemAdapter.ViewHolder> {



    private Activity activity;
    private RequestOptions requestOptions,requestOptions1;
    private ArrayList<VideoEntity.Video> videos;

    public RecommendItemAdapter(Activity activity) {
        this.activity = activity;
        requestOptions = new RequestOptions().centerCrop()
                .placeholder(activity.getResources().getDrawable(R.drawable.ic_launcher_background));
        requestOptions1= new RequestOptions().circleCrop()
                .placeholder(activity.getResources().getDrawable(R.drawable.ic_launcher_background));
    }

    public void setVideos(ArrayList<VideoEntity.Video> videos,boolean clear) {
        if(clear) this.videos.clear();
        this.videos.addAll(videos);
        notifyDataSetChanged();
    }

    public void setVideos(ArrayList<VideoEntity.Video> videos) {
        this.videos=videos;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_cover)
        ImageView ivCover;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.iv_head)
        ImageView ivHead;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_number)
        TextView tvNumber;
        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(activity).inflate(R.layout.item_recomend_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int i) {
        Glide.with(activity).load(Globals.URL_QINIU+videos.get(i).getImageUrl()).apply(requestOptions).into(holder.ivCover);
        Glide.with(activity).load(Globals.URL_QINIU+videos.get(i).getAvatar()).apply(requestOptions1).into(holder.ivHead);
        holder.tvName.setText(videos.get(i).getNickname());
        holder.tvNumber.setText(String.valueOf(videos.get(i).getPlayCount()));
        holder.tvTitle.setText(videos.get(i).getVideoTitle());
        holder.ivCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(new Intent(activity, VideoToShowActivity.class)
                        .putExtra("videoId",videos.get(holder.getAdapterPosition()).getVid()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return videos==null?0:videos.size();
    }


}
