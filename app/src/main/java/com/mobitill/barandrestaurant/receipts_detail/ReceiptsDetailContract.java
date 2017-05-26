package com.mobitill.barandrestaurant.receipts_detail;

import com.mobitill.barandrestaurant.BasePresenter;
import com.mobitill.barandrestaurant.BaseView;
import com.mobitill.barandrestaurant.data.order.model.Order;
import com.mobitill.barandrestaurant.data.orderItem.model.OrderItem;

import java.util.List;

/**
 * Created by andronicus on 5/26/2017.
 */

public interface ReceiptsDetailContract {
    interface View extends BaseView<Presenter>{
        void showOrder(Order order);
        void showOrderItems(List<OrderItem> orderItems);
    }

    interface Presenter extends BasePresenter{
        void getOrder(String orderId);
        void getOrderItems(String orderId);
    }
}
