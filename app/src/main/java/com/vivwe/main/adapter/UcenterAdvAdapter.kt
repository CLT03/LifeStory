package com.vivwe.main.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.vivwe.main.R

/**
 * ahtor: super_link
 * date: 2019/6/12 15:25
 * remark:
 */
class UcenterAdvAdapter: ArrayAdapter<String> {

    internal var context: Context
    constructor (context: Context): super(context, R.layout.fragment_ucenter_adv) {
        this.context = context
    }

    override fun getView(position: Int, contentView: View?, parent: ViewGroup): View {
        val v = contentView!!.findViewById<View>(R.id.content) as TextView
        val ll_parent = contentView.findViewById<View>(R.id.ll_parent)
        v.text = getItem(position)
        ll_parent.setOnClickListener { Toast.makeText(context, "点击了 ：$position", Toast.LENGTH_SHORT).show() }
        return contentView
    }
}