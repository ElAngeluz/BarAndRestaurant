package com.mobitill.barandrestaurant.auth;

import com.mobitill.barandrestaurant.BasePresenter;
import com.mobitill.barandrestaurant.BaseView;
import com.mobitill.barandrestaurant.data.waiter.waitermodels.response.Waiter;

import java.util.List;

/**
 * Created by james on 4/27/2017.
 */

public interface AuthContract {
    interface View extends BaseView<Presenter>{
        boolean isActive();

        void showLoadingWaitersError();

        void showLoadingIndicator(boolean b);

        void onWaitersLoaded(List<Waiter> waiters);

        void showPlaceOrderActivity(String waiterName);

        void showWaiterLoginError();

        void showLoginFailed();

        void invalidCredentials();
    }

    interface  Presenter extends BasePresenter{

        void login();


        void performLogin(String phone,String password, List<Waiter> waiters);
    }

}
