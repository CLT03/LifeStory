package com.vivwe.video.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.Group;
import android.support.v4.app.INotificationSideChannel;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.vivwe.base.cache.ImageLoaderCache;
import com.vivwe.main.R;
import com.vivwe.video.activity.MusicLibraryActivity;
import com.vivwe.video.entity.MusicEntity;
import com.vivwe.video.ui.MusicPlayer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MusicLibraryAdapter extends RecyclerView.Adapter<MusicLibraryAdapter.ViewHolder> {


    private Context context;
    private List<MusicEntity> datas = new ArrayList<>();
    private Group group;
    private boolean ifPlay;


    public MusicLibraryAdapter(Context context) {
        this.context = context;
    }

    /**
     * 追加数据
     * @param datas
     */
    public void addDatas(List<MusicEntity> datas){
        if(datas != null){
            this.datas.addAll(datas);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return this.datas.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_video_music_library, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.tvName.setText(datas.get(position).getMusicTitle());
        ImageLoaderCache.getInstance().displayImage("http://prj0w0ymc.bkt.clouddn.com/" + datas.get(position).getImageUrl(), holder.tvCover);
        holder.tvTime.setText(String.valueOf(datas.get(position).getSize()));

        holder.tvCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://prj0w0ymc.bkt.clouddn.com/"+datas.get(position).getMusicUrl();
                MusicPlayer.getInstance().playMusic(url,holder.tvName.getText().toString());
            }
        });
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_cover)
        ImageView tvCover;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_time)
        TextView tvTime;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
