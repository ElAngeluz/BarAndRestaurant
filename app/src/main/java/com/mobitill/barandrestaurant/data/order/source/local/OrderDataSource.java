package com.mobitill.barandrestaurant.data.order.source.local;

import com.mobitill.barandrestaurant.data.DataSource;
import com.mobitill.barandrestaurant.data.order.model.Order;

/**
 * Created by andronicus on 5/16/2017.
 */

public interface OrderDataSource extends DataSource<Order, String> {
    Order getOrderFromRowId(Long rowId);
}
