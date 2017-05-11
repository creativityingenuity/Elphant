package com.gxs.elphant.adapter;

import rx.Subscriber;

public abstract class ProgressSubscriber<T> extends Subscriber<T> {

    @Override
    public void onCompleted() {
        endTask();
    }

    public abstract void preTask();

    protected abstract void endTask();
}