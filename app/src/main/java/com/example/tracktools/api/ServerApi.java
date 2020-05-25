package com.example.tracktools.api;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ServerApi {


    /**
     * 创建服务
     * @param key
     * @param name
     * @param desc
     * @return
     */
    @POST("add")
    Observable<Object> addServer(@Query("key") String key,
                                        @Query("name") String name,
                                        @Query("desc") String desc);

    /**
     * 删除服务
     * @param key
     * @param sid
     * @return
     */
    @POST("delete")
    Observable<Object> delectServer(@Query("key") String key,
                                 @Query("sid") String sid);

    /**
     * 获取服务
     * @param key
     * @return
     */
    @POST("list")
    Observable<Object> getServer(@Query("key") String key);
}
