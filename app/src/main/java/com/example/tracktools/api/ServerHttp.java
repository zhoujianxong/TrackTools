package com.example.tracktools.api;

import android.util.Log;

import com.example.tracktools.base.BaseActivity;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ServerHttp {
    /**
     * 服务端 key
     */
    public static final String ServerKEY = "acd8adb36cd58ea22c3a719ca99cb13b";


    /**
     * 添加服务
     *
     * @param activity
     * @param serverName
     */
    public static void addServer(BaseActivity activity, String serverName) {
        HttpReq.getInstance()
                .addServer(ServerKEY, serverName, "add服务1")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ObserverResponse<Object>(activity) {
                    @Override
                    public void success(Object data) {
                        Log.v("TAG", "addServer  " + data.toString());
                    }

                    @Override
                    public void error(Throwable e) {
                        Log.v("TAG", "e  " + e);
                    }
                });
    }

    /**
     * 返回服务器
     * @param activity
     */
    public static void getServer(BaseActivity activity) {
        HttpReq.getInstance()
                .getServer(ServerKEY)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ObserverResponse<Object>(activity) {
                    @Override
                    public void success(Object data) {
                        Log.v("TAG", "getServer  " + data.toString());
                    }

                    @Override
                    public void error(Throwable e) {
                        Log.v("TAG", "e  " + e);
                    }
                });
    }


    /**
     *
     * @param activity
     * @param serverId  "148278"  服务id
     */
    public static void delectServer(BaseActivity activity,String serverId){
                HttpReq.getInstance()
                .delectServer(ServerKEY,serverId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ObserverResponse<Object>(activity) {
                    @Override
                    public void success(Object data) {
                        Log.v("TAG","delectServer  "+data.toString());
                    }

                    @Override
                    public void error(Throwable e) {
                        Log.v("TAG","e  "+e);
                    }
                });
    }




}
