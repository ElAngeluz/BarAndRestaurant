package com.mobitill.barandrestaurant.receipts;

import android.support.annotation.NonNull;

import com.mobitill.barandrestaurant.data.order.OrderRepository;
import com.mobitill.barandrestaurant.data.order.model.Order;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by andronicus on 5/22/2017.
 */

public class ReceiptsPresenter implements ReceiptsContract.Presenter {

    private static final String TAG = ReceiptsPresenter.class.getSimpleName();

    private List<Order> mOrderList;

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
        getSortedOrders();
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
                            view.showNoOrders();
                        },
                        () -> {}

                );
       compositeDisposable.add(disposable);

    }

    @Override
    public List<String> getDate() {
        return orderRepository.getDate();
    }

    @Override
    public void getOrdersPerDate(String date) {
        compositeDisposable.clear();
        Disposable disposable = orderRepository
                .getOrdersPerDate(date)
                .subscribe(sortedList ->
                        {
                            mOrderList = sortedList;
                            view.showOrdersPerDate(sortedList);
                        }
                        );
        compositeDisposable.add(disposable);
    }

    @Override
    public List<Order> getSortedOrders() {
        return mOrderList;
    }

}
