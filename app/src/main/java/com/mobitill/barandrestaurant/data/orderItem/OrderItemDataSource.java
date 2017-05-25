package com.mobitill.barandrestaurant.data.orderItem;


import com.mobitill.barandrestaurant.data.DataSource;
import com.mobitill.barandrestaurant.data.orderItem.model.OrderItem;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by andronicus on 5/23/2017.
 */

public interface OrderItemDataSource extends DataSource<OrderItem, String> {

    Observable<List<OrderItem>> getOrderItemWithOrderId(String orderId);

}
