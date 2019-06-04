package com.vivwe.video.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.vivwe.base.util.ImageUtils
import com.vivwe.base.util.imgeloader.utils.ImageLoader
import com.vivwe.main.R
import com.vivwe.video.listener.OnVideoCreateImageItemClicListener

/**
 * ahtor: super_link
 * date: 2019/5/22 11:12
 * remark:
 */
class VideoCreateDynamicImagesAdapter(context: Context) : RecyclerView.Adapter<VideoCreateDynamicImagesAdapter.ViewHolder>() {

    lateinit var context: Context
    private var listener: OnVideoCreateImageItemClicListener? = null
    var chooseIndexs: ArrayList<Boolean> = ArrayList<Boolean>()

    var datas: ArrayList<String>?  = ArrayList()
    set(value) {
        if(value == null){
            field?.clear()
        } else {
            field = value
        }
        resetDefaultChooseIndexs()
    }

    var isEdit: Boolean  = false
    set(value) {
        field = value
        resetDefaultChooseIndexs()
    }

    init {
        this.context = context
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(this.context!!).inflate(R.layout.item_video_create_image, null))
    }

    /**
     * 重置选中
     */
    fun resetDefaultChooseIndexs(){
        chooseIndexs.clear()

        for(index in 0 until datas!!.size){
            chooseIndexs.add(false)
        }

        notifyDataSetChanged()
    }

    /**
     * 新增数据
     * @param datas 图片列表
     */
    fun addDatas(datas:ArrayList<String>) {
        if(this.datas == null){
            this.datas = ArrayList()
        }
        this.datas!!.addAll(datas)

        resetDefaultChooseIndexs()
    }

    /**
     *
     * 设置点击事件
     * @param listener 点击事件
     *
     */
    fun setListener(listener: OnVideoCreateImageItemClicListener){
        this.listener = listener
    }

    override fun getItemCount(): Int {
        return datas!!.size + 1
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        if(position == datas!!.size) {
            viewHolder.image.setImageResource(R.mipmap.icon_video_create_addimg)
            viewHolder.editIv.visibility = View.GONE
            viewHolder.chooseIv.visibility = View.GONE
        } else {
            var path = datas!!.get(position)

            // 设置图片
            ImageLoader.getInstance().loadImage(path, viewHolder.image)
            if(isEdit){
                viewHolder.chooseIv.visibility = View.VISIBLE
                viewHolder.editIv.visibility = View.GONE
            } else {
                viewHolder.chooseIv.visibility = View.GONE
                viewHolder.editIv.visibility = View.VISIBLE
            }

            viewHolder.chooseIv.setImageResource(if (chooseIndexs[position]) R.mipmap.icon_checked else R.mipmap.icon_check)
        }

        // 图片的点击事件
        viewHolder.image.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {

                if(isEdit && position != datas!!.size) {
                    chooseIndexs[position] = !chooseIndexs[position]
                    viewHolder.chooseIv.setImageResource(if (chooseIndexs[position]) R.mipmap.icon_checked else R.mipmap.icon_check)
                } else if(listener != null){
                    if(position != datas!!.size){
                        listener!!.onItemClick(datas!!.get(position), position)
                    } else {
                        listener!!.onAddClick()
                    }
                }
            }
        })

        viewHolder.editIv.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                if(listener != null) {
                    listener!!.onEditClick(datas!!.get(position), position)
                }
            }
        })


    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        @BindView(R.id.iv_image)
        lateinit var image: ImageView

        @BindView(R.id.iv_edit)
        lateinit var editIv: ImageView

        @BindView(R.id.iv_choose)
        lateinit var chooseIv: ImageView

        init {
            ButterKnife.bind(this, view)
        }
    }

}