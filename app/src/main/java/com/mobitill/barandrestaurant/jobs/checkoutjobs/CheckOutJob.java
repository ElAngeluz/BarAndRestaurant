package com.mobitill.barandrestaurant.jobs.checkoutjobs;

import android.support.annotation.NonNull;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;

import java.util.concurrent.TimeUnit;

/**
 * Created by dataintegrated on 5/29/2017.
 */

public class CheckOutJob extends Job{

    public static final String TAG = "checkout_job_tag";
    private static final String PARAM_ORDER_CHECKOUT = "order_checkout";

    @NonNull
    @Override
    protected Result onRunJob(Params params) {
        checkout();
        return Result.SUCCESS;
    }

    private void checkout(){
        CheckoutJobEngine checkoutJobEngine = new CheckoutJobEngine();
        checkoutJobEngine.checkout();
    }


    public static void scheduleJob(){
        new JobRequest.Builder(CheckOutJob.TAG)
                .setExecutionWindow(1000L, 5000L)
                .setBackoffCriteria(TimeUnit.MINUTES.toMillis(5), JobRequest.BackoffPolicy.LINEAR )
                .setRequiredNetworkType( JobRequest.NetworkType.ANY )
                .setUpdateCurrent(false)
                .setRequirementsEnforced( true )
                .setPersisted( true )
                .build()
                .schedule();
    }

}
