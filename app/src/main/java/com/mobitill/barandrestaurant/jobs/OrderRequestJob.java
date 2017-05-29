package com.mobitill.barandrestaurant.jobs;

import android.support.annotation.NonNull;
import android.util.Log;

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

//        if(orderRequest()){
//            return Result.SUCCESS;
//        }

        orderRequest();

        return Result.SUCCESS;
    }

    private boolean orderRequest() {
        OrderRequestEngine orderRequestEngine = new OrderRequestEngine();
        orderRequestEngine.orderRequest();
        for(int i = 0; i < 10; i++){
            Log.i(TAG, "orderRequest: " + i);
            return false;
        }
        Log.i(TAG, "orderRequest: ");
        return true;
    }

    public static void scheduleJob(){
        PersistableBundleCompat extras = new PersistableBundleCompat();
        extras.putString(PARAM_ORDER_REQUEST, "name");
        new JobRequest.Builder(OrderRequestJob.TAG)
                .setExecutionWindow(1000L, 5000L)
                .setBackoffCriteria(TimeUnit.MINUTES.toMillis(5), JobRequest.BackoffPolicy.LINEAR )
                .setRequiredNetworkType( JobRequest.NetworkType.ANY )
                .setExtras( extras )
                .setUpdateCurrent(false)
                .setRequirementsEnforced( true )
                .setPersisted( true )
                .build()
                .schedule();
    }

}
