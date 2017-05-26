package com.mobitill.barandrestaurant.data.request.remotemodels.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderRemoteRequestbody {

    @SerializedName("order")
    @Expose
    private List<OrderRemoteItem> order = null;
    @SerializedName("waiter")
    @Expose
    private OrderRemoteWaiter waiter;

    public List<OrderRemoteItem> getOrder() {
        return order;
    }

    public void setOrder(List<OrderRemoteItem> order) {
        this.order = order;
    }

    public OrderRemoteWaiter getWaiter() {
        return waiter;
    }

    public void setWaiter(OrderRemoteWaiter waiter) {
        this.waiter = waiter;
    }

}