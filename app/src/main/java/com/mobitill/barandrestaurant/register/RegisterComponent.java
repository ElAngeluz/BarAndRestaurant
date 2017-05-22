package com.mobitill.barandrestaurant.register;

import com.mobitill.barandrestaurant.data.product.ProductRepositoryComponent;
import com.mobitill.barandrestaurant.utils.FragmentScoped;

import dagger.Component;

/**
 * Created by james on 5/10/2017.
 */
@FragmentScoped
@Component(modules = RegisterPresenterModule.class, dependencies = ProductRepositoryComponent.class)
public interface RegisterComponent {
    void inject(RegisterActivity registerActivity);
    void inject(RegisterFragment registerFragment);
}
