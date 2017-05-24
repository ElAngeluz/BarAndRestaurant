package com.mobitill.barandrestaurant.data.orderItem;



import com.mobitill.barandrestaurant.data.DataSource;
import com.mobitill.barandrestaurant.data.orderItem.model.OrderItem;

import io.reactivex.Observable;

/**
 * Created by andronicus on 5/23/2017.
 */

public interface OrderItemDataSource extends DataSource<OrderItem, String> {

    Observable<OrderItem> getOrderItemWithIdentifier(String identifier);

}
