package com.mobitill.barandrestaurant.data.waiter.source.remote;

import android.support.annotation.NonNull;

import com.fernandocejas.frodo.annotation.RxLogObservable;
import com.mobitill.barandrestaurant.data.ApiEndpointInterface;
import com.mobitill.barandrestaurant.data.DataUtils;
import com.mobitill.barandrestaurant.data.waiter.WaitersDataSource;
import com.mobitill.barandrestaurant.data.waiter.waitermodels.response.Waiter;
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
 * Created by james on 4/27/2017.
 */
@Singleton
public class WaitersRemoteDataSource implements WaitersDataSource{

    private static final String TAG = WaitersRemoteDataSource.class.getSimpleName();

    private final Retrofit mRetrofit;

    @NonNull
    BaseScheduleProvider mScheduleProvider;

    @Inject
    public WaitersRemoteDataSource(@Named(Constants.RetrofitSource.MOBITILL) Retrofit retrofit,
                                   @NonNull BaseScheduleProvider schedulerProvider){
        checkNotNull(schedulerProvider, "scheduleProvider cannot be null");
        mScheduleProvider = schedulerProvider;
        mRetrofit = retrofit;
    }

    @Override
    @RxLogObservable
    public Observable<List<Waiter>> getAll() {
        ApiEndpointInterface apiEndpointInterface  = mRetrofit.create(ApiEndpointInterface.class);
        return apiEndpointInterface
                .getWaiters(DataUtils.getRequest())
                .subscribeOn(mScheduleProvider.io())
                .observeOn(mScheduleProvider.ui())
                .flatMap(waiterResponse -> {
                    List<Waiter> waiters = waiterResponse.getData();
                    return Observable.fromArray(waiters.toArray(new Waiter[waiters.size()])).toList().toObservable();
                });
    }

    @Override
    public Observable<Waiter> getOne(String id) {
        return null;
    }

    @Override
    public Waiter save(Waiter item) {
        return null;
    }

    @Override
    public int delete(String id) {
        return 0;
    }

    @Override
    public int update(Waiter item) {
        return 0;
    }

    @Override
    public void deleteAll() {
    }

    @Override
    public Waiter getLastCreated() {
        return null;
    }

    @Override
    public Observable<Waiter> getWaiterFromPin(String pin) {
        return null;
    }
}