package com.vivwe.main.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.*
import butterknife.OnClick
import butterknife.OnItemClick
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.vivwe.base.cache.ImageLoaderCache
import com.vivwe.main.R

/**
 * ahtor: super_link
 * date: 2019/6/12 15:25
 * remark:
 */
class UcenterAdvAdapter: ArrayAdapter<String> {

    internal var context: Context

    var listener: AdapterView.OnItemClickListener

    var request : RequestOptions

 /*   init {
        this.context = context
        this.listener = listener
    }*/

    constructor (context: Context, listener: AdapterView.OnItemClickListener): super(context, R.layout.fragment_ucenter_adv) {
        this.listener = listener
        this.context = context
        request=RequestOptions().placeholder(context.resources.getDrawable(R.drawable.ic_launcher_background)).fitCenter()
    }

    override fun getView(position: Int, contentView: View?, parent: ViewGroup): View {
//        val v = contentView!!.findViewById<View>(R.id.content) as TextView
        val cl = contentView!!.findViewById<View>(R.id.cl)

        var image: ImageView = contentView!!.findViewById(R.id.iv_image)
        var path:String = getItem(position)

        Glide.with(context).load(path).apply(request).into(image)

//        v.text = getItem(position)
        cl.setOnClickListener {
            listener.onItemClick(null, cl, position, -1)
        }
        return contentView
    }
}