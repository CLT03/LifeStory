package com.vivwe.video.adapter

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import butterknife.BindView
import butterknife.ButterKnife
import com.vivwe.main.R
import com.vivwe.video.model.TemplateModel
import com.vivwe.video.ui.GroupThumbView

/**
 * ahtor: super_link
 * date: 2019/6/11 11:11
 * remark:
 */
class VideoCreateStandaryThumbAdapter: RecyclerView.Adapter<VideoCreateStandaryThumbAdapter.GroupThumbHolder>() {

    private var mTemplateModel: TemplateModel? = null
    private var mSelectedItem: Int = 0
    private var mOnItemSelectedListener: OnItemSelectedListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): GroupThumbHolder {
        val groupThumbView = GroupThumbView(parent.getContext())
        groupThumbView.setBackgroundColor(Color.BLACK)
        return GroupThumbHolder(groupThumbView)
    }

    override fun getItemCount(): Int {
        return if (mTemplateModel == null) 0 else mTemplateModel!!.groupSize
    }

    override fun onBindViewHolder(holder: GroupThumbHolder, position: Int) {
        val thumbView = holder.itemView as GroupThumbView
        thumbView.setAssetGroup(mTemplateModel!!.groups.get(position + 1))
        thumbView.setSelected(position == mSelectedItem)
    }

    fun setTemplateModel(templateModel: TemplateModel) {
        mTemplateModel = templateModel
        notifyDataSetChanged()
    }

    interface OnItemSelectedListener {
        fun onItemSelected(index: Int)
    }

    fun setOnItemSelectedListener(onItemSelectedListener: OnItemSelectedListener) {
        mOnItemSelectedListener = onItemSelectedListener
    }

    inner class GroupThumbHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            val position = adapterPosition
            if (position != mSelectedItem) {
                val lastItem = mSelectedItem
                mSelectedItem = position
                notifyItemChanged(lastItem)
                notifyItemChanged(mSelectedItem)

                if (mOnItemSelectedListener != null) {
                    mOnItemSelectedListener!!.onItemSelected(mSelectedItem)
                }
            }
        }
    }
}