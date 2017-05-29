package com.mobitill.barandrestaurant.data.product.source.remote;

import android.content.Context;
import android.support.annotation.NonNull;

import com.f2prateek.rx.preferences2.RxSharedPreferences;
import com.mobitill.barandrestaurant.data.ApiEndpointInterface;
import com.mobitill.barandrestaurant.data.DataUtils;
import com.mobitill.barandrestaurant.data.product.ProductDataSource;
import com.mobitill.barandrestaurant.data.product.models.Product;
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
 * Created by james on 5/12/2017.
 */
@Singleton
public class ProductRemoteDataSource implements ProductDataSource {

    private static final String TAG = ProductRemoteDataSource.class.getSimpleName();

    @NonNull
    private final Retrofit mCounterARetrofit;

    @NonNull
    private final Retrofit mCounterBRetrofit;

    @NonNull
    private final Retrofit mMobitillRetrofit;

    @NonNull
    BaseScheduleProvider mScheduleProvider;

    @NonNull
    RxSharedPreferences mRxSharedPreferences;

    @NonNull
    Context mContext;

    @Inject
    public ProductRemoteDataSource(@NonNull @Named(Constants.RetrofitSource.COUNTERA) Retrofit counterARetrofit,
                                   @NonNull @Named(Constants.RetrofitSource.COUNTERB) Retrofit counterBRetrofit,
                                   @NonNull @Named(Constants.RetrofitSource.MOBITILL) Retrofit mobitillRetrofit,
                                   @NonNull BaseScheduleProvider scheduleProvider,
                                   @NonNull RxSharedPreferences rxSharedPreferences,
                                   @NonNull Context context){
        checkNotNull(scheduleProvider, "scheduleProvider cannot be null");
        mScheduleProvider = scheduleProvider;
        mCounterARetrofit = counterARetrofit;
        mCounterBRetrofit = counterBRetrofit;
        mRxSharedPreferences = rxSharedPreferences;
        mMobitillRetrofit = mobitillRetrofit;
        mContext = context;
    };

    @Override
    public Observable<List<Product>> getAll() {
        ApiEndpointInterface apiEndpointInterface = mMobitillRetrofit.create(ApiEndpointInterface.class);
        return apiEndpointInterface
                .getProducts(DataUtils.getRequest())
                .subscribeOn(mScheduleProvider.io())
                .observeOn(mScheduleProvider.ui())
//                .doOnNext(response -> mRxSharedPreferences
//                        .getInteger(mContext.getString(R.string.key_products_version))
//                        .set(response.getProductsVersion()))
                .flatMap(productResponse -> {
                    List<Product> products = productResponse.getData();
                    return Observable.fromArray(products.toArray(new Product[products.size()])).toList().toObservable();
                });
    }


    @Override
    public Observable<Product> getOne(String id) {
        return null;
    }

    @Override
    public Product save(Product item) {
        return null;
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
    public Product getLastCreated() {
        return null;
    }

    @Override
    public Observable<Product> getProductWithIdentifier(String identifier) {
        return null;
    }
}
