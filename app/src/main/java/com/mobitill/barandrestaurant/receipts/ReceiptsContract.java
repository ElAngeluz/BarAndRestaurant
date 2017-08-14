package com.mobitill.barandrestaurant.receipts;

import com.mobitill.barandrestaurant.BasePresenter;
import com.mobitill.barandrestaurant.BaseView;
import com.mobitill.barandrestaurant.data.order.model.Order;

import java.util.List;

/**
 * Created by andronicus on 5/22/2017.
 */

public interface ReceiptsContract {

    interface View extends BaseView<Presenter>{

        void checkedOut();

        void notCheckedOut();

        void showOrders(List<Order> orders);

        void showNoOrders();

        void showOrdersPerDate(List<Order> sortedList);

    }
    interface Presenter extends BasePresenter{

        void isCheckedOut();

        void isNotCheckedOut();

        void getOrders();

        List<String> getDate();

        void getOrdersPerDate(String date);


    }
}
