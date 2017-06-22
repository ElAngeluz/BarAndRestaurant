package com.mobitill.barandrestaurant.checkout;

import com.mobitill.barandrestaurant.BaseComponent;
import com.mobitill.barandrestaurant.utils.FragmentScoped;

import dagger.Component;

/**
 * Created by dataintegrated on 5/29/2017.
 */
@FragmentScoped
@Component(modules = CheckOutPresenterModule.class, dependencies = BaseComponent.class)
public interface CheckOutComponent {
    void inject(CheckOutFragment checkOutFragment);
    void inject(CheckOutPresenter checkOutPresenter);
}
