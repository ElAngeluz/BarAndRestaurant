package com.mobitill.barandrestaurant.utils.schedulers;

import android.support.annotation.NonNull;

import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * Implementation of the {@link V1BaseSchedulerProvider} making all {@link Scheduler}s immediate
 */
public class V1ImmediateSchedulerProvider implements V1BaseSchedulerProvider{
    @NonNull
    @Override
    public Scheduler computation() {
        return Schedulers.immediate();
    }

    @NonNull
    @Override
    public Scheduler io() {
        return Schedulers.immediate();
    }

    @NonNull
    @Override
    public Scheduler ui() {
        return Schedulers.immediate();
    }
}
