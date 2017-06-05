package com.mobitill.barandrestaurant.receipts_detail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;


import com.mobitill.barandrestaurant.data.order.OrderRepository;
import com.mobitill.barandrestaurant.data.orderItem.OrderItemRepository;
import com.mobitill.barandrestaurant.data.orderItem.model.OrderItem;
import com.mobitill.barandrestaurant.data.product.ProductRepository;
import com.mobitill.barandrestaurant.data.product.models.Product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

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
    private final OrderItemRepository mOrderItemRepository;

    @NonNull
    private final OrderRepository mOrderRepository;


    @NonNull
    private final Context mContext;

    @NonNull
    private CompositeDisposable mDisposable;

    @Inject
    public ReceiptsDetailPresenter(@NonNull ReceiptsDetailContract.View view,
                                   @NonNull ProductRepository productRepository,
                                   @NonNull OrderItemRepository orderItemRepository,
                                   @NonNull OrderRepository orderRepository,
                                   @NonNull Context context) {
        mView = checkNotNull(view);
        mProductRepository = productRepository;
        mOrderRepository = orderRepository;
        mOrderItemRepository = orderItemRepository;
        mContext = context;
        mDisposable = new CompositeDisposable();
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

//        refactored (List<OrderItems> orderItems)

        Disposable disposable = mOrderItemRepository
                .getOrderItemWithOrderId(orderId)
                .subscribe(
                        orderItems -> {
                            mView.showOrderItems(orderItems);
                        },
                        throwable -> Log.e(TAG, "getOrderItems: ", throwable),
                        () -> {
                        });
        mDisposable.add(disposable);
    }

    @Override
    public void getProducts(List<Product> products) {

        mOrderRepository.getAll().subscribe(orders -> mView.showProducts(products));

    }

    @Override
    public Map<String, Stack<OrderItem>> aggregateOrderItems(List<OrderItem> orderItems) {
        Map<String, Stack<OrderItem>> orderItemMap = new HashMap<>();
        // aggregate items into a stack
        for(OrderItem orderItem: orderItems)
            if (orderItemMap.containsKey(orderItem.getProductId())) {
                orderItemMap.get(orderItem.getProductId()).push(orderItem);
            } else {
                Stack<OrderItem> orderItemStack = new Stack<>();
                orderItemStack.push(orderItem);
                orderItemMap.put(orderItem.getProductId(), orderItemStack);
            }

        return orderItemMap;
    }

}
