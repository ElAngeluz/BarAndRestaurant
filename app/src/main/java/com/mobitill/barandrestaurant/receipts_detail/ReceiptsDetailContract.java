package com.mobitill.barandrestaurant.receipts_detail;

import com.mobitill.barandrestaurant.BasePresenter;
import com.mobitill.barandrestaurant.BaseView;
import com.mobitill.barandrestaurant.data.order.model.Order;
import com.mobitill.barandrestaurant.data.orderItem.model.OrderItem;
import com.mobitill.barandrestaurant.data.product.models.Product;

import java.util.List;
import java.util.Map;
import io.reactivex.Observable;
import java.util.Stack;

/**
 * Created by andronicus on 5/26/2017.
 */

public interface ReceiptsDetailContract {
    interface View extends BaseView<Presenter>{
        void showOrder(Order order);
        void showOrderItems(List<OrderItem> orderItems);
        void showProducts(List<Product> products);
    }

    interface Presenter extends BasePresenter{
        void getOrder(String orderId);
        void getOrderItems(String orderId);
        void getProducts(List<Product> products);
        Map<String,Stack<OrderItem>> aggregateOrderItems(List<OrderItem> orderItems);


    }
}
