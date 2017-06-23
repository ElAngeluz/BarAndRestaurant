package com.mobitill.barandrestaurant.checkout;

import com.mobitill.barandrestaurant.BasePresenter;
import com.mobitill.barandrestaurant.BaseView;

/**
 * Created by dataintegrated on 5/29/2017.
 */

public interface CheckOutContract {
    interface View extends BaseView<Presenter>{

        String setPaymentMethod();
        String setAmount();
        String setTransactionsId();

    }
    interface Presenter extends BasePresenter{

        void checkout(String orderId);
        void makeCall(String orderId);
    }
}
