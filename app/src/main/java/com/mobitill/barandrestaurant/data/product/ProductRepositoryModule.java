package com.mobitill.barandrestaurant.data.product;

import com.mobitill.barandrestaurant.data.Local;
import com.mobitill.barandrestaurant.data.Remote;
import com.mobitill.barandrestaurant.data.product.source.local.ProductLocalDataSource;
import com.mobitill.barandrestaurant.data.product.source.remote.ProductRemoteDataSource;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

/**
 * Created by james on 5/12/2017.
 */
@Module
abstract class ProductRepositoryModule {
    @Singleton
    @Binds
    @Remote
    abstract ProductDataSource provideProductRemoteDataSource(ProductRemoteDataSource productRemoteDataSource);

    @Singleton
    @Binds
    @Local
    abstract ProductDataSource provideProductLocalDataSource(ProductLocalDataSource productLocalDataSource);
}
