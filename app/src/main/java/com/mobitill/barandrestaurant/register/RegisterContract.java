package com.mobitill.barandrestaurant.register;

import com.mobitill.barandrestaurant.BasePresenter;
import com.mobitill.barandrestaurant.BaseView;
import com.mobitill.barandrestaurant.data.order.model.Order;
import com.mobitill.barandrestaurant.data.orderItem.model.OrderItem;
import com.mobitill.barandrestaurant.data.product.models.Product;

import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * Created by james on 5/10/2017.
 */

public interface RegisterContract {
    interface View extends BaseView<Presenter> {
        void showProducts(List<Product> products);

        void showNoProducts();

        void showOrderItemCreated(Order orderItem);

        void showOrderItemsOnTicket(List<OrderItem> orderItems);
    }
    interface Presenter extends BasePresenter{
        void getProducts();

        void createOrder(Product product);

        void getOrderItems(Order order);

        Map<String,Stack<OrderItem>> aggregateOrderItems(List<OrderItem> orderItems);

        void addOrderItem(OrderItem orderItem);

        void removeOrderItem(OrderItem orderItemToDelete);

        void sendOrderRequest();
    }
}
