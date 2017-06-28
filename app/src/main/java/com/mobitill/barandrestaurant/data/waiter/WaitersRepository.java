package com.mobitill.barandrestaurant.data.waiter;

import android.support.annotation.NonNull;

import com.mobitill.barandrestaurant.data.Local;
import com.mobitill.barandrestaurant.data.Remote;
import com.mobitill.barandrestaurant.data.waiter.waitermodels.response.Waiter;
import com.mobitill.barandrestaurant.utils.schedulers.BaseScheduleProvider;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.annotations.Nullable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by james on 4/27/2017.
 */
public class WaitersRepository implements WaitersDataSource {

    private static final String TAG = WaitersRepository.class.getSimpleName();

    private final WaitersDataSource mWaitersRemoteDataSource;
    private final WaitersDataSource mWaitersLocalDataSource;

    @NonNull
    private final BaseScheduleProvider mScheduleProvider;

    @Nullable
    Map<String, Waiter> mCachedWaiters;

    //Marks the cache as invalid
    //to force an update the next time data is loaded
    boolean mCacheIsDirty = false;

    @Inject
    WaitersRepository(@Remote WaitersDataSource waitersRemoteDataSource,
                      @Local WaitersDataSource waitersLocalDataSource,
                      @NonNull BaseScheduleProvider scheduleProvider){
        checkNotNull(scheduleProvider, "scheduleProvider cannot be null");
        mScheduleProvider = scheduleProvider;
        mWaitersRemoteDataSource = waitersRemoteDataSource;
        mWaitersLocalDataSource = waitersLocalDataSource;
    }

    /**
     * Get waiters from cache, local data source (SQLite) or remote data source,
     * whichever is available first
     * */
    @Override
    public Observable<List<Waiter>> getAll() {
        // Respond immediately with cache if cache is available an not dirty
        if(mCachedWaiters != null && !mCacheIsDirty){
            return Observable.fromIterable(mCachedWaiters.values()).toList().toObservable();
        } else if(mCachedWaiters == null){
            mCachedWaiters = new LinkedHashMap<>();
        }

        Observable<List<Waiter>> remoteWaiters = getAndSaveRemoteWaiters();

        if(mCacheIsDirty){
            return remoteWaiters;
        } else {
            //Query the local storage if available. If not query the network.
            Observable<List<Waiter>> localWaiters = getAndCacheLocalWaiters();

            List<Waiter> defaultWaiters = new ArrayList<>();
           /* return Observable.concat(localWaiters, remoteWaiters)
                    .filter(waiters -> !waiters.isEmpty())
                    .firstOrError()
                    .toObservable();*/

           return Observable.merge(localWaiters, remoteWaiters);

        }

    }


    private Observable<List<Waiter>> getAndSaveRemoteWaiters() {
        return mWaitersRemoteDataSource
                .getAll()
                .observeOn(mScheduleProvider.ui())
                .flatMap(waiters -> Observable.fromArray(waiters.toArray(new Waiter[waiters.size()])).doOnNext(waiter -> {
                    mWaitersLocalDataSource.save(waiter);
                    mCachedWaiters.put(waiter.getId(), waiter);
                }).toList().toObservable())
                .doOnComplete(() -> mCacheIsDirty = false);
    }

    private Observable<List<Waiter>> getAndCacheLocalWaiters() {
        
        return mWaitersLocalDataSource.getAll()
                .observeOn(mScheduleProvider.ui())
                .flatMap(waiters -> Observable
                        .fromArray(waiters.toArray(new Waiter[waiters.size()]))
                        .doOnNext(waiter -> mCachedWaiters.put(waiter.getId(), waiter))
                        .toList()
                        .toObservable());
    }


    @Override
    public Observable<Waiter> getOne(String id) {

        checkNotNull(id);

        final Waiter cachedWaiter = getWaiterWithId(id);
        if (isWaiterCached(cachedWaiter)) {
            return Observable.just(cachedWaiter);
        }

        // Is the waiter in the local data source if no query the network
        Observable<Waiter> localWaiter = getWaiterWithIdFromLocalRepository(id);
        Observable<Waiter> remoteWaiter = mWaitersRemoteDataSource
                .getAll()
                .flatMap(waiters -> Observable
                        .fromArray(waiters.toArray(new Waiter[waiters.size()]))
                        .filter(waiter -> waiter.getId().equals(id))
                        .firstElement()
                        .toObservable());

        return Observable.concat(localWaiter, remoteWaiter)
                .firstOrError()
                .map(waiter -> {
                    if(waiter == null){
                        throw new NoSuchElementException("No waiter found with Id " + id);
                    }
                    return waiter;
                }).toObservable();
    }

    private boolean isWaiterCached(Waiter cachedWaiter) {
        // respond immediately with cache if available
        if(cachedWaiter != null){
            return true;
        }

        // do in memory cache update to keep the app UI up to date
        if(mCachedWaiters == null){
            mCachedWaiters = new LinkedHashMap<>();
        }
        return false;
    }

    @Override
    public Waiter save(Waiter item) {
        return mWaitersLocalDataSource.save(item);
    }

    @Override
    public int delete(String id) {
        return mWaitersLocalDataSource.delete(id);
    }

    @Override
    public int update(Waiter item) {
        return 0;
    }

    private Waiter getWaiterWithId(@NonNull String id) {
        checkNotNull(id);
        if(mCachedWaiters == null || mCachedWaiters.isEmpty()){
            return null;
        } else {
            return mCachedWaiters.get(id);
        }
    }

    @NonNull
    private Observable<Waiter> getWaiterWithIdFromLocalRepository(@NonNull String id) {
        return mWaitersLocalDataSource
                .getOne(id)
                .doOnNext(waiter -> Observable.just(waiter)
                        .doOnNext(waiter1 ->  mCachedWaiters.put(id, waiter1)))
                .firstElement().toObservable();

    }

    @Override
    public void deleteAll() {
        mWaitersLocalDataSource.deleteAll();
    }

    @Override
    public Waiter getLastCreated() {
        return null;
    }

    @Override
    public Observable<Waiter> getWaiterFromPin(String pin) {
//        checkNotNull(pin);

        final Waiter cachedWaiter = getWaiterWithPin(pin);

        if (isWaiterCached(cachedWaiter)) {
            return Observable.just(cachedWaiter);
        }

        // Is the waiter in the local data source if no query the network
        Observable<Waiter> localWaiter = getWaiterWithPinFromLocalRepository(pin);
        Observable<Waiter> remoteWaiter = mWaitersRemoteDataSource
                .getAll()
                .flatMap(waiters -> Observable
                        .fromArray(waiters.toArray(new Waiter[waiters.size()]))
                        .filter(waiter -> waiter.getPin().equals(pin))
                        .firstElement()
                        .toObservable());

        return Observable.concat(localWaiter, remoteWaiter)
                .firstOrError()
                .map(waiter -> {
                    if(waiter.getPin() == null){
                        throw new NoSuchElementException("No waiter found with Pin " + pin);
                    }
                    return waiter;
                }).toObservable();
    }

    private Observable<Waiter> getWaiterWithPinFromLocalRepository(String pin) {
        return mWaitersLocalDataSource
                .getWaiterFromPin(pin);
    }

    private Waiter getWaiterWithPin(@NonNull String pin) {
        checkNotNull(pin);

        if(mCachedWaiters == null || mCachedWaiters.isEmpty()){
            return null;
        } else {
            for(Map.Entry<String, Waiter> entry: mCachedWaiters.entrySet()){
                if(entry.getValue().getId().equals(pin)){
                    return entry.getValue();
                }
            }
        }

        return null;
    }

}
