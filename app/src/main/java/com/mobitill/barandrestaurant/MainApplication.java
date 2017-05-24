package com.mobitill.barandrestaurant;

import android.app.Application;


/**
 * Created by james on 4/27/2017.
 */

public class MainApplication extends Application {


    private BaseComponent mBaseComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mBaseComponent = DaggerBaseComponent.builder()
                .applicationModule(new ApplicationModule(getApplicationContext()))
                .build();
    }


    public BaseComponent mBaseComponent(){
        return mBaseComponent;
    }
}
