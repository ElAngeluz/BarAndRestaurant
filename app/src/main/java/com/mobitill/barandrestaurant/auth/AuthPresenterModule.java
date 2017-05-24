package com.mobitill.barandrestaurant.auth;

import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;

/**
 * Created by james on 4/27/2017.
 */
@Module
public class AuthPresenterModule {
    private final AuthContract.View mView;

    public AuthPresenterModule(AuthContract.View view){
        mView = view;
    }

    @Provides
    @NonNull
    AuthContract.View provideAuthContractView(){
        return mView;
    }



}
