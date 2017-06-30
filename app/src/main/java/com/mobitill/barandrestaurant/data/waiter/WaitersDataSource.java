package com.mobitill.barandrestaurant.data.waiter;

import com.mobitill.barandrestaurant.data.DataSource;
import com.mobitill.barandrestaurant.data.waiter.waitermodels.response.Waiter;

import io.reactivex.Observable;

public interface WaitersDataSource extends DataSource<Waiter, String> {
    Observable<Waiter> getWaiterFromPhoneAndPassword(String phone, String password);
}
