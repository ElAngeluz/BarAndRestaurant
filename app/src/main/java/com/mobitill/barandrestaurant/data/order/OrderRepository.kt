package com.mobitill.barandrestaurant.data.order

import android.support.annotation.NonNull
import com.mobitill.barandrestaurant.data.Local
import com.mobitill.barandrestaurant.data.Remote
import com.mobitill.barandrestaurant.data.order.model.Order
import com.mobitill.barandrestaurant.data.order.source.local.OrderDataSource
import com.mobitill.barandrestaurant.data.order.source.local.OrderLocalDataSource
import com.mobitill.barandrestaurant.data.order.source.remote.OrderRemoteDataSource
import com.mobitill.barandrestaurant.utils.schedulers.BaseScheduleProvider
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by andronicus on 5/16/2017.
 */
@Singleton
class OrderRepository @Inject constructor(@Local private val mOrderLocalDataSource: OrderLocalDataSource,
                                          @Remote private val mOrderRemoteDataSource: OrderRemoteDataSource,
                                          @NonNull private val mSchedulerProvider: BaseScheduleProvider) : OrderDataSource{




    override fun getAll(): Observable<MutableList<Order>> {
        return mOrderLocalDataSource
                .all
                .observeOn(mSchedulerProvider.ui())
    }

    override fun getOne(id: String?): Observable<Order> {
        return mOrderLocalDataSource
                .getOne(id)
                .observeOn(mSchedulerProvider.ui())
    }

    override fun save(item: Order?): Long {
        return mOrderLocalDataSource.save(item)
    }

    override fun delete(id: String?): Int {
        return mOrderLocalDataSource.delete(id)
    }

    override fun update(item: Order?): Int {
        return mOrderLocalDataSource.update(item)
    }

    override fun deleteAll() {
        mOrderLocalDataSource.deleteAll()
    }

}
