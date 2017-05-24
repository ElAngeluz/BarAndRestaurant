package com.mobitill.barandrestaurant.data.product.source.remote;

import android.content.Context;
import android.support.annotation.NonNull;

import com.f2prateek.rx.preferences2.RxSharedPreferences;
import com.mobitill.barandrestaurant.R;
import com.mobitill.barandrestaurant.data.ApiEndpointInterface;
import com.mobitill.barandrestaurant.data.product.ProductDataSource;
import com.mobitill.barandrestaurant.data.product.models.Product;
import com.mobitill.barandrestaurant.data.product.models.pos.payload.POSRequest;
import com.mobitill.barandrestaurant.data.product.models.pos.payload.Requestbody;
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
    BaseScheduleProvider mScheduleProvider;

    @NonNull
    RxSharedPreferences mRxSharedPreferences;

    @NonNull
    Context mContext;

    @Inject
    public ProductRemoteDataSource(@NonNull @Named(Constants.RetrofitSource.COUNTERA) Retrofit counterARetrofit,
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
    };

    @Override
    public Observable<List<Product>> getAll() {
        ApiEndpointInterface counterAEndpointInterface = mCounterARetrofit.create(ApiEndpointInterface.class);
        ApiEndpointInterface counterBEndpointInterface = mCounterBRetrofit.create(ApiEndpointInterface.class);

        Observable<List<Product>> counterAProducts = getProducts(counterAEndpointInterface);

        Observable<List<Product>> counterBProducts = getProducts(counterBEndpointInterface);

        return Observable.concat(counterAProducts, counterBProducts)
                    .filter(products -> !products.isEmpty())
                    .firstOrError()
                    .toObservable();
    }

    private Observable<List<Product>> getProducts(ApiEndpointInterface apiEndpointInterface){

        POSRequest posRequest = new POSRequest();
        posRequest.setRequestbody(new Requestbody());
        posRequest.setRequestId("181");
        posRequest.setRequestname("queryproducts");
        posRequest.setProductsVersion(mRxSharedPreferences.getInteger(mContext.getString(R.string.key_products_version), 0).get());

       return apiEndpointInterface
                .getProducts(posRequest)
                .subscribeOn(mScheduleProvider.io())
                .observeOn(mScheduleProvider.ui())
                .doOnNext(response -> mRxSharedPreferences
                            .getInteger(mContext.getString(R.string.key_products_version))
                            .set(response.getProductsVersion()))
                .flatMap(posResponse -> {
                    List<Product> products = posResponse.getResponse().getData();
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
