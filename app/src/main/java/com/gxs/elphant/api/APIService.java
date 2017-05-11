package com.gxs.elphant.api;

import com.gxs.elphant.entity.NewsEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 2
 * 封装所有请求API
 */
public interface APIService {
    @GET("toutiao/index")
    Observable<NewsEntity> getNews(@Query("type") String type, @Query("key") String key);

    /**
     * 测试用例1
     * @param page
     * @return
     */
    @GET("test")
    Call<String> callBook(@Query("page") String page);

    /**
     * 测试用例2
     * @param page
     * @return
     */
    @GET("test")
    Call<String> callBook1(@Query("page") String page);
}
