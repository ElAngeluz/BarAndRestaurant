package com.mobitill.barandrestaurant.data.product;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.mobitill.barandrestaurant.data.Local;
import com.mobitill.barandrestaurant.data.Remote;
import com.mobitill.barandrestaurant.data.product.models.Product;
import com.mobitill.barandrestaurant.utils.schedulers.BaseScheduleProvider;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by james on 5/12/2017.
 */

public class ProductRepository implements ProductDataSource {

    private static final String TAG = ProductRepository.class.getSimpleName();

    private final ProductDataSource mProductRemoteDataSource;
    private final ProductDataSource mProductLocalDataSource;

    @NonNull
    private final BaseScheduleProvider mScheduleProvider;

    @Nullable
    Map<String, Product> mCachedProducts;

    //Marks the data as invalid to force an update
    //the next time the data is loaded
    boolean mCachedIsDirty = false;

    @Inject
    public ProductRepository(
            @Remote ProductDataSource productRemoteDataSource,
            @Local ProductDataSource productLocalDataSource,
            @NonNull BaseScheduleProvider scheduleProvider) {
        checkNotNull(scheduleProvider, "scheduleProvider cannot be null");
        mScheduleProvider = scheduleProvider;
        mProductRemoteDataSource = productRemoteDataSource;
        mProductLocalDataSource = productLocalDataSource;
    }

    /**
     * Get waiters from cache, local data source (SQLite) or remote data source,
     * whichever is available first
     * */
    @Override
    public Observable<List<Product>> getAll() {
        // Respond immediately with the cache if cached is available and not dirty
        if(mCachedProducts != null && !mCachedIsDirty){
            return Observable.fromIterable(mCachedProducts.values()).toList().toObservable();
        } else if (mCachedProducts == null){
            mCachedProducts = new LinkedHashMap<>();
        }

        Observable<List<Product>> remoteProducts = getAndSaveRemoteProducts();

<<<<<<< HEAD
        // TODO: 5/13/2017
    return remoteProducts;
=======
        if(mCachedIsDirty){
            return remoteProducts;
        } else {
            // Query the local storage if available, then query the remote network
            Observable<List<Product>> localProducts = getAndCacheLocalProducts();
            return Observable.merge(localProducts, remoteProducts);
        }


    }
>>>>>>> 3f20903c5d7f38a7d0432e49ce0f992c1408cb72

    private Observable<List<Product>> getAndCacheLocalProducts() {
        return mProductLocalDataSource.getAll()
                .observeOn(mScheduleProvider.ui())
                .flatMap(products -> Observable
                        .fromArray(products.toArray(new Product[products.size()]))
                        .doOnNext(product -> mCachedProducts.put(product.getId(), product))
                        .toList()
                        .toObservable());
    }

    private Observable<List<Product>> getAndSaveRemoteProducts() {
        return mProductRemoteDataSource
                .getAll()
                .flatMap(products -> Observable
                        .fromArray(products.toArray(new Product[products.size()]))
                        .doOnNext(product -> {
                    mProductLocalDataSource.save(product);
                    mCachedProducts.put(product.getId(), product);
                }).toList().toObservable())
                .doOnComplete(() -> mCachedIsDirty = false);
    }

    @Override
    public Observable<Product> getOne(String id) {
        return null;
    }

    @Override
    public long save(Product item) {
        return 0;
    }

    @Override
    public int delete(String id) {
        return 0;
    }

    @Override
    public int update(Product item) {
        return 0;
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public Observable<Product> getProductWithIdentifier(String identifier) {
        return null;
    }
}
