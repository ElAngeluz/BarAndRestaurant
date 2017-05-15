package com.mobitill.barandrestaurant.data.waiter;

import com.mobitill.barandrestaurant.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by james on 4/27/2017.
 */
@Singleton
@Component(modules = {WaitersRepositoryModule.class, ApplicationModule.class})
public interface WaitersRepositoryComponent {
    WaitersRepository waitersRepository();
}
