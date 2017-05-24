package com.mobitill.barandrestaurant.data.orderItem;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.mobitill.barandrestaurant.data.Local;
import com.mobitill.barandrestaurant.data.Remote;
import com.mobitill.barandrestaurant.data.orderItem.model.OrderItem;
import com.mobitill.barandrestaurant.utils.schedulers.BaseScheduleProvider;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by andronicus on 5/23/2017.
 */

public class OrderItemRepository implements OrderItemDataSource {

    private static final String TAG = OrderItemRepository.class.getSimpleName();

    private final OrderItemDataSource orderItemRemoteDataSource;
    private final OrderItemDataSource orderItemLocalDataSource;


    @NonNull
    private final BaseScheduleProvider scheduleProvider;

    @Nullable
    Map<String, OrderItem> cachedOrderItems;

    //Marks the data as invalid to force an update
    //the next time the data is loaded
    boolean cachedIsDirty = false;

    @Inject
    public OrderItemRepository(@Remote OrderItemDataSource orderItemRemoteDataSource,
                               @Local OrderItemDataSource orderItemLocalDataSource,
                               @NonNull BaseScheduleProvider scheduleProvider) {
        checkNotNull(scheduleProvider, "scheduleProvider cannot be null");
        this.orderItemRemoteDataSource = orderItemRemoteDataSource;
        this.orderItemLocalDataSource = orderItemLocalDataSource;
        this.scheduleProvider = scheduleProvider;
    }

    /**
     * Get order items from cache, local data source (SQLite) or remote data source,
     * whichever is available first
     */

    @Override
    public Observable<List<OrderItem>> getAll() {
        // Respond immediately with the cache if cached is available and not dirty
        if (cachedOrderItems != null && !cachedIsDirty) {
            return Observable.fromIterable(cachedOrderItems.values()).toList().toObservable();
        } else if (cachedOrderItems == null) {
            cachedOrderItems = new LinkedHashMap<>();
        }

        Observable<List<OrderItem>> remoteOrderItems = getAndSaveRemoteOrderItems();
        //return remoteProducts;


        if (cachedIsDirty) {
            return remoteOrderItems;
        } else {

            //    Query the local storage if available, then query the remote network

            //Query the local storage if available, then query the remote network

            Observable<List<OrderItem>> localOrderItems = getAndCacheLocalOrderItems();
            return Observable.merge(localOrderItems, remoteOrderItems);
        }
    }

    private Observable<List<OrderItem>> getAndCacheLocalOrderItems() {
        return null;
    }

    private Observable<List<OrderItem>> getAndSaveRemoteOrderItems() {
        return orderItemRemoteDataSource
                .getAll()
                .flatMap(orderItems -> Observable
                        .fromArray(orderItems.toArray(new OrderItem[orderItems.size()]))
                        .doOnNext(orderItem -> {
                            orderItemLocalDataSource.getOne(orderItem.getOrderId())
                                    .subscribe(
                                            product1 -> {
                                                if (product1 != null) {
                                                    orderItemRemoteDataSource.update(orderItem);
                                                } else {
                                                    orderItemLocalDataSource.save(orderItem);
                                                }
                                            },
                                            throwable -> orderItemLocalDataSource.save(orderItem),
                                            () -> {
                                            });

                            cachedOrderItems.remove(orderItem.getOrderId());
                            cachedOrderItems.put(orderItem.getOrderId(), orderItem);
                        })
                        .toList()
                        .toObservable())
                .doOnComplete(() -> cachedIsDirty = false);
    }

    @Override
    public Observable<OrderItem> getOne(String id) {
        return null;

    }

    @Override
    public OrderItem save(OrderItem item) {
        return orderItemLocalDataSource.save(item);
    }

    @Override
    public int delete(String id) {
        return orderItemLocalDataSource.delete(id);
    }

    @Override
    public int update(OrderItem item) {
        return orderItemLocalDataSource.update(item);
    }

    @Override
    public void deleteAll() {
        orderItemRemoteDataSource.deleteAll();
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
