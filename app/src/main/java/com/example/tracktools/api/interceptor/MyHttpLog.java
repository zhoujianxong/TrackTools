package com.example.tracktools.api.interceptor;
import okhttp3.logging.HttpLoggingInterceptor;

public class MyHttpLog implements HttpLoggingInterceptor.Logger {

    @Override
    public void log(String message) {
//        if (CommUtils.isJson(message)) {
//            ALog.json(message);
//        } else {
//            //判断不是json 的输出
//            if(BuildConfig.DEBUG){
//                Log.v("RetrofitFactory", message);
//            }
//        }
    }
}
