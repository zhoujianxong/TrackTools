package com.example.tracktools.api.function;


import com.blankj.ALog;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * @author : huanzi
 * @date : 2020/3/16
 * desc :  网络异常重连
 */
public class FunctionRx implements Function<Observable<Throwable>, ObservableSource<?>> {

    private int currentRetryCount = 0;
    private int maxCurrentRetryCount = 3;

    @Override
    public ObservableSource<?> apply(Observable<Throwable> throwableObservable) throws Exception {
        return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(Throwable throwable) throws Exception {
                if (throwable instanceof SocketTimeoutException) {
                    if (currentRetryCount < maxCurrentRetryCount) {
                        currentRetryCount++;
                        ALog.i("当前网络环境不佳");
                        //ToastUtils.showLongSafe("当前网络环境不佳");
                        return Observable.just(1).delay(1000, TimeUnit.MILLISECONDS);
                    } else {
                        ALog.i("网络数据获取错误");
                        return Observable.error(throwable);
                    }
                } else {
                    //上传其他异常
                    return Observable.error(throwable);
                }
            }
        });
    }
}
