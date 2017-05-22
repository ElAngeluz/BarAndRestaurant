package com.mobitill.barandrestaurant.receipts;

import com.mobitill.barandrestaurant.utils.FragmentScoped;

import dagger.Component;
import dagger.Module;

/**
 * Created by andronicus on 5/22/2017.
 */

@FragmentScoped
@Component(modules = ReceiptsPresenterModule.class)
public interface ReceiptsComponent {
    void inject (ReceiptsActivity receiptsActivity);
    void inject (ReceiptsFragment receiptsFragment);

}
