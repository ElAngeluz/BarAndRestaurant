package com.mobitill.barandrestaurant.receipts;

import com.mobitill.barandrestaurant.BaseComponent;
import com.mobitill.barandrestaurant.utils.FragmentScoped;

import dagger.Component;

/**
 * Created by andronicus on 5/22/2017.
 */

@FragmentScoped
@Component(modules = ReceiptsPresenterModule.class, dependencies = BaseComponent.class)
public interface ReceiptsComponent {
    void inject (ReceiptsActivity receiptsActivity);
    void inject (ReceiptsFragment receiptsFragment);

}
