package com.vivwe.personal.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mbs.sdk.net.HttpRequest;
import com.mbs.sdk.net.listener.OnResultListener;
import com.mbs.sdk.net.msg.WebMsg;
import com.vivwe.base.ui.alert.Toast;
import com.vivwe.main.R;
import com.vivwe.personal.api.PersonalApi;
import com.vivwe.personal.entity.AttentionEntity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyAttentionAdapter extends RecyclerView.Adapter<MyAttentionAdapter.ViewHolder> {


    private Activity activity;
    private ArrayList<AttentionEntity.Attention> attentions;
    private RequestOptions requestOptions;
    private int tag;

    public MyAttentionAdapter(Activity activity) {
        this.activity = activity;
        requestOptions = new RequestOptions().centerCrop()
                .placeholder(activity.getResources().getDrawable(R.drawable.ic_launcher_background));
    }

    public void setAttentions(ArrayList<AttentionEntity.Attention> attentions,int tag) {//1 是关注 0 是粉丝
        this.attentions = attentions;
        this.tag=tag;
        if(tag==1) {
            for (int i = 0; i < attentions.size(); i++) {
                attentions.get(i).setIsSub(tag);
            }
        }
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_head)
        ImageView ivHead;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_remark)
        TextView tvRemark;
        @BindView(R.id.btn_attention)
        Button btnAttention;
        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(activity).inflate(R.layout.item_personal_myattention, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int i) {
        Glide.with(activity).load(attentions.get(i).getAvatar()).apply(requestOptions).into(holder.ivHead);
        holder.tvName.setText(attentions.get(i).getNickname());
        holder.tvRemark.setText(attentions.get(i).getSignature());
        if(attentions.get(i).getIsSub()==0){
            holder.btnAttention.setBackground(activity.getDrawable(R.drawable.r2_red_btn));
            holder.btnAttention.setText("关注");
        }else {
            holder.btnAttention.setBackground(activity.getDrawable(R.drawable.r2_gray_btn));
            holder.btnAttention.setText(tag==1?"已关注":"相互关注");
        }
        holder.btnAttention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpRequest.getInstance().excute(HttpRequest.create(PersonalApi.class).attentionOrCancel(attentions.get(holder.getAdapterPosition()).getId()), new OnResultListener() {
                    @Override
                    public void onWebUiResult(WebMsg webMsg) {
                        if (webMsg.dataIsSuccessed()) {
                            if(attentions.get(holder.getAdapterPosition()).getIsSub()==0){
                                attentions.get(holder.getAdapterPosition()).setIsSub(1);
                                holder.btnAttention.setBackground(activity.getDrawable(R.drawable.r2_gray_btn));
                                holder.btnAttention.setText(tag==1?"已关注":"相互关注");
                            }else {
                                attentions.get(holder.getAdapterPosition()).setIsSub(0);
                                holder.btnAttention.setBackground(activity.getDrawable(R.drawable.r2_red_btn));
                                holder.btnAttention.setText("关注");
                            }
                            Toast.show(activity, webMsg.getDesc(), 2000);
                        } else if (webMsg.netIsSuccessed()) {
                            Toast.show(activity, webMsg.getDesc(), 2000);
                        }
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return attentions==null?0:attentions.size();
    }


}
