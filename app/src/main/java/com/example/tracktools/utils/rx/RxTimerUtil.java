package com.example.tracktools.utils.rx;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.blankj.ALog;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Rxjava2.x实现轮询定时器.
 *  2019年04月15日09:07:59
 * @author yzh
 */
public class RxTimerUtil {

    /**
     * 作用1、避免重复执行相同name定时器2,计时结束后取消订阅
     */
    private static Map mDisposableMap=new HashMap();

    /**
     * 是否存在定时器
     * @param name
     */
    public static boolean hasTimer(String name){
        return mDisposableMap.containsKey(name);
    }

    /**
     * x秒后执行next操作
     */
    public static void timer(long seconds, final String name, final IListener next) {
        if(mDisposableMap.containsKey(name)){
            ALog.e(TextUtils.concat("已经有定时器【",name,"】在执行了，本次重复定时器不在执行").toString());
            return;
        }
        Observable.timer(seconds, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable disposable) {

                        mDisposableMap.put(name,disposable);
                    }

                    @Override
                    public void onNext(@NonNull Long number) {
                        if (next != null) {
                            next.onComplete();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        //取消订阅
                        cancel(name);
                    }

                    @Override
                    public void onComplete() {
                        //取消订阅
                        cancel(name);

                    }
                });
    }

    /**
     * xx后执行next操作
     */
    public static void timer(long seconds, final String name, TimeUnit timeUnit,final IListener next) {
        if(mDisposableMap.containsKey(name)){
            ALog.e(TextUtils.concat("已经有定时器【",name,"】在执行了，本次重复定时器不在执行").toString());
            return;
        }
        Observable.timer(seconds,timeUnit)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable disposable) {

                        mDisposableMap.put(name,disposable);
                    }

                    @Override
                    public void onNext(@NonNull Long number) {
                        if (next != null) {
                            next.onComplete();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        //取消订阅
                        cancel(name);
                    }

                    @Override
                    public void onComplete() {
                        //取消订阅
                        cancel(name);

                    }
                });
    }

    /**
     * 每隔milliseconds秒后执行next操作
     * @param milliseconds
     * @param name 给当前定时器命名
     * @param next
     */
    public static void interval(long milliseconds, final String name , final IRxNext next) {
        if(mDisposableMap.containsKey(name)){
            ALog.e(TextUtils.concat("已经有定时器【",name,"】在执行了，本次重复定时器不在执行").toString());
            return;
        }
        Observable.interval(milliseconds, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable disposable) {
                        mDisposableMap.put(name,disposable);
                    }

                    @Override
                    public void onNext(@NonNull Long number) {
                        if (next != null) {
                            next.doNext(number);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        cancel(name);
                        ALog.e("---onError---");
                    }

                    @Override
                    public void onComplete() {
                        cancel(name);
                    }
                });
    }


    /**
     * 每隔xx后执行next操作
     */
    public static void interval(long milliseconds, TimeUnit unit, final String name, final IRxNext next) {
        if(mDisposableMap.containsKey(name)){
            ALog.e(TextUtils.concat("已经有定时器【",name,"】在执行了，本次重复定时器不在执行").toString());
            return;
        }
        Observable.interval(milliseconds,unit)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable disposable) {
                        mDisposableMap.put(name,disposable);
                    }

                    @Override
                    public void onNext(@NonNull Long number) {
                        if (next != null) {
                            next.doNext(number);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        cancel(name);
                    }

                    @Override
                    public void onComplete() {
                        cancel(name);
                    }
                });
    }





    /**
     * 隔xx后执行next操作
     */
    public static void countDownTimer(final long seconds, final String name,TextView tv,ITimer iTimer) {
        if(mDisposableMap.containsKey(name)){
            ALog.e(TextUtils.concat("已经有定时器【",name,"】在执行了，本次重复定时器不在执行").toString());
            return;
        }
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(seconds + 1)
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        return seconds - aLong;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())//ui线程中进行控件更新
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        if(tv!=null){
                            tv.setVisibility(View.VISIBLE);
                            //tv.setTextColor(tv.getContext().getColor(color));
                        }

                    }
                })
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {mDisposableMap.put(name,disposable); }
                    @Override
                    public void onNext(Long num) {
                        //tv.setText("剩余" + num + "秒");

                        if(tv!=null){
                            if(num<=3){
                                //CommUtils.setTextColor(tv, R.color.color_red);
                            }
                            tv.setText(String.valueOf(num));
                        }

                        boolean bool=num==0;
                        iTimer.doNext(num,bool);
                        if(bool){
                            cancel(name);
                        }

                    }
                    @Override
                    public void onError(Throwable e) {cancel(name);}

                    @Override
                    public void onComplete() {
                        //回复原来初始状态
                        // tv.setEnabled(true);
                        // tv.setText("发送验证码");
                        cancel(name);
                        if(tv!=null){
                            tv.setVisibility(View.GONE);
                        }

                    }
                });
    }



    /**
     * 隔xx后执行next操作
     */
    public static void countDownTimer(final long seconds, TimeUnit timeUnit,final String name,ITimer iTimer) {
        if(mDisposableMap.containsKey(name)){
            ALog.e(TextUtils.concat("已经有定时器【",name,"】在执行了，本次重复定时器不在执行").toString());
            return;
        }
        Observable.interval(0, 1,timeUnit)
                .take(seconds + 1)
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        return seconds - aLong;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())//ui线程中进行控件更新
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {
                        mDisposableMap.put(name,disposable);
                    }
                    @Override
                    public void onNext(Long num) {
                        boolean bool=num==0;
                        if(bool){
                            cancel(name);
                        }
                        iTimer.doNext(num,bool);
                    }
                    @Override
                    public void onError(Throwable e) {cancel(name);}

                    @Override
                    public void onComplete() {
                        cancel(name);
                        // iTimer.onComplete();
                    }
                });
    }


    /**
     * 取消订阅
     */
    public static void cancel(@NonNull String ...timerNames) {
        if(timerNames!=null&&timerNames.length>0){
            for (String name:timerNames){
                Disposable mDisposable= (Disposable) mDisposableMap.get(name);
                if (mDisposable != null) {
                    mDisposableMap.remove(name);
                    if(!mDisposable.isDisposed()){
                        mDisposable.dispose();
                        Log.w("RxTimerUtil","---Rx定时器【"+name+"】取消---");
                    }
                }
            }
        }

    }

    public interface IRxNext {
        void doNext(long number);
    }
    public interface ITimer {
        void doNext(long number, boolean complete);
    }

    public interface IListener {
        void onComplete();
    }
}
