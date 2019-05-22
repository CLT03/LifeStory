package com.vivwe.video.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.constraint.Group;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.vivwe.main.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MusicLibraryAdapter extends RecyclerView.Adapter<MusicLibraryAdapter.ViewHolder> {


    private Activity activity;
    private Group group;
    private boolean ifPaly;

    public MusicLibraryAdapter(Activity activity) {
        this.activity = activity;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_cover)
        ImageView tvCover;
        @BindView(R.id.iv_play)
        ImageView ivPlay;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.seekBar)
        SeekBar seekBar;
        @BindView(R.id.tv_start)
        TextView tvStart;
        @BindView(R.id.tv_end)
        TextView tvEnd;
        @BindView(R.id.btn_use)
        Button btnUse;
        @BindView(R.id.group_play)
        Group groupPlay;
        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(activity).inflate(R.layout.item_video_music_library, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int i) {
        holder.seekBar.setPadding(0,0,0,0);
        holder.ivPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(group!=null) group.setVisibility(View.GONE);
                holder.groupPlay.setVisibility(View.VISIBLE);
                group=holder.groupPlay;
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }


}
