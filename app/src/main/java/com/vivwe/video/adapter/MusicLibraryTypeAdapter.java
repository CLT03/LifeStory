package com.vivwe.video.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.Group;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vivwe.main.R;
import com.vivwe.video.entity.MusicTypeEntity;
import com.vivwe.video.listener.OnMusicTypeItemClick;
import com.vivwe.video.ui.MusicPlayer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MusicLibraryTypeAdapter extends RecyclerView.Adapter<MusicLibraryTypeAdapter.ViewHolder> {

    /** 上下文 */
    private Context context;
    /** 数据 */
    private List<MusicTypeEntity> datas = new ArrayList<>();
    /** 选中下标 */
    private int currentIndex = 0;
    /** item点击事件 */
    private ViewPager.OnPageChangeListener onPageChangeListener;


    public MusicLibraryTypeAdapter(Context context, ViewPager.OnPageChangeListener onPageChangeListener) {
        this.context = context;
        this.onPageChangeListener = onPageChangeListener;
    }

    public List<MusicTypeEntity> getDatas() {
        return datas;
    }

    /***
     * 设置显示数据
     * @param datas
     */
    public void setDatas(List<MusicTypeEntity> datas) {
        if(datas == null){
            this.datas.clear();
        } else {
            this.datas = datas;
        }

        notifyDataSetChanged();
    }

    /**
     * 设置选中
     * @param currentIndex
     */
    public void setCurrentIndex(int currentIndex){
        this.currentIndex = currentIndex;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_video_music_library_type, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.lineV.setVisibility(position == currentIndex ? View.VISIBLE: View.GONE);
        holder.nameTv.setText(datas.get(position).getTypeName());
        holder.contentV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentIndex != position){
                    currentIndex = position;
                    onPageChangeListener.onPageSelected(position);
                }
            }
        });

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

        View contentV;

        ViewHolder(View view) {
            super(view);
            contentV = view;
            ButterKnife.bind(this,view);
        }
    }
}
