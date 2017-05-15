package com.mobitill.barandrestaurant.utils.schedulers;

/**
 * Created by james on 5/2/2017.
 */

import android.support.annotation.NonNull;

import rx.Scheduler;


/**
 * Allow providing different types of {@link Scheduler}s
 */

public interface V1BaseSchedulerProvider {
    @NonNull
    Scheduler computation();

    @NonNull
    Scheduler io();

    @NonNull
    Scheduler ui();
}
