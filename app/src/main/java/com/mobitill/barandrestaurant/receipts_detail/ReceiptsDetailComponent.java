package com.mobitill.barandrestaurant.receipts_detail;

import com.mobitill.barandrestaurant.BaseComponent;
import com.mobitill.barandrestaurant.utils.FragmentScoped;

import dagger.Component;

/**
 * Created by andronicus on 5/26/2017.
 */
@FragmentScoped
@Component(modules = ReceiptsDetailPresenterModule.class, dependencies = BaseComponent.class)
public interface ReceiptsDetailComponent {
    void inject(ReceiptsDetailActivity receiptsDetailActivity);
    void inject(ReceiptsDetailFragment receiptsDetailFragment);
}
