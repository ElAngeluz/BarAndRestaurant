package com.mobitill.barandrestaurant.data.order;

import com.mobitill.barandrestaurant.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by andronicus on 5/16/2017.
 */

@Singleton
@Component(modules = {OrderRepositoryModule.class, ApplicationModule.class})
public interface OrderRepositoryComponent {
    OrderRepository orderRepository();
}
