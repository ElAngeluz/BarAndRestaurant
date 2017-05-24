package com.mobitill.barandrestaurant.data.orderItem.source.remote;

import com.mobitill.barandrestaurant.data.orderItem.OrderItemDataSource;
import com.mobitill.barandrestaurant.data.orderItem.model.OrderItem;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * Created by andronicus on 5/23/2017.
 */
@Singleton
public class OrderItemRemoteDataSource implements OrderItemDataSource{

    private static final String TAG = OrderItemRemoteDataSource.class.getSimpleName();

    @Inject
    OrderItemRemoteDataSource(){}


    @Override
    public Observable<List<OrderItem>> getAll() {
        return null;
    }

    @Override
    public Observable<OrderItem> getOne(String id) {
        return null;
    }

    @Override
    public OrderItem save(OrderItem item) {
        return null;
    }

    @Override
    public int delete(String id) {
        return 0;
    }

    @Override
    public int update(OrderItem item) {
        return 0;
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public OrderItem getLastCreated() {
        return null;
    }

    @Override
    public Observable<OrderItem> getOrderItemWithIdentifier(String identifier) {
        return null;
    }
}
