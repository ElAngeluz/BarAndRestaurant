package com.mobitill.barandrestaurant.receipts_detail;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mobitill.barandrestaurant.data.order.OrderRepository;
import com.mobitill.barandrestaurant.data.product.ProductRepository;

import javax.inject.Inject;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by andronicus on 5/26/2017.
 */

public class ReceiptsDetailPresenter implements ReceiptsDetailContract.Presenter{

    private static final String TAG = ReceiptsDetailPresenter.class.getSimpleName();

    @NonNull
    private final ReceiptsDetailContract.View mView;

    @NonNull
    private final ProductRepository mProductRepository;

    @NonNull
    private final OrderRepository mOrderRepository;

    @NonNull
    private final Context mContext;

    @Inject
    public ReceiptsDetailPresenter(@NonNull ReceiptsDetailContract.View view,
                                   @NonNull ProductRepository productRepository,
                                   @NonNull OrderRepository orderRepository,
                                   @NonNull Context context) {
        mView = checkNotNull(view);
        mProductRepository = productRepository;
        mOrderRepository = orderRepository;
        mContext = context;
    }

    @Inject
    void setupListeners() {
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void getOrder(String orderId) {

        mOrderRepository.getOne(orderId).subscribe(order -> mView.showOrder(order));


    }

    @Override
    public void getOrderItems(String orderId) {

    }
}
