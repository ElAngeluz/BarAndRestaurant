package com.mobitill.barandrestaurant.register;

import android.support.annotation.NonNull;

import com.mobitill.barandrestaurant.data.product.ProductRepository;
import com.mobitill.barandrestaurant.data.product.models.Product;

import javax.inject.Inject;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by james on 5/10/2017.
 */

public class RegisterPresenter  implements RegisterContract.Presenter{

    @NonNull
    private final RegisterContract.View mView;

    @NonNull
    private final ProductRepository mProductRepository;

    @Inject
    public RegisterPresenter(@NonNull RegisterContract.View view,
                             @NonNull ProductRepository productRepository){
        mView = checkNotNull(view);
        mProductRepository = productRepository;
    }

    /**
     * Method injection is used here to safely reference {@code this} after the object is created.
     */
    @Inject
    void setupListeners() {
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        getProducts();
    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void getProducts() {
        mProductRepository
                .getAll()
                .subscribe(
                        //onNext
                        products -> mView.showProducts(products)

                        //onError

                        //onCompleted

                );
    }
}
