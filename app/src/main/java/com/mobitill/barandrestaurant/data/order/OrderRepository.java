package com.mobitill.barandrestaurant.data.order;

import android.support.annotation.NonNull;
import android.util.Log;

import com.mobitill.barandrestaurant.data.Local;
import com.mobitill.barandrestaurant.data.Remote;
import com.mobitill.barandrestaurant.data.order.model.Order;
import com.mobitill.barandrestaurant.data.order.source.local.OrderDataSource;
import com.mobitill.barandrestaurant.utils.schedulers.BaseScheduleProvider;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by james on 5/24/2017.
 */

public class OrderRepository implements OrderDataSource {

    private static final String TAG = OrderRepository.class.getSimpleName();

    private final OrderDataSource mOrderLocalDataSource;
    private final OrderDataSource mOrderRemoteDataSource;

    @NonNull private final BaseScheduleProvider mSchedulerProvider;

    @Inject
    OrderRepository (
            @Local OrderDataSource orderLocalDataSource,
            @Remote OrderDataSource orderRemoteDataSource,
            @NonNull BaseScheduleProvider scheduleProvider
    ){
        checkNotNull(scheduleProvider, "scheduleProvider should not be null");
        mSchedulerProvider = scheduleProvider;
        mOrderLocalDataSource = orderLocalDataSource;
        mOrderRemoteDataSource = orderRemoteDataSource;
    }

    @Override
    public Observable<List<Order>> getAll() {
        return mOrderLocalDataSource
                .getAll()
                .observeOn(mSchedulerProvider.ui());
    }

    @Override
    public Observable<Order> getOne(String id) {
        Log.i(TAG, "getOne: ");
        return mOrderLocalDataSource
                .getOne(id)
                .observeOn(mSchedulerProvider.ui());
    }

    @Override
    public Order save(Order item) {
        return mOrderLocalDataSource.save(item);
    }

    @Override
    public int delete(String id) {
        return mOrderLocalDataSource.delete(id);
    }

    @Override
    public int update(Order item) {
        return mOrderLocalDataSource.update(item);
    }

    @Override
    public void deleteAll() {
        mOrderLocalDataSource.deleteAll();
    }

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
        return mOrderLocalDataSource
                .getOrdersWithSynced(isSynced);
    }

    @Override
    public Observable<List<Order>> getOrdersForCheckout(Integer checkout, Integer checkoutFlagged) {
        return mOrderLocalDataSource.getOrdersForCheckout(checkout, checkoutFlagged);
    }
}
