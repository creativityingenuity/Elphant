package com.gxs.elphant.api;

import com.gxs.elphant.entity.NewsEntity;
import com.gxs.elphant.entity.WeiXinEntity;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Call:vipggxs@163.com
 * Created by YT
 */

public interface JuHeAPI {

    @GET("toutiao/index")
    Observable<NewsEntity> getNews(@Query("type") String type, @Query("key") String key);

    @GET("weixin/query")
    Observable<WeiXinEntity> getWeiXinInfo(@Query("pno") int pno, @Query("ps") int ps, @Query("key") String key);
    
}
