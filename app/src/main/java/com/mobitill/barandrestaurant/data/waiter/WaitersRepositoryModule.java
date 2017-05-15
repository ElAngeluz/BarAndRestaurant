package com.mobitill.barandrestaurant.data.waiter;

import com.mobitill.barandrestaurant.data.Local;
import com.mobitill.barandrestaurant.data.Remote;
import com.mobitill.barandrestaurant.data.waiter.source.local.WaitersLocalDataSource;
import com.mobitill.barandrestaurant.data.waiter.source.remote.WaitersRemoteDataSource;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

/**
 * Created by james on 4/27/2017.
 */
@Module
abstract class WaitersRepositoryModule {
    @Singleton
    @Binds
    @Remote
    abstract WaitersDataSource provideWaitersRemoteDataSource(WaitersRemoteDataSource waitersRemoteDataSource);

    @Singleton
    @Binds
    @Local
    abstract WaitersDataSource provideWaitersLocalDataSource(WaitersLocalDataSource waitersLocalDataSource);

}
