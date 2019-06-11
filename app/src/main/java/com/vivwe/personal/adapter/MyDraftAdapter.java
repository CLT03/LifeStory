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
import com.vivwe.personal.entity.AssetsEntity;
import com.vivwe.personal.entity.DraftEntity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyDraftAdapter extends RecyclerView.Adapter<MyDraftAdapter.ViewHolder> {


    private Activity activity;
    private RequestOptions requestOptions;
    private ArrayList<DraftEntity> draftEntities;
    private boolean ifEdit;//是否编辑
    private ArrayList<DraftEntity> waitDeleteDrafts;//要删除的素材


    public MyDraftAdapter(Activity activity) {
        this.activity = activity;
        requestOptions = new RequestOptions().centerCrop()
                .placeholder(activity.getResources().getDrawable(R.drawable.ic_launcher_background));
    }

    public void setDraftEntities(ArrayList<DraftEntity> draftEntities) {
        this.draftEntities = draftEntities;
        if (waitDeleteDrafts==null) waitDeleteDrafts=new ArrayList<>();
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_cover)
        ImageView ivCover;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.iv_choose)
        ImageView ivChoose;
        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(activity).inflate(R.layout.item_personal_mydraft, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int i) {
        Glide.with(activity).load(draftEntities.get(i).getImageUrl()).apply(requestOptions).into(holder.ivCover);
        holder.tvTime.setText(draftEntities.get(i).getGmtEndTime().substring(5,16)+"到期");
        if(ifEdit){
            holder.ivChoose.setVisibility(View.VISIBLE);
            if(waitDeleteDrafts.contains(draftEntities.get(i))) holder.ivChoose.setImageDrawable(activity.getResources().getDrawable(R.mipmap.icon_checked));
            else holder.ivChoose.setImageDrawable(activity.getResources().getDrawable(R.mipmap.icon_check));
        }else holder.ivChoose.setVisibility(View.GONE);

        holder.ivChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(waitDeleteDrafts.contains(draftEntities.get(holder.getAdapterPosition()))){
                    holder.ivChoose.setImageDrawable(activity.getResources().getDrawable(R.mipmap.icon_check));
                    waitDeleteDrafts.remove(draftEntities.get(holder.getAdapterPosition()));
                }else{
                    holder.ivChoose.setImageDrawable(activity.getResources().getDrawable(R.mipmap.icon_checked));
                    waitDeleteDrafts.add(draftEntities.get(holder.getAdapterPosition()));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return draftEntities==null?0:draftEntities.size();
    }

    public void setIfEdit(boolean ifEdit) {
        this.ifEdit = ifEdit;
        notifyDataSetChanged();
    }

    public void allChoose(boolean ifAllChoose){
        if(waitDeleteDrafts!=null){
            if(ifAllChoose){
                waitDeleteDrafts.clear();
            }else{
                waitDeleteDrafts.clear();
                waitDeleteDrafts.addAll(draftEntities);
            }
        }
        notifyDataSetChanged();
    }

    public ArrayList<Integer> getChooseIdList() {
        ArrayList<Integer> chooseIdList=new ArrayList<>();
        if(waitDeleteDrafts!=null) {
            for (int i = 0; i < waitDeleteDrafts.size(); i++) {
                chooseIdList.add(draftEntities.get(i).getId());
            }
        }
        return chooseIdList;
    }

    public void deleteSuccess(){
        draftEntities.removeAll(waitDeleteDrafts);
        waitDeleteDrafts.clear();
        notifyDataSetChanged();
    }


}

