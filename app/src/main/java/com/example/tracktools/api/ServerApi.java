package com.example.tracktools.api;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ServerApi {
    @POST("add")
    Observable<String> addServer(@Query("key") String key,
                                        @Query("name") String name,
                                        @Query("desc") String desc);
}
