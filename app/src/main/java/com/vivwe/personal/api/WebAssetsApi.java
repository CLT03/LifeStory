package com.vivwe.personal.api;

import com.mbs.sdk.net.msg.WebMsg;
import com.vivwe.personal.constant.AssetTypeEnum;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * ahtor: super_link
 * date: 2019/6/10 15:18
 * remark:
 */
public interface WebAssetsApi {

    /**
     * 查询素材
     * @param pageNum 当前页数
     * @param pageSize 每页条数
     * @param type 类型 1-图片 2-Gif 3-其他
     * @return
     */
    @GET("api/asset")
    Observable<WebMsg> listAssets(@Query("pageNum") int pageNum, @Query("pageSize") int pageSize, @Query("type") AssetTypeEnum type);
}
