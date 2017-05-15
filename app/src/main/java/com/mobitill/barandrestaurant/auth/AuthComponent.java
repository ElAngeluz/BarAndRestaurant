package com.mobitill.barandrestaurant.auth;

import com.mobitill.barandrestaurant.data.waiter.WaitersRepositoryComponent;
import com.mobitill.barandrestaurant.utils.FragmentScoped;

import dagger.Component;

/**
 * Created by james on 4/27/2017.
 */
@FragmentScoped
@Component(dependencies = WaitersRepositoryComponent.class,
        modules = AuthPresenterModule.class)
public interface AuthComponent {
    void inject(AuthActivity authActivity);
}
