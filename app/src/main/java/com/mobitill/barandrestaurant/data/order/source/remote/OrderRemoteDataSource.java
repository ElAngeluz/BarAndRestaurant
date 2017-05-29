package com.mobitill.barandrestaurant.data.order.source.remote;

import com.mobitill.barandrestaurant.data.order.model.Order;
import com.mobitill.barandrestaurant.data.order.source.local.OrderDataSource;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * Created by james on 4/27/2017.
 */
@Singleton
public class OrderRemoteDataSource implements OrderDataSource {

    private static final String TAG = OrderRemoteDataSource.class.getSimpleName();

    @Inject
    OrderRemoteDataSource(){}

    @Override
    public Observable<List<Order>> getAll() {
        return null;
    }

    @Override
    public Observable<Order> getOne(String id) {
        return null;
    }

    @Override
    public Order save(Order item) {
        return null;
    }

    @Override
    public int delete(String id) {
        return 0;
    }

    @Override
    public int update(Order item) {
        return 0;
    }

    @Override
    public void deleteAll() {}

    @Override
    public Order getLastCreated() {
        return null;
    }

    @Override
    public Order getOrderFromRowId(Long rowId) {
        return null;
    }

    @Override
    public Observable<List<Order>> getOrdersWithSynced(Integer isSynced) {
        return null;
    }

    @Override
    public Observable<List<Order>> getOrdersForCheckout(Integer checkout, Integer checkoutFlagged) {
        return null;
    }
}