package com.mobitill.barandrestaurant.data.orderItem;

import com.mobitill.barandrestaurant.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by andronicus on 5/23/2017.
 */
@Singleton
@Component(modules = {ApplicationModule.class, OrderItemRepositoryModule.class})
public interface OrderItemRepositoryComponent {

    OrderItemRepository orderItemRepository();
}
