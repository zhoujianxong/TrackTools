package com.example.tracktools.api;


import com.example.tracktools.bean.Data;
import com.example.tracktools.bean.Result;
import com.example.tracktools.bean.ServerBean;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.Query;


public class HttpReq {

    private static volatile HttpReq mInstance;

    public static HttpReq getInstance() {
        if (mInstance == null) {
            synchronized (HttpReq.class) {
                if (mInstance == null) {
                    mInstance = new HttpReq();
                }
            }
        }
        return mInstance;
    }

    /**
     * @param beanObservable
     * @param retryWhenTimeout 为true时，抛异常后会重连3次
     * @param <T>
     * @return
     */
    private <T> Observable<T> requests(Observable<T> beanObservable, boolean retryWhenTimeout) {
        return beanObservable
//                .flatMap((Function<ResultBean, ObservableSource<ResultBean<T>>>) resultBean -> {
//                    if (resultBean != null) {
//                        if (resultBean.getCode() == 403) {
//                            return Observable.error(new TokenExpiredException());
//                        } else if (resultBean.getCode() == CODE_20014) {
//                            return Observable.error(new NotRegisterException());
//                        }
//                    }
//                    return Observable.just(resultBean);
//                })
//                .retryWhen(new TokenExpiredRetry(retryWhenTimeout))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 添加server
     * @param key
     * @param name
     * @param desc
     * @return
     */
    public Observable<Object> addServer(String key, String name, String desc) {
        return requests(RetrofitFactory.getInstance().create(ServerApi.class).addServer(key, name, desc), false);
    }

    /**
     * 获取服务
     * @param key
     * @return
     */
    public Observable<Result<Data<List<ServerBean>>>> getServer(String key){
        return requests(RetrofitFactory.getInstance().create(ServerApi.class).getServer(key),false);
    }

    /**
     * 删除 s
     * @param key
     * @param sid
     * @return
     */
    public Observable<Object> delectServer(String key,String sid){
        return requests(RetrofitFactory.getInstance().create(ServerApi.class).delectServer(key,sid),false);
    }


}
