package com.mobitill.barandrestaurant.data.orderItem.source.remote;

import com.mobitill.barandrestaurant.data.orderItem.OrderItemDataSource;
import com.mobitill.barandrestaurant.data.product.models.Product;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by andronicus on 5/23/2017.
 */

public class OrderItemRemoteDataSource implements OrderItemDataSource{

    private static final String TAG = OrderItemRemoteDataSource.class.getSimpleName();

    @Inject
    OrderItemRemoteDataSource(){}

    @Override
    public Observable<List<Product>> getAll() {
        return null;
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
    public android.database.Observable<Product> getProductWithIdentifier(String identifier) {
        return null;
    }
}
