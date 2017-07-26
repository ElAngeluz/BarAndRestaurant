package com.mobitill.barandrestaurant.jobs;

import android.support.annotation.NonNull;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;
import com.evernote.android.job.util.support.PersistableBundleCompat;

import java.util.concurrent.TimeUnit;

/**
 * Created by james on 5/26/2017.
 */

public class OrderRequestJob extends Job {

    public static final String TAG = "order_request_job_tag";
    private static final String PARAM_ORDER_REQUEST = "order_request";


    public OrderRequestJob() {

    }

    @NonNull
    @Override
    protected Result onRunJob(Params params) {

        PersistableBundleCompat extras = params.getExtras();
        String name = extras.getString(PARAM_ORDER_REQUEST, "");

        orderRequest();

        return Result.SUCCESS;
    }

    private boolean orderRequest() {

        //if countsync  > o
        OrderRequestEngine orderRequestEngine = new OrderRequestEngine();
//        orderRequestEngine.orderRequest();
      //  while (orderRequestEngine.extractSycA()){
            orderRequestEngine.orderRequestA();
        //}

        //while (orderRequestEngine.extractSycB()){
            orderRequestEngine.orderRequestB();
       // }

        return false;
    }

    public static void scheduleJob(){

        new JobRequest.Builder(OrderRequestJob.TAG)
                .setExecutionWindow(1000L, 5000L)
                .setBackoffCriteria(TimeUnit.MINUTES.toMillis(5), JobRequest.BackoffPolicy.LINEAR )
                .setRequiredNetworkType( JobRequest.NetworkType.ANY )
                .setRequiresDeviceIdle(false)
                .setUpdateCurrent(false)
                .setRequirementsEnforced( true )
                .setPersisted( true )
                .build()
                .schedule();
    }

}
