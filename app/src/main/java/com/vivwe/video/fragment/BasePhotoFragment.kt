package com.vivwe.video.fragment

import android.graphics.Bitmap
import android.support.v4.app.Fragment

/**
 * ahtor: super_link
 * date: 2019/6/18 15:54
 * remark: 图片编辑BaseFragment
 */
open abstract class BasePhotoFragment : Fragment() {

    var imgPath: Bitmap? = null
    set(value) {
        field = value
        resetImgUI()
    }

    /** 重置UI */
    abstract fun resetImgUI()

}