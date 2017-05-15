package com.mobitill.barandrestaurant;

import android.app.Application;

import com.mobitill.barandrestaurant.data.product.DaggerProductRepositoryComponent;
import com.mobitill.barandrestaurant.data.product.ProductRepositoryComponent;
import com.mobitill.barandrestaurant.data.waiter.DaggerWaitersRepositoryComponent;
import com.mobitill.barandrestaurant.data.waiter.WaitersRepositoryComponent;

/**
 * Created by james on 4/27/2017.
 */

public class MainApplication extends Application {

    private WaitersRepositoryComponent mWaitersRepositoryComponent;
    private ProductRepositoryComponent mProductRepositoryComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mWaitersRepositoryComponent = DaggerWaitersRepositoryComponent.builder()
                .applicationModule(new ApplicationModule(getApplicationContext()))
                .build();

        mProductRepositoryComponent = DaggerProductRepositoryComponent.builder()
                .applicationModule(new ApplicationModule(getApplicationContext()))
                .build();
    }

    public WaitersRepositoryComponent getWaitersRepositoryComponent(){
       return mWaitersRepositoryComponent;
    }

    public ProductRepositoryComponent getProductRepositoryComponent(){
        return mProductRepositoryComponent;
    }
}
