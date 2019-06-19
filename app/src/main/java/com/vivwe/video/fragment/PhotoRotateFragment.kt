package com.vivwe.video.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import com.vivwe.base.ui.imageview.CropImageView
import com.vivwe.main.R


/**
 * ahtor: super_link
 * date: 2019/6/18 14:58
 * remark:
 */
class PhotoRotateFragment : BasePhotoFragment() {

    @BindView(R.id.eiv_content)
    lateinit var contentEiv: CropImageView

    override fun resetImgUI() {
//        contentEiv.setImage
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_photo_rotate, null)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun init(){

    }

}