package com.vivwe.video.adapter;

import android.app.Activity;
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

import com.vivwe.main.R;
import com.vivwe.video.activity.MusicLibraryActivity;
import com.vivwe.video.ui.MusicPlayer;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MusicLibraryAdapter extends RecyclerView.Adapter<MusicLibraryAdapter.ViewHolder> {


    private Activity activity;
    private Group group;
    private boolean ifPlay;

    public MusicLibraryAdapter(Activity activity) {
        this.activity = activity;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_cover)
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

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(activity).inflate(R.layout.item_video_music_library, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int i) {
        holder.tvCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = holder.getAdapterPosition()%2==1?
                        "http://mp3.djwma.com/mp3/别做爱情傀儡dj【高峰】最新热播.mp3":"http://mp3.djwma.com/mp3/《男人的痛你永远不会懂》急速摇头舞曲.mp3";
                MusicPlayer.getInstance().playMusic(url,holder.tvName.getText().toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }


}
