package com.mobitill.barandrestaurant.jobs;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;

/**
 * Created by james on 5/26/2017.
 */

public class OrdersJobCreator implements JobCreator {

    public static final String TAG = OrdersJobCreator.class.getSimpleName();


    @Override
    public Job create(String tag) {
        switch (tag){
            case OrderRequestJob.TAG:
                return new OrderRequestJob();
            default:
                return null;
        }
    }


}
