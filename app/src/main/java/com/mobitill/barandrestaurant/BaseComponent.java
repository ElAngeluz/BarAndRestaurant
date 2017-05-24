package com.mobitill.barandrestaurant;

import android.content.Context;

import com.f2prateek.rx.preferences2.RxSharedPreferences;
import com.mobitill.barandrestaurant.data.order.OrderRepository;
import com.mobitill.barandrestaurant.data.order.OrderRepositoryModule;
import com.mobitill.barandrestaurant.data.orderItem.OrderItemRepository;
import com.mobitill.barandrestaurant.data.orderItem.OrderItemRepositoryModule;
import com.mobitill.barandrestaurant.data.product.ProductRepository;
import com.mobitill.barandrestaurant.data.product.ProductRepositoryModule;
import com.mobitill.barandrestaurant.data.waiter.WaitersRepository;
import com.mobitill.barandrestaurant.data.waiter.WaitersRepositoryModule;
import com.mobitill.barandrestaurant.utils.schedulers.BaseScheduleProvider;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by james on 5/24/2017.
 */
@Singleton
@Component(modules = {
        OrderRepositoryModule.class,
        ProductRepositoryModule.class,
        WaitersRepositoryModule.class,
        OrderItemRepositoryModule.class,
        ApplicationModule.class
})
public interface BaseComponent {
    WaitersRepository waitersRepository();
    OrderRepository orderRepository();
    OrderItemRepository orderItemRepository();
    ProductRepository productRepository();
    BaseScheduleProvider baseScheduleProvider();
    RxSharedPreferences rxSharedPreferences();
    Context context();
}
