package com.mobitill.barandrestaurant.checkout;

import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dataintegrated on 5/29/2017.
 */
@Module
public class CheckOutPresenterModule {

    private final CheckOutContract.View mView;

    public CheckOutPresenterModule(CheckOutContract.View view) {
        mView = view;
    }

    @Provides
    @NonNull
    CheckOutContract.View provideCheckOutContractView(){
        return mView;
    }

}
