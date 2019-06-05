package com.vivwe.video.adapter;

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
import com.vivwe.personal.entity.UserEntity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoSearchUserAdapter extends RecyclerView.Adapter<VideoSearchUserAdapter.ViewHolder> {


    private Activity activity;
    private RequestOptions requestOptions;
    private ArrayList<UserEntity.User> users;



    public VideoSearchUserAdapter(Activity activity) {
        this.activity = activity;
        requestOptions = new RequestOptions().circleCrop()
                .placeholder(activity.getResources().getDrawable(R.drawable.ic_launcher_background));
    }


    public void setUsers(ArrayList<UserEntity.User> users) {
        this.users = users;
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
        View v = LayoutInflater.from(activity).inflate(R.layout.item_personal_myfans, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int i) {
        Glide.with(activity).load(users.get(i).getAvatar()).apply(requestOptions).into(holder.ivHead);
        holder.tvName.setText(users.get(i).getNickName());
        holder.tvRemark.setText(users.get(i).getSignature());
        if(users.get(i).getIsSub()==0){
            holder.btnAttention.setBackground(activity.getDrawable(R.drawable.r2_red_btn));
            holder.btnAttention.setText("关注");
        }else {
            holder.btnAttention.setBackground(activity.getDrawable(R.drawable.r2_gray_btn));
            holder.btnAttention.setText("已关注");
        }
        if(users.get(i).getUid()==16){
            holder.btnAttention.setVisibility(View.GONE);
        }else holder.btnAttention.setVisibility(View.VISIBLE);
            holder.btnAttention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpRequest.getInstance().excute(HttpRequest.create(PersonalApi.class).attentionOrCancel(users.get(holder.getAdapterPosition()).getUid()), new OnResultListener() {
                    @Override
                    public void onWebUiResult(WebMsg webMsg) {
                        if (webMsg.dataIsSuccessed()) {
                            if(users.get(holder.getAdapterPosition()).getIsSub()==0){
                                users.get(holder.getAdapterPosition()).setIsSub(1);
                                holder.btnAttention.setBackground(activity.getDrawable(R.drawable.r2_gray_btn));
                                holder.btnAttention.setText("已关注");
                            }else {
                                users.get(holder.getAdapterPosition()).setIsSub(0);
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
        return users==null?0: users.size();
    }


}
