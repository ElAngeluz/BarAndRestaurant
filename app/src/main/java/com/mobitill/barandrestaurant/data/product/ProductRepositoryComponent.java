package com.mobitill.barandrestaurant.data.product;

import com.mobitill.barandrestaurant.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by james on 5/12/2017.
 */
@Singleton
@Component(modules = {ApplicationModule.class, ProductRepositoryModule.class})
public interface ProductRepositoryComponent {
    ProductRepository productRepository();
}
