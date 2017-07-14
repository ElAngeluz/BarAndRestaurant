package com.mobitill.barandrestaurant.data.product.source.remote;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.f2prateek.rx.preferences2.RxSharedPreferences;
import com.mobitill.barandrestaurant.data.ApiEndpointInterface;
import com.mobitill.barandrestaurant.data.DataUtils;
import com.mobitill.barandrestaurant.data.product.ProductDataSource;
import com.mobitill.barandrestaurant.data.product.models.Product;
import com.mobitill.barandrestaurant.data.updatedproducts.response.NewProduct;
import com.mobitill.barandrestaurant.data.updatedproducts.response.ProductsResponseModel;
import com.mobitill.barandrestaurant.utils.Constants;
import com.mobitill.barandrestaurant.utils.schedulers.BaseScheduleProvider;

import java.util.ArrayList;
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


        ApiEndpointInterface apiEndpointInterface = null;
        try {
            apiEndpointInterface = mMobitillRetrofit.create(ApiEndpointInterface.class);
            return apiEndpointInterface
//                .getProducts(DataUtils.getRequest())
                    .getProducts(DataUtils.makeProductsRequest())
                    .subscribeOn(mScheduleProvider.io())
                    .observeOn(mScheduleProvider.ui())
//                .doOnNext(response -> mRxSharedPreferences
//                        .getInteger(mContext.getString(R.string.key_products_version))
//                        .set(response.getProductsVersion()))
                    .flatMap((ProductsResponseModel productResponse) -> {
                        List<NewProduct> products = null;
                        try {
                            products = productResponse.getData().getOrganization().getProducts();
                        } catch (Exception e) {
                            Toast.makeText(mContext, "Product List empty!", Toast.LENGTH_SHORT).show();
                            return Observable.empty();
                        }
                        List<Product> ListOfProducts = new ArrayList<>();
                        for (NewProduct item: products) {
                            Product p = new Product();
                            p.setBarcode("");
                            p.setId(item.getId());
                            p.setIdentifier(String.valueOf(item.getSku()));
                            p.setName(item.getDescription()== null ? "product name" : item.getDescription());
                            p.setPrice(String.valueOf(item.getPrice()));
                            p.setVat(item.getVat()== null ? " vat " : item.getVat());

                            p.setCategory(item.getLocation() == null ? "" : item.getLocation());
                        /*TODO SET VAT & CATEGORY*/

                            ListOfProducts.add(p);

                        }
//                    return Observable.fromArray(products.toArray(new Product[products.size()])).toList().toObservable();
                        return Observable.fromArray(ListOfProducts.toArray(new Product[ListOfProducts.size()])).toList().toObservable();

                    });
        } catch (Exception e) {
            e.printStackTrace();
            return Observable.empty();
        }

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
