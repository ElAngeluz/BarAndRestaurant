package com.mobitill.barandrestaurant.data.order.source.local;

import com.mobitill.barandrestaurant.data.DataSource;
import com.mobitill.barandrestaurant.data.order.model.Order;

import io.reactivex.Observable;

/**
 * Created by andronicus on 5/16/2017.
 */

public interface OrderDataSource extends DataSource<Order, String> {
}
