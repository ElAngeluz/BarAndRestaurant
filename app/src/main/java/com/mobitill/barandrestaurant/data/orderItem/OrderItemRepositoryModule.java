package com.mobitill.barandrestaurant.data.orderItem;

import com.mobitill.barandrestaurant.data.Local;
import com.mobitill.barandrestaurant.data.Remote;
import com.mobitill.barandrestaurant.data.orderItem.source.local.OrderItemLocalDataSource;
import com.mobitill.barandrestaurant.data.orderItem.source.remote.OrderItemRemoteDataSource;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

/**
 * Created by andronicus on 5/23/2017.
 */

@Module
abstract class OrderItemRepositoryModule {

    @Singleton
    @Binds
    @Remote
    abstract OrderItemDataSource provideOrderItemRemoteDataSource(OrderItemRemoteDataSource orderItemRemoteDataSource);

    @Singleton
    @Binds
    @Local
    abstract OrderItemDataSource provideOrderItemLocalDataSource(OrderItemLocalDataSource orderItemLocalDataSource);

}
