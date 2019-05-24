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

    var datas: ArrayList<String>?  = ArrayList()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    lateinit var context: Context
    private var listener: OnVideoCreateImageItemClicListener? = null

    init {
        this.context = context
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(this.context!!).inflate(R.layout.item_video_create_image, null))
    }

    /**
     * 设置数据
     * @param datas 图片列表
     */
    /*fun set(datas: ArrayList<String>){
        this.datas = datas
        notifyDataSetChanged()
    }*/

    /**
     * 新增数据
     * @param datas 图片列表
     */
    fun addDatas(datas:ArrayList<String>) {
        if(this.datas == null){
            this.datas = ArrayList()
        }
        this.datas!!.addAll(datas)
        notifyDataSetChanged()
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
        } else {
            var path = datas!!.get(position)
            viewHolder.editIv.visibility = View.VISIBLE

            // 设置图片

            ImageLoader.getInstance().loadImage(path, viewHolder.image)

//            viewHolder.image.setImageBitmap(ImageUtils.readBitMap(path, true))
        }

        // 图片的点击事件
        viewHolder.image.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                if(listener != null ){

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

        init {
            ButterKnife.bind(this, view)
        }
    }

}