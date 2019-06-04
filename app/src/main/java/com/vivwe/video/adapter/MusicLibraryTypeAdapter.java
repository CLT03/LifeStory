package com.vivwe.video.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.Group;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vivwe.main.R;
import com.vivwe.video.ui.MusicPlayer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MusicLibraryTypeAdapter extends RecyclerView.Adapter<MusicLibraryTypeAdapter.ViewHolder> {

    /** 上下文 */
    private Context context;
    /** 数据 */
    private List<String> datas = new ArrayList<>();
    /** 选中下标 */
    private int currentIndex = 0;


    public MusicLibraryTypeAdapter(Context context) {
        this.context = context;
    }

    /***
     * 设置显示数据
     * @param datas
     */
    public void setDatas(List<String> datas) {
        if(datas == null){
            this.datas.clear();
        } else {
            this.datas = datas;
        }

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_video_music_library_type, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.lineV.setVisibility(position == currentIndex ? View.VISIBLE: View.GONE);
        holder.nameTv.setText(datas.get(position));
    }

    @Override
    public int getItemCount() {
        return this.datas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        /** 名称 */
        @BindView(R.id.tv_name)
        TextView nameTv;
        /** 选中下划线 */
        @BindView(R.id.v_line)
        View lineV;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }
    }
}
