package com.mobitill.barandrestaurant.jobs;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import com.mobitill.barandrestaurant.ApplicationModule;
import com.mobitill.barandrestaurant.MainApplication;
import com.mobitill.barandrestaurant.data.order.OrderRepository;
import com.mobitill.barandrestaurant.data.orderItem.OrderItemRepository;
import com.mobitill.barandrestaurant.data.orderItem.model.OrderItem;
import com.mobitill.barandrestaurant.data.product.ProductRepository;
import com.mobitill.barandrestaurant.data.request.remotemodels.request.OrderRemoteRequest;
import com.mobitill.barandrestaurant.data.waiter.WaitersRepository;
import com.mobitill.barandrestaurant.utils.Constants;
import com.mobitill.barandrestaurant.utils.schedulers.BaseScheduleProvider;

import javax.inject.Inject;

/**
 * Created by james on 5/27/2017.
 */

public class OrderRequestCheckOutHandler extends HandlerThread{

    public static final String TAG = OrderRequestCheckOutHandler.class.getSimpleName();
    private boolean mHasQuit = false;
    private static final int ORDER_REQUEST = 0;
    private static final int CHECK_REQUEST = 1;
    private Handler mRequestHandler;
    private Handler mResponseHandler;

    @Inject
    public OrderRepository mOrderRepository;
    @Inject
    public OrderItemRepository mOrderItemRepository;
    @Inject
    public WaitersRepository mWaitersRepository;
    @Inject
    public ProductRepository mProductRepository;
    @Inject
    public BaseScheduleProvider mScheduleProvider;

    public interface OrderCheckOutListener{
        void onOrderCheckedOut();
    }

    private OrderCheckOutListener mOrderCheckOutListener;

    public void setOrderCheckoutListener(OrderCheckOutListener orderCheckoutListener){
        mOrderCheckOutListener = orderCheckoutListener;
    }



    public OrderRequestCheckOutHandler() {
        super(TAG);
        DaggerJobsComponent.builder()
                .applicationModule(new ApplicationModule(MainApplication.getAppContext()))
                .build()
                .inject(this);
    }



    @Override
    protected void onLooperPrepared() {
        mRequestHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case ORDER_REQUEST:
                        OrderRemoteRequest orderRemoteRequest =(OrderRemoteRequest) msg.obj;
                        handleRequest(orderRemoteRequest);
                        break;
                    case CHECK_REQUEST:
                         OrderRemoteRequest orderRemoteRequest1 =(OrderRemoteRequest) msg.obj;
                         handleCheckout(orderRemoteRequest1);
                         break;

                }

            }
        };
    }

    public boolean quit(){
        mHasQuit =true;
        return super.quit();
    }

    public void queueRequest(OrderRemoteRequest orderRemoteRequest){
            if(orderRemoteRequest != null){
                    mRequestHandler
                            .obtainMessage(ORDER_REQUEST, orderRemoteRequest)
                            .sendToTarget();

            }
    }

    public void queueCheckout(OrderRemoteRequest orderRemoteRequest){
        if(orderRemoteRequest != null){
            mRequestHandler
                    .obtainMessage(CHECK_REQUEST, orderRemoteRequest)
                    .sendToTarget();
        }
    }

    public void clearQueue(){
        if(mResponseHandler != null){
            mRequestHandler.removeMessages(ORDER_REQUEST);
        }
    }

    private void handleRequest(final OrderRemoteRequest orderRemoteRequest){
        mOrderItemRepository
                .orderRequest(orderRemoteRequest,
                        Constants.RetrofitSource.COUNTERA)
                .subscribeOn(mScheduleProvider.computation())
                .subscribe(orderRemoteResponse -> {
                    if (orderRemoteResponse.getMessage().equals("ok")) {
                        mOrderItemRepository
                                .getOrderItemWithOrderId(String.valueOf(orderRemoteRequest.getOrderId()))
                                .subscribe(orderItems -> {
                                    for (OrderItem orderItem : orderItems) {
                                        orderItem.setSynced(1);
                                        mOrderItemRepository.update(orderItem);
                                    }
                                    mOrderRepository.getOne(String.valueOf(orderRemoteResponse.getOrderId()))
                                            .subscribe(order -> {
                                                order.setSynced(1);
                                                Log.i(TAG, "handleRequest: ");
                                                int updated = mOrderRepository.update(order);
                                                if (updated > -1) {
//                                                    force the next order not synced to be processed
                                                    OrderRequestJob.scheduleJob();
                                                    Log.i(TAG, "handleRequest: " + order.getEntryId() + "Order sent Successfully ");
                                                } else {
                                                    Log.i(TAG, "handleRequest: " + order.getEntryId() + "Order sending failed");
                                                }
                                            });
                                });
                    } else {
                        Log.d(" Order Remote Request", "catch exceptions");
                    }
                });

    }

    private void handleCheckout(final OrderRemoteRequest orderRemoteRequest){
        mOrderItemRepository
                .orderRequest(orderRemoteRequest,
                        Constants.RetrofitSource.COUNTERA)
                .subscribeOn(mScheduleProvider.computation())
                .subscribe(orderRemoteResponse -> {
                    if (orderRemoteResponse.getMessage().equals("ok")) {
                        mOrderItemRepository
                                .getOrderItemWithOrderId(String.valueOf(orderRemoteRequest.getOrderId()))
                                .subscribe(orderItems -> {
                                    for (OrderItem orderItem : orderItems) {
                                        orderItem.setCheckedOut(1);
                                        Log.d("Update orderItem", "set checkout to 1");
                                        mOrderItemRepository.update(orderItem);

                                    }
                                    mOrderRepository.getOne(String.valueOf(orderRemoteResponse.getOrderId()))
                                            .subscribe(order -> {
                                                order.setCheckedOut(1);
                                                int updated = mOrderRepository.update(order);
                                                if (updated > -1) {
                                                    Log.i(TAG, "handleRequest: " + order.getEntryId() + " is checked out");
                                                } else {
                                                    Log.i(TAG, "handleRequest: " + order.getEntryId() + " checkout failed out");
                                                }
                                            });
                                });

                    }

                });
    }

}
