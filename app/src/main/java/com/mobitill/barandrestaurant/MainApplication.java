package com.mobitill.barandrestaurant;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.evernote.android.job.JobManager;
import com.mobitill.barandrestaurant.jobs.JobsCreator;


/**
 * Created by james on 4/27/2017.
 */
/*
*
*Extending multiDex application solved NoClassDefFoundError in Lollipop devices thanks to @Arash-gm and @HugoMatilla
* */
public class MainApplication extends MultiDexApplication {

    private BaseComponent mBaseComponent;

    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();

        mBaseComponent = DaggerBaseComponent.builder()
                .applicationModule(new ApplicationModule(getApplicationContext()))
                .build();

        JobManager.create(this).addJobCreator(new JobsCreator());

        MainApplication.sContext = getApplicationContext();
    }

    public BaseComponent mBaseComponent(){
        return mBaseComponent;
    }

    public static Context getAppContext(){
        return  MainApplication.sContext;
    }
}
