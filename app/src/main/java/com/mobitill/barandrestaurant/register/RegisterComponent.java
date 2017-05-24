package com.mobitill.barandrestaurant.register;

import com.mobitill.barandrestaurant.BaseComponent;
import com.mobitill.barandrestaurant.utils.FragmentScoped;

import dagger.Component;

/**
 * Created by james on 5/10/2017.
 */
@FragmentScoped
@Component(modules = RegisterPresenterModule.class, dependencies = BaseComponent.class)
public interface RegisterComponent {
    void inject(RegisterActivity registerActivity);
    void inject(RegisterFragment registerFragment);
}
