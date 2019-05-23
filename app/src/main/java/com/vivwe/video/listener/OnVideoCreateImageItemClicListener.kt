package com.vivwe.video.listener

/**
 * ahtor: super_link
 * date: 2019/5/22 14:17
 * remark:
 */
interface OnVideoCreateImageItemClicListener {

    /**
     * @param path 路径
     * @param postion 下标
     */
    fun onItemClick(path: String, postion: Int)

    /**
     * 新增
     */
    fun onAddClick()

    /**
     * @param path 路径
     * @param postion 下标
     */
    fun onEditClick(path: String, postion: Int)

}