package com.vivwe.author.adapter;

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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TemplateNoPassAdapter extends RecyclerView.Adapter<TemplateNoPassAdapter.ViewHolder> {

    private Activity activity;
    private ArrayList<TemplateEntity.Template> templates;
    private RequestOptions requestOptions;
    private boolean ifEdit;//是否编辑
    private ArrayList<TemplateEntity.Template> waitDeleteTemplates;//要删除的模板

    public TemplateNoPassAdapter(Activity activity) {
        this.activity = activity;
        requestOptions = new RequestOptions().centerCrop()
                .placeholder(activity.getResources().getDrawable(R.drawable.ic_launcher_background));
    }

    public void setTemplates(ArrayList<TemplateEntity.Template> templates) {
        this.templates = templates;
        if(waitDeleteTemplates==null) waitDeleteTemplates=new ArrayList<>();
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.iv_cover)
        ImageView ivCover;
        @BindView(R.id.tv_money)
        TextView tvMoney;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_material)
        TextView tvMaterial;
        @BindView(R.id.tv_reason)
        TextView tvReason;
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
        View v = LayoutInflater.from(activity).inflate(R.layout.item_author_approve_no_pass, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int i) {
        Glide.with(activity).load(Globals.URL_QINIU+templates.get(i).getImageUrl()).apply(requestOptions).into(holder.ivCover);
        holder.tvMaterial.setText(templates.get(i).getMax_material_count()+"个素材");
        holder.tvMoney.setText("¥"+templates.get(i).getPrice()+"元");
        holder.tvTime.setText(templates.get(i).getMax_duration()+"秒");
        holder.tvTitle.setText(templates.get(i).getTitle());
        holder.tvReason.setText(templates.get(i).getReason());
        if(ifEdit){
            holder.ivChoose.setVisibility(View.VISIBLE);
            if(waitDeleteTemplates.contains(templates.get(i))) holder.ivChoose.setImageDrawable(activity.getResources().getDrawable(R.mipmap.icon_checked));
            else holder.ivChoose.setImageDrawable(activity.getResources().getDrawable(R.mipmap.icon_check));
        }else holder.ivChoose.setVisibility(View.GONE);

        holder.ivChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(waitDeleteTemplates.contains(templates.get(holder.getAdapterPosition()))){
                    holder.ivChoose.setImageDrawable(activity.getResources().getDrawable(R.mipmap.icon_check));
                    waitDeleteTemplates.remove(templates.get(holder.getAdapterPosition()));
                }else{
                    holder.ivChoose.setImageDrawable(activity.getResources().getDrawable(R.mipmap.icon_checked));
                    waitDeleteTemplates.add(templates.get(holder.getAdapterPosition()));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return templates == null ? 0 : templates.size();
    }

    public void setIfEdit(boolean ifEdit) {
        this.ifEdit = ifEdit;
        notifyDataSetChanged();
    }

    public void allChoose(boolean ifAllChoose){
        if(waitDeleteTemplates!=null){
            if(ifAllChoose){
                waitDeleteTemplates.clear();
            }else{
                waitDeleteTemplates.clear();
                waitDeleteTemplates.addAll(templates);
            }
        }
        notifyDataSetChanged();
    }

    public ArrayList<Integer> getChooseIdList() {
        ArrayList<Integer> chooseIdList=new ArrayList<>();
        if(waitDeleteTemplates!=null) {
            for (int i = 0; i < waitDeleteTemplates.size(); i++) {
                chooseIdList.add(waitDeleteTemplates.get(i).getId());
            }
        }
        return chooseIdList;
    }

    public void deleteSuccess(){
        templates.removeAll(waitDeleteTemplates);
        waitDeleteTemplates.clear();
        notifyDataSetChanged();
    }
}
