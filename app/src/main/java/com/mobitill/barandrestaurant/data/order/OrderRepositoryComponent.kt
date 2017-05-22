package com.mobitill.barandrestaurant.data.order

import com.mobitill.barandrestaurant.ApplicationModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by andronicus on 5/16/2017.
 */

@Singleton
@Component(modules = arrayOf(OrderRepositoryModule::class, ApplicationModule::class))
interface OrderRepositoryComponent {
    fun orderRepository(): OrderRepository
}
