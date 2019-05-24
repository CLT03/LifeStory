package com.vivwe.video.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vivwe.main.R;
import com.vivwe.video.ui.MyRecyclerViewInner;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoToShowCommendAdapter extends RecyclerView.Adapter<VideoToShowCommendAdapter.ViewHolder> {

    private Activity activity;

    public VideoToShowCommendAdapter(Activity activity) {
        this.activity = activity;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_head)
        ImageView ivHead;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_comment)
        TextView tvComment;
        @BindView(R.id.iv_like)
        ImageView ivLike;
        @BindView(R.id.tv_like)
        TextView tvLike;
        @BindView(R.id.recyclerView)
        MyRecyclerViewInner recyclerView;
        @BindView(R.id.tv_open)
        TextView tvOpen;
        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(activity).inflate(R.layout.item_video_to_show_comment, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        holder.recyclerView.setLayoutManager(linearLayoutManager);
        VideoToShowCommendToCommentAdapter adapter=new VideoToShowCommendToCommentAdapter(activity);
        holder.recyclerView.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return 10;
    }


}
