package com.mobitill.barandrestaurant.data.product;

import com.mobitill.barandrestaurant.data.DataSource;
import com.mobitill.barandrestaurant.data.product.models.Product;

import io.reactivex.Observable;

/**
 * Created by james on 5/12/2017.
 */

public interface ProductDataSource extends DataSource<Product, String> {
    Observable<Product> getProductWithIdentifier(String identifier);
}
