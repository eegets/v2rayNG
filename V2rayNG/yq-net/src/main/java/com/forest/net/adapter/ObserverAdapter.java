package com.forest.net.adapter;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * Implements all methods, but doesn't do anything
 */
public class ObserverAdapter <T> implements Observer<T> {

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onSubscribe(Disposable disposable) {

    }

    @Override
    public void onNext(T t) {

    }
}
