package com.mobitill.barandrestaurant.data.order;

import com.mobitill.barandrestaurant.data.Local;
import com.mobitill.barandrestaurant.data.Remote;
import com.mobitill.barandrestaurant.data.order.source.local.OrderDataSource;
import com.mobitill.barandrestaurant.data.order.source.local.OrderLocalDataSource;
import com.mobitill.barandrestaurant.data.order.source.remote.OrderRemoteDataSource;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

/**
 * Created by andronicus on 5/16/2017.
 */

@Module
public abstract class OrderRepositoryModule {
    @Singleton
    @Binds
    @Remote
    abstract OrderDataSource provideOrderRemoteDataSource (OrderRemoteDataSource orderRemoteDataSource);

    @Singleton
    @Binds
    @Local
    abstract OrderDataSource provideOrderLocalDataSource (OrderLocalDataSource orderLocalDataSource);
}
