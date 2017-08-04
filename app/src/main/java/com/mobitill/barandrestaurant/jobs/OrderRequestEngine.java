package com.mobitill.barandrestaurant.jobs;

import com.mobitill.barandrestaurant.ApplicationModule;
import com.mobitill.barandrestaurant.MainApplication;
import com.mobitill.barandrestaurant.data.order.OrderRepository;
import com.mobitill.barandrestaurant.data.order.model.Order;
import com.mobitill.barandrestaurant.data.order.source.local.OrderLocalDataSource;
import com.mobitill.barandrestaurant.data.orderItem.OrderItemRepository;
import com.mobitill.barandrestaurant.data.orderItem.model.OrderItem;
import com.mobitill.barandrestaurant.data.product.ProductRepository;
import com.mobitill.barandrestaurant.data.request.remotemodels.request.OrderRemoteItem;
import com.mobitill.barandrestaurant.data.request.remotemodels.request.OrderRemoteRequest;
import com.mobitill.barandrestaurant.data.request.remotemodels.request.OrderRemoteRequestbody;
import com.mobitill.barandrestaurant.data.request.remotemodels.request.OrderRemoteWaiter;
import com.mobitill.barandrestaurant.data.waiter.WaitersRepository;
import com.mobitill.barandrestaurant.utils.schedulers.BaseScheduleProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingQueue;

import javax.inject.Inject;

/**
 * Created by james on 5/26/2017.
 */

public class OrderRequestEngine  {

    private static final String TAG = OrderRequestEngine.class.getSimpleName();

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
    @Inject
    OrderLocalDataSource mOrderLocalDataSource;

    private OrderRequestCheckOutHandler mOrderRequestCheckOutHandler;

    public OrderRequestEngine() {
        DaggerJobsComponent.builder()
                .applicationModule(new ApplicationModule(MainApplication.getAppContext()))
                .build()
                .inject(this);
        mOrderRequestCheckOutHandler = new OrderRequestCheckOutHandler();
        mOrderRequestCheckOutHandler.start();
        mOrderRequestCheckOutHandler.getLooper();
    }

    public void orderRequest() {
        Queue<OrderRemoteRequest> orderRemoteRequestQueue = new LinkedBlockingQueue<>();
        mOrderRepository.getOrdersWithSynced(0)
                .observeOn(mScheduleProvider.io())
                .map(orders -> {
//                        for (Order order : orders) {
//                            orderRemoteRequestQueue.add(OrderRequestEngine.this.getOrderOrderRemoteRequestItem(order));
//                        }
//                        Refactor to retrieve only one order
                    if (orders != null){
                        if (orders.size() > 0){
                            for(Order order: orders){
                                //if(order.getProcessState() == 0){
                                if(mOrderLocalDataSource.getProcessState(order.getEntryId()) == 0){
                                    order.setProcessState(1);
                                    //mOrderRepository.;
                                    mOrderLocalDataSource.updateProcessState(order.getEntryId(),1);
                                    orderRemoteRequestQueue.add(OrderRequestEngine.this.getOrderOrderRemoteRequestItem(order));
                                }
                            }
//                            orderRemoteRequestQueue.add(OrderRequestEngine.this.getOrderOrderRemoteRequestItem(orders.get(0)));
                        }
                    }
                    return orderRemoteRequestQueue;
                })
                .subscribe(orderRemoteRequests -> {
                    for (OrderRemoteRequest orderRemoteRequest : orderRemoteRequests) {
                        mOrderRequestCheckOutHandler.queueRequest(orderRemoteRequest);
                    }
                });
    }

    public void orderRequestA() {
        Queue<OrderRemoteRequest> orderRemoteRequestQueue = new LinkedBlockingQueue<>();
        mOrderRepository.getOrdersWithCounterASynced(0)
                .observeOn(mScheduleProvider.io())
                .map(orders -> {
//                        for (Order order : orders) {
//                            orderRemoteRequestQueue.add(OrderRequestEngine.this.getOrderOrderRemoteRequestItem(order));
//                        }
//                        Refactor to retrieve only one order
                    if (orders != null){
                        if (orders.size() > 0){
                            for(Order order: orders){
                                //if(order.getProcessState() == 0){
                                if(mOrderLocalDataSource.getProcessState(order.getEntryId()) == 0){
                                    order.setProcessState(1);
                                    //mOrderRepository.;
                                    mOrderLocalDataSource.updateProcessState(order.getEntryId(),1);
                                    orderRemoteRequestQueue.add(OrderRequestEngine.this.getOrderOrderRemoteRequestItem(order));
                                }
                            }
//                            orderRemoteRequestQueue.add(OrderRequestEngine.this.getOrderOrderRemoteRequestItem(orders.get(0)));
                        }
                    }
                    return orderRemoteRequestQueue;
                })
                .subscribe(orderRemoteRequests -> {
                    for (OrderRemoteRequest orderRemoteRequest : orderRemoteRequests) {
                        mOrderRequestCheckOutHandler.queueRequest(orderRemoteRequest);
                    }
                });
    }

