package com.mobitill.barandrestaurant;

import android.app.Application;
import android.content.Context;

import com.evernote.android.job.JobManager;
import com.mobitill.barandrestaurant.jobs.OrdersJobCreator;


/**
 * Created by james on 4/27/2017.
 */

public class MainApplication extends Application {

    private BaseComponent mBaseComponent;

    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();

        mBaseComponent = DaggerBaseComponent.builder()
                .applicationModule(new ApplicationModule(getApplicationContext()))
                .build();

        JobManager.create(this).addJobCreator(new OrdersJobCreator());

        MainApplication.sContext = getApplicationContext();
    }

    public BaseComponent mBaseComponent(){
        return mBaseComponent;
    }

    public static Context getAppContext(){
        return  MainApplication.sContext;
    }
}
