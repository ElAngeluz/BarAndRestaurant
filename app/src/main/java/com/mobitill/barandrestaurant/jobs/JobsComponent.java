package com.mobitill.barandrestaurant.jobs;

import com.mobitill.barandrestaurant.ApplicationModule;
import com.mobitill.barandrestaurant.data.order.OrderRepository;
import com.mobitill.barandrestaurant.data.order.OrderRepositoryModule;
import com.mobitill.barandrestaurant.data.orderItem.OrderItemRepository;
import com.mobitill.barandrestaurant.data.orderItem.OrderItemRepositoryModule;
import com.mobitill.barandrestaurant.data.product.ProductRepository;
import com.mobitill.barandrestaurant.data.product.ProductRepositoryModule;
import com.mobitill.barandrestaurant.data.waiter.WaitersRepository;
import com.mobitill.barandrestaurant.data.waiter.WaitersRepositoryModule;
import com.mobitill.barandrestaurant.jobs.checkoutjobs.CheckoutJobEngine;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by james on 5/26/2017.
 */
@Singleton
@Component(modules = {
        OrderRepositoryModule.class,
        OrderItemRepositoryModule.class,
        WaitersRepositoryModule.class,
        ProductRepositoryModule.class,
        ApplicationModule.class,
        JobsModule.class
})
public interface JobsComponent {
    OrderRepository mOrderRepository();
    OrderItemRepository orderItemRepository();
    WaitersRepository waitersRepository();
    ProductRepository productRepository();
    void inject(OrderRequestEngine orderRequestEngine);
    void inject(OrderRequestCheckOutHandler orderRequestCheckOutHandler);
    void inject(CheckoutJobEngine checkoutJobEngine);
}
