package com.vivwe.faceunity.adapter;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.faceunity.p2a_art.entity.BundleRes;
import com.vivwe.base.ui.RoundImageView;
import com.vivwe.main.R;

import static com.vivwe.base.app.MyApplication.getContext;

/**
 * ahtor: super_link
 * date: 2019/5/6 09:20
 * remark: 形象部件适配器
 */
public class AvatarPartsAdapter extends RecyclerView.Adapter<AvatarPartsAdapter.ViewHolder> {

    BundleRes[] bundleRes;
    double[][] colorRes;

    public void setDatas(BundleRes[] bundleRes){
        this.bundleRes = bundleRes;
        this.colorRes = null;
        this.notifyDataSetChanged();
    }

    public void setDatas(double[][] colorRes){
        this.colorRes = colorRes;
        this.bundleRes = null;
        this.notifyDataSetChanged();

    }

    public BundleRes getBundleRes(int position) {
        return bundleRes[position];
    }

    public double[] getColorRes(int position) {
        return colorRes[position];
    }

    @NonNull
    @Override
    public AvatarPartsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new ViewHolder(new RoundImageView(getContext()));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        if(bundleRes != null){
            holder.image.setLayoutParams(new ViewGroup.LayoutParams(getContext().getResources().getDimensionPixelSize(R.dimen.x120),
                    getContext().getResources().getDimensionPixelSize(R.dimen.x194)));
            holder.image.setImageResource(bundleRes[position].resId);
            holder.image.setBackground(Color.TRANSPARENT);
            holder.image.setCornerSize(0);
        } else {
            holder.image.setLayoutParams(new ViewGroup.LayoutParams(getContext().getResources().getDimensionPixelSize(R.dimen.x120),
                    getContext().getResources().getDimensionPixelSize(R.dimen.x120)));
            holder.image.setImageResource(R.drawable.transparent);
            holder.image.setCornerSize(100);
            double[] rgb = colorRes[position];
            holder.image.setPaddingSize(20);
            holder.image.setBackground(Color.argb(255, (int) rgb[0], (int) rgb[1], (int) rgb[2]));
        }

//        holder.image.setBackgroundResource(R.drawable.ra_white_btn);

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mScenesSelectListener != null) {
                    mScenesSelectListener.onScenesSelectListener(bundleRes[position], holder.getLayoutPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {

        int count = colorRes == null ? 0 : colorRes.length;
        count = bundleRes == null ? count : bundleRes.length;

        return count;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        RoundImageView image;

        public ViewHolder(@NonNull RoundImageView itemView) {
            super(itemView);
            image = itemView;
            image.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            image.setLayoutParams(new ViewGroup.LayoutParams(getContext().getResources().getDimensionPixelSize(R.dimen.x120),
                    getContext().getResources().getDimensionPixelSize(R.dimen.x194)));
            image.setBackgroundResource(R.drawable.ra_white_btn);
//            int len = getContext().getResources().getDimensionPixelSize(R.dimen.x10);
            int len = 0;
            image.setPadding(len, 0, len, 0);
        }
    }

    private BundleResSelectListener mScenesSelectListener;

    public void setBundleResSelectListener(BundleResSelectListener scenesSelectListener) {
        this.mScenesSelectListener = scenesSelectListener;
    }

    public interface BundleResSelectListener {
        void onScenesSelectListener(BundleRes scenes, int position);
    }
}
