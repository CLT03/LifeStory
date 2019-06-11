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
import com.vivwe.personal.entity.AssetsEntity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyBrowsingHistoryAdapter extends RecyclerView.Adapter<MyBrowsingHistoryAdapter.ViewHolder> {


    private Activity activity;
    private ArrayList<VideoHistoryEntity.MyVideo> historyEntities;
    private RequestOptions requestOptions;
    private boolean ifEdit;//是否编辑
    private ArrayList<VideoHistoryEntity.MyVideo> waitDeleteHistory;//要删除的历史

    public MyBrowsingHistoryAdapter(Activity activity) {
        this.activity = activity;
        requestOptions=new RequestOptions().centerCrop()
                .placeholder(activity.getResources().getDrawable(R.drawable.ic_launcher_background));
    }

    public void setHistoryEntities(ArrayList<VideoHistoryEntity.MyVideo> historyEntities) {
        this.historyEntities = historyEntities;
        if(waitDeleteHistory==null) waitDeleteHistory=new ArrayList<>();
        notifyDataSetChanged();
    }

    public ArrayList<VideoHistoryEntity.MyVideo> getHistoryEntities() {
        return historyEntities;
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
    public void onBindViewHolder(@NonNull final ViewHolder holder, int i) {
        Glide.with(activity).load(historyEntities.get(i).getImageUrl()).apply(requestOptions).into(holder.ivCover);
        holder.tvTitle.setText(historyEntities.get(i).getVideoTitle());
        if(ifEdit){
            holder.ivChoose.setVisibility(View.VISIBLE);
            if(waitDeleteHistory.contains(historyEntities.get(i))) holder.ivChoose.setImageDrawable(activity.getResources().getDrawable(R.mipmap.icon_checked));
            else holder.ivChoose.setImageDrawable(activity.getResources().getDrawable(R.mipmap.icon_check));
        }else holder.ivChoose.setVisibility(View.GONE);
        holder.ivChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(waitDeleteHistory.contains(historyEntities.get(holder.getAdapterPosition()))){
                    holder.ivChoose.setImageDrawable(activity.getResources().getDrawable(R.mipmap.icon_check));
                    waitDeleteHistory.remove(historyEntities.get(holder.getAdapterPosition()));
                }else{
                    holder.ivChoose.setImageDrawable(activity.getResources().getDrawable(R.mipmap.icon_checked));
                    waitDeleteHistory.add(historyEntities.get(holder.getAdapterPosition()));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return historyEntities==null?0:historyEntities.size();
    }

    public void setIfEdit(boolean ifEdit) {
        this.ifEdit = ifEdit;
        notifyDataSetChanged();
    }

    public void allChoose(boolean ifAllChoose){
        if(waitDeleteHistory!=null){
            if(ifAllChoose){
                waitDeleteHistory.clear();
            }else{
                waitDeleteHistory.clear();
                waitDeleteHistory.addAll(historyEntities);
            }
        }
        notifyDataSetChanged();
    }

    public ArrayList<Integer> getChooseIdList() {
        ArrayList<Integer> chooseIdList=new ArrayList<>();
        if(waitDeleteHistory!=null) {
            for (int i = 0; i < waitDeleteHistory.size(); i++) {
                chooseIdList.add(waitDeleteHistory.get(i).getPlayHistoryId());
            }
        }
        return chooseIdList;
    }

    public void deleteSuccess(){
        historyEntities.removeAll(waitDeleteHistory);
        waitDeleteHistory.clear();
        notifyDataSetChanged();
    }
    


}
