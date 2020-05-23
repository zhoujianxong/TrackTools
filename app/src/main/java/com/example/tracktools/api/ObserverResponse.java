package com.example.tracktools.api;

import com.blankj.ALog;
import com.example.tracktools.base.BaseActivity;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class ObserverResponse<T> implements Observer<T> {
    private BaseActivity baseActivity;
    public ObserverResponse(BaseActivity context) {
        this.baseActivity = context;
    }

    public abstract void success(T data);

    public abstract void error(Throwable e);

    public void onFinish() {
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        error(e);

    }



    @Override
    public void onNext(T t) {
        success(t);
    }

    @Override
    public void onSubscribe(Disposable d) {
        if (baseActivity != null) {
            baseActivity.addDisposable(d);
            baseActivity.loadingCount++;
        }
    }

    @Override
    public void onComplete() {
            onFinish();
    }
}
