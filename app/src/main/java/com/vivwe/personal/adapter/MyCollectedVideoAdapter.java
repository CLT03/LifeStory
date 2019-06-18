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
import com.vivwe.personal.entity.TemplateEntity;
import com.vivwe.personal.entity.VideoEntity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyCollectedVideoAdapter extends RecyclerView.Adapter<MyCollectedVideoAdapter.ViewHolder> {



    private Activity activity;
    private ArrayList<VideoEntity.Video> videos;
    private RequestOptions requestOptions;
    private boolean ifEdit;//是否编辑
    private ArrayList<VideoEntity.Video> waitDeleteVideos;//要删除的模板


    public MyCollectedVideoAdapter(Activity activity) {
        this.activity = activity;
        requestOptions = new RequestOptions().centerCrop()
                .placeholder(activity.getResources().getDrawable(R.drawable.ic_launcher_background));
    }

    public void setVideos(ArrayList<VideoEntity.Video> videos) {
        this.videos = videos;
        if(waitDeleteVideos==null) waitDeleteVideos=new ArrayList<>();
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_cover)
        ImageView ivCover;
        @BindView(R.id.iv_choose)
        ImageView ivChoose;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(activity).inflate(R.layout.item_personal_mycollected_video, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int i) {
        Glide.with(activity).load(Globals.URL_QINIU+videos.get(i).getImageUrl()).apply(requestOptions).into(holder.ivCover);
        if(ifEdit){
            holder.ivChoose.setVisibility(View.VISIBLE);
            if(waitDeleteVideos.contains(videos.get(i))) holder.ivChoose.setImageDrawable(activity.getResources().getDrawable(R.mipmap.icon_checked));
            else holder.ivChoose.setImageDrawable(activity.getResources().getDrawable(R.mipmap.icon_check));
        }else holder.ivChoose.setVisibility(View.GONE);

        holder.ivChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(waitDeleteVideos.contains(videos.get(holder.getAdapterPosition()))){
                    holder.ivChoose.setImageDrawable(activity.getResources().getDrawable(R.mipmap.icon_check));
                    waitDeleteVideos.remove(videos.get(holder.getAdapterPosition()));
                }else{
                    holder.ivChoose.setImageDrawable(activity.getResources().getDrawable(R.mipmap.icon_checked));
                    waitDeleteVideos.add(videos.get(holder.getAdapterPosition()));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return videos==null?0:videos.size();
    }

    public void setIfEdit(boolean ifEdit) {
        this.ifEdit = ifEdit;
        notifyDataSetChanged();
    }

    public void allChoose(boolean ifAllChoose){
        if(waitDeleteVideos!=null){
            if(ifAllChoose){
                waitDeleteVideos.clear();
            }else{
                waitDeleteVideos.clear();
                waitDeleteVideos.addAll(videos);
            }
        }
        notifyDataSetChanged();
    }

    public ArrayList<Integer> getChooseIdList() {
        ArrayList<Integer> chooseIdList=new ArrayList<>();
        if(waitDeleteVideos!=null) {
            for (int i = 0; i < waitDeleteVideos.size(); i++) {
                chooseIdList.add(waitDeleteVideos.get(i).getStarId());
            }
        }
        return chooseIdList;
    }

    public void deleteSuccess(){
        videos.removeAll(waitDeleteVideos);
        waitDeleteVideos.clear();
        notifyDataSetChanged();
    }

}


