package com.vivwe.personal.entity

import com.vivwe.personal.constant.AssetTypeEnum

/**
 * ahtor: super_link
 * date: 2019/6/10 16:03
 * remark: 资源实体
 */
class AssetEntity {
    /** 资源ID */
    var id:Int = 0
    /** 类型 */
    var type:AssetTypeEnum = AssetTypeEnum.IMAGE
    /** 资源路径 */
    var url:String? = null
}