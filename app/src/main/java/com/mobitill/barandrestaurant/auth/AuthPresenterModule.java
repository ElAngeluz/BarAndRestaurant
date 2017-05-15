package com.mobitill.barandrestaurant.auth;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import com.f2prateek.rx.preferences2.RxSharedPreferences;
import com.mobitill.barandrestaurant.utils.schedulers.BaseScheduleProvider;
import com.mobitill.barandrestaurant.utils.schedulers.SchedulersProvider;

import dagger.Module;
import dagger.Provides;

/**
 * Created by james on 4/27/2017.
 */
@Module
public class AuthPresenterModule {
    private final AuthContract.View mView;
    private final Context mContext;

    public AuthPresenterModule(AuthContract.View view, Context context){
        mContext = context;
        mView = view;
    }

    @Provides
    @NonNull
    AuthContract.View provideAuthContractView(){
        return mView;
    }

    @Provides
    @NonNull
    Context provideContext(){
        return mContext;
    }

    @Provides
    @NonNull
    BaseScheduleProvider provideSchedulersProvider(){
        return new SchedulersProvider();
    }

    @Provides
    SharedPreferences provideSharedPreferences(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Provides
    RxSharedPreferences provideRxPreferences(SharedPreferences sharedPreferences){
        return RxSharedPreferences.create(sharedPreferences);
    }

}
