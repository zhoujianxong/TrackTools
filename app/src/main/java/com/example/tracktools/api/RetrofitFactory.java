package com.example.tracktools.api;

import com.example.tracktools.api.interceptor.MyHeaderInterceptor;
import com.example.tracktools.api.interceptor.MyHttpLog;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
/**
 * Retrofit  生产类
 */
public class RetrofitFactory {
    public static final int TIMEOUT = 20;
    private static HttpLoggingInterceptor mHttpLoggingInterceptor;
    private static MyHttpLog mPrimaryHttpLog;
    private static MyHeaderInterceptor mPrimaryHeaderInterceptor;

    /**
     * 初始化
     *
     * @return
     */
    public static Retrofit getInstance() {
        initInterceptor();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                //设置连接超时时间
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                //设置读取超时时间
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                //设置写入超时时间
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                //加header  he token
                .addInterceptor(mPrimaryHeaderInterceptor)
                //设置禁止代理
//                .proxy(Proxy.NO_PROXY)
                //请求日志拦截打印
                .addInterceptor(mHttpLoggingInterceptor)
                .build();

        return new Retrofit.Builder()
                .client(okHttpClient)
                // 设置请求的域名
                .baseUrl(ConfigApi.BASE_URL_DEMO)
                // 设置解析转换工厂
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    private static void initInterceptor() {
        if (null == mHttpLoggingInterceptor) {
            if (null == mPrimaryHttpLog) {
                mPrimaryHttpLog = new MyHttpLog();
            }
            mHttpLoggingInterceptor = new HttpLoggingInterceptor(mPrimaryHttpLog);
            mHttpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        }
        if (null == mPrimaryHeaderInterceptor) {
            mPrimaryHeaderInterceptor = new MyHeaderInterceptor();
        }
    }
}