    public void orderRequestB() {
        Queue<OrderRemoteRequest> orderRemoteRequestQueue = new LinkedBlockingQueue<>();
        mOrderRepository.getOrdersWithCounterBSynced(0)
                .observeOn(mScheduleProvider.io())
                .map(orders -> {
//                        for (Order order : orders) {
//                            orderRemoteRequestQueue.add(OrderRequestEngine.this.getOrderOrderRemoteRequestItem(order));
//                        }
//                        Refactor to retrieve only one order
                    if (orders != null){
                        if (orders.size() > 0){
                            for(Order order: orders){
                                //if(order.getProcessState() == 0){
                                if(mOrderLocalDataSource.getProcessState(order.getEntryId()) == 0){
                                    order.setProcessState(1);
                                    //mOrderRepository.;
                                    mOrderLocalDataSource.updateProcessState(order.getEntryId(),1);
                                    orderRemoteRequestQueue.add(OrderRequestEngine.this.getOrderOrderRemoteRequestItem(order));
                                }
                            }
//                            orderRemoteRequestQueue.add(OrderRequestEngine.this.getOrderOrderRemoteRequestItem(orders.get(0)));
                        }
                    }
                    return orderRemoteRequestQueue;
                })
                .subscribe(orderRemoteRequests -> {
                    for (OrderRemoteRequest orderRemoteRequest : orderRemoteRequests) {
                        mOrderRequestCheckOutHandler.queueRequest(orderRemoteRequest);
                    }
                });
    }

    private OrderRemoteRequest getOrderOrderRemoteRequestItem(Order order) {
        OrderRemoteRequest orderRemoteRequest = new OrderRemoteRequest();
        List<OrderRemoteItem> orderRemoteItems = new ArrayList<>();
        OrderRemoteWaiter orderRemoteWaiter = new OrderRemoteWaiter();
        OrderRemoteRequestbody orderRemoteRequestbody = new OrderRemoteRequestbody();

        // prepare OrderRemoteWaiter
        orderRemoteWaiter.setId(order.getWaiterId());
        mWaitersRepository.getOne(order.getWaiterId())
                .blockingSubscribe(waiter -> orderRemoteWaiter.setName(waiter.getName()));

        // prepare OrderRemoteItem
        mOrderItemRepository
                .getOrderItemWithOrderId(order.getEntryId())
                .blockingSubscribe(orderItems -> {
                    if (!orderItems.isEmpty()) {

                        Map<String, Stack<OrderItem>> orderItemMap = new HashMap<>();

                        for (OrderItem orderItem : orderItems) {
                            if (!orderItemMap.containsKey(orderItem.getProductId())) {
                                Stack<OrderItem> orderItemList = new Stack<>();
                                orderItemList.push(orderItem);
                                orderItemMap.put(orderItem.getProductId(), orderItemList);
                            } else {
                                orderItemMap.get(orderItem.getProductId()).push(orderItem);
                            }
                        }

                        for (Map.Entry<String, Stack<OrderItem>> entry : orderItemMap.entrySet()) {
                            final OrderRemoteItem orderRemoteItem = new OrderRemoteItem();
                            mProductRepository.getOne(entry.getKey())
                                    .subscribe(product -> {
                                        orderRemoteItem.setSubtotal(entry.getValue().size() * Double.valueOf(product.getPrice()).intValue());
                                        orderRemoteItem.setQuantity(entry.getValue().size());
                                        orderRemoteItem.setName(entry.getValue().peek().getProductName());
                                        orderRemoteItem.setId(entry.getValue().peek().getProductId());
                                        orderRemoteItem.setCategory(entry.getValue().peek().getCategory());
                                        orderRemoteItems.add(orderRemoteItem);
                                    });
                        }

                    }
                });

        // prepare orderRemoteRequestbody
        orderRemoteRequestbody.setOrder(orderRemoteItems);
        orderRemoteRequestbody.setWaiter(orderRemoteWaiter);

        // prepare orderRemoteRequest
        orderRemoteRequest.setOrderId(Integer.valueOf(order.getEntryId()));
        orderRemoteRequest.setProductsVersion(0);
        orderRemoteRequest.setRequestbody(orderRemoteRequestbody);
        orderRemoteRequest.setRequestId("181");
        orderRemoteRequest.setRequestname("orderrequest");
//        orderRemoteRequest.setLocation();
        return orderRemoteRequest;
    }


}
