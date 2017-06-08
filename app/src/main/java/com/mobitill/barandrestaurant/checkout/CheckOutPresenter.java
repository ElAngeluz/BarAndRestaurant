package com.mobitill.barandrestaurant.checkout;

import android.support.annotation.NonNull;
import android.util.Log;

import com.mobitill.barandrestaurant.data.order.OrderRepository;
import com.mobitill.barandrestaurant.jobs.checkoutjobs.CheckOutJob;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by dataintegrated on 5/29/2017.
 */

public class CheckOutPresenter implements CheckOutContract.Presenter {

    private static final String TAG = CheckOutPresenter.class.getSimpleName();

    @NonNull CheckOutContract.View mView;

    @NonNull
    private final OrderRepository mOrderRepository;

    @NonNull
    private CompositeDisposable compositeDisposable;

    @Inject
    CheckOutPresenter(@NonNull CheckOutContract.View view,
                      @NonNull OrderRepository orderRepository){
        mView = view;
        mOrderRepository = orderRepository;
        compositeDisposable = new CompositeDisposable();
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

    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    @Override
    public void checkout(String orderId) {
        if(orderId != null){
            mOrderRepository.getOne(orderId)
                    .subscribe(order -> {
                        order.setCheckoutFlagged(1);
                        order.setPaymentMethod(mView.setPaymentMethod());
                        order.setAmount(mView.setAmount());
                        order.setTransactionId(mView.setTransactionsId());
                        int updated = mOrderRepository.update(order);
                        if (updated > -1) {
                            Log.i(TAG, "checkout: order: " + order.getEntryId());
                            CheckOutJob.scheduleJob();
                        }
                    });
        }
    }


}
