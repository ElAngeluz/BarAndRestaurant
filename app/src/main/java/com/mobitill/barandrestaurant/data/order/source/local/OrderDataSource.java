package com.mobitill.barandrestaurant.data.order.source.local;

import com.mobitill.barandrestaurant.data.DataSource;
import com.mobitill.barandrestaurant.data.order.model.Order;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by andronicus on 5/16/2017.
 */

public interface OrderDataSource extends DataSource<Order, String> {
    Order getOrderFromRowId(Long rowId);
    Observable<List<Order>> getOrdersWithSynced(Integer isSynced);
    Observable<List<Order>> getOrdersWithCounterASynced(Integer isCounterASynced);
    Observable<List<Order>> getOrdersWithCounterBSynced(Integer isCounterBSynced);
    Observable<List<Order>>  getOrdersForCheckout(Integer checkout, Integer checkoutFlagged);
    ArrayList<String> getOrdersWithTimestamp();
    List<String> getDate();
}
