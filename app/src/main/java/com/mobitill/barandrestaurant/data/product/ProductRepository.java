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
import java.util.NoSuchElementException;

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
     */
    @Override
    public Observable<List<Product>> getAll() {
        // Respond immediately with the cache if cached is available and not dirty
        if (mCachedProducts != null && !mCachedIsDirty) {
            return Observable.fromIterable(mCachedProducts.values()).toList().toObservable();
        } else if (mCachedProducts == null) {
            mCachedProducts = new LinkedHashMap<>();
        }

        Observable<List<Product>> remoteProducts = getAndSaveRemoteProducts();
        //return remoteProducts;

        if (mCachedIsDirty) {
            return remoteProducts;
        } else {
             //Query the local storage if available, then query the remote network
            Observable<List<Product>> localProducts = getAndCacheLocalProducts();
            return Observable.merge(localProducts, remoteProducts);
        }


    }

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
                            mProductLocalDataSource.getOne(product.getId())
                                    .subscribe(
                                            product1 -> {
                                                if (product1 != null) {
                                                    mProductLocalDataSource.update(product);
                                                } else {
                                                    mProductLocalDataSource.save(product);
                                                }
                                            },
                                            throwable -> mProductLocalDataSource.save(product),
                                            () -> {
                                            });

                            mCachedProducts.remove(product.getId());
                            mCachedProducts.put(product.getId(), product);
                        })
                        .toList()
                        .toObservable())
                .doOnComplete(() -> mCachedIsDirty = false);
    }

    @Override
    public Observable<Product> getOne(String id) {
        checkNotNull(id);

        final Product cachedProduct = getProductWithId(id);
        if (isProductCached(cachedProduct)) {
            return Observable.just(cachedProduct);
        }

        // Is the product in the local data source? If no query the network
        Observable<Product> localProduct = getProductFromLocalRepository(id);
        Observable<Product> remoteProduct = mProductRemoteDataSource
                .getAll()
                .flatMap(products -> Observable
                        .fromArray(products.toArray(new Product[products.size()]))
                        .filter(product -> product.getId().equals(id)))
                .firstElement()
                .toObservable();

        return Observable.concat(localProduct, remoteProduct)
                .firstOrError()
                .map(product -> {
                    if (product == null) {
                        throw new NoSuchElementException("No Waiter found with Id " + id);
                    }
                    return product;
                }).toObservable();

    }

    private Observable<Product> getProductFromLocalRepository(String id) {
        return mProductLocalDataSource
                .getOne(id)
                .observeOn(mScheduleProvider.ui())
                .doOnNext(product -> Observable
                        .just(product)
                        .doOnNext(product1 -> mCachedProducts.put(id, product1)))
                .firstElement()
                .toObservable();
    }

    private boolean isProductCached(Product cachedProduct) {

        //respond immediately with cache if available
        if (cachedProduct != null) {
            return true;
        }

        // do in memory cache update to keep UI up to date
        if (mCachedProducts == null) {
            mCachedProducts = new LinkedHashMap<>();
        }
        return false;
    }

    private Product getProductWithId(String id) {
        checkNotNull(id);
        if (mCachedProducts == null || mCachedProducts.isEmpty()) {
            return null;
        } else {
            return mCachedProducts.get(id);
        }
    }

    @Override
    public long save(Product item) {
        return mProductLocalDataSource.save(item);
    }

    @Override
    public int delete(String id) {
        return mProductLocalDataSource.delete(id);
    }

    @Override
    public int update(Product item) {
        return mProductLocalDataSource.update(item);
    }

    @Override
    public void deleteAll() {
        mProductRemoteDataSource.deleteAll();
    }

    @Override
    public Observable<Product> getProductWithIdentifier(String identifier) {
        checkNotNull(identifier);

        final Observable<Product> cachedProduct = getCachedProductWithIdentifier(identifier);
        final Observable<Product> localProduct = getProductWithIdentifierFromLocalDataSource(identifier);
        final Observable<Product> remoteProduct = mProductRemoteDataSource
                .getAll()
                .flatMap(products -> Observable.fromArray(products.toArray(new Product[products.size()]))
                        .filter(product -> product.getIdentifier().equals(identifier))
                        .firstElement()
                        .toObservable());

        return Observable.concat(cachedProduct, localProduct, remoteProduct)
                .filter(product -> product != null)
                .firstOrError()
                .toObservable();

    }

    private Observable<Product> getProductWithIdentifierFromLocalDataSource(String identifier) {
        return mProductLocalDataSource.getProductWithIdentifier(identifier);
    }

    private Observable<Product> getCachedProductWithIdentifier(@NonNull String identifier) {
        checkNotNull(identifier);
        if (mCachedProducts == null || mCachedProducts.isEmpty()) {
            return null;
        } else {
            return Observable.fromIterable(mCachedProducts.values())
                    .filter(product1 -> product1.getIdentifier().equals(identifier))
                    .firstOrError()
                    .toObservable();
        }
    }

}
