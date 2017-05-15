package com.mobitill.barandrestaurant.register;

import android.content.Context;
import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;

/**
 * Created by james on 5/10/2017.
 */
@Module
public class RegisterPresenterModule {
    private final RegisterContract.View mView;
    private final Context mContext;

    public RegisterPresenterModule(RegisterContract.View view, Context context){
        mView = view;
        mContext = context;
    }



    @Provides
    @NonNull
    RegisterContract.View provideRegisterContractView(){
        return mView;
    }
}


