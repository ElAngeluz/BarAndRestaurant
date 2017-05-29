package com.mobitill.barandrestaurant.data.orderItem.source.remote;

import android.content.Context;
import android.support.annotation.NonNull;

import com.f2prateek.rx.preferences2.RxSharedPreferences;
import com.mobitill.barandrestaurant.data.ApiEndpointInterface;
import com.mobitill.barandrestaurant.data.orderItem.OrderItemDataSource;
import com.mobitill.barandrestaurant.data.orderItem.model.OrderItem;
import com.mobitill.barandrestaurant.data.request.remotemodels.request.OrderRemoteRequest;
import com.mobitill.barandrestaurant.data.request.remotemodels.response.OrderRemoteResponse;
import com.mobitill.barandrestaurant.utils.Constants;
import com.mobitill.barandrestaurant.utils.schedulers.BaseScheduleProvider;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.Observable;
import retrofit2.Retrofit;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by andronicus on 5/23/2017.
 */
@Singleton
public class OrderItemRemoteDataSource implements OrderItemDataSource{

    private static final String TAG = OrderItemRemoteDataSource.class.getSimpleName();

    @NonNull
    private final Retrofit mCounterARetrofit;

    @NonNull
    private final Retrofit mCounterBRetrofit;

    @NonNull
    BaseScheduleProvider mScheduleProvider;

    @NonNull
    RxSharedPreferences mRxSharedPreferences;

    @NonNull
    Context mContext;

    @Inject
    OrderItemRemoteDataSource(@NonNull @Named(Constants.RetrofitSource.COUNTERA) Retrofit counterARetrofit,
                              @NonNull @Named(Constants.RetrofitSource.COUNTERB) Retrofit counterBRetrofit,
                              @NonNull BaseScheduleProvider scheduleProvider,
                              @NonNull RxSharedPreferences rxSharedPreferences,
                              @NonNull Context context){
        checkNotNull(scheduleProvider, "scheduleProvider cannot be null");
        mScheduleProvider = scheduleProvider;
        mCounterARetrofit = counterARetrofit;
        mCounterBRetrofit = counterBRetrofit;
        mRxSharedPreferences = rxSharedPreferences;
        mContext = context;
    }


    @Override
    public Observable<List<OrderItem>> getAll() {
        return null;
    }

    @Override
    public Observable<OrderItem> getOne(String id) {
        return null;
    }

    @Override
    public OrderItem save(OrderItem item) {
        return null;
    }

    @Override
    public int delete(String id) {
        return 0;
    }

    @Override
    public int update(OrderItem item) {
        return 0;
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public OrderItem getLastCreated() {
        return null;
    }


    @Override
    public Observable<List<OrderItem>> getOrderItemWithOrderId(String orderId) {
        return null;
    }

    @Override
    public Observable<OrderRemoteResponse> orderRequest(OrderRemoteRequest order, String counter) {
         return mApiEndpointInterface(counter)
                .orderRequest(order)
                 .subscribeOn(mScheduleProvider.io())
                 .onErrorResumeNext(Observable.empty());
    }

    private ApiEndpointInterface mApiEndpointInterface(String counter){

        switch (counter){
            case Constants.RetrofitSource.COUNTERA:
                ApiEndpointInterface counterAEndpointInterface = mCounterARetrofit.create(ApiEndpointInterface.class);
                return counterAEndpointInterface;
            case Constants.RetrofitSource.COUNTERB:
                ApiEndpointInterface counterBEndpointInterface = mCounterBRetrofit.create(ApiEndpointInterface.class);
                return counterBEndpointInterface;
            default:return null;
        }
    }

}
