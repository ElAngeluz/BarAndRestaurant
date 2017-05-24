package com.mobitill.barandrestaurant.auth;

import com.mobitill.barandrestaurant.BaseComponent;
import com.mobitill.barandrestaurant.utils.FragmentScoped;

import dagger.Component;

/**
 * Created by james on 4/27/2017.
 */
@FragmentScoped
@Component(dependencies = BaseComponent.class,
        modules = AuthPresenterModule.class)
public interface AuthComponent {
    void inject(AuthActivity authActivity);
}
