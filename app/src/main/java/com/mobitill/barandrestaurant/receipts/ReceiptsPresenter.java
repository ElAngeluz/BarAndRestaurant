package com.mobitill.barandrestaurant.receipts;

import android.support.annotation.NonNull;
import android.util.Log;

import com.mobitill.barandrestaurant.data.order.OrderRepository;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by andronicus on 5/22/2017.
 */

public class ReceiptsPresenter implements ReceiptsContract.Presenter {

    private static final String TAG = ReceiptsPresenter.class.getSimpleName();

    @NonNull
    private final OrderRepository orderRepository;

    @NonNull
    private final ReceiptsContract.View view;

    @NonNull
    private CompositeDisposable compositeDisposable;

    @Inject
    public ReceiptsPresenter(@NonNull OrderRepository orderRepository,
                             @NonNull ReceiptsContract.View view
    ){
        this.orderRepository = orderRepository;
        this.compositeDisposable = new CompositeDisposable();
        this.view = checkNotNull(view);
    }


    /**
     * Method injection is used here to safely reference {@code this} after the object is created.
     */
    @Inject
    void setupListeners() {
        view.setPresenter(this);
    }

    @Override
    public void subscribe() {
        getOrders();
    }

    @Override
    public void unsubscribe() {

        compositeDisposable.clear();
    }

    @Override
    public void isCheckedOut() {

    }

    @Override
    public void isNotCheckedOut() {

    }

    @Override
    public void getOrders() {

        compositeDisposable.clear();
        Disposable disposable = orderRepository
                .getAll()
                .subscribe(
                        //onNext
                        orders -> {
                            if(orders != null && !orders.isEmpty()){
                                view.showOrders(orders);
                            } else {
                                view.showNoOrders();
                            }

                        },
                        throwable -> {
                            Log.e(TAG, "getProducts: ", throwable);
                            view.showNoOrders();
                        },
                        () -> {}

                );
       compositeDisposable.add(disposable);

    }
}
