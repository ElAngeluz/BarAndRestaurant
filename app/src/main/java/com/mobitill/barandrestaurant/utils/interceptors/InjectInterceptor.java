package com.mobitill.barandrestaurant.utils.interceptors;

import com.mobitill.barandrestaurant.data.order.source.local.OrderLocalDataSource;

import javax.inject.Inject;

/**
 * Created by dataintegrated on 6/29/2017.
 */

public class InjectInterceptor {

    @Inject
    OrderLocalDataSource mOrderLocalDataSource;


    public void getUpdatedState(String orderId){

        mOrderLocalDataSource.updateProcessState("empty",0);
    }
}
