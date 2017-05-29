package com.mobitill.barandrestaurant.jobs;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

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

import java.util.Iterator;

import javax.inject.Inject;

/**
 * Created by james on 5/27/2017.
 */

public class OrderRequestCheckOutHandler extends HandlerThread{

    public static final String TAG = OrderRequestCheckOutHandler.class.getSimpleName();
    private boolean mHasQuit = false;
    private static final int MESSAGE_DOWNLOAD = 0;
    private Handler mHandler;
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



    public OrderRequestCheckOutHandler(Handler responseHandler) {
        super(TAG);
        mRequestHandler = responseHandler;
        DaggerJobsComponent.builder()
                .applicationModule(new ApplicationModule(MainApplication.getAppContext()))
                .build()
                .inject(this);
    }



    @Override
    protected void onLooperPrepared() {
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == MESSAGE_DOWNLOAD){
                    OrderRemoteRequest orderRemoteRequest =(OrderRemoteRequest) msg.obj;
                    handleRequest(orderRemoteRequest);
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
                            .obtainMessage(MESSAGE_DOWNLOAD, orderRemoteRequest)
                            .sendToTarget();
            }
    }

    public void clearQueue(){
        mRequestHandler.removeMessages(MESSAGE_DOWNLOAD);
    }

    private void handleRequest(final OrderRemoteRequest orderRemoteRequest){

        mOrderItemRepository
                .orderRequest(orderRemoteRequest,
                        Constants.RetrofitSource.COUNTERA)
                .subscribeOn(mScheduleProvider.computation())
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                        mOrderItemRepository
                                .getOrderItemWithOrderId(String.valueOf(orderRemoteRequest.getOrderId()))
                                .subscribe(orderItems -> {
                                    Iterator<OrderItem> iterator = orderItems.iterator();
                                    while (orderItems.iterator().hasNext()) {
                                        OrderItem orderItem = iterator.next();
                                        orderItem.setSynced(1);
                                        if (mOrderItemRepository.update(orderItem) > -1) {
                                            iterator.remove();
                                        }
                                    }
                                    mOrderRepository.getOne(String.valueOf(orderRemoteRequest.getOrderId()))
                                            .subscribe(order -> {
                                                order.setSynced(1);
                                                if (mOrderRepository.update(order) > -1) {
                                                    mResponseHandler.post(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            mOrderCheckOutListener.onOrderCheckedOut();
                                                        }
                                                    });
                                                    //orderRemoteRequests.remove();
                                                }
                                            });
                                });
                    }
                });
    }

}
